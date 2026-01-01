package sokkiacommand;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ts.TSInterface;

import java.util.regex.Pattern;

public class SokkiaCommand_00H extends SokkiaCommand {

    private final static Logger logger = LogManager.getLogger(SokkiaCommand_00H.class);

    private final String SEND_COMMAND = "0";	// 0x11 = 17

    public SokkiaCommand_00H(TSInterface ts) {
        super(ts);
        command = SEND_COMMAND;
        p = Pattern.compile("^\u0000");
    }

    /*
    * @description
    * D.MS形式の角度データを符号付 sec 単位に変換。
    * 0.1秒単位まで変換。
     */
    public static double DMS2SEC(double a) {
        int sign;
        String strA = String.format("%011.6f", a);
        String ww;
        ww = strA.substring(0, 4);
        int degA = Integer.parseInt(ww);
        if (a >= 0) {
            sign = 1;
        } else {
            sign = -1;
            degA *= -1;
        }
        ww = strA.substring(5, 7);
        int minA = degA * 60 + Integer.parseInt(ww);
        // 小数点以下第5位まで切り出して
        ww = strA.substring(7, 10);
        double secA = minA * 60 + Integer.parseInt(ww) / 10.;
        secA *= sign;
        return secA;
    }

    public static double SEC2DMS(double a) {
        while (a < 0) {
            a += 60 * 60 * 360;
        }
        while (a >= 60 * 60 * 360) {
            a -= 60 * 60 * 360;
        }
        int deg = (int) a / 3600;
        a -= (deg * 3600);
        int min = (int) a / 60;
        a -= (min * 60);
        // 0.01秒の位で四捨五入し、0.1秒単位で出力しよう
        int sec = (int) Math.round(a * 10);
        double ret = Double.parseDouble(String.format("%4d.%02d%03d", deg, min, sec));
        return ret;
    }

    public static String DMS2FIX7(double dms) {
        // 2018.01.21 n_otsuka
        // ここは本来 "%08.4f" と指定したいが 359.59596 が四捨五入により 359.5960 となる不具合が
        // 解決できないので、"%09.5f" と指定して 0.1秒単位を切り捨てる。
        //String w = String.format("%08.4f", dms);
        String w = String.format("%09.5f", dms);
        String w2 = String.format("%s%s", w.substring(0, 3), w.substring(4, 8));
        return w2;
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
//		try {
//			sleep(1000);
//		} catch (InterruptedException e) {
//			// TODO 自動生成された catch ブロック
//			e.printStackTrace();
//		}
//
//		Command comm = new Command_11H();
//		try {
//			comm.sendCommand();
//		} catch (TimeoutException e) {
//			// TODO 自動生成された catch ブロック
//			e.printStackTrace();
//		}
//		System.out.println(((Command_11H) comm).getDistanceS());
    }

    /**
     * 斜距離を返す。
     *
     * @return 斜距離
     */
    public double getDistanceS() {
        return 0;
    }

    /**
     * @return 鉛直角(ラジアン)
     */
    public double getAngleVSEC() {
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
        try {
            String ret = receive;
            String[] tmp = ret.split(" ");
            return DMS2SEC(Double.parseDouble(tmp[1]) / 100000);

        } catch (Exception e) {
            logger.error("Error Message", e);
            e.printStackTrace();
            return 0.0;
        }
    }

    /**
     * @return 水平角(ラジアン)
     */
    public double getAngleHSEC() {
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
        try {
            String ret = receive;
            String[] tmp = ret.split(" ");
            return DMS2SEC(Double.parseDouble(tmp[2]) / 100000);
        } catch (Exception e) {
            logger.error("Error Message", e);
            e.printStackTrace();
            return 0.0;
        }
    }

    @Override
    public String makeResponse(String args) {
        return(String.format("00000000 %s %s \n", DMS2FIX7(tsInterface.getTS().getAngleVDMS()), DMS2FIX7(tsInterface.getTS().getAngleHDMS())));
    }
}
