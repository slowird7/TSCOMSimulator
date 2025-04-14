package sokkiacommand;

import connection.ConnectionMode;
import exception.ReceiveFailedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 */
public class SokkiaCommand_EbCam extends SokkiaCommand {

    private final static Logger logger = LogManager.getLogger(SokkiaCommand_EbCam.class);

    private final String SEND_COMMAND = "Eb";

    public SokkiaCommand_EbCam() {
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
//		SokkiaCommand comm = new Command_Eb();
//		try {
//			comm.sendCommand();
//		} catch (TimeoutException e) {
//			// TODO 自動生成された catch ブロック
//			e.printStackTrace();
//		}
    }

    public double getDistance() throws ReceiveFailedException {
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
            ret = ret.replaceAll("Eb ", "");
            String[] tmp = ret.split(",");

            return Double.parseDouble(tmp[4]);	// 水平距離
        } catch (Exception e) {
            logger.error("Error Message", e);
            throw new ReceiveFailedException();
        }
    }

    public double getAngle() throws ReceiveFailedException {
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
            ret = ret.replaceAll("Eb ", "");
            String[] tmp = ret.split(",");

            return Double.parseDouble(tmp[6]);	// 水平角
        } catch (Exception e) {
            logger.error("Error Message", e);
            throw new ReceiveFailedException();
        }
    }

    public double getVAngle() throws ReceiveFailedException {
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
            ret = ret.replaceAll("Eb ", "");
            String[] tmp = ret.split(",");

            return Double.parseDouble(tmp[5]);	// 鉛直角
        } catch (Exception e) {
            logger.error("Error Message", e);
            throw new ReceiveFailedException();
        }
    }

}
