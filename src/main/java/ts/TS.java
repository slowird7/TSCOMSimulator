/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ts;

import environment.Room;
import javafx.beans.property.*;
import javafx.geometry.Point3D;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import point.AngleData;
import point.PointData;

import java.util.Date;

/**
 * @author n_otsuka
 */
public class TS {

    private final static Logger LOGGER = LogManager.getLogger(TS.class);

    public static TS INSTANCE;

    private PointData kikai, koushi, stakeout;

    private final int receiveNGCount = 0;
    private final int rotateNGCount = 0;
    private final int measureNGCount = 0;
    public LongProperty lastUpdate;
    public BooleanProperty receiveNG;    //コマンド送受信のフラグ　true：成功　false：失敗
    public BooleanProperty sendNG;
    public BooleanProperty rotateNG;
    public BooleanProperty measureNG;
    protected StringProperty trackingFlag;   // 追尾フラグ(0=スタンバイ/1=追尾/2=サーチ/3=ウェイト/4=旋回サーチ中/5=旋回中/6=自動視準中)
    protected StringProperty currentStatus;         // 自動視準／旋回フラグ(0=自動視準・旋回正常終了／1=自動視準・旋回中／2=旋回失敗／3=キャンセル／4=自動視準失敗)
    protected DoubleProperty angleHDMS;
    protected DoubleProperty angleVDMS;
    protected DoubleProperty distance_M;
    protected DoubleProperty eyeHeight_M;
    protected IntegerProperty targetType;
    protected DoubleProperty parameter;
    protected IntegerProperty diameter;

    protected IntegerProperty searchMode;
    protected IntegerProperty trackingMode;
    protected DoubleProperty HSearchRange;
    protected DoubleProperty VSearchRange;
    protected DoubleProperty tiltXDMS;
    protected DoubleProperty tiltYDMS;


    private int sendNGCount = 0;

    private TS() {
        trackingFlag = new SimpleStringProperty("");
        currentStatus = new SimpleStringProperty();
        angleHDMS = new SimpleDoubleProperty(0.);
        angleVDMS = new SimpleDoubleProperty(0.);
        eyeHeight_M = new SimpleDoubleProperty(0.);
        distance_M = new SimpleDoubleProperty(Double.NaN);
        lastUpdate = new SimpleLongProperty(new Date().getTime());
        receiveNG = new SimpleBooleanProperty(true);
        sendNG = new SimpleBooleanProperty(true);
        rotateNG = new SimpleBooleanProperty(true);
        measureNG = new SimpleBooleanProperty(true);
        targetType = new SimpleIntegerProperty(0);
        parameter = new SimpleDoubleProperty(0.);
        diameter = new SimpleIntegerProperty(0);
        searchMode = new SimpleIntegerProperty(0);
        trackingMode = new SimpleIntegerProperty(0);
        HSearchRange = new SimpleDoubleProperty(0.);
        VSearchRange = new SimpleDoubleProperty(0.);
        tiltXDMS = new SimpleDoubleProperty(0.);
        tiltYDMS = new SimpleDoubleProperty(0.);

        kikai = new PointData(0., 0., 0.);
        stakeout = new PointData(0., 0., 0.);
    }

    public static TS getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TS();
        }
        return INSTANCE;
    }

    public void setKikai(PointData newKikai) {
        kikai.setX(newKikai.getX());
        kikai.setY(newKikai.getY());
        kikai.setZ(newKikai.getZ());
    }
    public PointData getKikai() {
        return kikai;
    }

    public PointData getKoushi() {
        return koushi;
    }

    public PointData getStakeout() {
        return stakeout;
    }

    //    public void setCurrentAngleHDMS(double newDMS) {
