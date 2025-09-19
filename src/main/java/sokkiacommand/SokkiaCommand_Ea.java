package sokkiacommand;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ts.TS;
import ts.TSInterface;

import java.util.regex.Pattern;

/**
 *
 */
public class SokkiaCommand_Ea extends SokkiaCommand {

    private final static Logger logger = LogManager.getLogger(SokkiaCommand_Ea.class);

    public SokkiaCommand_Ea(TSInterface ts) {
        super(ts);
        commandID = "Ea";
        p = Pattern.compile("^Ea");
    }

    public String makeResponse(String args) {

        if (tsInterface.getTS().getDistance_M().isInfinite()) {
            return String.format("Ea 0000,0,%.4f,4,E200,%.4f,%.4f\n",
                    tsInterface.getTS().getEyeHeight(), tsInterface.getTS().getAngleVDMS(), tsInterface.getTS().getAngleHDMS());
        } else {
            return String.format("Ea 0000,0,%.4f,0,%.4f,%.4f,%.4f\n",
                    tsInterface.getTS().getEyeHeight(), tsInterface.getTS().getDistance_M(), tsInterface.getTS().getAngleVDMS(), tsInterface.getTS().getAngleHDMS());
        }
    }

}
