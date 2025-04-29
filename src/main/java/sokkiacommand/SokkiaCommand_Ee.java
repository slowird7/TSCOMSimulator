package sokkiacommand;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ts.TS;

import java.util.regex.Pattern;

/**
 *
 */
public class SokkiaCommand_Ee extends SokkiaCommand {

    private final static Logger logger = LogManager.getLogger(SokkiaCommand_Ee.class);

    public SokkiaCommand_Ee() {
        commandID = "Ee";
        p = Pattern.compile("^Ee");

    }

    public String makeResponse(String args) {
        return String.format("Ee 0000,0,%.4f,4,%.4f,%.4f,%.4f,%.4f\n",
                    0., TS.getInstance().getAngleHDMS(), TS.getInstance().getAngleVDMS(), TS.getInstance().getTiltXDMS(), TS.getInstance().getTiltYDMS());
    }

}
