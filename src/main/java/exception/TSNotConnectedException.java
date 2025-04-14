/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exception;

/**
 * @author otsuka@kinsoku
 */
public class TSNotConnectedException extends TSGeneralException {

    public TSNotConnectedException(String msg) {
        super(msg);
    }

    public TSNotConnectedException() {
        super();
    }

}
