package sokkiacommand;

import connection.ConnectionMode;

/**
 * 水平角0°設定
 */
public class SokkiaCommand_Xi extends SokkiaCommand {

    private final String SEND_COMMAND = "Xi";

    public SokkiaCommand_Xi() {
        com = ConnectionMode.getInstance();
        receive = "";
        command = SEND_COMMAND;

        comSuc = command + "... succeed.";
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
//		Command comm = new Command_Xh();
//		try {
//			comm.sendCommand();
//		} catch (TimeoutException e) {
//			// TODO 自動生成された catch ブロック
//			e.printStackTrace();
//		}
    }
}
