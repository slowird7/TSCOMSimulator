package application;

import environment.Target;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.geometry.Point3D;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class TargetDialogController {
    @FXML private TextField nameField;
    @FXML private TextField xField;
    @FXML private TextField yField;
    @FXML private TextField zField;
    @FXML private TextField dirXField;
    @FXML private TextField dirYField;
    @FXML private TextField dirZField;
    @FXML private GridPane gridPane;

    private Dialog<ButtonType> dialog;
    private Target target;
    private static final String DEFAULT_DIR_X = "1.0";
    private static final String DEFAULT_DIR_Y = "0.0";
    private static final String DEFAULT_DIR_Z = "0.0";
    private static final String ERROR_EMPTY_NAME = "名前を入力してください。\n";
    private static final String ERROR_EMPTY_COORDINATES = "座標を入力してください。\n";

    public void setDialog(Dialog<ButtonType> dialog) {
        this.dialog = dialog;
        setupDialogButtons();
    }

    public void setTarget(Target target) {
        this.target = target;
    }

    @FXML
    private void initialize() {
        // Set up input validation
        initializeNumericFields();
        initializeDirectionFields();
    }

    private void initializeNumericFields() {
        List<TextField> numericFields = Arrays.asList(
                xField, yField, zField, dirXField, dirYField, dirZField
        );
        numericFields.forEach(this::setupNumericField);
    }

    private void initializeDirectionFields() {
        dirXField.setText(DEFAULT_DIR_X);
        dirYField.setText(DEFAULT_DIR_Y);
        dirZField.setText(DEFAULT_DIR_Z);
    }

    private void setupDialogButtons() {
        // Get the dialog pane
        DialogPane dialogPane = dialog.getDialogPane();

        // Set the button types
        ButtonType createButtonType = new ButtonType("作成", ButtonData.OK_DONE);
        dialogPane.getButtonTypes().setAll(createButtonType, ButtonType.CANCEL);

        // Enable/Disable create button depending on whether a name was entered
        Button createButton = (Button) dialogPane.lookupButton(createButtonType);
        createButton.setDisable(true);

        // Add validation listener
        nameField.textProperty().addListener((observable, oldValue, newValue) -> {
            createButton.setDisable(newValue.trim().isEmpty());
        });

        // Handle create button action
        createButton.setOnAction(event -> {
            if (validateInput()) {
                dialog.setResult(createButtonType);
            } else {
                event.consume(); // Prevent dialog from closing
            }
        });
    }

    private void setupNumericField(TextField field) {
        field.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("-?\\d*\\.?\\d*")) {
                field.setText(oldValue);
            }
        });
    }

    public String getName() {
        return nameField.getText().trim();
    }

    public Point3D getPosition() {
        try {
            double x = Double.parseDouble(xField.getText());
            double y = Double.parseDouble(yField.getText());
            double z = Double.parseDouble(zField.getText());
            return new Point3D(x, y, z);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public Point3D getDirection() {
        try {
            double x = Double.parseDouble(dirXField.getText());
            double y = Double.parseDouble(dirYField.getText());
            double z = Double.parseDouble(dirZField.getText());
            return new Point3D(x, y, z);
        } catch (NumberFormatException e) {
            return new Point3D(1, 0, 0); // Default direction
        }
    }

    public boolean validateInput() {
        StringBuilder errorMessage = new StringBuilder();

        validateName(errorMessage);
        validateCoordinates(errorMessage);

        if (errorMessage.length() > 0) {
            showError("入力エラー", "入力内容を確認してください", errorMessage.toString());
            return false;
        }

        return true;
    }

    private void showError(String title, String header, String content) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void validateName(StringBuilder errorMessage) {
        if (nameField.getText() == null || nameField.getText().trim().isEmpty()) {
            errorMessage.append(ERROR_EMPTY_NAME);
        }
    }

    private void validateCoordinates(StringBuilder errorMessage) {
        if (Stream.of(xField, yField, zField)
                .anyMatch(field -> field.getText() == null || field.getText().trim().isEmpty())) {
            errorMessage.append(ERROR_EMPTY_COORDINATES);
        }
    }

}