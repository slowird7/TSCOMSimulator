package exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AlreadyBootedException extends RuntimeException {

    private final static Logger logger = LogManager.getLogger(AlreadyBootedException.class);

    public AlreadyBootedException() {

        super("プログラムの多重起動は出来ません。");

        logger.info("AlreadyBootedException Message", AlreadyBootedException.this);

    }
}
