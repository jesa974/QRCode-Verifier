import com.chilkatsoft.CkAsn;
import com.chilkatsoft.CkXml;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import java.security.cert.Certificate;

public class QRCode {

    static {
        try {
            System.loadLibrary("chilkat");
        } catch (UnsatisfiedLinkError e) {
            System.err.println("Native code library failed to load.\n" + e);
            System.exit(1);
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
            SAXException, CertificateException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyStoreException {

        //String filePath = "/home/matthieu/Pictures/2Ddoc.png";
        String filePath = "./res/2Ddoc.png";
        String charset = "UTF-8"; // or "ISO-8859-1"
        Map<EncodeHintType, ErrorCorrectionLevel> hintMap = new HashMap<EncodeHintType, ErrorCorrectionLevel>();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);

        System.out.println("Data read from QR Code: \n"
                + readQRCode(filePath, charset, hintMap));

        String data = readQRCode(filePath, charset, hintMap);

        System.out.println("DC version: "
                + getDCversion(data));

        Integer version = getDCversion(data);

        System.out.println("En-tête: "
                + getEntete(data, version));

        String header = getEntete(data, version);

        System.out.println("AC: "
                + getAC(header));

        String ID_CA = getAC(header);

        System.out.println("ID: "
                + getID(header));

        String ID_Cert = getID(header);

        String xmlFileName = "./res/TSL.xml";

        String URI_CA = findURI(xmlFileName,RefactorPosAC(ID_CA));

        String newURI_CA = SplitStrings(URI_CA);

        System.out.println("newURI: "
                + newURI_CA);

        String[] certURL = newURI_CA.split("[?]");

        System.out.println("certURL: "
                + certURL[0]);

        String myCert = getcert(ID_Cert,certURL[0]);

        System.out.println("Cert: "
                + myCert);

        //X509Certificate test = convertToX509Cert(myCert);

        dlCert(ID_Cert,certURL[0]);

        //System.out.println("MYTEST: " + convertPEM(ID_Cert));

        decodeASN1(ID_Cert);
    }

    // Récupération des données contenues dans le 2D-Doc
    public static String readQRCode(String filePath, String charset, Map hintMap) throws
            IOException,
            NotFoundException
    {


        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(
                new BufferedImageLuminanceSource(
                        ImageIO.read(new FileInputStream(filePath)))));
        Result qrCodeResult = new MultiFormatReader().decode(binaryBitmap, hintMap);
        return qrCodeResult.getText();
    }

    // Récupération de la Version du 2D-Doc
    public static Integer getDCversion(String data) {

        char[] DCversion = new char[4];
        data.getChars(0,4,DCversion,0);

        String result = String.valueOf(DCversion);

        char[] version = new char[2];
        result.getChars(2,4,version,0);

        return Integer.valueOf(String.valueOf(version));
    }

    // Récupération de l'en-tête en fonction de la version du 2D-Doc
    public static String getEntete(String data, int version) {
        if(version == 1 || version == 2) {

            char[] header = new char[22];

            data.getChars(0,22,header,0);

            return String.valueOf(header);
        }
        else if(version == 3) {

            char[] header = new char[24];

            data.getChars(0,24,header,0);

            return String.valueOf(header);
        }
        else if(version == 4) {

            char[] header = new char[26];

            data.getChars(0,26,header,0);

            return String.valueOf(header);
        }
        else {
            return null;
        }
    }

    // Récupération de l'identifiant de l'Autorité de Certification
    public static String getAC(String header) {

        char[] AC = new char[4];

        header.getChars(4,8,AC,0);

        return String.valueOf(AC);
    }

    // Récupération de l'Identifiant du certificat
    public static String getID(String header) {

        char[] ID = new char[4];

        header.getChars(8,12,ID,0);

        return String.valueOf(ID);
    }

    // Transforme l'AC en Position
    public static Integer RefactorPosAC(String AC) {

        char[] num = new char[2];

        AC.getChars(2,4,num,0);

        return Integer.valueOf(String.valueOf(num));
    }

    // Sépare les URLs pour en avoir qu'une
    public static String SplitStrings(String src) {

        Integer newLength = (src.length())/2;

        char[] newStr = new char[newLength];

        src.getChars(0,newLength,newStr,0);

        return String.valueOf(newStr);
    }

    // Téléchargement du .der
    public static void dlCert(String certID, String certURL) throws IOException {
        String myURL = certURL + "?name=" + certID;
        URL url = new URL(myURL);

        try (BufferedInputStream bis = new BufferedInputStream(url.openStream());
             FileOutputStream fos = new FileOutputStream("./res/"+certID+".der")) {
                byte data[] = new byte[1024];
                int byteContent;
                while ((byteContent = bis.read(data, 0, 1024)) != -1) {
                    fos.write(data, 0, byteContent);
                }
        } catch (IOException e) {
            e.printStackTrace(System.out);
        }
    }

