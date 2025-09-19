package connection;

import exception.ReceiveFailedException;
import exception.SendFailedException;
import exception.TSNotConnectedException;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.event.EventHandler;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import project.Property;
import ts.TSInterface;

import java.io.InputStream;
import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;

import static java.lang.Thread.sleep;

/**
 * @author kinsoku
 */
public class COMPortConnection extends ConnectionMode {

    public static final int BAUDRATE_110 = 110;
    public static final int BAUDRATE_300 = 300;
    public static final int BAUDRATE_600 = 600;
    public static final int BAUDRATE_1200 = 1200;
    public static final int BAUDRATE_4800 = 4800;
    public static final int BAUDRATE_9600 = 9600;
    public static final int BAUDRATE_14400 = 14400;
    public static final int BAUDRATE_19200 = 19200;
    public static final int BAUDRATE_38400 = 38400;
    public static final int BAUDRATE_57600 = 57600;
    public static final int BAUDRATE_115200 = 115200;
    public static final int BAUDRATE_128000 = 128000;
    public static final int BAUDRATE_256000 = 256000;

    public static final int DATABITS_5 = 5;
    public static final int DATABITS_6 = 6;
    public static final int DATABITS_7 = 7;
    public static final int DATABITS_8 = 8;

    public static final int STOPBITS_1 = 1;
    public static final int STOPBITS_2 = 2;
    public static final int STOPBITS_1_5 = 3;

    public static final int PARITY_NONE = 0;
    public static final int PARITY_ODD = 1;
    public static final int PARITY_EVEN = 2;
    public static final int PARITY_MARK = 3;
    public static final int PARITY_SPACE = 4;
    private final static Logger logger = LogManager.getLogger(COMPortConnection.class);
    //protected static COMPortConnetion instance;
    private static final Property ppt = Property.getInstance();
    protected SerialPort port;
    protected InputStream in;
    protected String receive;
    protected StringBuffer buffer = new StringBuffer();
    private ConcurrentLinkedDeque<String> commandQue = null;
    private ConcurrentLinkedDeque<String> responseQue = null;
    private Responser thResponser;
    private TSInterface ts;

    public COMPortConnection() {
        //sokkiaCommand = null;
        commands = null;
        port = null;
        setState(STATE.DISCONNECT);
        this.commandQue = new ConcurrentLinkedDeque<>();
        this.responseQue = new ConcurrentLinkedDeque<>();
        instance = this;
    }

    public Deque<String> getCommandQue() {
        return commandQue;
    }

    public Deque<String> getResponseQue() {
        return responseQue;
    }

    public SerialPort getPort() {
        return port;
    }

    @Override
    public boolean open(String id) {
        boolean ret = false;
        logger.debug("id: " + id);
        try {
            /*
	    * TS接続設定の確認
             */

            if (!open(id, 9600, DATABITS_8, STOPBITS_1, PARITY_NONE)) {
                return false;
            }
            thResponser = new Responser();
            thResponser.start();
            return true;

        } catch (SerialPortException ex) {
            logger.error("Error Message", ex);
        }
        return false;
    }

    private boolean open(String id, int baudRate, int dataBits, int stopBits, int parity) throws SerialPortException {

        logger.debug(isConnected());
        try {
            if (isConnected()) {
                logger.debug("Already connected.");
                return true;
            }

            //try {
            port = new SerialPort(id);
            try {
                port.openPort();//Open serial port
            } catch (jssc.SerialPortException e) {
                logger.error("Error Message", e);
                //DisposX.WarningBox("指定されたポートをオープンできませんでした", e.getMessage());
                //DisposX.LOGGER.warning("COMPortConnetion.open() 失敗" + e.getMessage()+e.getPortName());
                if (e.getExceptionType().equals(jssc.SerialPortException.TYPE_PORT_BUSY)) {
                    port.closePort();
                    port.openPort();
                }
            }
            port.addEventListener(new SerialPortReader());//Add SerialPortEventListener
            // ボーレート、データビット数、ストップビット数、パリティを設定
            port.setParams(baudRate, dataBits, stopBits, parity);

            // フロー制御はしない
            port.setFlowControlMode(SerialPort.FLOWCONTROL_NONE);

            int mask = SerialPort.MASK_RXCHAR + SerialPort.MASK_CTS + SerialPort.MASK_DSR;//Prepare mask
            port.setEventsMask(mask);//Set mask

            //in = port.getInputStream();
            //port.addEventListener(new SerialPortListener());
            //port.notifyOnDataAvailable(true);
            logger.debug("*** port opened." + id);
            setState(STATE.CONNECTED);
            return true;
        } catch (jssc.SerialPortException ex) {
            logger.error("Error Message", ex);
            throw new SerialPortException(ex.getMessage());
        }
    }

    @Override
    public boolean isConnected() {
        return port != null && port.isOpened();
    }

    @Override
    public boolean isConnectedEx() {
        return port != null && port.isOpened();
    }


    @Override
    public int send() {
        logger.warn("send>not implemented.");
        return 0;
    }

    @Override
    public int sendReceive() throws TSNotConnectedException, SendFailedException, ReceiveFailedException {
        logger.warn("sendReceive>not implemented.");
        return 0;
    }

    private int sendSokkiaResponse(String response) throws TSNotConnectedException, SendFailedException, SerialPortException {

        boolean number = true;
        int nn = 0;
        byte[] buffer = new byte[256];

        logger.debug("COMPortConnetion>send response: [" + response + "]");
//        try {
//            nn = Integer.parseInt(response);
//        } catch (NumberFormatException e) {
//            number = false;
//        }
//        if (nn > 255) {
//            number = false;
//        }
        number = false;
        try {
            if (number) {
                port.writeByte((byte) nn);
                port.writeByte((byte) '\r');
            } else {
                byte[] com = response.getBytes();
                port.writeBytes(com);
                port.writeByte((byte) '\r');
                try {
                    sleep(100);
                } catch (InterruptedException ex) {
                    // do nothing
                }
            }
        } catch (jssc.SerialPortException ex) {
            logger.error("Error Message", ex);
            throw new SerialPortException(ex.getMessage());
        }
        return 0;
    }


