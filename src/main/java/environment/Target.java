package environment;

import javafx.geometry.Point3D;
import javafx.scene.paint.Color;

public class Target {
    private static final double RADIUS = 0.5; // [m]
    private Face face;
    private Point3D coord;
    private Point3D norm;



    private Target() {};

    public Target(String name, Point3D coord, Point3D bearing) {
        this.coord = coord;
        Point3D normal = bearing.normalize();
        this.norm = normal;
        Point3D v1 = new Point3D(coord.getX(), coord.getY(), coord.getZ() + RADIUS);
        Point3D v2 = new Point3D(coord.getX() + normal.getY() * RADIUS, coord.getY() - normal.getX() * RADIUS, coord.getZ());
        Point3D v3 = new Point3D(coord.getX(), coord.getY(), coord.getZ() - RADIUS);
        Point3D v4 = new Point3D(coord.getX() - normal.getY() * RADIUS, coord.getY() + normal.getX() * RADIUS, coord.getZ());
        face = new Face(name, v2.getX(), v2.getY(), v4.getX(), v4.getY(), v1.getZ(), v3.getZ(),Color.WHITE);
    }

    public String getName() {
        return face.name;
    }

    public Face getFace() {
        return face;
    }

    public Point3D getCoord() {
        return coord;
    }

    public Point3D getBearing() {
        return norm;
    }

}
