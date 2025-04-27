package sokkiacommand;

import java.util.regex.Pattern;

public class SokkiaCommand_Q_in extends SokkiaCommand {

    public SokkiaCommand_Q_in() {
        commandID = "*Q";
        p = Pattern.compile("^\\*Q$");
        setTimeout(110);
    }

    public String makeResponse(String args) {
        return "\u0006";
    }
}
