package sokkiacommand;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Thread.sleep;

public class SokkiaCommand_A extends SokkiaCommand {

    private final static Logger logger = LogManager.getLogger(SokkiaCommand_A.class);
    private final String commandAResponse = "A MS05AXII,KJ0416,0327,0150\n";

    public SokkiaCommand_A() {
        commandID = "A";
        command = commandID;
        p = Pattern.compile("^A");
    }


    @Override
    public String makeResponse(String args) {
        return commandAResponse;
    }

    /**
     * 通信している器械がMSであるか否かを返す "^MS|^GT|^PS|^IX|^NET|^SX|^DX" ならtrue それ以外ならfalse
     */
    public boolean isMS() {
        int count = 0;
        while (true) {
            if (!receive.equals("")) {
                break;
            }
            try {
                if (count == 20) {
                    return false;
                }
                sleep(100);
                count++;
            } catch (InterruptedException e) {
                logger.error("Error Message", e);
                e.printStackTrace();
            }
        }
        try {
            String ret = receive;
            ret = ret.replaceAll("A ", "");
            Matcher mm = Pattern.compile("^MS|^GT|^PS|^DS|^IX|^NET|^SX|^DX").matcher(ret);
//			return ret.startsWith("MS");
            return mm.find();
        } catch (Exception e) {
            logger.error("Error Message", e);
            e.printStackTrace();
            return false;
        }
    }
}
