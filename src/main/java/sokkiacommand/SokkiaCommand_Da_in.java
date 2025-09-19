package sokkiacommand;

import ts.TS;
import ts.TSInterface;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 器械点座標の設定
 */
public class SokkiaCommand_Da_in extends SokkiaCommand {

    public SokkiaCommand_Da_in(TSInterface ts) {
        super(ts);
        commandID = "/Da";
        p = Pattern.compile("^/Da (\\-?[0-9.]+),(\\-?[0-9.]+),(\\-?[0-9.]+)$");
    }

    @Override
    public String makeResponse(String args) {
        double kikaiX,kikaiY, kikaiZ;
        Matcher mm = p.matcher(args);
        if (!mm.matches()) {
            return "\u0015";
        }
        try {
            kikaiX = Double.parseDouble(mm.group(1));
            kikaiY = Double.parseDouble(mm.group(2));
            kikaiZ = Double.parseDouble(mm.group(3));
        } catch (NumberFormatException ex) {
            return "\u0015";
        }
        tsInterface.getTS().getKikai().setX(kikaiX);
        tsInterface.getTS().getKikai().setY(kikaiY);
        tsInterface.getTS().getKikai().setZ(kikaiZ);
        return "\u0006";
    }
}
