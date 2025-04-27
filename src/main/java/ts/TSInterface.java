/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ts;

import command.CommandTable;
import connection.COMPortConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Deque;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author n_otsuka
 */
public class TSInterface {

    private final static Logger LOGGER = LogManager.getLogger(TSInterface.class);

    private COMPortConnection connection;
    private final Deque<String> commandQue;
    private final Deque<String> responseQue;
    private CommandTable commandTable;
    private TS ts;

    private String responseLast = null;
    private volatile boolean running = false;
    private boolean isContinuous = false;
    private boolean isRepeating = false;
    private double hDistSurvey;
    private double errX = 0.001, errY = 0.002, errZ = 0.;

    public TSInterface() {
        commandTable = new CommandTable("C:/Users/otsuka/Documents/GitHub/TSCOMSimulator/target/classes/sokkiacommand");
        try {
            commandTable.loadCommands();
        } catch (IOException ex) {
            LOGGER.error("TSSimulator>", ex);
        }
        connection = new COMPortConnection();
        this.commandQue = connection.getCommandQue();
        this.responseQue = connection.getResponseQue();
        //TextInputDialog textIn = new TextInputDialog("0.0");
        //String str = textIn.showAndWait().orElse("");
    }

    public boolean open(String id) {
        // start COM thread
        TSCommandProcesser th = new TSCommandProcesser();
        LOGGER.debug("COM thread START #1.");
        th.start();

        if (!connection.open(id)) {
            return false;
        }


        return true;
    }

    public void close() {
        running = false;
        connection.close();
    }

    public boolean isConnected() {
        return running;
    }

    public boolean isConnectedEx() {
        return running;
    }

    synchronized private void processCommand(String commandStr) {
        ;
        isRepeating = false;

        if (commandStr.isEmpty()) {
            return;
        }
        responseQue.add(commandTable.dispatchCommand(commandStr));
        return;
    }


    private void updateResponses() {

        double diffX = 0.;
        double diffY = 0.;
        double diffZ = 0.;
        double hDistDesign = Math.sqrt(Math.pow(diffX, 2) + Math.pow(diffY, 2));
        double vAngleDesign = Math.atan2(hDistDesign, diffZ);
        double hAngleDesign = Math.atan2(diffY, diffX);
        hDistSurvey = Math.sqrt(Math.pow(diffX + errX, 2) + Math.pow(diffY + errY, 2));
        double vAngleSurvey = Math.atan2(hDistDesign, diffZ + errZ);
        double hAngleSurvey = Math.atan2(diffY + errY, diffX + errX);
        while (hAngleSurvey < 0) {
            hAngleSurvey += 2. * Math.PI;
        }
        while (hAngleSurvey >= 2. * Math.PI) {
            hAngleSurvey -= 2. * Math.PI;
        }
    }

    private class TSCommandProcesser extends Thread {

        TSCommandProcesser() {
            super();
            setName("TSCommandProcessor");
        }

        public void run() {
            LOGGER.debug("=== TSCommandProcesser start.");
            running = true;
            while (running) {
                if (commandQue.peek() != null) {
                    processCommand(commandQue.remove());
                } else if (isRepeating) {
                    responseQue.add(responseLast);
                }
//                emitResponse();
                try {
                    sleep(500);
                } catch (InterruptedException ex) {
                    LOGGER.error("Error Message", ex);
                    running = false;
                }
            }
            LOGGER.info("=== TSCommandProcessor stopped.");
        }
    }
}
