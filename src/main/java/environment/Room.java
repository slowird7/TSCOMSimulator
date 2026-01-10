package environment;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ts.TS;

import java.util.ArrayList;
import java.util.List;

public class Room {
    private final static Logger LOGGER = LogManager.getLogger(Room.class);
    public static Room INSTANCE;
    private static final double R = 400.;
    private static final double X_MAX = 15.0 * R;
    private static final double X_MIN = -5.0 * R;
    private static final double Y_MAX = 5.0 * R;
    private static final double Y_MIN = -10.0 * R;
    private static final double Z_MAX = 6.0 * R;
    private static final double Z_MIN = -1.0 * R;

    private TS ts;
    private List<Face> faces;
    final public Group node;
    final public ObjectProperty<Point3D> intersection;

    private Sphere spot;

    Room() {
        ts = TS.getInstance();
        faces = new ArrayList<>();
//        faces.add(new Face("", X_MIN, Y_MIN, X_MAX, Y_MAX, Z_MIN, Z_MAX));
//        faces.add(new Face("bottom", X_MIN, Y_MIN, X_MAX, Y_MIN, Z_MIN, Z_MAX));
        faces.add(new Face("right", X_MAX, Y_MAX, X_MIN, Y_MAX, Z_MIN, Z_MAX, Color.RED));
        faces.add(new Face("left", X_MIN, Y_MIN, X_MAX, Y_MIN, Z_MIN, Z_MAX, Color.BLUE));
        faces.add(new Face("front", X_MIN, Y_MAX, X_MIN, Y_MIN,  Z_MIN, Z_MAX, Color.YELLOW));
        faces.add(new Face("back", X_MAX, Y_MIN, X_MAX, Y_MAX, Z_MIN, Z_MAX, Color.GREEN));
//        faces.add(new Face("ceil", X_MIN, Y_MIN, X_MAX, Y_MAX, Z_MAX, Color.PINK));
        intersection = new SimpleObjectProperty<>();
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

        node = new Group();
        faces.stream().forEach(face ->
                node.getChildren().add(face.node));
        spot = new Sphere(0.05 * R);
        spot.setMaterial(new PhongMaterial(Color.RED));
        spot.setVisible(false);
        node.getChildren().add(spot);


    }

    public void makeNode(Group group) {
        faces.stream().forEach(face ->
                group.getChildren().add(face.makeNode()));
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

    public Double getDistance() {
        final double[] distance = {Double.POSITIVE_INFINITY};
        faces.stream().forEach(face -> {
            intersection.set(face.intersection(ts.getKikai().getPoint3D(), ts.getDirection()));
            if (intersection.get() != null) {
                Platform.runLater(()->{

                });
                double d = intersection.get().distance(ts.getKikai().getPoint3D());
                if (d < distance[0]) {
                    distance[0] = d;
                }
            }
        });

        return distance[0];
    }

}
