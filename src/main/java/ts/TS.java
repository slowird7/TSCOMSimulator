/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ts;

import javafx.beans.property.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;

/**
 * @author n_otsuka
 */
public class TS {

    private final static Logger LOGGER = LogManager.getLogger(TS.class);

    public static TS INSTANCE;

    static {
        INSTANCE = new TS();
    }

    private final int IGNORABLE_TILTOVERS = 3;      // これ以上チルトオーバーが連続して検出されたらチルトオーバーが起きていると見做す回数
    private final int receiveNGCount = 0;
    private final int rotateNGCount = 0;
    private final int measureNGCount = 0;
    public LongProperty lastUpdate;
    public BooleanProperty receiveNG;    //コマンド送受信のフラグ　true：成功　false：失敗
    public BooleanProperty tiltover;    //trueがチルトオーバー発生、falseがチルト正常
    public BooleanProperty sendNG;
    public BooleanProperty rotateNG;
    public BooleanProperty measureNG;
    protected StringProperty currentTrackingFlag;   // 追尾フラグ(0=スタンバイ/1=追尾/2=サーチ/3=ウェイト/4=旋回サーチ中/5=旋回中/6=自動視準中)
    protected StringProperty currentStatus;         // 自動視準／旋回フラグ(0=自動視準・旋回正常終了／1=自動視準・旋回中／2=旋回失敗／3=キャンセル／4=自動視準失敗)
    protected DoubleProperty currentAngleHDMS;
    protected DoubleProperty currentAngleVDMS;
    protected DoubleProperty currentDistanceH_M;
    private int tiltoverCounter = 0;
    private int sendNGCount = 0;

    private TS() {
        currentTrackingFlag = new SimpleStringProperty("");
        currentStatus = new SimpleStringProperty();
        currentAngleHDMS = new SimpleDoubleProperty(0.);
        currentAngleVDMS = new SimpleDoubleProperty(0.);
        currentDistanceH_M = new SimpleDoubleProperty(Double.NaN);
        lastUpdate = new SimpleLongProperty(new Date().getTime());
        receiveNG = new SimpleBooleanProperty(true);
        tiltover = new SimpleBooleanProperty(false);
        sendNG = new SimpleBooleanProperty(true);
        rotateNG = new SimpleBooleanProperty(true);
        measureNG = new SimpleBooleanProperty(true);
    }

//    public void setCurrentAngleHDMS(double newDMS) {
//        this.currentAngleHDMS.set(newDMS);0.
//        lastUpdate.set(new Date().getTime());
//    }
//
    public String getCurrentTrackingFlag() {
        return currentTrackingFlag.get();
    }

    public String getCurrentStatus() {
        return currentStatus.get();
    }

    public double getCurrentAngleHDMS() {
        return currentAngleHDMS.get();
    }

//    public void setCurrentAngleVDMS(double newDMS) {
//        this.currentAngleVDMS.set(newDMS);
//        lastUpdate.set(new Date().getTime());
//    }
//
    public double getCurrentAngleVDMS() {
        return currentAngleVDMS.get();
    }

    public double getCurrentDistanceH_M() {
        return currentDistanceH_M.get();
    }

    public void updateCurrent(String trackingFlag, double horizontalAngleDMS, double verticalAngleDMS) {
        this.currentTrackingFlag.set(trackingFlag);
        this.currentAngleHDMS.set(horizontalAngleDMS);
        this.currentAngleVDMS.set(verticalAngleDMS);
        lastUpdate.set(new Date().getTime());
    }

    public void updateCurrent(String trackingFlag, double horizontalAngleDMS, double verticalAngleDMS, double distanceH_M) {
        this.currentTrackingFlag.set(trackingFlag);
        this.currentAngleHDMS.set(horizontalAngleDMS);
        this.currentAngleVDMS.set(verticalAngleDMS);
        this.currentDistanceH_M.set(distanceH_M);
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

    /**
     * @param a
     * @brief detect continuout tile over status.
     */
    public void settilt(boolean a) {
        if (!a) {
            tiltoverCounter = 0;
            tiltover.set(false);
        } else {
            tiltoverCounter++;
            if (tiltoverCounter > IGNORABLE_TILTOVERS) {
                tiltover.set(true);
                tiltoverCounter = 0;
            }
        }
    }

    public boolean gettilt() {
        return tiltover.get();
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
            if (sendNGCount > IGNORABLE_TILTOVERS) {
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

}
