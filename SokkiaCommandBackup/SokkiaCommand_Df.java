package sokkiacommand;

import connection.ConnectionMode;
import exception.ReceiveFailedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SokkiaCommand_Df extends SokkiaCommand {

    private final static Logger logger = LogManager.getLogger(SokkiaCommand_Df.class);

    private final String SEND_COMMAND = "Df";

    public SokkiaCommand_Df() {
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
//		Command comm = new SokkiaCommand_Df();
//		try {
//			comm.sendCommand();
//		} catch (TimeoutException e) {
//			// TODO 自動生成された catch ブロック
//			e.printStackTrace();
//		}
    }

    /**
     * @return 実測座標
     */
    public double[] getCoordinate() throws ReceiveFailedException {
        int count = 0;
        while (true) {
            if (!receive.equals("")) {
                break;
            }
            try {
                if (count == 20) {
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
            ret = ret.replaceAll("Df ", "");
            String[] tmp = ret.split(",");

            double[] retD = {Double.parseDouble(tmp[0]), Double.parseDouble(tmp[1]), Double.parseDouble(tmp[2])};
            return retD;
        } catch (Exception e) {
            logger.error("Error Message", e);
            throw new ReceiveFailedException();
        }
    }
}
