package sokkiacommand;

import ts.TS;
import ts.TSInterface;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 「モーター駆動の設定」
 */
public class SokkiaCommand_PG_in extends SokkiaCommand {

    public SokkiaCommand_PG_in(TSInterface ts) {
        super(ts);
        commandID = "*/PG";
        p = Pattern.compile("^\\*/PG ([01234]),(\\-?[0-9.]+),([0-9.]+)$");
    }

    public String makeResponse(String args) {
        double parameter;
        int diameter;
        Matcher mm = p.matcher(args);
        if (!mm.matches()) {
            return "\u0015";
        }
        try {
            parameter = Double.parseDouble(mm.group(2));
        } catch (NumberFormatException ex) {
            return "\u0015";
        }
        try {
            diameter = Integer.parseInt(mm.group(3));
        } catch (NumberFormatException ex) {
            return "\u0015";
        }
        tsInterface.getTS().setTargetType(Integer.parseInt(mm.group(1)));
        tsInterface.getTS().setParameter(parameter);
        tsInterface.getTS().setDiameter(diameter);
        return "\u0006";
    }
}
