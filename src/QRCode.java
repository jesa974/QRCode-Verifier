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

import com.spire.pdf.PdfDocument;
import com.spire.pdf.PdfPageBase;
import java.util.ArrayList;

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

import com.helger.xmldsig.keyselect.X509KeySelector;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import javax.xml.crypto.dsig.XMLSignature;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.dom.DOMValidateContext;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.FileInputStream;
import java.nio.file.Files;

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

        /** NOTE : Code bas?? sur les 'Sp??cifications techniques des codes ?? barres 2D-Doc' Version: 3.1.1 de l'ANTS **/

        String filePath = "./res/bouyguestelecomfacture2DDOC.jpg";
        Map<EncodeHintType, ErrorCorrectionLevel> hintMap = new HashMap<>();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

        /***
         * 2D-Doc
         */
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

        /**
         * CA
         */
        //Retrieve CA trusted list
        String xmlFileName = "./res/TSL.xml";
        CA ca = new CA(doc.getCaID(),xmlFileName);
        System.out.println("CA name: " + doc.getCaID());

        //Check if the CA cert is revoked
        try{
            System.out.println("Is CA revoked (CRL + Validity period) ? : " + ca.isRevoked(ca.getCaCert())); //Self signed
        }catch (CRLException e){
            System.out.println("Note: CRL Distribution Point not present in CA cert, not able to verify if revoked.");
            System.out.println("Is CA validity period still valid ? : " + CA.checkValidity(ca.getCaCert()));
        }


        //Get from CA repository the certificate that have signed the document
        X509Certificate participantCert = getcert(doc.getCertID(),ca.getCaRepoUrls().get(0));
        System.out.println("Participant name: " + doc.getCertID());

        //Check if participant cert is revoked
        try{
            System.out.println("Is Participant revoked (CRL + Validity period) ? : " + ca.isRevoked(participantCert));
        }catch (CRLException e){
            System.out.println("Note: CRL Distribution Point not present in participant cert, not able to verify if revoked.");
            System.out.println("Is participant validity period still valid ? : " + CA.checkValidity(participantCert));
        }

        Base32 base32 = new Base32();
        byte[] signature = base32.decode(doc.getRawSignature());
        Signature sign = Signature.getInstance("SHA256withECDSA");
        sign.initVerify(participantCert.getPublicKey());
        sign.update((doc.getRawHeader()+doc.getRawMessage()).getBytes(StandardCharsets.UTF_8));
        boolean isSignatureOk = sign.verify(toDerSignature(signature));
        System.out.println("Le 2D Doc est int??gre ? : " + isSignatureOk);

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

    // R??cup??ration du .der en String
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
    
    // R??cup??ration du 2D Doc en PNG
    public static void extractImgFromPDF(String pathPDF) throws IOException {
        //Load the PDF File
        PdfDocument PDFdoc = new PdfDocument();
        PDFdoc.loadFromFile(pathPDF);
        StringBuilder buffer = new StringBuilder();
        ArrayList<BufferedImage> images = new ArrayList < BufferedImage > ();
        //loop through the pages
        if (PDFdoc.getPages() != null) {
            int cpt = 0;
            for (PdfPageBase page: (Iterable < PdfPageBase > ) PDFdoc.getPages()) {
                //extract images from a particular page
                if (page.extractImages() != null) {
                    //System.out.println("Nombre d'images: " + page.extractImages().length);

                    for (BufferedImage image : page.extractImages()) {
                        //declare an int variable
                        int index = 0;
                        //specify the file path and name
                        File output = new File("output/" + String.format("Image_%d.png", index++));
                        //save image as .png file
                        ImageIO.write(image, "PNG", output);
                    }
                } //else
                //System.out.println("No image");
                break;
            }
        }
        //else
        //System.out.println("No page");
    }
    
    // V??rification de la signature de la TSL
    private static void VerifyTSLSign(String SignTSL, X509Certificate TSLCert, String message) throws
            Exception {

        /*
        // M??thode 1
        XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        DocumentBuilder builder = null;

        try {
            builder = dbf.newDocumentBuilder();
        } catch (Exception e) {
            e.printStackTrace();
        }

        File myXMLFile = new File("./res/TSL.xml");
        byte[] ByteXML = Files.readAllBytes(myXMLFile.toPath());

        Document doc = builder.parse(new ByteArrayInputStream(ByteXML));

        // Find Signature element
        NodeList nl = doc.getElementsByTagNameNS(XMLSignature.XMLNS, "Signature");
        if (nl.getLength() == 0) {
            throw new Exception("Cannot find Signature element");
        }

        DOMValidateContext valContext = new DOMValidateContext(new X509KeySelector(), nl.item(0));

        // Unmarshal the XMLSignature.
        XMLSignature signature = fac.unmarshalXMLSignature(valContext);

        boolean isSignatureOk = signature.validate(valContext);

        System.out.println("La TSL est int??gre ? : " + isSignatureOk);
        */

        // M??thode 2
        Signature sign = Signature.getInstance("SHA256withRSA");
        sign.initVerify(TSLCert.getPublicKey());
        File myXMLFile = new File("./res/TSL.xml");
        byte[] ByteXML = Files.readAllBytes(myXMLFile.toPath());
        String XML = new String(ByteXML);
        String data = XML.substring(XML.indexOf("<tsl:SchemeInformation>"), XML.indexOf("</tsl:TrustServiceProviderList>"));
        sign.update(data.getBytes());
        boolean isSignatureOk = sign.verify(SignTSL.getBytes());
        System.out.println("La TSL est int??gre ? : " + isSignatureOk);


        /*
        // M??thode 3
        Signature sign = Signature.getInstance("SHA256withRSA");
        sign.initVerify(TSLCert.getPublicKey());
        boolean isSignatureOk = sign.verify(SignTSL.getBytes());
        //boolean isSignatureOk = sign.verify(toDerSignature(SignTSL.getBytes()));
        System.out.println("La TSL est int??gre ? : " + isSignatureOk);
         */

        /*
        // M??thode 4
        // Find Signature element.
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        Document doc = dbf.newDocumentBuilder().parse(new FileInputStream("./res/TSL.xml"));

        NodeList nl = doc.getElementsByTagNameNS(XMLSignature.XMLNS, "Signature");
        if (nl.getLength() == 0) {
            throw new Exception("Cannot find Signature element");
        }

        // Create a DOMValidateContext and specify a KeySelector and document context.
        DOMValidateContext valContext = new DOMValidateContext(new X509KeySelector(), nl.item(0));

        // Unmarshal the XMLSignature.
        XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM");
        XMLSignature signature = fac.unmarshalXMLSignature(valContext);

        // Validate the XMLSignature.
        boolean coreValidity = signature.validate(valContext);
         */

    }
}
