package environment;

import javafx.geometry.Point3D;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

class Face {
    private final static Logger LOGGER = LogManager.getLogger(Face.class);

    String name;
    Point3D normalVector;
    Point3D footPoint;
    Point3D[] vertex = new Point3D[4];
    Pane node;
    Paint paint;

    public Face(String name, Point3D[] points) {
        this.name = name;
        for (int i = 0; i < 4; i++) {
            vertex[i] = new Point3D(points[i].getX(), points[i].getY(), points[i].getZ());
        }
        normalVector = vertex[1].subtract(vertex[0]).crossProduct(vertex[2].subtract(vertex[0])).normalize();
        node = makeNode();
    }

    public Face(String name, Point3D v1, Point3D v2, Point3D v3, Point3D v4) {
        this.name = name;
        Point3D[] points = new Point3D[]{v1, v2, v3, v4};
        for (int i = 0; i < 4; i++) {
            vertex[i] = new Point3D(points[i].getX(), points[i].getY(), points[i].getZ());
        }
        normalVector = vertex[1].subtract(vertex[0]).crossProduct(vertex[2].subtract(vertex[0])).normalize();
        node = makeNode();
    }

    public Face(String name, double[] v1, double[] v2, double[] v3, double[] v4) {
        this.name = name;
        Point3D[] points = new Point3D[]{new Point3D(v1[0], v1[1], v1[2]), new Point3D(v2[0], v2[1], v2[2]), new Point3D(v3[0], v3[1], v3[2]), new Point3D(v4[0], v4[1], v4[2])};
        for (int i = 0; i < 4; i++) {
            vertex[i] = new Point3D(points[i].getX(), points[i].getY(), points[i].getZ());
        }
        normalVector = vertex[1].subtract(vertex[0]).crossProduct(vertex[2].subtract(vertex[0])).normalize();
        node = makeNode();
    }

    public Face(String name, double x1, double y1, double x2, double y2, double z_top, double z_bottom, Paint paint) {
        this.name = name;
        vertex[0] = new Point3D(x1, y1, z_bottom);
        vertex[1] = new Point3D(x1, y1, z_top);
        vertex[2] = new Point3D(x2, y2, z_top);
        vertex[3] = new Point3D(x2, y2, z_bottom);
        this.paint = paint;
        normalVector = vertex[1].subtract(vertex[0]).crossProduct(vertex[2].subtract(vertex[0])).normalize();
        node = makeNode();
    }

    public Face(String name, double x1, double y1, double x2, double y2, double z, Paint paint) {
        this.name = name;
        vertex[0] = new Point3D(x1, y1, z);
        vertex[1] = new Point3D(x1, y2, z);
        vertex[2] = new Point3D(x2, y2, z);
        vertex[3] = new Point3D(x2, y1, z);
        this.paint = paint;
        normalVector = vertex[1].subtract(vertex[0]).crossProduct(vertex[2].subtract(vertex[0])).normalize();
        node = makeNode();
    }

    public Pane makeNode() {
        Pane pane =new Pane();
        pane.setLayoutX(vertex[0].getX());
        pane.setLayoutY(vertex[0].getY());

        double width = vertex[1].distance(vertex[2]);
        double height = vertex[0].distance(vertex[1]);
        Shape shape = new Rectangle(0., -height, width, height);
        shape.setFill(paint);
        shape.setOpacity(0.2);
        pane.getChildren().add(shape);
        Text label = new Text(0., 0., name);
//        label.setLayoutX(vertex[0].getX());
//        label.setLayoutY(vertex[0].getY());
        label.setFont( Font.font( 500 ) );
        label.setOpacity(0.5);
        pane.getChildren().add(label);
        double deg = Math.toDegrees(Math.atan2(vertex[2].getY() - vertex[1].getY(), vertex[2].getX() - vertex[1].getX()));
        Point3D axis = vertex[2].subtract(vertex[1]);
//        label.getTransforms().add(new Rotate(180, axis));
        pane.getTransforms().add(new Rotate(-90, axis));
        pane.getTransforms().add(new Rotate(deg, Rotate.Z_AXIS));
        //node.getTransforms().add(new Rotate(normalVector, vertex[0].getX(), vertex[0].getY()));
        return pane;
    }

    public Point3D getNormalVecotor() {
        return normalVector;
    }

    public Point3D getFootPointFrom(@NotNull Point3D p) {
        double t = (vertex[0].subtract(p).dotProduct(normalVector));
        Point3D footPoint = p.add(normalVector.multiply(t));
        return footPoint;
    }

    public Point3D intersection(Point3D origin, Point3D direction) {
        Point3D normalizedDirection = direction.normalize();
        Point3D f = normalVector.multiply(vertex[0].subtract(origin).dotProduct(normalVector));
        double dotProduct = normalizedDirection.dotProduct(f);
        if (dotProduct == 0.) {
            return null;
        }
        double k = f.dotProduct(f) / dotProduct;
        Point3D intersection;
        if (k >= 0) {
            intersection = origin.add(normalizedDirection.multiply(k));
            if (!isInsideOfFace(intersection)) {
                intersection = null;
            }
        } else{
            intersection = null;
        }
        return intersection;
    }

    public boolean isInsideOfFace(Point3D p) {
        boolean x = (p.subtract(vertex[0]).dotProduct(vertex[1].subtract(vertex[0])) >= 0);
        return x == (p.subtract(vertex[1]).dotProduct(vertex[2].subtract(vertex[1])) >= 0)
                && x == (p.subtract(vertex[2]).dotProduct(vertex[3].subtract(vertex[2])) >= 0)
                && x == (p.subtract(vertex[3]).dotProduct(vertex[0].subtract(vertex[3])) >= 0);
    }

}
