package application;

import environment.Room;
import exception.TSNotConnectedException;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point3D;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.input.DragEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.ZoomEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import point.PointData;
import ts.TS;
import ts.TSInterface;
import ts.TS_3DModel;

import java.net.URL;
import java.util.ResourceBundle;

//import com.interactivemesh.jfx.importer.ImportException;
//import com.interactivemesh.jfx.importer.obj.ObjModelImporter;

public class MainController implements Initializable {
    @FXML
    Button btnConnect;
    @FXML
    ComboBox<String> txtCOMPort;
    @FXML
    private SubScene subScene;
//    @FXML
    private PerspectiveCamera camera;

    @FXML
    private Button btnCollimate;
    @FXML
    private TextField txtAimX;
    @FXML
    private TextField txtAimY;
    @FXML
    private TextField txtAimZ;
    @FXML
    private Button btnKikai;
    @FXML
    private TextField txtKikaiX;
    @FXML
    private TextField txtKikaiY;
    @FXML
    private TextField txtKikaiZ;
    @FXML
    private TextField txtAngleA;
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
    private Slider sliderAzimuthFine;
    @FXML
    private Slider sliderElevation;
    @FXML
    private Slider sliderElevationFine;
    @FXML
    private Slider sliderTSAzimuth;
    @FXML
    private Slider sliderTSAzimuthFine;
    @FXML
    private Slider sliderTSElevation;
    @FXML
    private Slider sliderTSElevationFine;
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
    private Room room;
    private TS ts;
    private final Translate tsLocation = new Translate(0, 0, 0); // TS location

    TSInterface conn;

    @FXML
    private void onBtnConnectClicked()
    {
        if (!conn.isConnected()) {
            try {
                if (!conn.open(txtCOMPort.getValue())) {
                    throw new TSNotConnectedException("COMポート" + txtCOMPort.getValue() + "に接続できません");
                }
                btnConnect.setText("Close");
            } catch (TSNotConnectedException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        } else {
            conn.close();
            btnConnect.setText("connect");
        }
    }

    @FXML
    private void onBtnKikaiClicked()
    {
        ts.setKikai(new PointData(Double.parseDouble(txtKikaiX.getText()), Double.parseDouble(txtKikaiY.getText()), Double.parseDouble(txtKikaiZ.getText())));
        tsLocation.setX(Double.parseDouble(txtKikaiX.getText()));
        tsLocation.setY(Double.parseDouble(txtKikaiY.getText()));
        tsLocation.setZ(Double.parseDouble(txtKikaiZ.getText()));

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ts = TS.getInstance();
        conn = new TSInterface(ts);

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
        group.getTransforms().addAll(new Scale(1., 1., -1.)
                , new Rotate(90., new Point3D(1., 0., 0.))
                , new Rotate(90., new Point3D(0., 0., 1.)));
        makeAxis(group);
        // オブジェクトを生成・配置
        makeObjects(group);
        hBox.getChildren().remove(subScene);
        subScene = new SubScene(group, 600, 600, true, SceneAntialiasing.BALANCED);
        subScene.setFill(Color.LIGHTGREY);
        subScene.setDepthTest(DepthTest.ENABLE);
        subScene.setRoot(group);
        subScene.setCamera(camera);
        hBox.getChildren().add(subScene);


        handleSliders();
        handleMouseOnSubscene();

        sliderTSAzimuth.valueProperty().bindBidirectional(ts.getAngleHDMSProperty());
        sliderTSElevation.valueProperty().bindBidirectional(ts.getAngleVDMSProperty());

    }

    private void makeObjects(Group group) {
        ts1 = new TS_3DModel(ts);
        ts1.node.getTransforms().add(new Rotate(90., new Point3D(0., 1., 0.)));
        ts1.node.getTransforms().add(new Scale(1., 1., 1.));
        ts1.node.getTransforms().add(tsLocation);
//        ts1.node.getTransforms().add(new Rotate(180., new Point3D(0, 0., 0.)));
        group.getChildren().add(ts1.node);

        room = Room.getInstance();
        group.getChildren().add(room.node);
    }

    private void makeAxis(Group group) {
        int span = 500;
        int noOfLine = 5;
        Pane snappedPane = new Pane();
        for (int y = -span * noOfLine; y <= span * noOfLine; y += span) {
            Line lineX = new Line(-span * noOfLine, y, span * noOfLine, y);
            lineX.setStroke(Color.TEAL);
            Line lineY = new Line(y, -span * noOfLine, y, span * noOfLine);
            lineY.setStroke(Color.BLUE);
            snappedPane.getChildren().addAll(lineX, lineY);
        }
        snappedPane.setSnapToPixel(true);
        group.getChildren().add(snappedPane);
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

    private double sliderAnchor = 0.;

    private void setupSliderFine(Slider sliderCoase, Slider sliderFine) {
        sliderFine.setMaxHeight(sliderCoase.getMax());
        sliderFine.setMinHeight(sliderCoase.getMin());
        sliderFine.setOnMousePressed(mouseEvent -> {
            sliderAnchor = sliderCoase.getValue();
        });

        sliderFine.valueProperty().addListener((ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
            if (new_val != null && new_val.doubleValue() != 0.) {
                double newAzimuth = sliderAnchor + sliderFine.getValue();
                if (newAzimuth > sliderFine.getMaxHeight()) newAzimuth = sliderFine.getMinHeight();
                if (newAzimuth < sliderFine.getMinHeight()) newAzimuth = sliderFine.getMaxHeight();
                sliderCoase.setValue(newAzimuth);
            }
        });

        sliderFine.valueChangingProperty().addListener((obs, wasChanging, isNowChanging) -> {
            if (!isNowChanging) {
                sliderFine.setValue(0.);
            }
        });
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
                Platform.runLater(() -> {
                    ts1.setAzimuth(-(double)new_val);
                    textTSAzimuth.setText(String.format("%8.4f", new_val));
                });
            }
        });

