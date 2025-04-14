package project;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.file.Path;
import java.util.*;

/**
 * 設定： [port]	COMポート名 [current]	カレントディレクトリ [autocollimate]	首振りのon/off [lately]
 * 最近開いたファイル [machine]	器械名
 */
public class Property {

    private final static Logger LOGGER = LogManager.getLogger(Property.class);
    private static final Property INSTANCE = new Property();
    private final Properties ppt;
    private final String PROPERTY_PATH = "conf.xml";

    private Property() {
        ppt = new Properties();
    }

    public static Property getInstance() {
        return INSTANCE;
    }

    /**
     * conf.xmlをロードし、設定を取得する conf.xmlが存在しない場合は新たに作成する
     */
    public void load() {

        try ( InputStream is = new FileInputStream(PROPERTY_PATH)) {
            ppt.loadFromXML(is);

            String port = getPort();
            if (port.equals("NULL")) {
                port = (ResourceBundle.getBundle("resource/messages").getString("未設定"));
            }
            String machine = getMachine();
            if (machine.equals("NULL")) {
                machine = (ResourceBundle.getBundle("resource/messages").getString("未設定"));
            }

        } catch (FileNotFoundException e) {
            LOGGER.info("load", e);

            ppt.setProperty("autocollimate", "on");
            ppt.setProperty("port", "NULL");
            ppt.setProperty("machine", "NULL");
            ppt.setProperty("simulation", "off");
            ppt.setProperty("lately", "");
            ppt.setProperty("connectionmode", "WiFi");
            ppt.setProperty("measuremode", "ワンマン観測モード");
            ppt.setProperty("current", "C:\\");
            ppt.setProperty("cameraNo", "1");
            ppt.setProperty("machinePoint", "NULL");
            ppt.setProperty("machineAngle", "0.0000");
            ppt.setProperty("backwardIntersection", "NULL");
            ppt.setProperty("getUseMeasureHeightSystemValue", "on");
            ppt.setProperty("enableReplayButton", "on");
            ppt.setProperty("leaningLimitCircle", "on");
            ppt.setProperty("Mport", "587");
            ppt.setProperty("userName", "kinsoku.kingp@gmail.com");
            ppt.setProperty("host", "smtp.gmail.com");
            ppt.setProperty("from", "kinsokukaihatsu@gmail.com");
            ppt.setProperty("password", "kinsoku7710");
            ppt.setProperty("sendAddress", "kinsoku.kingp@gmail.com");
            ppt.setProperty("searchRange", "5");
            
            store();

//			// 最近開いたファイルの設定
//			if(getLately()!=null){
//				UniqueFC.getInstance().setMenuItems();
//			}else{
//			}
        } catch (IOException e) {
            LOGGER.info("load", e);
        }
    } //end of load

    public Path getCurrent() {
        File f = new File(ppt.getProperty("current", "\\"));

        if (f.exists()) {
            return f.toPath();
        } else {
            return new File("\\").toPath();
        }
    }

    public void setCurrent(String str) {
        ppt.setProperty("current", str);
    }

    public String getOffsetUnit() {
        return ppt.getProperty("offsetunit", "mm");
    }

    public void setOffsetUnit(String unit) {
        ppt.setProperty("offsetunit", unit);
    }

    public String getMeasureMode() {
        if (getSimulation()) {
            return "通常観測モード";
        }
        return ppt.getProperty("measuremode", "通常観測モード");
    }

    public void setMeasureMode(String mode) {
        ppt.setProperty("measuremode", mode);
    }

    public String getConnectionMode() {
        if (getSimulation()) {
            return "Bluetooth";
        }
        return ppt.getProperty("connectionMode", "WiFi");
    }

    public void setConnectionMode(String mode) {
        ppt.setProperty("connectionMode", mode);
    }

    public String getPort() {
        return ppt.getProperty("port", "NULL");
    }

    public void setPortAndMachine(String com, String machine) {
        ppt.setProperty("port", com);
        ppt.setProperty("machine", machine);
        //BluetoothController.setPortAndMachine(com, machine);
    }

    public String getMachine() {
        return ppt.getProperty("machine", "NULL");
    }

    public void setMachine(String machine) {
        ppt.setProperty("machine", machine);
        //WLanController.setDevice(machine);
    }

