package point;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 実測値。設計値等に対しこれらを持たせる
 */
public class SurveyData extends PointData {

    private static SimpleDateFormat sdf;

    private final SimpleDoubleProperty height;
    private final SimpleStringProperty date;
    private final SimpleDoubleProperty difX, difY, difZ;
    private final SimpleDoubleProperty thX, thY;
    private final SimpleDoubleProperty ddZ;  //深度

    public SurveyData(double x, double y, double z, double difX, double difY, double difZ, double thX, double thY, Double ddZ, long pileId, String stage, String date) {
        this(x, y, z, difX, difY, difZ, thX, thY, ddZ, pileId, stage);
        this.date.set(date);
    }

    public SurveyData(double x, double y, double z, double difX, double difY, double difZ, double thX, double thY, Double ddZ, long pileId, String stage) {
        this(x, y, z, difX, difY, difZ, thX, thY, ddZ, pileId);
    }

    public SurveyData(double x, double y, double z, double difX, double difY, double difZ, double thX, double thY, Double ddZ, long pileId) {
        super(x, y, z);
        this.difX = new SimpleDoubleProperty(difX);
        this.difY = new SimpleDoubleProperty(difY);
        this.difZ = new SimpleDoubleProperty(difZ);
        this.thX = new SimpleDoubleProperty(thX);
        this.thY = new SimpleDoubleProperty(thY);
        this.ddZ = new SimpleDoubleProperty(ddZ);
        this.height = new SimpleDoubleProperty(0);
        this.date = new SimpleStringProperty("");
        this.height.set(0);
        this.id.set(pileId);
        this.name.set("");
        sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        date.set(sdf.format(new Date()));
    }

    public double getHeight() {
        return this.height.get();
    }

    public void setHeight(double height) {
        this.height.set(height);
    }

    public SimpleDoubleProperty heightProperty() {
        return this.height;
    }

    public double getDifX() {
        return this.difX.get();
    }

    public void setDifX(double difX) {
        this.difX.set(difX);
    }

    public SimpleDoubleProperty difXProperty() {
        return this.difX;
    }

    public double getDifY() {
        return this.difY.get();
    }

    public void setDifY(double difY) {
        this.difY.set(difY);
    }

    public SimpleDoubleProperty difYProperty() {
        return this.difY;
    }

    public double getDifZ() {
        return this.difZ.get();
    }

    public void setDifZ(double difZ) {
        this.difZ.set(difZ);
    }

    public SimpleDoubleProperty difZProperty() {
        return this.difZ;
    }

    public double getThX() {
        return this.thX.get();
    }

    public void setThX(double thX) {
        this.thX.set(thX);
    }

    public SimpleDoubleProperty thXProperty() {
        return this.thX;
    }

    public double getThY() {
        return this.thY.get();
    }

    public void setThY(double thY) {
        this.thY.set(thY);
    }

    public SimpleDoubleProperty thYProperty() {
        return this.thY;
    }

    public Double getDdZ() {
        return this.ddZ.get();
    }

    public void setDdZ(Double ddz) {

        this.ddZ.set(ddz);
    }

    public void setDate() {
        this.date.set(sdf.format(new Date()));
    }

    public String getDate() {
        return date.get();
    }

    public void setDate(Date date_) {
        this.date.set(sdf.format(date_));
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

}
