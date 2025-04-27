package sokkiacommand;

import ts.TS;

import java.util.regex.Pattern;

/**
 * 器械点座標の設定
 */
public class SokkiaCommand_Da extends SokkiaCommand {

    public SokkiaCommand_Da() {
        commandID = "Da";
        p = Pattern.compile("Da$");
        setTimeout(110);
    }

    public String makeResponse(String args) {
        return String.format("%s %.4f,%.4f,%.4f\n", commandID, TS.getInstance().getKikai().getX(), TS.getInstance().getKikai().getY(), TS.getInstance().getKikai().getZ());
    }

}