    public String getSearchPattern() {
        return ppt.getProperty("searchpattern", "パターン 1");
    }

    public void setSearchPattern(String pattern) {
        ppt.setProperty("searchpattern", pattern);
    }

    public String getCollimationAccuracy() {
        return ppt.getProperty("collimationaccuracy", "高速");
    }

    public void setCollimationAccuracy(String accuracy) {
        ppt.setProperty("collimationaccuracy", accuracy);
    }

    public String getHCollimationRange() {
        return ppt.getProperty("hcollimationrange", "2");
    }

    public void setHCollimationRange(String range) {
        ppt.setProperty("hcollimationrange", range);

    }

    public String getVCollimationRange() {
        return ppt.getProperty("vcollimationrange", "2");
    }

    public void setVCollimationRange(String range) {
        ppt.setProperty("vcollimationrange", range);
    }

    public String getTextColor() {
        return ppt.getProperty("textcolor", "black");
    }

    public void setTextColor(String color) {
        ppt.setProperty("textcolor", color);
    }

    public String getIsMotorSupport() {
        return ppt.getProperty("motorsupport", "false");
    }

    public void setIsMotorSupport(String support) {
        ppt.setProperty("motorsupport", support);
    }

    public String getUserName() {
        return ppt.getProperty("userName");
    }

    public String getPassword() {
        return ppt.getProperty("password");
    }

    public int getMport() {
        return Integer.parseInt(ppt.getProperty("Mport"));
    }

    public String getHost() {
        return ppt.getProperty("host");
    }

    public String getFrom() {
        return ppt.getProperty("from");
    }

    public String getsendAddress() {
        return ppt.getProperty("sendAddress");
    }

    public boolean isBackwardIntersection() {
        return ppt.getProperty("backwardIntersection", "on").equals("on");
    }

    public void setBackwardIntersection(boolean isBackwardIntersection) {
        if (isBackwardIntersection) {
            ppt.setProperty("backwardIntersection", "on");
        } else {
            ppt.setProperty("backwardIntersection", "off");
        }
    }

    public long getMachinePointId() {
        return Long.parseLong(ppt.getProperty("machinePoint", "0"));
    }

    public void setMachinePointId(long machinePointId) {
        ppt.setProperty("machinePoint", String.valueOf(machinePointId));
    }

    public long getBacksitePointId() {
        return Long.parseLong(ppt.getProperty("backsitePoint", "0"));
    }

    public void setBacksitePointId(long backsitePointId) {
        ppt.setProperty("backsitePoint", String.valueOf(backsitePointId));
    }

//    public double getMachineY() {
//        return Double.parseDouble(ppt.getProperty("machineY", "0."));
//    }
//    
//    public double getMachineZ() {
//        return Double.parseDouble(ppt.getProperty("machineZ", "0."));
//    }
//    
    public double getMachineAngle() {
        return Double.parseDouble(ppt.getProperty("machineAngle", "0."));
    }

    public void setMachineAngle(double angle) {
        ppt.setProperty("machineAngle", String.format("%.5f", angle));
    }
    


//    public void setMachineXYZAngle(double x, double y, double z, double angle) {
//        ppt.setProperty("machineX", String.format("%.4f", x));
//        ppt.setProperty("machineY", String.format("%.4f", y));
//        ppt.setProperty("machineZ", String.format("%.4f", z));
//        ppt.setProperty("machineAngle", String.format("%.4f", angle));
//    }
    public Boolean getUseMeasureHeightSystemValue() {
        return ppt.getProperty("useMeasureHeightSystemValue", "on").equals("on");
    }

    public void setUseMeasureHeightSystemValue(boolean on) {
        if (on) {
            ppt.setProperty("useMeasureHeightSystemValue", "on");
        } else {
            ppt.setProperty("useMeasureHeightSystemValue", "off");
        }
    }

    public Boolean isEnableReplayButton() {
        return ppt.getProperty("enableReplayButton", "off").equals("on");
    }

    public void setEnableReplayButton(boolean on) {
        if (on) {
            ppt.setProperty("enableReplayButton", "on");
        } else {
            ppt.setProperty("enableReplayButton", "off");
        }
    }

    public Boolean isLeaningLimitCircleOn() {
        return ppt.getProperty("leaningLimitCircle", "on").equals("on");
    }

