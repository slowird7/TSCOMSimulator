package sokkiacommand;

import connection.ConnectionMode;

/**
 * 座標のステークアウトデータの設定
 */
public class SokkiaCommand_DHI_in extends SokkiaCommand {

    private final String SEND_COMMAND = "*D";

    public SokkiaCommand_DHI_in(double hAngleDMS) {
        com = ConnectionMode.getInstance();
        String strHAngleDMS = ((hAngleDMS >= 0.) ? "+" : "-") + String.format("%07.0f", Math.abs(hAngleDMS) * 10000);
        receive = "";
        command = SEND_COMMAND + "HI" + strHAngleDMS + "VI+0000000";
        comSuc = command + "... succeed.";
    }

    public SokkiaCommand_DHI_in(double hAngleDMS, double vAngleDMS) {
        com = ConnectionMode.getInstance();
        String strHAngleDMS = ((hAngleDMS >= 0.) ? "+" : "-") + String.format("%07.0f", Math.abs(hAngleDMS) * 10000);
        String strVAngleDMS = ((vAngleDMS >= 0.) ? "-" : "+") + String.format("%07.0f", Math.abs(vAngleDMS) * 10000);
        receive = "";
        command = SEND_COMMAND + "HI" + strHAngleDMS + "VI" + strVAngleDMS;
        comSuc = command + "... succeed.";
    }

    @Override
    public boolean dataHandler(int received_data) {
        // 入力ナシなら終了
        if (received_data == -1) {
            //System.out.println("No data available.");
            return false;
        }

        // ACK 応答を受信
        if (received_data == 0x06) {
            System.out.println("Command: Received ACK.");
            System.out.println(getComSuc());
            receivedACK = true;
            comp = false;
            System.out.println("Command: set comp true.");
            return true;
        }

        // NAK 応答を受信
        if (received_data == 0x15) {
            System.out.println("received NAK !!!");
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
        buffer.append('\n');
        this.receive = buffer.toString();
        buffer.delete(0, buffer.length() + 1);
        if (receive.equals("OK\n")) {
            comp = true;
            return false;
        }
        comp = true;
        return false;
    }
}
