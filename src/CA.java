import com.google.zxing.NotFoundException;
import org.bouncycastle.asn1.ASN1InputStream;
import org.bouncycastle.asn1.DERIA5String;
import org.bouncycastle.asn1.DERObject;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.x509.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class CA {
    private X509Certificate caCert = null;
    private List<String> caRepoUrls = null;


    public CA(String caID, String TSLXmlPath) throws ParserConfigurationException, IOException, SAXException, CertificateException, InitException {
        File xmlFile = new File(TSLXmlPath);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(xmlFile);
        doc.getDocumentElement().normalize();

        NodeList nodeList = doc.getElementsByTagName("tsl:TrustServiceProvider");
        //Loop over trust services (ex: FR01, FR02, ...)
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                //Get the current trust service
                Element trustService = (Element) node;

                //Get CA names
                Element tspTradeName = (Element) trustService.getElementsByTagName("tsl:TSPTradeName").item(0);
                String enName = tspTradeName.getElementsByTagName("tsl:Name").item(0).getTextContent();
                String frName = tspTradeName.getElementsByTagName("tsl:Name").item(1).getTextContent();

                //test if it correspond to CA who've signed
                if(caID.equals(enName) || caID.equals(frName)) {

                    Element tspInformationURI = (Element) trustService.getElementsByTagName("tsl:TSPInformationURI").item(0);

                    //Get certificate
                    this.caCert = convertToX509Cert(trustService.getElementsByTagName("tsl:X509Certificate").item(0).getTextContent());

                    //Get urls to CA certficate
                    List<String> urls = new ArrayList<String>();
                    for(int j=0;j<tspInformationURI.getElementsByTagName("tsl:URI").getLength(); j++){
                        urls.add(tspInformationURI.getElementsByTagName("tsl:URI").item(j).getTextContent().split("\\?")[0]);
                    }

                    this.caRepoUrls = urls;
                }
            }
        }

        if(this.caCert == null || this.caRepoUrls == null)
            throw new InitException("CA id not found in TSL");


    }

    public static X509Certificate convertToX509Cert(String certificateString) throws CertificateException {
        X509Certificate certificate = null;
        CertificateFactory cf = null;
        try {
            if (certificateString != null && !certificateString.trim().isEmpty()) {
                certificateString = certificateString.replace("-----BEGIN CERTIFICATE-----\n", "")
                        .replace("-----END CERTIFICATE-----", ""); // NEED FOR PEM FORMAT CERT STRING
                byte[] certificateData = Base64.getMimeDecoder().decode(certificateString);
                cf = CertificateFactory.getInstance("X509");
                certificate = (X509Certificate) cf.generateCertificate(new ByteArrayInputStream(certificateData));
            }
        } catch (CertificateException e) {
            throw new CertificateException(e);
        }
        return certificate;
    }

    public boolean isRevoked(X509Certificate issuerCertificate) throws IOException, CertificateException, CRLException {

        X509CRLEntry revokedCertificate = null;
        CertificateFactory cf = CertificateFactory.getInstance("X509");
        X509CRL crl = null;

        if(this.caCert.getExtensionValue(X509Extensions.CRLDistributionPoints.getId()) == null)
            throw new CRLException("Point de distrution CRL non présent dans le cert");
        ASN1InputStream oAsnInStream = new ASN1InputStream(new ByteArrayInputStream(this.caCert.getExtensionValue(X509Extensions.CRLDistributionPoints.getId())));
        DERObject derObjCrlDP = oAsnInStream.readObject();
        DEROctetString dosCrlDP = (DEROctetString) derObjCrlDP;
        byte[] crldpExtOctets = dosCrlDP.getOctets();
        ASN1InputStream oAsnInStream2 = new ASN1InputStream(new ByteArrayInputStream(crldpExtOctets));
        DERObject derObj2 = oAsnInStream2.readObject();
        CRLDistPoint distPoint = CRLDistPoint.getInstance(derObj2);

        List<String> crlUrls = new ArrayList<String>();
        for (DistributionPoint dp : distPoint.getDistributionPoints()) {
            DistributionPointName dpn = dp.getDistributionPoint();
            // Look for URIs in fullName
            if (dpn != null) {
                if (dpn.getType() == DistributionPointName.FULL_NAME) {
                    GeneralName[] genNames = GeneralNames.getInstance(
                            dpn.getName()).getNames();
                    // Look for an URI
                    for (int j = 0; j < genNames.length; j++) {
                        if (genNames[j].getTagNo() == GeneralName.uniformResourceIdentifier) {
                            String url = DERIA5String.getInstance(
                                    genNames[j].getName()).getString();
                            crlUrls.add(url);
                        }
                    }
                }
            }
        }

        try{
            URL url = new URL(crlUrls.get(0));

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            try(DataInputStream inStream = new DataInputStream(connection.getInputStream())){
                crl = (X509CRL)cf.generateCRL(inStream);
            }
        }catch (IOException e){
            URL url = new URL(crlUrls.get(1));
            System.out.println(url.toString());

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            try(DataInputStream inStream = new DataInputStream(connection.getInputStream())){
                crl = (X509CRL)cf.generateCRL(inStream);
            }
        }

        revokedCertificate = crl.getRevokedCertificate(issuerCertificate.getSerialNumber());

        if(revokedCertificate !=null){
            return true;
        }
        else{
            try{
                issuerCertificate.checkValidity();
                return false;
            }catch (CertificateExpiredException | CertificateNotYetValidException e){
                return true;
            }
        }
    }

    public static boolean checkValidity(X509Certificate certificate){
        try{
            certificate.checkValidity();
            return true;
        } catch (CertificateNotYetValidException | CertificateExpiredException e) {
            return false;
        }
    }

    public X509Certificate getCaCert() {
        return caCert;
    }

    public List<String> getCaRepoUrls() {
        return caRepoUrls;
    }
}
