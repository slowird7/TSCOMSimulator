package sokkiacommand;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ts.TS;

import java.util.regex.Pattern;

/**
 *
 */
public class SokkiaCommand_Ea extends SokkiaCommand {

    private final static Logger logger = LogManager.getLogger(SokkiaCommand_Ea.class);

    public SokkiaCommand_Ea() {
        commandID = "Ea";
        p = Pattern.compile("^Ea");
    }

    public String makeResponse(String args) {
        if (TS.getInstance().getDistance_M().isInfinite()) {
            return String.format("Ea 0000,0,%.4f,4,E200,%.4f,%.4f\n",
                    TS.getInstance().getEyeHeight(), TS.getInstance().getAngleVDMS(), TS.getInstance().getAngleHDMS());
        } else {
            return String.format("Ea 0000,0,%.4f,0,%.4f,%.4f,%.4f\n",
                    TS.getInstance().getEyeHeight(), TS.getInstance().getDistance_M(), TS.getInstance().getAngleVDMS(), TS.getInstance().getAngleHDMS());
        }
    }

}
