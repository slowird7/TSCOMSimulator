package point;

import javafx.beans.property.*;
import javafx.geometry.Point3D;
import project.Property;

import java.util.LinkedHashMap;
import java.util.Map;

public class PointData {

    protected final SimpleStringProperty name;
    protected final SimpleStringProperty targettype;
    //    private static ArrayList<String> S_name = new ArrayList<String>();
    protected final SimpleStringProperty pilespec;
    protected final SimpleDoubleProperty x, y, z;
    protected final SimpleDoubleProperty mirrorHeight;

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
        this.x = new SimpleDoubleProperty(x);
        this.y = new SimpleDoubleProperty(y);
        this.z = new SimpleDoubleProperty(z);
        this.mirrorHeight = new SimpleDoubleProperty(m);
        this.name = new SimpleStringProperty(name);
        this.targettype = new SimpleStringProperty(t);
        this.pilespec = new SimpleStringProperty(s);

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

    public StringProperty targettypeProperty() {
        return targettype;
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

}
