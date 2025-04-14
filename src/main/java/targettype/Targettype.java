package targettype;

public class Targettype {

    protected String name;
    protected int type;
    protected double param;
    protected double diameter;
    protected boolean autocollimatable;
    protected boolean autotrackable;

    /**
     * nameプロパティの値を取得します。
     *
     * @return possible object is {@link String }
     */
    public String getName() {
        return name;
    }

    /**
     * nameプロパティの値を設定します。
     *
     * @param value allowed object is {@link String }
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * typeプロパティの値を取得します。
     */
    public int getType() {
        return type;
    }

    /**
     * typeプロパティの値を設定します。
     */
    public void setType(int value) {
        this.type = value;
    }

    /**
     * paramプロパティの値を取得します。
     */
    public double getParam() {
        return param;
    }

    /**
     * paramプロパティの値を設定します。
     */
    public void setParam(double value) {
        this.param = value;
    }

    /**
     * diameterプロパティの値を取得します。
     */
    public double getDiameter() {
        return diameter;
    }

    /**
     * diameterプロパティの値を設定します。
     */
    public void setDiameter(double value) {
        this.diameter = value;
    }

    /**
     * autocollimatableプロパティの値を取得します。
     */
    public boolean isAutocollimatable() {
        return autocollimatable;
    }

    /**
     * autocollimatableプロパティの値を設定します。
     */
    public void setAutocollimatable(boolean value) {
        this.autocollimatable = value;
    }

    /**
     * autotrackableプロパティの値を取得します。
     */
    public boolean isAutotrackable() {
        return autotrackable;
    }

    /**
     * autotrackableプロパティの値を設定します。
     */
    public void setAutotrackable(boolean value) {
        this.autotrackable = value;
    }

}
