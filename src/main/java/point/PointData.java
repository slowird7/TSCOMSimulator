package point;

import javafx.beans.property.*;
import javafx.geometry.Point3D;
import project.Property;

import java.util.LinkedHashMap;
import java.util.Map;

public class PointData {

    private static final String prevName = "";
    private static long lastPointID = 1;
    protected final SimpleLongProperty id;
    protected final SimpleStringProperty name;
    protected final SimpleStringProperty targettype;
    //    private static ArrayList<String> S_name = new ArrayList<String>();
    protected final SimpleStringProperty pilespec;
    protected final SimpleDoubleProperty x, y, z;
    protected final SimpleDoubleProperty mirrorHeight;
    // 実測値データのオブジェクトのマップを各pointが持っている。
    private final Map<String, SurveyData> sd;
    private final Property ppt = Property.getInstance();
    protected SimpleBooleanProperty selected;
    private PointData parent;
    private int ex_id;    //
    private boolean koushi;	// 主に描画時に使用。左から選択されている、器械点である、後視点である

    public PointData(double x, double y, double z) {
        this(x, y, z, "");

    }

    public PointData(double x, double y, double z, String name) {
        this("-", "-", x, y, z, 0, name);
    }

    public PointData(double x, double y, double z, String name, String pilespec, String targettype) {
        this(pilespec, targettype, x, y, z, 0, name);
    }

    public PointData(String s, String t, double x, double y, double z, double m, String name) {
//        this.id = new SimpleStringProperty("ID" + name);
//        this.id = new SimpleStringProperty(String.valueOf(new Date().getTime())); 
        this.id = new SimpleLongProperty(serialNumber());
        this.x = new SimpleDoubleProperty(x);
        this.y = new SimpleDoubleProperty(y);
        this.z = new SimpleDoubleProperty(z);
        this.mirrorHeight = new SimpleDoubleProperty(m);
        this.name = new SimpleStringProperty(name);
        this.targettype = new SimpleStringProperty(t);
        this.pilespec = new SimpleStringProperty(s);
        this.selected = new SimpleBooleanProperty(false);
        this.koushi = false;
        this.sd = new LinkedHashMap<>();
        this.parent = null;
        
    }

    public PointData(PointData pd) {
        this(pd.getPilespec(), pd.getTargettype(), pd.getX(), pd.getY(), pd.getZ(), pd.getMirrorHeight(), pd.getName());
        this.id.set(pd.id.get());
    }

    public static void resetLastPointID(long lastID) {
        lastPointID = lastID;
    }
    
    public static long getLastPointID() {
        return lastPointID;
    }

    public void putSD(String stage, SurveyData sd) {
        this.sd.put(stage, sd);
    }

    public Map<String, SurveyData> getSDfor() {
        return sd;
    }

    public void setCoodinate(double x, double y, double z) {
        this.x.set(x);
        this.y.set(y);
        this.z.set(z);
    }

    public double getX() {
        return x.get();
    }

    public void setX(double x) {
        this.x.set(x);
    }

    public double getY() {
        return y.get();
    }

    public void setY(double y) {
        this.y.set(y);
    }

    public double getZ() {
        return z.get();
    }

    public void setZ(double z) {
        this.z.set(z);
    }

    public double getMirrorHeight() {
        return mirrorHeight.get();
    }

    public void setMirrorHeight(double mh) {
        this.mirrorHeight.set(mh);
    }

    public long getId() {
        return id.get();
    }
    
    public void setId(long id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getTargettype() {
        return targettype.get();
    }

    public void setTargettype(String name) {
        this.targettype.set(name);
    }

    public String getPilespec() {
        return pilespec.get();
    }

    public void setPilespec(String name) {
        this.pilespec.set(name);
    }

    public PointData getParent() {
        return parent;
    }

    public void setParent(PointData parent) {
        this.parent = parent;
    }

    public DoubleProperty xProperty() {
        return x;
    }

    public DoubleProperty yProperty() {
        return y;
    }

    public DoubleProperty zProperty() {
        return z;
    }

    public DoubleProperty mirrorHeightProperty() {
        return mirrorHeight;
    }

    public BooleanProperty selectedProperty() {
        return selected;
    }

    public LongProperty idProperty() {
        return id;
    }

    public StringProperty targettypeProperty() {
        return targettype;
    }

    public StringProperty pilespecProperty() {
        return pilespec;
    }

    public int geEx_id() {
        return ex_id;
    }

    public void setEx_id(int n) {
        ex_id = n;
    }

    public double[] getCoodinate() {
        double[] ret = {this.x.get(), this.y.get(), this.z.get()};
        return ret;
    }

    public boolean getKoushi() {
        return koushi;
    }

    public void setKoushi(boolean f) {
        koushi = f;
    }

    public Point3D getPoint3D() {
        return new Point3D(x.get(), y.get(), z.get());
    }

    @Override
    public String toString() {
//        return id.getValue() + "," + x.getValue() + "," + y.getValue() + "," + z.getValue();
       // return id.getValue
       
       return name.get();
    }


    public void updateSurveyData(double offsetX, double offsetY, double offsetZ) {
        if (sd == null) {
            return;
        }
        sd.forEach((process, surveyData) -> {
            surveyData.setDifX(surveyData.getDifX() + offsetX);
            surveyData.setDifY(surveyData.getDifY() + offsetY);
            surveyData.setDifZ(surveyData.getDifZ() + offsetZ);
        });
    }
    
    private long serialNumber(){
        lastPointID++;
        return lastPointID;
    }

}
