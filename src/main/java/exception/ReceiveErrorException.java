package exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ReceiveErrorException extends TSGeneralException {

    private final static Logger logger = LogManager.getLogger(ReceiveErrorException.class);

    public ReceiveErrorException(String str) {
        super(str);
        if (str.equals("E114") || str.equals("E115") || str.equals("E116") || str.equals("E117")) {
            logger.error("器械の傾きを整準し直してください。");
        } else if (str.equals("E200")) {
            logger.error("測定条件が悪い可能性があります。");
        } else if (str.equals("E210")) {
            logger.error("信号なし。反射光が弱くなったか遮断されました。");
        } else if (str.equals("E211")) {
            logger.error("受光エラー。測定条件に問題があります。");
        } else if (str.equals("E212") || str.equals("E214")) {
            logger.error("視準エラー。測定条件に問題があります。");
        } else if (str.equals("E215")) {
            logger.error("温度範囲外。使用温度範囲内で測定してください。");
        }
    }
}
