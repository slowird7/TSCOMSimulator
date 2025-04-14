/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author n_otsuka
 */
public class TSGeneralException extends Exception {

    private final static Logger logger = LogManager.getLogger(TSGeneralException.class);

    public TSGeneralException() {
        super();
    }

    public TSGeneralException(String str) {
        super(str);
        logger.warn(str);
    }
}
