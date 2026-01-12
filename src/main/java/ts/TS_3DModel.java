package ts;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;
import javafx.scene.PerspectiveCamera;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static java.lang.Thread.sleep;

public class TS_3DModel implements Initializable {
    @FXML
    public Pane body;
    @FXML
    public Pane baseHCoord;
    @FXML
    public Box base;
    @FXML
    public Box table;
    @FXML
    public Box columnL;
    @FXML
    public Box columnR;
    @FXML
    public Pane baseVCoord;
    @FXML
    public Box turner;
    @FXML
    public Cylinder eyepiece;
    @FXML
    public Cylinder telescope;
    @FXML
    public Cylinder lenz;
    @FXML
    public Box sight;
    @FXML
    public Line axisX;
    
    private PerspectiveCamera finderCamera;

    private final Rotate rotateBaseH = new Rotate(0, 100., 0., 0., new Point3D(0., -1., 0.));
    private final Rotate rotateBaseV = new Rotate(0., 0., 100., 0., new Point3D(1., 0., 0.));
    private final PhongMaterial peruPhongMaterial = new PhongMaterial(Color.PERU);
    private final PhongMaterial goldPhongMaterial = new PhongMaterial(Color.GOLD);
    private final PhongMaterial greyPhongMaterial = new PhongMaterial(Color.GREY);
    private final PhongMaterial blackPhongMaterial = new PhongMaterial(Color.BLACK);

    public Node node;
    private TS ts;

    public TS_3DModel(TS ts) {
        try {
            FXMLLoader loader = new FXMLLoader(TS_3DModel.class.getResource("/ts/TS_3D.fxml"));
            loader.setController(this);
            node = loader.load();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        this.ts = ts;
        ts.getIsMeasuringProperty().addListener((e) -> {
            if (ts.isMeasuring()) {
                Platform.runLater(() -> {
                    axisX.setVisible(true);
                });
            } else {
                Platform.runLater(() -> {
                    axisX.setVisible(false);
                });
            }
        });

    }

    public TS_3DModel(TS ts, double x, double y, double z) {
        this(ts);
        body.setTranslateX(x);
        body.setTranslateY(y);
        body.setTranslateZ(z);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize camera
        finderCamera = new PerspectiveCamera(true);
        finderCamera.setNearClip(0.1);
        finderCamera.setFarClip(10000);
        finderCamera.setFieldOfView(60);
        
        // Position camera at the center of the lens
        finderCamera.translateXProperty().bind(lenz.translateXProperty());
        finderCamera.translateYProperty().bind(lenz.translateYProperty());
        finderCamera.translateZProperty().bind(lenz.translateZProperty());
        
        // Add camera to the scene
        baseVCoord.getChildren().add(finderCamera);

        base.setMaterial(greyPhongMaterial);
        table.setMaterial(goldPhongMaterial);
        columnL.setMaterial(greyPhongMaterial);
        columnR.setMaterial(greyPhongMaterial);
        turner.setMaterial(goldPhongMaterial);
        eyepiece.setMaterial(greyPhongMaterial);
        telescope.setMaterial(greyPhongMaterial);
        lenz.setMaterial(blackPhongMaterial);
        sight.setMaterial(blackPhongMaterial);
        baseHCoord.getTransforms().addAll(rotateBaseH);
        baseVCoord.getTransforms().addAll(rotateBaseV);

    }

    public void setAzimuth(double azimuth_DEG) {
        rotateBaseH.setAngle(-azimuth_DEG);
    }

    public void setElevation(double elevation_DEG) {
        rotateBaseV.setAngle(90 - elevation_DEG);
    }
    
    public PerspectiveCamera getFinderCamera() {
        return finderCamera;
    }

    public void LazerOn() {
        axisX.setVisible(true);
    }

    public void LazerOff() {
        axisX.setVisible(false);
    }
}
