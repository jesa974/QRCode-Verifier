import com.chilkatsoft.CkAsn;
import com.chilkatsoft.CkXml;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.apache.commons.codec.binary.Base32;
import org.bouncycastle.asn1.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import org.bouncycastle.asn1.x509.CRLDistPoint;
import org.bouncycastle.asn1.x509.DistributionPoint;
import org.bouncycastle.asn1.x509.DistributionPointName;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.GeneralNames;
import org.bouncycastle.asn1.x509.X509Extensions;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.*;
import java.util.*;

import static java.lang.System.exit;

public class QRCode {

    static {
        try {
            System.loadLibrary("chilkat");
        } catch (UnsatisfiedLinkError e) {
            System.err.println("Native code library failed to load.\n" + e);
            exit(1);
        }
    }

    /**
     *
     * @param args
     * @throws WriterException
     * @throws IOException
     * @throws NotFoundException
     */
    public static void main(String[] args) throws
            IOException,
            NotFoundException,
            ParserConfigurationException,
            SAXException, CertificateException, NoSuchAlgorithmException, CRLException, InvalidKeyException, SignatureException, InitException {

        /** NOTE : Code basé sur les 'Spécifications techniques des codes à barres 2D-Doc' Version: 3.1.1 de l'ANTS **/

        String filePath = "./res/2Ddoc.png";
        Map<EncodeHintType, ErrorCorrectionLevel> hintMap = new HashMap<>();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

        Doc2Dv1 doc;
        try {
            doc = new Doc2Dv1(filePath, hintMap);

        }catch (InitException err1){
            try {
                System.out.println(err1);
                doc = new Doc2Dv2(filePath, hintMap);

            }catch (InitException err2){
                try {
                    System.out.println(err2);
                    doc = new Doc2Dv3(filePath, hintMap);

                }catch (InitException err3){
                    System.out.println(err3);
                    doc = new Doc2Dv4(filePath, hintMap);



                }
            }
        }

        //Retrieve CA trusted list
        String xmlFileName = "./res/TSL.xml";
        CA ca = new CA(doc.getCaID(),xmlFileName);

        //Check if the CA cert is revoked
        System.out.println("Is CA revoked (CRL + Validity period) ? : " + CA.isRevoked(ca.getCaCert()));

        //Get from CA repository the certificate that have signed the document
        X509Certificate participantCert = getcert(doc.getCertID(),ca.getCaRepoUrls().get(0));
        //Check if he is revoked
        System.out.println("Is Participant revoked (CRL + Validity period) ? : " + CA.isRevoked(participantCert));

        Base32 base32 = new Base32();
        byte[] signature = base32.decode(doc.getRawSignature());
        Signature sign = Signature.getInstance("SHA256withECDSA");
        sign.initVerify(participantCert.getPublicKey());
        sign.update((doc.getRawHeader()+doc.getRawMessage()).getBytes(StandardCharsets.UTF_8));
        boolean isSignatureOk = sign.verify(toDerSignature(signature));
        System.out.println("Le 2D Doc est intègre ? : " + isSignatureOk);

        if(isSignatureOk == true){
            System.out.println("\nComparer les informations suivantes au document papier:");
            System.out.println("Type de document: " + doc.getDocTypeDesc());
            System.out.println("Informations: ");
            for (Data message : doc.getMessages()) {
                System.out.println(message.toString());
            }
        }
    }

    private static byte[] toDerSignature(byte[] jwsSig) throws IOException {

        byte[] rBytes = Arrays.copyOfRange(jwsSig, 0, 32);
        byte[] sBytes = Arrays.copyOfRange(jwsSig, 32, 64);

        BigInteger r = new BigInteger(1, rBytes);
        BigInteger s = new BigInteger(1, sBytes);

        DERSequence sequence = new DERSequence(new ASN1Encodable[] {
                new ASN1Integer(r),
                new ASN1Integer(s)
        });

        return sequence.getDEREncoded();
    }

    // Récupération du .der en String
    public static X509Certificate getcert(String certID, String certURL) throws IOException, CertificateException {
        String myURL = certURL + "?name=" + certID;
        URL url = new URL(myURL);

        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        X509Certificate cert;
        //URL url = new URL("http://certificates.certigna.fr/search.php?name=CNO0");

        /* Ouvre une connection avec l'object URL */
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try(DataInputStream inStream = new DataInputStream(connection.getInputStream())) {
            cert = (X509Certificate) cf.generateCertificate(inStream);
        }

        return cert;
    }
}