package sokkiacommand;

import connection.ConnectionMode;
import exception.ReceiveFailedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SokkiaCommand_Gd extends SokkiaCommand {

    private final static Logger logger = LogManager.getLogger(SokkiaCommand_Gd.class);

    private final String SEND_COMMAND = "Gd";

    public SokkiaCommand_Gd() {
        com = ConnectionMode.getInstance();
        receive = "";
        command = SEND_COMMAND;
    }

}
