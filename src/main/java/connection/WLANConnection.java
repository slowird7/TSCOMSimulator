package connection;

import org.apache.logging.log4j.LogManager;
import project.Property;

import java.net.InetAddress;

/**
 * @author kinsoku
 */
public class WLANConnection extends ConnectionMode {

    private final static org.apache.logging.log4j.Logger logger = LogManager.getLogger(WLANConnection.class);
    private static final int tsujoTimeout = 10000;
    private static final int onemanTimeout = 5000;
    private static final int RECEIVEBUFFER_SIZE = 256;
    private static final int RECTYPE = 1;   // 0 にセットすると、傾き計測時にずっとサーチ動作が付いて回るらしい 
    private static final int TIMEOUT_PING_MS = 1000;

    private byte[] recBuffer;
    private int buffSize;
    private String devName;
    private InetAddress ipAddressRemote;
    private Property ppt;

    protected WLANConnection() {
        commands = null;
        setState(STATE.DISCONNECT);
    }

    private void standbyCommand() {
//        if (!LANInterfaceLib.INSTANCE.topcon_COMSendBCC(comHandle, "ZD39999")) {
//
//            System.out.println("Failed to send ZD3");
//        } else {
//            System.out.println("ZD3 command: OK");
//        }
//        if (!LANInterfaceLib.INSTANCE.topcon_COMSendBCC(comHandle, "T32")) {
//
//            System.out.println("Failed to send T32");
//        } else {
//            System.out.println("T32 command: OK");
//        }
    }

    @Override
    public boolean open(String deviceName) {
        return false;
    }

    /*　2021.07.16 n_otsuka@kinsoku.net 
    『トプコンDSに関する質問集_Ans2.docx』の下記回答に従えば、LineCheck() は応答時間が許容範囲内かどうかを確認するためのものと思われるので、 
    接続チェックの際には LineCheck() の結果は見ないことにする。 
     
    「A8．REM_ConnectCheckがFalseを返す場合は接続が完全に切れています。再開する場合はREM_Connectからやり直す必要があります。 
    　REM_LineCheckがFalseを返す場合は、接続が完全に切れた、または3秒以内に通信が確認出来なかった場合です。このAPIは通信状況を確認する為に使用します。」 
     */
    @Override
    public boolean isConnected() {
        return false;
    }

    @Override
    public boolean isConnectedEx() {
        return false;
    }

    private boolean openCom(int mode) {
        return false;
    }

    @Override
    public int send() {
        return 0;
    }

    @Override
    public int sendReceive() {
        return 0;
    }

    @Override
    public void reconnect() {
    }

    @Override
    public void close() {
    }

    private void closeCom() {
    }

    private void ConfirmReceiveBufferIsEmpty() {
    }

    private boolean isTSReachable() {
        return false;
    }

}
