package sokkiacommand;

import connection.ConnectionMode;
import exception.ReceiveFailedException;
import exception.TimeoutException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SokkiaCommand_Gb extends SokkiaCommand {

    private final static Logger logger = LogManager.getLogger(SokkiaCommand_Gb.class);

    private final String SEND_COMMAND = "Gb";

    public SokkiaCommand_Gb() {
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
//		Command comm = new SokkiaCommand_Gb();
//		try {
//			comm.sendCommand();
//		} catch (TimeoutException e) {
//			// TODO 自動生成された catch ブロック
//			e.printStackTrace();
//		}
//		double d = ((SokkiaCommand_Gb) comm).getDistance();
//		System.out.println(d);
    }

    public double getDistance() throws ReceiveFailedException, TimeoutException {
        int count = 0;
        while (true) {
            if (!receive.equals("")) {
                break;
            }
            try {
                if (count == 500) {
                    System.out.println("Timeout Error");
                    throw new TimeoutException("timeout 10sec.");
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
            ret = ret.replaceAll("Gb ", "");
            String[] tmp = ret.split(",");
            return Double.parseDouble(tmp[1]);	// 水平距離
        } catch (Exception e) {
            logger.error("Error Message", e);
            throw new ReceiveFailedException();
        }
    }

    public double getDifference() throws ReceiveFailedException, TimeoutException {
        int count = 0;
        while (true) {
            if (!receive.equals("")) {
                break;
            }
            try {
                if (count == 500) {
                    System.out.println("Timeout Error");
                    throw new TimeoutException("timeout 10sec.");
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
            ret = ret.replaceAll("Gb ", "");
            String[] tmp = ret.split(",");
            return Double.parseDouble(tmp[0]);	// 水平距離の杭打ち測定値
        } catch (Exception e) {
            logger.error("Error Message", e);
            throw new ReceiveFailedException();
        }
    }
}