    public void setLeaningLimitCircle(boolean on) {
        if (on) {
            ppt.setProperty("leaningLimitCircle", "on");
        } else {
            ppt.setProperty("leaningLimitCircle", "off");
        }
    }

    public String getSearchRange() {
        return ppt.getProperty("searchRange");
    }

    public void setSearchRange(String value) {
        String def = ppt.getProperty("searchRange");
        ppt.setProperty("searchRange", value.isEmpty() ? def
                : !value.matches("\\d*") ? def
                : Integer.parseInt(value) < 0 ? def
                : value.startsWith("0") ? removeZero(value)
                : value);
    }

    private String removeZero(String value) {
        String trimmedString = value.replaceFirst("^0+(?!$)", "");
        return trimmedString;
    }

    /**
     * デバッグモードフラグ
     */
    public Boolean getDebugMode() {
        return ppt.getProperty("debug", "off").equals("on");
    }

    /**
     * 最近開いたtkkファイルを追加 5個を超えると古い方から削除される。
     *
     * @param file
     */
    public void addLately(File file) {
        String fileConf = file.getName();

        //check whether the file have ".tkk"
        boolean extConf = (file.getPath().contains(".tkk") && file.isFile());
        if (!extConf) {
            return;
        }

        List<String> files;
        files = new ArrayList<>();

        if (getLately() != null) {
            files.addAll(Arrays.asList(getLately()));
        }

        if (extConf) {
            files.add(0, file.getPath());
        } else {
            //files.add(0, file.getPath().toString() + ".tkk");
        }

        /*
         **Previous Code (does not showp up files that are affected by Windows file type associate)**
         */
 /*
                String[] ext = file.getName().split("\\.");

		System.out.println(ext.length);

		if(ext.length > 1)
			if(ext[ext.length-1].equals("tkk"))    
				files.add(0, file.getPath().toString());
         */
        // 既にリストに存在すれば後者を削除
        for (int n = 1; n < files.size(); n++) {
            if (files.get(n).equals(files.get(0))) {
                files.remove(n);
                break;
            }
        }

        // 5個を超えていれば末尾を削除
        if (files.size() > 5) {
            files.remove(5);
        }

        //before
//	String prop = "";
//
//	for (String f : files) {
//	    prop += f + "::";
//	}
        //after
        StringBuilder buf = new StringBuilder();
        files.forEach((f) -> {
            buf.append(f).append("::");
        });
        String prop = buf.toString();

        prop = prop.substring(0, prop.length() - 2);

        ppt.setProperty("lately", prop);
    }

    /**
     * 最近開いたファイルのパスの配列を返す
     *
     * @return
     */
    public String[] getLately() {
        String str = ppt.getProperty("lately");
        if (str.equals("NULL")) {
//	    return null;
            return new String[0];
        }
        String[] files = str.split("::");
        return files;
    }

    public boolean getAutocollimate() {
        return ppt.getProperty("autocollimate").equals("on");
    }

    public void setAutocollimate(boolean on) {
        if (on) {
            ppt.setProperty("autocollimate", "on");
        } else {
            ppt.setProperty("autocollimate", "off");
        }
    }

    public boolean getSimulation() {
        String value = ppt.getProperty("simulation");
        if (value == null) {
            return false;
        } else {
            return value.equals("on");
        }
    }

    public void setSimulation(boolean on) {
        if (on) {
            ppt.setProperty("simulation", "on");
        } else {
            ppt.setProperty("simulation", "off");
        }
    }

    public int getCameraNo() {
        String value = ppt.getProperty("cameraNo");
        if (value == null) {
            return 1;
        } else {
            return Integer.parseInt(value);
        }
    }