    @Override
    public void reconnect() {
        logger.error("reconnect() is not implemented yet.");
    }

    @Override
    public void close() {
        thResponser.stopp();
        try {
            if (port != null) {
                port.closePort();
                port = null;
                instance = null;
                setState(STATE.DISCONNECT);
                logger.debug("*** port.close() ");
            }
        } catch (jssc.SerialPortException ex) {
            logger.error("Error Message", ex);
            ex.printStackTrace();
        }
    }

    public void openAsync(String id) {
        Task tsk = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                open(id);
                return null;
            }
        };

        tsk.setOnSucceeded(new EventHandler() {
            @Override
            public void handle(Event event) {
                if (instance.isConnected()) {
//                    TKKAlert.getInstance().showAlert(java.util.ResourceBundle.getBundle("resource/messages").getString("TS接続"), java.util.ResourceBundle.getBundle("resource/messages").getString("TSに接続しました")).show();
                } else {
//                    TKKAlert.getInstance().showAlert(java.util.ResourceBundle.getBundle("resource/messages").getString("TS接続"), java.util.ResourceBundle.getBundle("resource/messages").getString("TS接続に失敗しました")).show();
                }
            }
        });

        tsk.setOnFailed(new EventHandler() {
            @Override
            public void handle(Event event) {
                String msg = tsk.getException().getMessage();
//                TKKAlert.getInstance().showAlert(java.util.ResourceBundle.getBundle("resource/messages").getString("TS接続"), java.util.ResourceBundle.getBundle("resource/messages").getString("TS接続時にエラーが起きました:{0}") + msg).show();
            }
        });

        tsk.setOnCancelled(new EventHandler() {
            @Override
            public void handle(Event event) {
//                TKKAlert.getInstance().showAlert(java.util.ResourceBundle.getBundle("resource/messages").getString("TS接続"), java.util.ResourceBundle.getBundle("resource/messages").getString("TS接続がキャンセルされました")).show();
            }
        });

        Thread th = new Thread(tsk);
        th.setDaemon(true);
        th.start();
    }

    public class SerialPortException extends Exception {

        SerialPortException(String msg) {
            super(msg);
        }
    }

    class SerialPortReader implements SerialPortEventListener {

        StringBuffer buffer = new StringBuffer();
        byte received_data = 0;

        public void serialEvent(SerialPortEvent event) {
//            try {
            if (event.isRXCHAR()) {//If data is available
                try {
                    int noOfBytes = event.getEventValue();
                    byte[] bytes = port.readBytes(noOfBytes);
                    //System.out.print("**** reaBytes:'");
                    for (int ii = 0; ii < noOfBytes; ii++) {
                        if (ppt.getMeasureMode().equals("通常観測モード")) {
                            if (!dataHandler(bytes[ii])) {
                                break;
                            }
                        } else if (ppt.getMeasureMode().equals("ワンマン観測モード")) {
                            if (!dataHandler(bytes[ii])) {
                                break;
                            }
                        }
                    }	// for
                } catch (jssc.SerialPortException ex) {
                    logger.error("Error Message", ex);
                    setState(STATE.DISCONNECT);
                    ex.printStackTrace();
                }
            } else if (event.isCTS()) {//If CTS line has changed state
                if (event.getEventValue() == 1) {//If line is ON
                    logger.debug("CTS - ON");
                } else {
                    logger.debug("CTS - OFF");
                }
            } else if (event.isDSR()) {///If DSR line has changed state
                if (event.getEventValue() == 1) {//If line is ON
                    logger.debug("DSR - ON");
                } else {
                    logger.debug("DSR - OFF");
                    try {
                        port.closePort();
                        port = null;
                        setState(STATE.DISCONNECT);
                    } catch (jssc.SerialPortException e) {
                        logger.error("Error Message", e);
                        // TODO 自動生成された catch ブロック
                        e.printStackTrace();
                    }
                }
            }
//            } catch (SerialPortException ex) {
//                throw new SerialPortException()
//            }
        }
    }

    public boolean dataHandler(int received_data) {
//        logger.debug("received [" + received_data + "]");
        // 入力ナシなら終了
        if (received_data == -1) {
            //System.out.println("No data available.");
            return false;
        }

        if ((char) received_data != '\n' && (char) received_data != '\r') {
            //System.out.println("received char [" + received_data + "]");
            buffer.append((char) received_data);
            return true;
        }

        // 行末を受信
        logger.debug("received line [" + buffer.toString() + "]");
        if (commandQue != null) {
            commandQue.add(buffer.toString());
        }
        buffer.delete(0, buffer.length() + 1);
        return false;
    } //end of dataHandler()

    private class Responser extends Thread {
        boolean running = false;

        Responser() {
            super();
            setName("Responser");
        }

        public void stopp() {
            logger.info("stop> gonna stop.");
            running = false;

        }

        public void run() {
            logger.debug("run> Start response thread.");
            running = true;
            while (running) {
                if (responseQue.peek() != null) {
                    try {
                        sendSokkiaResponse(responseQue.poll());
                    } catch (SerialPortException | TSNotConnectedException | SendFailedException ex) {
                        // do nothing
                    }
                }
                try {
                    sleep(500);
                } catch (InterruptedException ex) {
                    logger.error("Error Message", ex);
                    running = false;
                }
            }
            logger.info("TSSimulator::response thread stopped.");
        }
    }

}
