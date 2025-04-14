package exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author a_evan
 */
public class DuplicateEntryException extends RuntimeException {

    private final static Logger logger = LogManager.getLogger(AlreadyBootedException.class);

    public DuplicateEntryException() {

        super("同じ点名の読み込みは出来ません。");

        logger.info("DuplicateEntryException Message", DuplicateEntryException.this);

    }

}
