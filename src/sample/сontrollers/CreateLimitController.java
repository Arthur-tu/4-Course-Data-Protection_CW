package sample.сontrollers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.animations.Shake;
import sample.logic.LimitObject;

public class CreateLimitController {
    @FXML
    private TextField maxField;
    @FXML
    private TextField minField;
    @FXML
    private Button cancelButton;
    @FXML
    private Button okButton;
    @FXML
    private CheckBox bigCheck;
    @FXML
    private CheckBox digitCheck;
    @FXML
    private CheckBox littleCheck;
    @FXML
    private CheckBox specCheck;

    @FXML
    void initialize() {
        cancelButton.setOnAction(event -> goBack());

        okButton.setOnAction(actionEvent -> save());
    }

    private void save() {
        LimitObject limitObject = new LimitObject();
        try {
            Integer max = Integer.parseInt(maxField.getText().trim());
            Integer min = Integer.parseInt(minField.getText().trim());
            if (max >= min && min > 0) {
                limitObject.setMax(max); limitObject.setMin(min);
                limitObject.setDigits(digitCheck.isSelected());
                limitObject.setLowerCase(littleCheck.isSelected());
                limitObject.setUpperCase(bigCheck.isSelected());
                limitObject.setSpecialSym(specCheck.isSelected());
                Controller.limitObject = limitObject;
                Controller.limitFlag = true;
                goBack();
            } else {
                Shake shakeokButton = new Shake(okButton);
                shakeokButton.playAnimation();
            }
        } catch (Exception e) {
            Shake shakeokButton = new Shake(okButton);
            shakeokButton.playAnimation();
        }
    }

    private void goBack() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
}
