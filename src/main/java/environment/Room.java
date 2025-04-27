package environment;

import javafx.geometry.Point3D;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ts.TS;

import java.util.ArrayList;
import java.util.List;

public class Room {
    private final static Logger LOGGER = LogManager.getLogger(Room.class);
    public static Room INSTANCE;

    private static final double X_MAX = 10.0;
    private static final double X_MIN = -5.0;
    private static final double Y_MAX = 5.0;
    private static final double Y_MIN = -5.0;
    private static final double Z_MAX = 4.0;
    private static final double Z_MIN = -1.0;

    List<Face> faces;

    Room() {
        faces = new ArrayList<>();
//        faces.add(new Face("", X_MIN, Y_MIN, X_MAX, Y_MAX, Z_MIN, Z_MAX));
//        faces.add(new Face("bottom", X_MIN, Y_MIN, X_MAX, Y_MIN, Z_MIN, Z_MAX));
        faces.add(new Face("right", X_MAX, Y_MAX, X_MIN, Y_MAX, Z_MIN, Z_MAX));
        faces.add(new Face("left", X_MIN, Y_MIN, X_MAX, Y_MIN, Z_MIN, Z_MAX));
        faces.add(new Face("front", X_MIN, Y_MIN, X_MIN, Y_MAX, Z_MIN, Z_MAX));
        faces.add(new Face("back", X_MAX, Y_MIN, X_MAX, Y_MAX, Z_MIN, Z_MAX));

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
            Point3D intersection = face.intersection(TS.getInstance().getKikai().getPoint3D(), TS.getInstance().getDirection());
            if (intersection != null) {
                double d = intersection.distance(TS.getInstance().getKikai().getPoint3D());
                if (d < distance[0]) {
                    distance[0] = d;
                }
            }
        });
        return distance[0];
    }

}
