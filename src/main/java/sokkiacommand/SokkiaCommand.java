package sokkiacommand;

import command.Commands;
import exception.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.regex.Pattern;

import static java.lang.Thread.sleep;

public abstract class SokkiaCommand extends Commands {

    protected final static Logger LOGGER = LogManager.getLogger(SokkiaCommand.class);
    protected String commandID = "";

    public String getCommandID() {
        return commandID;
    }

    @Override
    public void setTimeout(int dsec) {
        deadline = dsec;
    }

    @Override
    public String getComSuc() {
        return comSuc;
    }



    /**
     * receiveがエラーコードを含んでいないかをチェックする。 エラーコードを含む場合、例外を投げる。
     *
     * @throws exception.ReceiveErrorException
     */
    @Override
    public void checkError() throws ReceiveErrorException {
        if (receive.isEmpty()) {
            return;
        }
    }
}
