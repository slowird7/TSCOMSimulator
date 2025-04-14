package sokkiacommand;

import connection.ConnectionMode;

/**
 * 座標のステークアウトデータの設定
 */
public class SokkiaCommand_Df_in extends SokkiaCommand {

    private final String SEND_COMMAND = "/Df";

    private final double x;
    private final double y;
    private final double z;

    public SokkiaCommand_Df_in(double x, double y, double z) {
        com = ConnectionMode.getInstance();
        receive = "";

        this.x = x;
        this.y = y;
        this.z = z;

        command = SEND_COMMAND + " " + this.x + "," + this.y + "," + this.z;

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
//		Command comm = new SokkiaCommand_Df_in(20,0,0);
//		try {
//			comm.sendCommand();
//		} catch (TimeoutException e) {
//			// TODO 自動生成された catch ブロック
//			e.printStackTrace();
//		}
    }
}
