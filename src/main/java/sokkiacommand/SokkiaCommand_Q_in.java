package sokkiacommand;

import connection.ConnectionMode;

public class SokkiaCommand_Q_in extends SokkiaCommand {

    private final String SEND_COMMAND = "*Q";

    public SokkiaCommand_Q_in() {
        command = SEND_COMMAND;
        setTimeout(110);
    }

    public String makeResponse(String args) {
        return "\u0006";
    }
}
