package application;

import environment.Target;
import environment.Targets;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point3D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import java.io.File;

import java.io.IOException;
import java.util.Optional;

public class TargetsDialogController {
    @FXML
    private TableView<Target> targetsTable;
    @FXML
    private TableColumn<Target, String> nameColumn;
    @FXML
    private TableColumn<Target, String> coordColumn;
    @FXML
    private TableColumn<Target, String> normColumn;
    @FXML
    private Button addButton;
    @FXML
    private Button closeButton;
    @FXML
    private Button loadButton;
    @FXML
    private Button saveButton;

    private Stage dialogStage;
    private Targets targets;

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setTargets(Targets targets) {
        this.targets = targets;
        updateTable();
    }

    @FXML
    private void initialize() {
        // カラムとプロパティのバインド
        nameColumn.setCellValueFactory(cellData-> {
            String value = cellData.getValue().getName() != null ?
                    cellData.getValue().getName() : "N/A";
            return new SimpleStringProperty(value);
        });
        coordColumn.setCellValueFactory(cellData -> {
            String value = cellData.getValue().getCoord() != null ?
                cellData.getValue().getCoord().toString() : "N/A";
            return new SimpleStringProperty(value);
        });
        normColumn.setCellValueFactory(cellData -> {
            String value = cellData.getValue().getBearing() != null ?
                    cellData.getValue().getBearing().toString() : "N/A";
            return new SimpleStringProperty(value);
        });
    }

    @FXML
    private void handleAddTarget() {
        try {
            // Load the dialog FXML
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/application/TargetDialog.fxml"));
            Pane dialogContent = loader.load();

            // Create and configure the dialog
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("ターゲットの作成");
            dialog.getDialogPane().setContent(dialogContent);

            // Get the controller and set the dialog
            TargetDialogController controller = loader.getController();
            controller.setDialog(dialog);

            // Show the dialog and wait for user input
            Optional<ButtonType> result = dialog.showAndWait();

            // Process the result
            if (result.isPresent() && result.get() == ButtonType.OK) {
                if (controller.validateInput()) {
                    // Create a new target
                    String name = controller.getName();
                    Point3D position = controller.getPosition();
                    Point3D direction = controller.getDirection();

                    if (position != null) {
                        // Create and add the target
                        targets.createTarget(name, position, direction);
                        updateTable();
                        System.out.println("Created target: " + name + " at " + position);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Show error dialog
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("エラー");
            alert.setHeaderText("ターゲットの作成中にエラーが発生しました");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    @FXML
    private void handleClose() {
        dialogStage.close();
    }

    private void updateTable() {
        if (targets != null) {
            targetsTable.getItems().setAll(targets.getTargetsList());
        }
    }

    @FXML
    private void handleSave() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("ターゲットを保存");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("XML Files", "*.xml"));

        File file = fileChooser.showSaveDialog(dialogStage);
        if (file != null) {
            try {
                if (targets.saveToXml(file.getAbsolutePath())) {
                    showAlert("情報", "ターゲットを保存しました",
                            file.getAbsolutePath() + " に保存しました。",
                            Alert.AlertType.INFORMATION);
                } else {
                    showAlert("エラー", "保存に失敗しました",
                            "ターゲットの保存中にエラーが発生しました。",
                            Alert.AlertType.ERROR);
                }
            } catch (Exception e) {
                showAlert("エラー", "保存エラー",
                        "ファイルの保存中にエラーが発生しました: " + e.getMessage(),
                        Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void handleLoad() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("ターゲットを読み込み");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("XML Files", "*.xml"));

        File file = fileChooser.showOpenDialog(dialogStage);
        if (file != null) {
            try {
                if (targets.loadFromXml(file.getAbsolutePath())) {
                    updateTable();
                    showAlert("情報", "ターゲットを読み込みました",
                            file.getAbsolutePath() + " から読み込みました。",
                            Alert.AlertType.INFORMATION);
                } else {
                    showAlert("エラー", "読み込みに失敗しました",
                            "ターゲットの読み込み中にエラーが発生しました。",
                            Alert.AlertType.ERROR);
                }
            } catch (Exception e) {
                showAlert("エラー", "読み込みエラー",
                        "ファイルの読み込み中にエラーが発生しました: " + e.getMessage(),
                        Alert.AlertType.ERROR);
            }
        }
    }

    private void showAlert(String title, String header, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

}