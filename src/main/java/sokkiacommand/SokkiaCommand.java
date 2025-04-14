package sokkiacommand;

import command.Commands;
import connection.ConnectionMode;
import exception.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static java.lang.Thread.sleep;

public class SokkiaCommand extends Commands {

    protected final static Logger LOGGER = LogManager.getLogger(SokkiaCommand.class);
    protected String SEND_COMMAND = "";

    public String getCommandID() {
        return SEND_COMMAND;
    }

    @Override
    public void setTimeout(int dsec) {
        deadline = dsec;
    }

    @Override
    public String getComSuc() {
        return comSuc;
    }

    @Override
    protected boolean match(String recv) {
        m = p.matcher(recv);
        return m.matches();
    }

    @Override
    public String makeResponse(String args) {
        return null;
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

    /**
     * 受信した 1byte を処理する 以下の状態変数を操作する。 receivedACK ACK を受信すると true receivedNACK
     * NAK を受信すると true comp 応答データの受信を完了した recieve 受信した応答データ一式
     *
     * @param received_data 受信データ
     * @return true 受信待ちループを継続せよ false 受信待ちループを終了してよい（応答データ受信完了や受信エラーなどによる）
     */
    @Override
    public boolean dataHandler(int received_data) {
        // 入力ナシなら終了
        if (received_data == -1) {
            //System.out.println("No data available.");
            return false;
        }

        // ACK 応答を受信
        if (received_data == 0x06) {
            LOGGER.debug("Command: Received ACK.");
            LOGGER.debug(getComSuc());
            receivedACK = true;
            comp = true;
            LOGGER.debug("Command: set comp true.");
            return false;
        }

        // NAK 応答を受信
        if (received_data == 0x15) {
            LOGGER.error("received NAK !!!");
            receivedNACK = true;
            comp = true;
            return false;
        }

        // エラーコードを受信
        //if ()
        //receivedERR = true;
        if ((char) received_data == '\r') {
            // just ignore it.
            return true;
        }

        if ((char) received_data != '\n') {
            //System.out.println("received char [" + received_data + "]");
            buffer.append((char) received_data);
            return true;
        }

        // 行末を受信
        LOGGER.debug("received line [" + buffer.toString() + "]");
        receivedACK = true;
        buffer.append('\n');
        this.receive = buffer.toString();
        receivedData = receive;
        buffer.delete(0, buffer.length() + 1);
        comp = true;
        return false;
    } //end of dataHandler()

    public static String response() {
        return "";
    }
}
