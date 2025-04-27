package sokkiacommand;

import connection.ConnectionMode;
import ts.TS;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 「モーター駆動の設定」
 */
public class SokkiaCommand_PA_in extends SokkiaCommand {

    public SokkiaCommand_PA_in() {
        commandID = "*/PA";
        p = Pattern.compile("^\\*/PA ([012]),([01]),([0-9.]+),([0-9.]+),,,$");
    }

    public String makeResponse(String args) {
        int searchMode;
        int trackingMode;
        double HSearchRange, VSearchRange;
        Matcher mm = p.matcher(args);
        if (!mm.matches()) {
            return "\u0015";
        }
        try {
            searchMode = Integer.parseInt(mm.group(1));
        } catch (NumberFormatException ex) {
            return "\u0015";
        }
        try {
            trackingMode = Integer.parseInt(mm.group(2));
        } catch (NumberFormatException ex) {
            return "\u0015";
        }
        try {
            HSearchRange = Double.parseDouble(mm.group(3));
        } catch (NumberFormatException ex) {
            return "\u0015";
        }
        try {
            VSearchRange = Double.parseDouble(mm.group(3));
        } catch (NumberFormatException ex) {
            return "\u0015";
        }
        TS.getInstance().setSearchMode(searchMode);
        TS.getInstance().setTrackingMode(trackingMode);
        TS.getInstance().setHSearchRange(HSearchRange);
        TS.getInstance().setVSearchRange(VSearchRange);
        return "\u0006";
    }

}
