package exception;

/**
 * 何らかのエラーにより測定値などが取得できなかった
 */
public class SendFailedException extends TSGeneralException {

    public SendFailedException(String msg) {
        super(msg);
    }

}
