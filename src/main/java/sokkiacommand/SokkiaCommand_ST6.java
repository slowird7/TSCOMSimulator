package sokkiacommand;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ts.TSInterface;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 */
public class SokkiaCommand_ST6 extends SokkiaCommand {

    private final static Logger logger = LogManager.getLogger(SokkiaCommand_ST6.class);

    public static final String RESPONSE_ST6 = "Ea 0000,0,0.0000,0,0.0000,0.0000,0.0000\n";

    private double start;
    private double kikaiY;
    private double kikaiZ;

    public SokkiaCommand_ST6(TSInterface ts) {
        super(ts);
        commandID = "*ST6";
        p = Pattern.compile("^\\*ST6 ([0-9.]+),([0-9.]+),([0-9.]+),([0-9.]+)");
    }

    public String makeResponse(String args) {
        double topLeftVerticalAngle;
        double topLeftHorizontalAngle;
        double bottomRightVerticalAngle;
        double bottmRightHorizontalAngle;
        StringBuilder responseBuklder = new StringBuilder();

        Matcher mm = p.matcher(args);
        if (!mm.matches()) {
            return "\u0015";
        }
        try {
            topLeftHorizontalAngle = Double.parseDouble(mm.group(1));
            topLeftVerticalAngle = Double.parseDouble(mm.group(2));
            bottmRightHorizontalAngle = Double.parseDouble(mm.group(3));
            bottomRightVerticalAngle = Double.parseDouble(mm.group(4));

            responseBuklder.append(String.format("*ST6 0,90,1,4,%s,%s,\n", mm.group(1), mm.group(2)));
            responseBuklder.append(String.format("*ST6 0,90,2,4,%s,%s,\n", mm.group(1), mm.group(4)));
            responseBuklder.append(String.format("*ST6 0,90,3,4,%s,%s,\n", mm.group(3), mm.group(2)));
            responseBuklder.append(String.format("*ST6 0,90,4,4,%s,%s,\n", mm.group(3), mm.group(4)));

            return responseBuklder.toString();

        } catch (NumberFormatException ex) {
            return "\u0015";
        }
    }
}