        sliderTSElevation.valueProperty().addListener((ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
            if (new_val != null) {
                Platform.runLater(() -> {
                    ts1.setElevation((double)new_val);
                    textTSElevation.setText(String.format("%9.4f", new_val));
                });
            }
        });

        setupSliderFine(sliderAzimuth, sliderAzimuthFine);
        setupSliderFine(sliderElevation, sliderElevationFine);
        setupSliderFine(sliderTSAzimuth, sliderTSAzimuthFine);
        setupSliderFine(sliderTSElevation, sliderTSElevationFine);

//        {
//            sliderAzimuthFine.setOnMousePressed(mouseEvent -> {
//                sliderAnchor = sliderAzimuth.getValue();
//            });
//
//            sliderAzimuthFine.valueProperty().addListener((ObservableValue<? extends Number> ov, Number old_val, Number new_val) -> {
//                if (new_val != null && new_val.doubleValue() != 0.) {
//                    double newAzimuth = sliderAnchor + sliderAzimuthFine.getValue();
//                    while (newAzimuth > 360.) newAzimuth -= 360.;
//                    while (newAzimuth < 0.) newAzimuth += 360.;
//                    sliderAzimuth.setValue(newAzimuth);
//                }
//            });
//
//            sliderAzimuthFine.valueChangingProperty().addListener((obs, wasChanging, isNowChanging) -> {
//                if (!isNowChanging) {
//                    sliderAzimuthFine.setValue(0.);
//                }
//            });
//        }
        //textTSElevation.textProperty().bind(sliderTSElevation.valueProperty().asString("%04.1f"));
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

    @FXML
    private void onBtnCollimateClicked() {
        try {
            double aimX = Double.parseDouble(txtAimX.getText());
            double aimY = Double.parseDouble(txtAimY.getText());
            double aimZ = Double.parseDouble(txtAimZ.getText());
            ts.collimate(aimX, aimY, aimZ);
        } catch (NumberFormatException ex) {
        }

    }

    private void setSliderFine(Slider sliderCoase, Slider sliderFine) {
        sliderFine.setMin(Math.max(sliderCoase.getValue() - 1.0, 0.0));
        sliderFine.setMax(Math.min(sliderCoase.getValue() + 1.0, 360.0));
        sliderFine.setValue(sliderCoase.getValue());
    }

    @FXML
    private void onSliderFineDragDone(DragEvent ev) {
        ((Slider)ev.getSource()).setValue(0.);
    }

    /**
     * URL指定でOBJファイルからメッシュを作成する
     * @param url
     * @return

    public Node createModelFromObj( String url )
    {
        // 戻り値の3Dモデルグループを作成
        Group   root   = new Group();

        // 3Dモデルのインポーターを作成
        ObjModelImporter    importer    = new ObjModelImporter();

        // インポータにモデル・ファイルを設定
        try{
            importer.read( url );
        }catch( ImportException e ){
            e.printStackTrace();
        }

        // 3Dモデルを取込
        Node[]  meshes  = importer.getImport();
        root.getChildren().addAll( meshes );

        // インポータを閉じる
        importer.close();

        return root;
    }
    */
}