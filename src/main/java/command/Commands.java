package command;

import connection.ConnectionMode;
import exception.ReceiveErrorException;
import exception.TSGeneralException;
import exception.TSNotConnectedException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import project.Property;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author kinsoku
 */
public abstract class Commands extends Thread {

    protected final static Logger logger = LogManager.getLogger(Commands.class);

    protected String connectionMode = Property.getInstance().getConnectionMode();
    protected ConnectionMode com;
    protected String receive;
    protected String receivedData;
    protected String command;
    protected String comSuc;
    protected int deadline = 150;
    protected StringBuffer buffer = new StringBuffer();
    protected boolean receivedACK = false;
    protected boolean receivedNACK = false;
    protected boolean receivedERR = false;
    protected boolean comp = false;
    protected Pattern p;
    protected Matcher m;

    public boolean isReceivedACK() {
        return receivedACK;
    }

    public boolean isReceivedNAK() {
        return receivedNACK;
    }

    public boolean isReceivedERR() {
        return receivedERR;
    }

    public void setTimeout(int dsec) {

    }

    public String getComSuc() {
        return null;
    }

    public void setReceive(String str) {
        this.receive = str;
    }

    public String getReceiveData() {
        return receivedData;
    }

    public void setReceiveData(String data) {
        receivedData = data;
    }

    public boolean getReceivedACK() {
        return receivedACK;
    }

    public void setReceivedACK(boolean set) {
        receivedACK = set;
    }

    public String getCommand() {
        return command;

    }

    public boolean match(String recv) {
        if (p == null) {
            return false;
        }
        return p.matcher(recv).matches();
    };

    public abstract void checkError() throws ReceiveErrorException;

    public abstract String makeResponse(String args);

}
