package sample.сontrollers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import sample.animations.Shake;

public class EnterSecretKeyFormController {
    @FXML
    private Label labelMsg2;
    @FXML
    private Label labelMsg1;
    @FXML
    private PasswordField enterSecretKeyField;
    @FXML
    private Button goBackButton;
    @FXML
    private Button okButton;
    @FXML
    private Button refreshButton;

    @FXML
    void initialize() {
       refreshButton.setOnAction(actionEvent -> refresh());

       goBackButton.setOnAction(actionEvent -> goBack());

       okButton.setOnAction(actionEvent -> enterKey());
    }

    private void enterKey() {
        labelMsg1.setVisible(false);
        labelMsg2.setVisible(false);
        String key = enterSecretKeyField.getText().trim();
        String legalSecretKey = Controller.legalSecretKey;
        try {
            if (keysAreSame(key, legalSecretKey)) {
                Controller.flag = !Controller.flag;
                goBack();
            } else {
                Shake shake = new Shake(okButton);
                shake.playAnimation();
                labelMsg1.setVisible(true);
            }
        } catch (Exception e) {
            Shake shake = new Shake(okButton);
            shake.playAnimation();
            labelMsg2.setVisible(true);
        }

    }

    private void goBack() {
        Stage stage = (Stage) goBackButton.getScene().getWindow();
        stage.close();
    }

    private void refresh() {
        enterSecretKeyField.setText("");
    }

    private boolean keysAreSame(String p1, String p2) {
        char c;
        if (p1.length() != p2.length()) return false;

        for (int i = 0; i < p1.length(); i++) {
            c = p1.charAt(i);
            if (c != p2.charAt(i)) {
                return false;
            }
        }
        return true;
    }
}
