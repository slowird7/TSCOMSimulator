package sokkiacommand;

import ts.TS;
import ts.TSInterface;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 「モーター駆動の設定」
 */
public class SokkiaCommand_PA_in extends SokkiaCommand {

    public SokkiaCommand_PA_in(TSInterface ts) {
        super(ts);
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
        tsInterface.getTS().setSearchMode(searchMode);
        tsInterface.getTS().setTrackingMode(trackingMode);
        tsInterface.getTS().setHSearchRange(HSearchRange);
        tsInterface.getTS().setVSearchRange(VSearchRange);
        return "\u0006";
    }

}
