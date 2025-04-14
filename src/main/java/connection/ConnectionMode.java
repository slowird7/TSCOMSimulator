package connection;

import command.Commands;
import exception.ReceiveFailedException;
import exception.SendFailedException;
import exception.TSNotConnectedException;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import project.Property;

/**
 * @author kinsoku
 */
public abstract class ConnectionMode {

    private final static Logger logger = LogManager.getLogger(COMPortConnection.class);
    public static ConnectionMode instance;
    private static boolean commandStatus;
    protected final ObjectProperty<STATE> state = new SimpleObjectProperty<>(STATE.DISCONNECT);
    public boolean isReceivedNG;
    protected Commands commands;

    public static ConnectionMode getInstance() {
        return instance;
    }

    public STATE getState() {
        return state.getValue();
    }

    public void setState(STATE newState) {
        this.state.set(newState);
    }

    public ObjectProperty<STATE> stateProperty() {
        return state;
    }

    public void setCommand(Commands cmd) {
        this.commands = cmd;
    }

    public abstract boolean open(String name) throws TSNotConnectedException;

    public abstract boolean isConnected();

    public abstract boolean isConnectedEx();

    public abstract int send() throws TSNotConnectedException, SendFailedException;

    public abstract int sendReceive() throws TSNotConnectedException, SendFailedException, ReceiveFailedException;

    public abstract void reconnect();

    public abstract void close();

//    public String getDevice() {
//	return Property.getInstance().getMachine();
//    }
    public boolean isReceivedNG() {
        return isReceivedNG;
    }

    public enum STATE {
        UNDEF, // 未定義
        DISCONNECT, // 未接続
        CONNECTED, // 接続済み
        SENDING, // 送信中
        WAITING; // 受信待ち

        STATE() {
        }

        @Override
        public String toString() {
            switch (this) {
                case UNDEF:
                    return "？";
                case DISCONNECT:
                    return java.util.ResourceBundle.getBundle("resource/messages").getString("切断中");
                case CONNECTED:
                    return java.util.ResourceBundle.getBundle("resource/messages").getString("接続済み");
                case SENDING:
                    return java.util.ResourceBundle.getBundle("resource/messages").getString("送信中");
                case WAITING:
                    return java.util.ResourceBundle.getBundle("resource/messages").getString("受信待ち");
                default:
                    return "？";
            }
        }
    } //end of STATE

}
