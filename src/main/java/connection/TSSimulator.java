/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;

import command.CommandTable;
import command.Commands;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import point.AngleData;
import point.PointData;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author n_otsuka
 */
public class TSSimulator {

    private final static Logger LOGGER = LogManager.getLogger(TSSimulator.class);

    private  COMPortConnection connection;
    private final Deque<String> commandQue;
    private final Deque<String> responseQue;
    private CommandTable commandTable;

    private String responseLast = null;
    private boolean running = false;
    private boolean isContinuous = false;
    private boolean isRepeating = false;
    private String command13HResponse = "";
    private String command14HResponse = "";
    private String commandEbResponse = "Eb 0000,0,0.000,0,64.964,89.5959,359.5959\n";
    private String commandDbResponse = null;
    private String commandGbResponse = null;
    private PointData kikai, koushi, stakeout;
    private double hDistSurvey;
    private double errX = 0.001, errY = 0.002, errZ = 0.;

    public TSSimulator() {
        this.commandQue = new ArrayDeque<>();
        this.responseQue = new ArrayDeque<>();
        commandTable = new CommandTable("C:/Users/otsuka/Documents/GitHub/TSCOMSimulator/target/classes/sokkiacommand");
        try {
            commandTable.loadSubclasses();
        } catch (IOException ex) {
            LOGGER.error("TSSimulator>", ex);
        }
        connection = new COMPortConnection(commandQue, responseQue);
        kikai = new PointData(0., 0., 0.);
        stakeout = new PointData(0., 0., 0.);
        //TextInputDialog textIn = new TextInputDialog("0.0");
        //String str = textIn.showAndWait().orElse("");
    }

