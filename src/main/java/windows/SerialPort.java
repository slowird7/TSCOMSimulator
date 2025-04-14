/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package windows;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

/**
 * @author kinsoku
 */
public class SerialPort {

    private final static Logger logger = LogManager.getLogger(SerialPort.class);
    public static ArrayList<SerialPort> serviceFound = new ArrayList();
    private static SerialPort serialPort;
    public String pnpDeviceID = null;
    public String deviceName = null;
    public String service = null;
    private String name = null;
    private String deviceID = null;

    public SerialPort(String deviceID, String name, String pnpDeviceID, String service) {
        this.deviceID = deviceID;
        this.name = name;
        this.pnpDeviceID = pnpDeviceID;
        this.service = service;
    }

    public static void main(String[] args) {
    }

    public String toString() {
        String str;
        if (deviceName == null) {
            str = String.format("%s:%s", deviceID, name);
        } else {
            str = String.format("%s:%s:%s", deviceID, deviceName, name);
        }
        return str;
    }
}
