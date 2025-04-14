package sokkiacommand;

import connection.ConnectionMode;

/**
 * 「単回」
 */
public class SokkiaCommand_Xa extends SokkiaCommand {

    private final String SEND_COMMAND = "Xa";

    public SokkiaCommand_Xa() {
        com = ConnectionMode.getInstance();
        receive = "";
        command = SEND_COMMAND;

        comSuc = command + "... succeed.";
    }

    public static void main(String[] args) {

    }
}
