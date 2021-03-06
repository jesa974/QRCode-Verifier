import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Doc2Dv1 {
    private String rawDatas = null;

    ///////////////////////////
    //////////HEADER///////////
    ///////////////////////////
    private String rawHeader = null;
    private String docMarker = null;
    private Integer docVersion = null;
    private String caID = null;
    private String certHeader = null;
    private String certID = null;
    private String certEmmissionDate = null;
    private String certSignCreationDate = null;
    private String docType = null;

    ///////////////////////////
    //////////PAYLOAD//////////
    ///////////////////////////
    private String rawMessage = null;

    private List<Data> messages = null;

    ///////////////////////////
    /////////SIGNATURE/////////
    ///////////////////////////
    private String rawSignature = null;


    //Constructor
    public Doc2Dv1(String Doc2DPath, Map<EncodeHintType, ErrorCorrectionLevel> hintMap) throws IOException, NotFoundException, InitException {
        this.rawDatas = read2DDoc(Doc2DPath,hintMap);

        setDocMarker();
        setDocVersion();
        short headerSize = checkVersion();
        char[] header = new char[headerSize];
        this.rawDatas.getChars(0,headerSize,header,0);
        this.rawHeader = String.valueOf(header);
        setCaID();
        setCertID();
        setCertEmmissionDate();
        setCertSignCreationDate();
        setDocType();

        DocDatas docDatas = new DocDatas();
        List<Data> messages = new ArrayList();
        for (int i = headerSize; this.rawDatas.getBytes(StandardCharsets.UTF_8)[i] != 31; i++) {
            char[] rawId = new char[2];
            this.rawDatas.getChars(i,i+2,rawId,0);

            Data data = new Data();
            data.id = String.valueOf(rawId);

            DocDatas.DataStruct dataStruct = docDatas.datas.get(data.id);

            if(dataStruct == null)
                throw new InitException("Unkown msg id: "+(int) this.rawDatas.getBytes(StandardCharsets.UTF_8)[i]);


            data.name = dataStruct.name;

            String value = "";
            for (int j = 0; j < dataStruct.maxSize; j++) {
                if(this.rawDatas.getBytes(StandardCharsets.UTF_8)[i+2+j] == 29 || this.rawDatas.getBytes(StandardCharsets.UTF_8)[i+2+j] == 30){
                    i = i+2+j;
                    break;
                }
                value += (char) this.rawDatas.getBytes(StandardCharsets.UTF_8)[i+2+j];
                if(j == dataStruct.maxSize -1)
                    i = i+dataStruct.maxSize+1;
            }
            data.value = value;

            messages.add(data);

            if(this.rawDatas.getBytes(StandardCharsets.UTF_8)[i+1] == 31){
                char[] rawMessage = new char[i+1-headerSize];
                this.rawDatas.getChars(headerSize,i+1,rawMessage,0);
                this.rawMessage = String.valueOf(rawMessage);

                char[] rawSignature = new char[rawDatas.getBytes(StandardCharsets.UTF_8).length -i-2];
                this.rawDatas.getChars(i+2,rawDatas.getBytes(StandardCharsets.UTF_8).length,rawSignature,0);
                this.rawSignature = String.valueOf(rawSignature);
            }
        }
        this.messages = messages;

    }

    protected short checkVersion() throws InitException{
        if(this.docVersion == 1)
                return 22; //Header Size
        else
            throw new InitException("Wrong class, must use version: " + this.docVersion);
    }

    protected void setDocMarker(){
        char[] marker = new char[2];
        this.rawDatas.getChars(0,2,marker,0);
        this.docMarker = String.valueOf(marker);
    }

    protected void setDocVersion(){
        char[] version = new char[2];
        this.rawDatas.getChars(2,4,version,0);
        this.docVersion = Integer.valueOf(String.valueOf(version));
    }

    protected void setCaID(){
        char[] caID = new char[4];
        this.rawDatas.getChars(4,8,caID,0);
        this.caID = String.valueOf(caID);
    }

    protected void setCertID() {
        char[] certID = new char[4];
        this.rawDatas.getChars(8,12,certID,0);
        this.certID = String.valueOf(certID);
    }

    protected void setCertEmmissionDate() {
        char[] certEmmissionDate = new char[4];
        this.rawDatas.getChars(12,16,certEmmissionDate,0);
        this.certEmmissionDate = String.valueOf(certEmmissionDate);
    }

    protected void setCertSignCreationDate() {
        char[] certSignCreationDate = new char[4];
        this.rawDatas.getChars(16,20,certSignCreationDate,0);
        this.certSignCreationDate = String.valueOf(certSignCreationDate);
    }

    protected void setDocType(){
        char[] docType = new char[2];
        this.rawDatas.getChars(20,22,docType,0);
        this.docType = String.valueOf(docType);
    }

    // R??cup??ration des donn??es contenues dans le 2D-Doc
    private String read2DDoc(String filePath, Map hintMap) throws IOException, NotFoundException {
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(
                new BufferedImageLuminanceSource(
                        ImageIO.read(new FileInputStream(filePath))
                )
        ));
        Result qrCodeResult = new MultiFormatReader().decode(binaryBitmap, hintMap);
        return qrCodeResult.getText();
    }

    public String getDocTypeDesc() {
        switch (this.docType){
            case "00":
                return "Justificatif de domicile - Document  ??mis  sp??cifiquement  pour servir de justificatif de domicile.";
            case "01":
                return "Justificatif de domicile - Factures de fournisseur d?????nergie | Factures de t??l??phonie| Factures defournisseur d???acc??s internet | Factures de fournisseur d???eau";
            case "02":
                return "Justificatif de domicile - Avis de taxe d???habitation";
            case "03":
                return "Justificatif  de  domiciliation bancaire - Relev?? d???identit?? bancaire";
            case "05":
                return "Justificatif  de  domiciliation bancaire - Relev?? d???Identit?? SEPAmail";
            case "04":
                return "Justificatif de ressources - Avis d???imp??t sur le revenu";
            case "06":
                return "Justificatif de ressources - Bulletin de salaire";
            case "11":
                return "Justificatif de ressources - Relev?? de compte";
            case "07":
                return "Justificatif d???identit?? - Titre d???identit??";
            case "08":
                return "Justificatif d???identit?? - MRZ";
            case "13":
                return "Justificatif d???identit?? - Document ??tranger";
            case "09":
                return "Justificatif fiscal - Facture ??tendue";
            case "10":
                return "Justificatif d???emploi - Contrat de travail";
            case "A0":
                return "Justificatif ??cologique de v??hicule - Certificat de qualit?? de l???air";
            case "A7":
                return "Justificatif ??cologique de v??hicule - Certificat de qualit?? de l???air (V2)";
            case "A1":
                return "Justificatif permis de conduire - Courrier Permis ?? Points";
            case "A2":
                return "Justificatif de sant?? - Carte Mobilit?? Inclusion (CMI)";
            case "A3":
                return "Justificatif d???activit?? - Macaron  VTC (V??hicule  de  Transport avec Chauffeur)";
            case "A5":
                return "Justificatif d???activit?? - Carte T3P(Transport Public Particulier de Personnes)";
            case "A6":
                return "Justificatif d???activit?? - Carte Professionnelle Sapeur-Pompier";
            case "A4":
                return "Justificatif m??dical - Certificat de d??c??s";
            case "B0":
                return "Justificatif acad??mique - Dipl??me";
            case "B1":
                return "Justificatif acad??mique - Attestation de Versement de la Contribution ?? la Vie Etudiante";
            case "12":
                return "Justificatifjuridique/judiciaire - Acte d???huissier";
            case "A8":
                return "Certificat d???immatriculation - Certificat de cession ??lectronique";
            default:
                return "Document Inconnu (ID:'"+this.docType+"')";
        }
    }

    public String getDatas() {
        return rawDatas;
    }

    public String getRawHeader() {
        return rawHeader;
    }

    public String getRawMessage() { return rawMessage; }

    public String getRawSignature() {
        return rawSignature;
    }

    public String getCaID() {
        return caID;
    }

    public String getDocMarker() { return docMarker; }

    public Integer getDocVersion() {
        return docVersion;
    }

    public String getCertID() { return certID; }

    public String getCertEmmissionDate() {
        return certEmmissionDate;
    }

    public String getCertSignCreationDate() {
        return certSignCreationDate;
    }

    public String getDocType() {
        return docType;
    }

    public List<Data> getMessages() {
        return messages;
    }
}
