package sokkiacommand;

import connection.ConnectionMode;

/**
 * 「連続精密」
 */
public class SokkiaCommand_Xb extends SokkiaCommand {

    private final String SEND_COMMAND = "Xb";

    public SokkiaCommand_Xb() {
        com = ConnectionMode.getInstance();
        receive = "";
        command = SEND_COMMAND;

        comSuc = command + "... succeed.";
    }

    public static void main(String[] args) {

    }
}
