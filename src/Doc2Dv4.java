import com.google.zxing.EncodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.io.IOException;
import java.util.Map;

public class Doc2Dv4 extends Doc2Dv3{

    private String docCountry = null;

    public Doc2Dv4(String Doc2DPath, Map<EncodeHintType, ErrorCorrectionLevel> hintMap) throws IOException, NotFoundException, InitException {
        super(Doc2DPath, hintMap);

        setDocCountry();
    }

    @Override
    protected short checkVersion() throws InitException {
        if(this.getDocVersion() == 4)
            return 26; //Header Size
        else
            throw new InitException("Wrong class, must use version: " + this.getDocVersion());
    }

    public String getDocCountry() {
        return docCountry;
    }

    public void setDocCountry() {
        char[] docCountry = new char[2];
        this.getDatas().getChars(24,26,docCountry,0);
        this.docCountry = String.valueOf(docCountry);
    }
}