    public boolean open(String id) {
        // start COM thread
        processer th = new processer();
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


    synchronized public void pushCommand(String command) {
        commandQue.add(command);
    }

    synchronized private String retrieveCommand() {
        return commandQue.remove();
    }

    synchronized private void processCommand() {
        String commandStr;
        try {
            commandStr = retrieveCommand();
        } catch (NoSuchElementException ex) {
            //LOGGER.error("Error Message", ex);
            commandStr = "";
        }

        isRepeating = false;

        if (commandStr.isEmpty()) {
            return;
        }
        Matcher mm = Pattern.compile("(^[^ ]+) (.*)").matcher(commandStr);
        if (!mm.matches())  {
            return;
        }
        String commandId = mm.group(1);
        String args = mm.group(2);
        responseQue.add(commandTable.dispatchCommand(commandId, args));


/*
        if (commandStr.startsWith("19")) {
            if (command13HResponse.isEmpty()) {
                command13HResponse = "00000000 09000000 06000000\n";
            }
            responseQue.add(command13HResponse);
            isRepeating = false;
        } else if (commandStr.startsWith("20")) {
            responseQue.add(command14HResponse);
            isRepeating = false;
        } else if (commandStr.startsWith("A")) {
            responseQue.add(commandAResponse);
            isRepeating = false;
        } else if (commandStr.startsWith("Db")) {
            responseQue.add(commandDbResponse);
            isRepeating = false;
        } else if (commandStr.startsWith("Da")) {   // 器械点を返す
            responseQue.add(commandDaResponse());
            isRepeating = false;
        } else if (commandStr.startsWith("Df")) {
            responseQue.add(String.format("Df %.3f,%.3f,%.3f\n", stakeout.getX(), stakeout.getY(), stakeout.getZ()));
            isRepeating = false;
        } else if (commandStr.startsWith("Eb")) {
//            PointData s = LoopMeasure.getInstance().getKoushi();
            PointData s = null;
            if (s != null) {
                stakeout = s;
                stakeout.setX(s.getX() + (Math.random() - 0.5));
                stakeout.setY(s.getY() + (Math.random() - 0.5));
            }
            updateResponses();
            responseQue.add(commandEbResponse);
            isRepeating = isContinuous;
            responseLast = commandEbResponse;
        } else if (commandStr.startsWith("Gb")) {
            responseQue.add(commandGbResponse);
            isRepeating = isContinuous;
            responseLast = commandGbResponse;
        } else if (commandStr.startsWith("/Da")) {
            try {
                Matcher mm = Pattern.compile("/Da (\\-?[0-9\\.]+),(\\-?[0-9\\.]+),(\\-?[0-9\\.]+)").matcher(commandStr);
                if (mm.find()) {
                    double x = Double.parseDouble(mm.group(1));
                    double y = Double.parseDouble(mm.group(2));
                    double z = Double.parseDouble(mm.group(3));
                    kikai = new PointData(x, y, z);
                    updateResponses();
                }
            } catch (NumberFormatException ex) {
                LOGGER.error("Error Message", ex);
                System.out.println("内部エラー:" + ex.getMessage());
            }
            responseQue.add("\u0006");
        } else if (commandStr.startsWith("/Da")) {
            try {
                Matcher mm = Pattern.compile("/Da (\\-?[0-9\\.]+),(\\-?[0-9\\.]+),(\\-?[0-9\\.]+)").matcher(commandStr);
                if (mm.find()) {
                    double x = Double.parseDouble(mm.group(1));
                    double y = Double.parseDouble(mm.group(2));
                    double z = Double.parseDouble(mm.group(3));
                    kikai = new PointData(x, y, z);
                    updateResponses();
                }
            } catch (NumberFormatException ex) {
                LOGGER.error("Error Message", ex);
                System.out.println("内部エラー:" + ex.getMessage());
            }
            responseQue.add("\u0006");
        } else if (commandStr.startsWith("/Dd")) {
            try {
                Matcher mm = Pattern.compile("/Dd (\\-?[0-9\\.]+),(\\-?[0-9\\.]+),(\\-?[0-9\\.]+)").matcher(commandStr);
                if (mm.find()) {
                    double x = Double.parseDouble(mm.group(1));
                    double y = Double.parseDouble(mm.group(2));
                    double z = Double.parseDouble(mm.group(3));
                    koushi = new PointData(x, y, z);
                    //updateResponses();
                }
            } catch (NumberFormatException ex) {
                LOGGER.error("Error Message", ex);
                System.out.println("内部エラー:" + ex.getMessage());
            }
            responseQue.add("\u0006");
        } else if (commandStr.startsWith("/Df")) {
            try {
                Matcher mm = Pattern.compile("/Df (\\-?[0-9\\.]+),(\\-?[0-9\\.]+),(\\-?[0-9\\.]+)").matcher(commandStr);
                if (mm.find()) {
                    double x = Double.parseDouble(mm.group(1));
                    double y = Double.parseDouble(mm.group(2));
                    double z = Double.parseDouble(mm.group(3));
                    stakeout = new PointData(x, y, z);
                    updateResponses();
                }
            } catch (NumberFormatException ex) {
                LOGGER.error("Error Message", ex);
                System.out.println("内部エラー:" + ex.getMessage());
            }
            responseQue.add("\u0006");
        } else if (commandStr.startsWith("X")) {
            responseQue.add("\u0006");
            if (commandStr.startsWith("Xb") || commandStr.startsWith("Xd") || commandStr.startsWith("Xe")) {
                isContinuous = true;
            } else if (commandStr.startsWith("Xa") || commandStr.startsWith("Xc")) {
                isContinuous = false;
            }
        } else if (commandStr.startsWith("*DHA")) {
            Matcher mm = Pattern.compile("\\*DHA(\\-?[0-9]+)VA(\\-?[0-9]+)").matcher(commandStr);
            if (mm.find()) {
                double hAngleDMS = Integer.parseInt(mm.group(1)) * 0.0001;
                double vAngleDMS = Integer.parseInt(mm.group(2)) * 0.0001;
                double x = kikai.getX() + ts.TS.INSTANCE.getCurrentDistanceH_M() * Math.cos(AngleData.DMS2RAD(hAngleDMS));
                double y = kikai.getY() + ts.TS.INSTANCE.getCurrentDistanceH_M() * Math.sin(AngleData.DMS2RAD(hAngleDMS));
                double z = kikai.getZ();
                if (vAngleDMS != 0.) {
                    z += ts.TS.INSTANCE.getCurrentDistanceH_M() / Math.tan(AngleData.DMS2RAD(vAngleDMS));
                }
//                ts.TS.INSTANCE.setCurrentDistanceH_M(z);
                stakeout = new PointData(x, y, z);
                updateResponses();
                ts.TS.INSTANCE.updateCurrent("1", hAngleDMS, vAngleDMS, hDistSurvey);
            }
            responseQue.add("\u0006");
            responseQue.add("OK\n");
        } else if (commandStr.startsWith("*DHI")) {
            responseQue.add("\u0006");
            responseQue.add("OK\n");
        } else if (commandStr.startsWith("*Q")) {
            responseQue.add("\u0006");
        } else if (commandStr.startsWith("* /PG")) {
            responseQue.add("\u0006");
        } else if (commandStr.startsWith("*SJ")) {
            responseQue.add("\u0006");
            responseQue.add("OK\n");
        } else if (commandStr.startsWith("*JG")) {
            responseQue.add("\u0006");
            responseQue.add("OK\n");
        } else if (commandStr.startsWith("ZD3")) {
            responseQue.add("\u0006");
            responseQue.add("OK\n");
        } else if (commandStr.startsWith("ZC4")) {
            responseQue.add("\u0006");
            responseQue.add("OK\n");
        } else if (commandStr.startsWith("C50")) {
            responseQue.add("\u0006");
            responseQue.add("Q624101\n");
        }
*/
    }


    private void updateResponses() {

        //if (kikai == null || koushi == null) {
        if (kikai == null || stakeout == null) {
            return;
        }

        double diffX = stakeout.getX() - kikai.getX();
        double diffY = stakeout.getY() - kikai.getY();
        double diffZ = stakeout.getZ() - kikai.getZ();
        double hDistDesign = Math.sqrt(Math.pow(diffX, 2) + Math.pow(diffY, 2));
        double vAngleDesign = Math.atan2(hDistDesign, diffZ);
        double hAngleDesign = Math.atan2(diffY, diffX);
        hDistSurvey = Math.sqrt(Math.pow(diffX + errX, 2) + Math.pow(diffY + errY, 2));
        double vAngleSurvey = Math.atan2(hDistDesign, diffZ + errZ);
        double hAngleSurvey = Math.atan2(diffY + errY, diffX + errX);
/*
        nm = NormalMeasure.getInstance();
        bi = BackwardIntersection.getInstance();
        if (SetupTSSceneController.INSTANCE.getTsujo()) {
            hAngleDesign -= nm.getAngle();
            hAngleSurvey -= nm.getAngle();
        } else if (bi.getKoushi1() != null && bi.getKoushi2() != null && kikai != null) {
            hAngleDesign -= bi.getAngle();
            //+ Math.atan2(bi.getKoushi1().getY() - kikai.getY(), bi.getKoushi1().getX() - kikai.getX())); // bi
            hAngleSurvey -= bi.getAngle();
            //+ Math.atan2(bi.getKoushi1().getY() - kikai.getY(), bi.getKoushi1().getX() - kikai.getX())); // bi
        }

 */
        while (hAngleSurvey < 0) {
            hAngleSurvey += 2. * Math.PI;
        }
        while (hAngleSurvey >= 2. * Math.PI) {
            hAngleSurvey -= 2. * Math.PI;
        }
        command13HResponse = String.format("%08d %s %s\n", 0, AngleData.DMS2FIX7(vAngleSurvey), AngleData.DMS2FIX7(hAngleSurvey));
        command14HResponse = String.format("%08d %s %s\n", (int) (hDistSurvey * 10000.), AngleData.DMS2FIX7(vAngleSurvey), AngleData.DMS2FIX7(hAngleSurvey));
        commandDbResponse = String.format("Db %.4f,%.4f\n", hDistDesign, AngleData.RAD2DMS(hAngleDesign));
        commandEbResponse = String.format("Eb 0000,0,0,4,%.3f,%.4f,%.4f\n", hDistSurvey, AngleData.RAD2DMS(vAngleSurvey), AngleData.RAD2DMS(hAngleSurvey));
        commandGbResponse = String.format("Gb %.4f,%.4f\n", 0., hDistSurvey);
    }

    public void shiftErr(double x, double y, double z) {
        errX = x;
        errY = y;
        errZ = z;
        updateResponses();
    }

    private class processer extends Thread {

        processer() {
            super();
            setName("TSSimulator");
        }

        public void run() {
            LOGGER.debug("Start TSSimulation processer thread.");
            running = true;
            while (running) {
                if (commandQue.peek() != null) {
                    processCommand();
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
            LOGGER.info("TSSimulator::process thread stopped.");
        }
    }

    // 器械点座標値の出力
    private String commandDaResponse() {
        if (kikai == null) {
            return ("\u0015");
        }
        return String.format("Da %f,%f,%f\n", kikai.getX(), kikai.getY(), kikai.getZ());
    }
}