    public int getUseQuickTurn() {
        String value = ppt.getProperty("T17Range");
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException ex) {
            return 0;
        }
    }

    public int getCameraConnectionRetryTimes() {
        String value = ppt.getProperty("cameraConnectionRetryTimes");
        if (value == null) {
            return 3;
        } else {
            return Integer.parseInt(value);
        }
    }

    public int getTSConnectionRetryTimes() {
        String value = ppt.getProperty("TSConnectionRetryTimes");
        if (value == null) {
            return 3;
        } else {
            return Integer.parseInt(value);
        }
    }

    public Boolean getSearchRangeIsDisplayed() {
        String display = ppt.getProperty("searchRangeIsDisplayed");
        if (display.isEmpty()) {
            return false;
        }
        return Boolean.parseBoolean(display);
    }

    public void setSearchRangeIsDisplayed(boolean display) {
        if (display) {
            ppt.setProperty("searchRangeIsDisplayed", "true");
        } else {
            ppt.setProperty("searchRangeIsDisplayed", "false");
        }
    }

    public long getPileDriverPointId() {
        return Long.parseLong(ppt.getProperty("pileDriverPoint", "0"));
    }

    public void setPileDriverPointId(long pileDriverPointID) {
        ppt.setProperty("pileDriverPoint", String.valueOf(pileDriverPointID));
    }

    public boolean isKatamuki1() {
        return Boolean.parseBoolean(ppt.getProperty("isKatamuki1", "true"));
    }

    public void setKatamuki1(boolean b) {
        ppt.setProperty("isKatamuki1", String.valueOf(b));
    }

    public boolean isHenshin1() {
        return Boolean.parseBoolean(ppt.getProperty("isHenshin1", "true"));
    }

    public void setHenshin1(boolean b) {
        ppt.setProperty("isHenshin1", String.valueOf(b));
    }

    public boolean isHenshin2() {
        return Boolean.parseBoolean(ppt.getProperty("isHenshin2", "true"));
    }

    public void setHenshin2(boolean b) {
        ppt.setProperty("isHenshin2", String.valueOf(b));
    }

    public String getBase() {
        return ppt.getProperty("base");
    }

    public void setBase(String s) {
        ppt.setProperty("base", s);
    }

    public boolean isPileDriverBase() {
        String s = ppt.getProperty("base");
        if (Objects.isNull(s)) {
            return false;
        }
        return s.equals("pileDriver");
    }

    public boolean isEnableAutoAdjust() {
        return Boolean.parseBoolean(ppt.getProperty("autoAdjust"));
    }

    public void setEnableAutoAdjust(boolean b) {
        ppt.setProperty("autoAdjust", String.valueOf(b));
    }

    public double getAutoAdjustDiff() {
        return Double.parseDouble(ppt.getProperty("autoAdjustDiff", "5"));
    }

    public void setAutoAdjustDiff(double d) {
        ppt.setProperty("autoAdjustDiff", String.valueOf(d));
    }

    public int getErrorCount() {
        return Integer.parseInt(ppt.getProperty("errorCount", "3"));
    }

    public void setErrorCount(int i) {
        ppt.setProperty("errorCount", String.valueOf(i));
    }

    public double getAutoAdjustHeightGreen() {
        return Double.parseDouble(ppt.getProperty("autoAdjustHeightGreen", "0"));
    }

    public void setAutoAdjustHeightGreen(double d) {
        ppt.setProperty("autoAdjustHeightGreen", String.valueOf(d));
    }

    public double getAutoAdjustHeightRed() {
        return Double.parseDouble(ppt.getProperty("autoAdjustHeightRed", "0"));
    }

    public void setAutoAdjustHeightRed(double d) {
        ppt.setProperty("autoAdjustHeightRed", String.valueOf(d));
    }
    
    public double getAutoAdjustHeightGreenLimit() {
        return Double.parseDouble(ppt.getProperty("autoAdjustHeightGreenLimit", "30"));
    }
    
    public void setAutoAdjustHeightGreenLimit(double limit) {
        ppt.setProperty("autoAdjustHeightGreenLimit", String.valueOf(limit));
    }
    
    public double getAutoAdjustHeightRedLimit() {
        return Double.parseDouble(ppt.getProperty("autoAdjustHeightRedLimit", "-2"));
    }

    public void setAutoAdjustHeightRedLimit(double limit) {
        ppt.setProperty("autoAdjustHeightRedLimit", String.valueOf(limit));
    }
    
    public int getLoopCount() {
        return Integer.parseInt(ppt.getProperty("loopCount", "3"));
    }
    
    public int getFlasherStrokeWidth() {
        return Integer.parseInt(ppt.getProperty("flasherStrokeWidth", "80"));
    }

    public void store() {

        try ( OutputStream os = new FileOutputStream(PROPERTY_PATH)) {
            ppt.storeToXML(os, null);
        } catch (FileNotFoundException e1) {
            LOGGER.info("store", e1);
        } catch (IOException e1) {
            LOGGER.info("store", e1);
        }

    }

}
