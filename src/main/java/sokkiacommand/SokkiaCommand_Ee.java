package sokkiacommand;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ts.TS;
import ts.TSInterface;

import java.util.regex.Pattern;

/**
 *
 */
public class SokkiaCommand_Ee extends SokkiaCommand {

    private final static Logger logger = LogManager.getLogger(SokkiaCommand_Ee.class);

    public SokkiaCommand_Ee(TSInterface ts) {
        super(ts);
        commandID = "Ee";
        p = Pattern.compile("^Ee");
    }

    public String makeResponse(String args) {
        return String.format("Ee 0000,0,%.4f,4,%.4f,%.4f,%.4f,%.4f\n",
                    0., tsInterface.getTS().getAngleHDMS(), tsInterface.getTS().getAngleVDMS(), tsInterface.getTS().getTiltXDMS(), tsInterface.getTS().getTiltYDMS());
    }

}
