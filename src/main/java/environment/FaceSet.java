package environment;

import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import ts.TS;

import java.util.ArrayList;
import java.util.List;

public abstract class FaceSet {
    protected List<Face> faces;
    protected TS ts;
    final public Group nodes;
    final public ObjectProperty<Point3D> intersection;

    FaceSet () {
        faces = new ArrayList<>();
        nodes = new Group();
        intersection = new SimpleObjectProperty<>();
        ts = TS.getInstance();
    }

    public Double getDistance() {
        final double[] distance = {Double.POSITIVE_INFINITY};
        intersection.set(null);
       faces.stream().forEach(face -> {
            Point3D p = face.intersection(ts.getKikai().getPoint3D(), ts.getDirection());
            if (p != null) {
                double d = p.distance(ts.getKikai().getPoint3D());
                if (d < distance[0]) {
                    distance[0] = d;
                    intersection.set(p);

                }
            }
        });

        return distance[0];
    }

}
