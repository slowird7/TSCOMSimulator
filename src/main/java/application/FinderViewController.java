// FinderViewController.java
package application;

import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import ts.TS_3DModel;

public class FinderViewController {
    @FXML private BorderPane root;
    @FXML private Pane subSceneContainer;

    private Group sceneRoot;
    private TS_3DModel tsModel;
    private Rotate xRotate = new Rotate(0, Rotate.X_AXIS);
    private Rotate yRotate = new Rotate(0, Rotate.Y_AXIS);
    private Rotate zRotate = new Rotate(0, Rotate.Z_AXIS);
    private Translate position = new Translate();
    SubScene subScene;

    @FXML
    public void initialize() {
        // Create the scene root
        sceneRoot = new Group();
        sceneRoot.getTransforms().addAll(position, zRotate, yRotate, xRotate);

        // Create SubScene with anti-aliasing
        subScene = new SubScene(
                sceneRoot,
                subSceneContainer.getWidth(),
                subSceneContainer.getHeight(),
                true,  // depthBuffer
                SceneAntialiasing.BALANCED  // anti-aliasing
        );

        // Configure subscene - camera will be set when TS_3DModel is available
        subScene.setFill(Color.BLACK);

        // Add the SubScene to the container
        subScene.widthProperty().bind(subSceneContainer.widthProperty());
        subScene.heightProperty().bind(subSceneContainer.heightProperty());
        subSceneContainer.getChildren().add(subScene);
    }

    public void setTSModel(TS_3DModel tsModel) {
        this.tsModel = tsModel;
        subScene.setCamera(tsModel.getFinderCamera());
    }

    public void addToScene(Group node) {
        sceneRoot.getChildren().add(node);
    }

    public void removeFromScene(Group node) {
        sceneRoot.getChildren().remove(node);
    }
}
