package sokkiacommand;

import connection.ConnectionMode;
import exception.ReceiveFailedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 高低差の測定
 */
public class SokkiaCommand_Ec extends SokkiaCommand {

    private final static Logger logger = LogManager.getLogger(SokkiaCommand_Ec.class);

    private final String SEND_COMMAND = "Ec";

    public SokkiaCommand_Ec() {
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
//		Command comm = new SokkiaCommand_Ec();
//		try {
//			comm.sendCommand();
//		} catch (TimeoutException e) {
//			// TODO 自動生成された catch ブロック
//			e.printStackTrace();
//		} catch (Exception e) {
//			// TODO 自動生成された catch ブロック
//			e.printStackTrace();
//		}
    }

    public double getElevation() throws ReceiveFailedException {
        int count = 0;
        while (true) {
            if (!receive.equals("")) {
                break;
            }
            try {
                if (count == 20) {
                    System.out.println("Error");
                    throw new ReceiveFailedException();
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
            ret = ret.replaceAll("Ec ", "");
            String[] tmp = ret.split(",");

            return Double.parseDouble(tmp[4]);    // 高低差
        } catch (Exception e) {
            logger.error("Error Message", e);
            throw new ReceiveFailedException();
        }
    }

}
