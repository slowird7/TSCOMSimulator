package sokkiacommand;

import command.Commands;
import exception.ReceiveErrorException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ts.TSInterface;

public abstract class SokkiaCommand extends Commands {

    protected final static Logger LOGGER = LogManager.getLogger(SokkiaCommand.class);
    protected String commandID = "";

    public SokkiaCommand(TSInterface ts) {
        super(ts);
    }
    
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
        }
    }
}
