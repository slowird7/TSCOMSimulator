package environment;

import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableObjectValue;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.Node;
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
    private static final double R = 500.;
    private static final double X_MAX = 15.0 * R;
    private static final double X_MIN = -0.0 * R;
    private static final double Y_MAX = 0.0 * R;
    private static final double Y_MIN = -10.0 * R;
    private static final double Z_MAX = 6.0 * R;
    private static final double Z_MIN = -1.0 * R;

    private TS ts;
    private List<Face> faces;
    final public Group node;
    final public ObjectProperty<Point3D> intersection;

    private Sphere prizum;

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
                    prizum.setTranslateX(intersection.get().getX());
                    prizum.setTranslateY(intersection.get().getY());
                    prizum.setTranslateZ(intersection.get().getZ());
                    prizum.setVisible(true);
                } else {
                    prizum.setVisible(false);
                }
            }
        });

        node = new Group();
        faces.stream().forEach(face ->
                node.getChildren().add(face.node));
        prizum = new Sphere(0.05 * R);
        prizum.setMaterial(new PhongMaterial(Color.RED));
        prizum.setVisible(false);
        node.getChildren().add(prizum);


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