//        this.angleHDMS.set(newDMS);0.
//        lastUpdate.set(new Date().getTime());
//    }
//
    public String getTrackingFlag() {
        return trackingFlag.get();
    }

    public String getCurrentStatus() {
        return currentStatus.get();
    }

    public double getAngleHDMS() {
        return angleHDMS.get();
    }

    public double getAngleVDMS() {
        return angleVDMS.get();
    }

    public Double getDistance_M() {
        double distance = Room.getInstance().getDistance();
        return distance;
    }

    public Double getEyeHeight() {
        return eyeHeight_M.get();
    }

    public Point3D getDirection() {
        double x = Math.sin(AngleData.DMS2RAD(angleVDMS.get()))*Math.cos(AngleData.DMS2RAD(angleHDMS.get()));
        double y = Math.sin(AngleData.DMS2RAD(angleVDMS.get()))*Math.sin(AngleData.DMS2RAD(angleHDMS.get()));
        double z = Math.cos(AngleData.DMS2RAD(angleVDMS.get()));
        return new Point3D(x, y, z).normalize();
    }

    public void updateCurrent(double horizontalAngleDMS, double verticalAngleDMS) {
        this.angleHDMS.set(horizontalAngleDMS);
        this.angleVDMS.set(verticalAngleDMS);
        lastUpdate.set(new Date().getTime());
    }
    public void updateCurrent(String trackingFlag, double horizontalAngleDMS, double verticalAngleDMS) {
        this.trackingFlag.set(trackingFlag);
        this.angleHDMS.set(horizontalAngleDMS);
        this.angleVDMS.set(verticalAngleDMS);
        lastUpdate.set(new Date().getTime());
    }

    public void updateCurrent(String trackingFlag, double horizontalAngleDMS, double verticalAngleDMS, double distanceH_M) {
        this.trackingFlag.set(trackingFlag);
        this.angleHDMS.set(horizontalAngleDMS);
        this.angleVDMS.set(verticalAngleDMS);
        this.distance_M.set(distanceH_M);
        lastUpdate.set(new Date().getTime());
    }

    public void updateStatus(String status) {
        this.currentStatus.set(status);
        lastUpdate.set(new Date().getTime());
    }

    public void monitor(boolean on) {
        if (on) {

        }
    }


    public boolean isSendNG() {
        return sendNG.get();
    }

    public void setSendNG(boolean a) {
        if (!a) {
            sendNGCount = 0;
            sendNG.set(false);
        } else {
            sendNGCount++;
            if (sendNGCount > 1) {
                sendNG.set(a);
                sendNGCount = 0;
            }
        }
    }

    public boolean isReceiveNG() {
        return receiveNG.get();
    }

    public void setReceiveNG(boolean a) {
        receiveNG.set(a);
    }

    public int getTargetType() {
        return targetType.get();
    }

    public IntegerProperty targetTypeProperty() {
        return targetType;
    }

    public void setTargetType(int targetType) {
        this.targetType.set(targetType);
    }


    public double getParameter() {
        return parameter.get();
    }

    public DoubleProperty parameterProperty() {
        return parameter;
    }

    public void setParameter(double parameter) {
        this.parameter.set(parameter);
    }


    public int getDiameter() {
        return diameter.get();
    }

    public IntegerProperty diameterProperty() {
        return diameter;
    }

    public void setDiameter(int diameter) {
        this.diameter.set(diameter);
    }

    public int getSearchMode() {
        return searchMode.get();
    }

    public IntegerProperty searchModeProperty() {
        return searchMode;
    }

    public void setSearchMode(int searchMode) {
        this.searchMode.set(searchMode);
    }

    public int getTrackingMode() {
        return trackingMode.get();
    }

    public IntegerProperty trackingModeProperty() {
        return trackingMode;
    }

    public void setTrackingMode(int trackingMode) {
        this.trackingMode.set(trackingMode);
    }

    public double getHSearchRange() {
        return HSearchRange.get();
    }

    public DoubleProperty HSearchRangeProperty() {
        return HSearchRange;
    }

    public void setHSearchRange(double HSearchRange) {
        this.HSearchRange.set(HSearchRange);
    }

    public double getVSearchRange() {
        return VSearchRange.get();
    }

    public DoubleProperty VSearchRangeProperty() {
        return VSearchRange;
    }

    public void setVSearchRange(double VSearchRange) {
        this.VSearchRange.set(VSearchRange);
    }

    public double getTiltXDMS() {
        return tiltXDMS.get();
    }

    public DoubleProperty tiltXDMSProperty() {
        return tiltXDMS;
    }

    public void setTiltXDMS(double tiltXDMS) {
        this.tiltXDMS.set(tiltXDMS);
    }

    public double getTiltYDMS() {
        return tiltYDMS.get();
    }

    public DoubleProperty tiltYDMSProperty() {
        return tiltYDMS;
    }

    public void setTiltYDMS(double tiltYDMS) {
        this.tiltYDMS.set(tiltYDMS);
    }
}
