package sokkiacommand;

import connection.ConnectionMode;

/**
 * 器械点座標の設定
 */
public class SokkiaCommand_Dc_in extends SokkiaCommand {

    private final String SEND_COMMAND = "/Dc %8.4f";

    private double x, y, z;

    public SokkiaCommand_Dc_in(double angleDMS) {
        com = ConnectionMode.getInstance();
        receive = "";

        command = String.format(SEND_COMMAND, angleDMS);

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
//		Command comm = new SokkiaCommand_Da_in(0,0,0);
//		try {
//			comm.sendCommand();
//		} catch (TimeoutException e) {
//			// TODO 自動生成された catch ブロック
//			e.printStackTrace();
//		}
    }

}
