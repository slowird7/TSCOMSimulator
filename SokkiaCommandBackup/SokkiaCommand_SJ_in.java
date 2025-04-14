package sokkiacommand;

import connection.ConnectionMode;

public class SokkiaCommand_SJ_in extends SokkiaCommand {

    private final String SEND_COMMAND = "*SJ000000";

    public SokkiaCommand_SJ_in() {
        com = ConnectionMode.getInstance();
        receive = "";
        command = SEND_COMMAND;
    }

    public static void main(String[] args) {
//		ConnectionMode com = ConnectionMode.getInstance();
//		 try {
//			com.open("COM4", 9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
//		} catch (Exception e1) {
//			// COMポートの解放に失敗した場合終了
//			e1.printStackTrace();
//			System.out.println("COMポートの解放に失敗しました。");
//			System.exit(0);
//		}
//
//			try {
//				sleep(1000);
//			} catch (InterruptedException e) {
//				// TODO 自動生成された catch ブロック
//				e.printStackTrace();
//			}
//
//		try {
//			sleep(1000);
//		} catch (InterruptedException e) {
//			// TODO 自動生成された catch ブロック
//			e.printStackTrace();
//		}
//
//		Command comm = new Command_Df();
//		try {
//			comm.sendCommand();
//		} catch (TimeoutException e) {
//			// TODO 自動生成された catch ブロック
//			e.printStackTrace();
//		}
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
