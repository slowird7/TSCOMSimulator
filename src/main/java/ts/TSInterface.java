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

/**
 * @author n_otsuka
 */
public class TSInterface {

    private final static Logger LOGGER = LogManager.getLogger(TSInterface.class);

    private final COMPortConnection connection;
    private final Deque<String> commandQue;
    private final Deque<String> responseQue;
    private final CommandTable commandTable;
    private final TS ts;

    private final String responseLast = null;
    private volatile boolean running = false;
    private final boolean isContinuous = false;
    private boolean isRepeating = false;
    private double hDistSurvey;
    private final double errX = 0.001;
    private final double errY = 0.002;
    private final double errZ = 0.;

    public TSInterface(TS ts) {
        this.ts = ts;
        commandTable = new CommandTable("C:/Users/n_otsuka/Documents/Git_GitHub/TSCOMSimulator/target/classes/sokkiacommand", this);
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

    public TS getTS() {
        return ts;
    }

    public boolean open(String id) {
        // start COM thread
        TSCommandProcesser th = new TSCommandProcesser();
        LOGGER.debug("COM thread START #1.");
        th.start();

        return connection.open(id);
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
        isRepeating = false;

        if (commandStr.isEmpty()) {
            return;
        }
        responseQue.add(commandTable.dispatchCommand(commandStr));
    }

    synchronized public void respond(String response) {
        responseQue.add(response);
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
