package sokkiacommand;

import connection.ConnectionMode;

/**
 * 「単回」
 */
public class SokkiaCommand_Xc extends SokkiaCommand {

    private final String SEND_COMMAND = "Xc";

    public SokkiaCommand_Xc() {
        com = ConnectionMode.getInstance();
        receive = "";
        command = SEND_COMMAND;

        comSuc = command + "... succeed.";
    }

    public static void main(String[] args) {

    }
}
