package environment;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Point3D;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Room extends FaceSet {
    private final static Logger LOGGER = LogManager.getLogger(Room.class);
    public static Room INSTANCE;
    public static final double R = 400.;
    private static final double X_MAX = 15.0;
    private static final double X_MIN = -5.0;
    private static final double Y_MAX = 5.0;
    private static final double Y_MIN = -10.0;
    private static final double Z_MAX = 6.0;
    private static final double Z_MIN = -1.0;


    private Sphere spot;

    Room() {
        super();
//        faces.add(new Face("", X_MIN, Y_MIN, X_MAX, Y_MAX, Z_MIN, Z_MAX));
//        faces.add(new Face("bottom", X_MIN, Y_MIN, X_MAX, Y_MIN, Z_MIN, Z_MAX));
        faces.add(new Face("right", X_MAX, Y_MAX, X_MIN, Y_MAX, Z_MIN, Z_MAX, Color.RED));
        faces.add(new Face("left", X_MIN, Y_MIN, X_MAX, Y_MIN, Z_MIN, Z_MAX, Color.BLUE));
        faces.add(new Face("front", X_MIN, Y_MAX, X_MIN, Y_MIN,  Z_MIN, Z_MAX, Color.YELLOW));
        faces.add(new Face("back", X_MAX, Y_MIN, X_MAX, Y_MAX, Z_MIN, Z_MAX, Color.GREEN));
//        faces.add(new Face("ceil", X_MIN, Y_MIN, X_MAX, Y_MAX, Z_MAX, Color.PINK));
        ts.getIsMeasuringProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue && intersection.get() != null) {
                    spot.setTranslateX(intersection.get().getX());
                    spot.setTranslateY(intersection.get().getY());
                    spot.setTranslateZ(intersection.get().getZ());
                    spot.setVisible(true);
                } else {
                    spot.setVisible(false);
                }
            }
        });

        faces.stream().forEach(face ->
                nodes.getChildren().add(face.node));
        spot = new Sphere(0.05 * R);
        spot.setMaterial(new PhongMaterial(Color.RED));
        spot.setVisible(false);
        nodes.getChildren().add(spot);


    }

    public Double getDistance() {
        double distance = super.getDistance();
        if (intersection.get() != null) {
            spot.setTranslateX(intersection.get().getX() * R);
            spot.setTranslateY(intersection.get().getY() * R);
            spot.setTranslateZ(intersection.get().getZ() * R);
            spot.setVisible(true);
        } else {
            spot.setVisible(false);
        }
        return distance;
    }

        public static Room getInstance() {
        if (INSTANCE == null){
            INSTANCE = new Room();
        }
        return INSTANCE;
    }

    Room(Point3D topRightRear, Point3D bottomLeftFront) {
        this();
    }


}
