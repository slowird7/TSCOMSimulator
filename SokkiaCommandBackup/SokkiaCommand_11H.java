package sokkiacommand;

import connection.ConnectionMode;
import exception.ReceiveErrorException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SokkiaCommand_11H extends SokkiaCommand {

    private final static Logger logger = LogManager.getLogger(SokkiaCommand_11H.class);

    private final String SEND_COMMAND = "17";	// 0x11 = 17

    public SokkiaCommand_11H() {
        com = ConnectionMode.getInstance();
        receive = "";
        command = SEND_COMMAND;
    }

    /**
     * 斜距離を返す。
     *
     * @return 斜距離
     */
    public double getDistanceS() throws ReceiveErrorException {
        int count = 0;
        while (true) {
            if (!receive.equals("")) {
                break;
            }
            try {
                if (count == 20) {
                    return 0;
                }
                sleep(100);
                count++;
            } catch (InterruptedException e) {
                logger.error("Interrupted", e);
                e.printStackTrace();
                return 0;
            }
        }
        String ret = receive;
        String[] tmp = ret.split(" ");
        ret = tmp[0];
        try {
            return Double.parseDouble(ret) / 1000.;
        } catch (NumberFormatException ex) {
            throw new ReceiveErrorException("NumberFormatException" + ret);
        }
    }

    /**
     * @return 鉛直角(ラジアン)
     */
    public double getAngleV() throws ReceiveErrorException {
        int count = 0;
        while (true) {
            if (!receive.equals("")) {
                break;
            }
            try {
                if (count == 20) {
                    return 0.0;
                }
                sleep(100);
                count++;
            } catch (InterruptedException e) {
                logger.error("Error Message", e);
                e.printStackTrace();
                return 0.0;
            }
        }
        String ret = receive;
        String[] tmp = ret.split(" ");
        ret = tmp[1];
        try {
            return Double.parseDouble(ret);
        } catch (NumberFormatException ex) {
            throw new ReceiveErrorException("NumberFormatException" + ret);
        }
    }

    /**
     * @return 水平角(ラジアン)
     */
    public double getAngleH() throws ReceiveErrorException {
        int count = 0;
        while (true) {
            if (!receive.equals("")) {
                break;
            }
            try {
                if (count == 20) {
                    return 0.0;
                }
                sleep(100);
                count++;
            } catch (InterruptedException e) {
                logger.error("Error Message", e);
                e.printStackTrace();
                return 0.0;
            }
        }

        String ret = receive;
        String[] tmp = ret.split(" ");
        ret = tmp[2];

        try {
            return Double.parseDouble(ret);
        } catch (NumberFormatException ex) {
            throw new ReceiveErrorException("NumberFormatException" + ret);
        }
    }

//    public static void main(String args[]) {
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
//		try {
//			sleep(1000);
//		} catch (InterruptedException e) {
//			// TODO 自動生成された catch ブロック
//			e.printStackTrace();
//		}
//
//		Command comm = new SokkiaCommand_11H();
//		try {
//			comm.sendCommand();
//		} catch (TimeoutException e) {
//			// TODO 自動生成された catch ブロック
//			e.printStackTrace();
//		}
//		System.out.println(((SokkiaCommand_11H) comm).getDistanceS());
//    }
}
