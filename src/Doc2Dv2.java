import com.google.zxing.EncodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.io.IOException;
import java.util.Map;

public class Doc2Dv2 extends Doc2Dv1{
    public Doc2Dv2(String Doc2DPath, Map<EncodeHintType, ErrorCorrectionLevel> hintMap) throws IOException, NotFoundException, InitException {
        super(Doc2DPath, hintMap);
    }

    @Override
    protected short checkVersion() throws InitException {
        if(this.getDocVersion() == 2)
            return 22; //Header Size
        else
            throw new InitException("Wrong class, must use version: " + this.getDocVersion());
    }
}
