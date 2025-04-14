package sokkiacommand;

import connection.ConnectionMode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 測距などの停止
 */
public class SokkiaCommand_12H extends SokkiaCommand {

    private final static Logger logger = LogManager.getLogger(SokkiaCommand_12H.class);

    private final String SEND_COMMAND = "\u0012";	// 0x12 = 18

    public SokkiaCommand_12H() {
        com = ConnectionMode.getInstance();
        receive = "";
        command = SEND_COMMAND;
    }

}