    public static String convertPEM(String certID) throws CertificateException, IOException, NoSuchAlgorithmException, UnrecoverableKeyException, KeyStoreException {
        KeyStore keystore = KeyStore.getInstance("JKS");
        InputStream stream = new FileInputStream("./res/"+certID+".der");
        keystore.load(stream, null);
        PrivateKey key = (PrivateKey) keystore.getKey("mykey", "password".toCharArray());
        byte[] prvkey = key.getEncoded();
        String encoded = Base64.getEncoder().encodeToString(prvkey);
        String key_pem = "-----BEGIN PRIVATE KEY-----" + encoded + "-----END PRIVATE KEY-----";
        return key_pem;
    }

    public static void decodeASN1(String certID) {

        CkAsn asn = new CkAsn();

        boolean success;

        //  Begin with loading ASN.1 from a binary DER/BER format file.
        success = asn.LoadBinaryFile("./res/"+certID+".der");
        if (success != true) {
            System.out.println(asn.lastErrorText());
            return;
        }

        //  Convert ASN.1 to XML:
        String strXml = asn.asnToXml();
        if (asn.get_LastMethodSuccess() != true) {
            System.out.println(asn.lastErrorText());
            return;
        }

        //  The XML returned by AsnToXml will be compact and not pretty-formatted.
        //  Use Chilkat XML to format the XML better:
        CkXml xml = new CkXml();
        success = xml.LoadXml(strXml);
        //  Assuming success for this example..
        //  This is formatted better for human viewing:
        System.out.println(xml.getXml());

        //  Now get the ASN.1 in base64 format.  Any encoding supported
        //  by Chilkat can be passed, such as "hex", "uu", "quoted-printable", "base32", "modbase64", etc.
        String strBase64 = asn.getEncodedDer("base64");

        //  Load the ASN.1 from XML:
        CkAsn asn2 = new CkAsn();
        success = asn2.LoadAsnXml(xml.getXml());
        if (success != true) {
            System.out.println(asn2.lastErrorText());
            return;
        }

        //  Load the ASN.1 from an encoded string, such as base64:
        CkAsn asn3 = new CkAsn();
        success = asn3.LoadEncoded(strBase64,"base64");
        if (success != true) {
            System.out.println(asn3.lastErrorText());
            return;
        }
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


    // Récupération du .der en String
    public static String getcert(String certID, String certURL) throws IOException {
        String myURL = certURL + "?name=" + certID;
        URL url = new URL(myURL);
        //URL url = new URL("http://certificates.certigna.fr/search.php?name=CNO0");

        /* Ouvre une connection avec l'object URL */
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        //GET method
        connection.setRequestMethod("GET");

        /* Utilise BufferedReader pour lire ligne par ligne */
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        //La ligne courante
        String inputLine;

        //Le contenu de la reponse GET
        StringBuffer content = new StringBuffer();

        /* Pour chaque ligne dans la reponse GET */
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }

        //Ferme BufferedReader
        in.close();
        return content.toString();
    }

    // Récupération des données du fichier XML et complétion de la classe TSP
    public static String findURI(String filename, Integer pos) throws
            IOException,
            SAXException,
            ParserConfigurationException
    {
        File xmlFile = new File(filename);

        // Defines a factory API that enables
        // applications to obtain a parser that produces
        // DOM object trees from XML documents.
        DocumentBuilderFactory dbf
                = DocumentBuilderFactory.newInstance();

        // we are creating an object of builder to parse
        // the  xml file.
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(xmlFile);

        /*here normalize method Puts all Text nodes in
        the full depth of the sub-tree underneath this
        Node, including attribute nodes, into a "normal"
        form where only structure separates
        Text nodes, i.e., there are neither adjacent
        Text nodes nor empty Text nodes. */
        doc.getDocumentElement().normalize();
        System.out.println(
                "Root element: "
                        + doc.getDocumentElement().getNodeName());

        // Here nodeList contains all the nodes with
        // name geek.
        NodeList nodeList
                = doc.getElementsByTagName("tsl:TrustServiceStatusList");

        // Iterate through all the nodes in NodeList
        // using for loop.
        for (int i = 0; i < nodeList.getLength(); ++i) {
            Node node = nodeList.item(i);
            System.out.println("\nNode Name :"
                    + node.getNodeName());
            if (node.getNodeType()
                    == Node.ELEMENT_NODE) {
                Element tElement = (Element)node;
                System.out.println(
                        "URI: "
                                + tElement
                                .getElementsByTagName("tsl:TSPInformationURI")
                                .item(pos-1)
                                .getTextContent());
                return tElement
                        .getElementsByTagName("tsl:TSPInformationURI")
                        .item(pos-1)
                        .getTextContent();
            }
        }

        return null;
    }




}