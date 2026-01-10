package environment;

import javafx.geometry.Point3D;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Target {
    private static final double RADIUS = 0.1; // [m]
    private Face face;

    public static Map<String, Face> targets = new HashMap<>();

    public Face createTarget(String name, Point3D coord, Point3D bearing) {
        Point3D normal = bearing.normalize();
        Point3D v1 = new Point3D(coord.getX(), coord.getY(), coord.getZ() + RADIUS);
        Point3D v2 = new Point3D(coord.getX() + normal.getX() * RADIUS, coord.getY() + normal.getY() * RADIUS, coord.getZ());
        Point3D v3 = new Point3D(coord.getX(), coord.getY(), coord.getZ() - RADIUS);
        Point3D v4 = new Point3D(coord.getX() - normal.getX() * RADIUS, coord.getY() - normal.getY() * RADIUS, coord.getZ());
        Face t = new Face(name, v1, v2, v3, v4);
        targets.put(name, t);
        return t;
    }

}
