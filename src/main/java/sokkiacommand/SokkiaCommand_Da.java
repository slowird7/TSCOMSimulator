package sokkiacommand;

import ts.TSInterface;

import java.util.regex.Pattern;

/**
 * 器械点座標の設定
 */
public class SokkiaCommand_Da extends SokkiaCommand {

    public SokkiaCommand_Da(TSInterface ts) {
        super(ts);
        commandID = "Da";
        p = Pattern.compile("Da$");
        setTimeout(110);
    }

    public String makeResponse(String args) {
        return String.format("%s %.4f,%.4f,%.4f\n", commandID, tsInterface.getTS().getKikai().getX(), tsInterface.getTS().getKikai().getY(), tsInterface.getTS().getKikai().getZ());
    }

}
