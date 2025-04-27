package sokkiacommand;

import connection.ConnectionMode;
import ts.TS;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 「モーター駆動の設定」
 */
public class SokkiaCommand_PG_in extends SokkiaCommand {

    public SokkiaCommand_PG_in() {
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
        TS.getInstance().setTargetType(Integer.parseInt(mm.group(1)));
        TS.getInstance().setParameter(parameter);
        TS.getInstance().setDiameter(diameter);
        return "\u0006";
    }
}
