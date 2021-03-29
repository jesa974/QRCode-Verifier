import com.google.zxing.EncodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.io.IOException;
import java.util.Map;

public class Doc2Dv3 extends Doc2Dv2{

    private String docPerimeter = null;

    public Doc2Dv3(String Doc2DPath, Map<EncodeHintType, ErrorCorrectionLevel> hintMap) throws IOException, NotFoundException, InitException {
        super(Doc2DPath, hintMap);

        setDocPerimeter();
    }

    @Override
    protected short checkVersion() throws InitException {
        if(this.getDocVersion() == 3)
            return 24; //Header Size
        else
            throw new InitException("Wrong class, must use version: " + this.getDocVersion());
    }

    public String getDocPerimeter() {
        return docPerimeter;
    }

    public void setDocPerimeter() {
        char[] docPerimeter = new char[2];
        this.getDatas().getChars(22,24,docPerimeter,0);
        this.docPerimeter = String.valueOf(docPerimeter);
    }
}
