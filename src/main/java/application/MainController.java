package application;

import connection.TSSimulator;
import exception.TSNotConnectedException;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.ZoomEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import ts.TS_3DModel;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    Button btnConnect;
    @FXML
    ComboBox<String> txtCOMPort;

    private SubScene subScene;
//    @FXML
    private PerspectiveCamera camera;

    @FXML
    private Text textTranslateToRight;
    @FXML
    private Text textTranslateToDown;
    @FXML
    private Text textTranslateToForward;
    @FXML
    private Text textAzimuth;
    @FXML
    private Text textElevation;
    @FXML
    private Slider sliderTranslateToRight;
    @FXML
    private Slider sliderTranslateToDown;
    @FXML
    private Slider sliderTranslateToForward;
    @FXML
    private Text textTSAzimuth;
    @FXML
    private Text textTSElevation;
    @FXML
    private Slider sliderAzimuth;
    @FXML
    private Slider sliderElevation;
    @FXML
    private Slider sliderTSAzimuth;
    @FXML
    private Slider sliderTSElevation;
    @FXML
    private HBox hBox;

    // camera translation control variables
    private double azimuthRotateAngle = 0.0;
    private double elevationRotateAngle = 0.0;
    private double right = 0.0;			// view point of camera. default value is origin of global coordinate
    private double down = 0.0;			// view point of camera
    private double forward = -4000.0;	// camera setback distance
    private final Rotate azimuthRotate = new Rotate(-azimuthRotateAngle, Rotate.Y_AXIS);	// first, rotate around Y axis
    private final Rotate elevationRotate = new Rotate(-elevationRotateAngle, Rotate.X_AXIS);	// then, rotate around X axis
    // camera translations
    private final Translate translateXY = new Translate(right, down, 0.);
    private final Translate translateZ = new Translate(0., 0., forward);
    // objects
    private TS_3DModel ts1, ts2;

    TSSimulator conn;

    @FXML
    protected void onBtnConnectClicked()
    {
        if (!conn.isConnected()) {
            try {
                if (!conn.open(txtCOMPort.getValue().toString())) {
                    throw new TSNotConnectedException("COMポート" + txtCOMPort.getValue() + "に接続できません");
                };
                btnConnect.setText("Close");
            } catch (TSNotConnectedException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        } else {
            conn.close();
            btnConnect.setText("connect");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        conn = new TSSimulator();

        txtCOMPort.setValue("COM2");

        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setNearClip(100); // The default value is 0.1
        camera.setFarClip(100000); // The default value is 100.0
        camera.getTransforms().addAll(
                azimuthRotate,
                translateXY,
                elevationRotate,
                translateZ
        );

        // build up the scene
        Group group = new Group();
        // オブジェクトを生成・配置
        makeObjects(group);
        subScene = new SubScene(group, 600, 600, true, SceneAntialiasing.BALANCED);
        subScene.setFill(Color.LIGHTGREY);
        subScene.setRoot(group);
        subScene.setCamera(camera);
        hBox.getChildren().add(subScene);


        handleSliders();
        handleMouseOnSubscene();

    }

    private void makeObjects(Group group) {
        ts1 = new TS_3DModel();
        ts2 = new TS_3DModel(1000., 0., 0.);
        group.getChildren().add(ts1.node);
        group.getChildren().add(ts2.node);
    }

    private void setTranslates() {
        translateXY.setX(right);
        translateXY.setY(down);
        translateZ.setZ(forward);
        textTranslateToRight.setText(String.format("%06.1f", right));
        textTranslateToDown.setText(String.format("%06.1f", down));
        textTranslateToForward.setText(String.format("%06.1f", forward));
    }

    private void setAzimuthRotate( ) {
        azimuthRotate.setAngle(-azimuthRotateAngle);
        textAzimuth.setText(String.format("%04.1f", azimuthRotateAngle));
    }

    private void setElevationRotate() {
        elevationRotate.setAngle(-elevationRotateAngle);
        textElevation.setText(String.format("%04.1f", elevationRotateAngle));
    }

    private void handleSliders() {
        sliderTranslateToRight.valueProperty().addListener((ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
            right = (double)new_val;
            setTranslates();
        });

        sliderTranslateToDown.valueProperty().addListener((ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
            down = (double)new_val;
            setTranslates();
        });

        sliderTranslateToForward.valueProperty().addListener((ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
            forward = (double)new_val;
            setTranslates();
        });

        sliderAzimuth.valueProperty().addListener((ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
            if (new_val != null) {
                azimuthRotateAngle = (double)new_val;
                setAzimuthRotate();
            }
        });

        sliderElevation.valueProperty().addListener((ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
            if (new_val != null) {
                elevationRotateAngle = (double)new_val;
                setElevationRotate();
            }
        });

        sliderTSAzimuth.valueProperty().addListener((ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
            if (new_val != null) {
                ts1.setAzimuth((double)new_val);
                textTSAzimuth.setText(String.format("%04.1f", (double)new_val));
            }
        });

        sliderTSElevation.valueProperty().addListener((ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
            if (new_val != null) {
                ts1.setElevation((double)new_val);
                textTSElevation.setText(String.format("%04.1f", (double)new_val));
            }
        });
    }

    private void handleMouseOnSubscene() {
        subScene.setOnZoom((ZoomEvent ez) -> {

        });

        subScene.setOnScroll((ScrollEvent event) -> {
            double zoomFactor = 1.05;
            double deltaY = event.getDeltaY();

            if (deltaY < 0){
                zoomFactor = 0.95;
            }
            forward *= zoomFactor;
            setTranslates();
            event.consume();
        });
    }

}