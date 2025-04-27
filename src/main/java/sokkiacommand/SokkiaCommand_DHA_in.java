package sokkiacommand;

import connection.ConnectionMode;
import point.AngleData;
import ts.TS;

import java.util.regex.Pattern;

/**
 * 座標のステークアウトデータの設定
 */
public class SokkiaCommand_DHA_in extends SokkiaCommand {
    public SokkiaCommand_DHA_in() {

        commandID = "*DHA";
        p = Pattern.compile("^\\*DHA(\\d{7})VA(\\d{7})");
    }

    @Override
    public String makeResponse(String args) {
        m = p.matcher(args);
        if (!m.matches()) {
            return "\u0015";
        }
        double horizontalAngleDMS = Integer.parseInt(m.group(1)) / 10000.;
        double verticalAngleDMS = Integer.parseInt(m.group(2)) / 10000.;
        TS.getInstance().updateCurrent(horizontalAngleDMS, verticalAngleDMS);
        return "\u0006\nOK\n";
    }


}
