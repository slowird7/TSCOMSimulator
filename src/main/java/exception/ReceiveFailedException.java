package exception;

/**
 * 何らかのエラーにより測定値などが取得できなかった
 */
public class ReceiveFailedException extends TSGeneralException {

    public ReceiveFailedException(String msg) {
        super(msg);
    }

    public ReceiveFailedException() {
        super();
    }
}
