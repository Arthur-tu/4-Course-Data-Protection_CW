package sample.сontrollers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.animations.Shake;
import sample.logic.KeyAnalyzer;
import java.io.IOException;

public class CreateSecretKeyFormController {
    @FXML
    private Label labelMsg3;
    @FXML
    private Label labelMsg2;
    @FXML
    private Label labelMsg1;
    @FXML
    private Button okButton;
    @FXML
    private PasswordField enterSecretKeyField;
    @FXML
    private PasswordField enterSecretKeyField1;
    @FXML
    private Button goBackButton;
    @FXML
    private Button refreshButton;
    @FXML
    private Button limitButton;

    @FXML
    void initialize() {
        okButton.setOnAction(actionEvent -> createKey());

        refreshButton.setOnAction(actionEvent -> refresh());

        goBackButton.setOnAction(actionEvent -> goBack());

        limitButton.setOnAction(actionEvent -> showLimitForm());
    }

    private void showLimitForm() {
        openNewModalScene("/sample/fxml/createLimit.fxml", "Задайте ограничения для секретного ключа");
    }

    private void goBack() {
        Stage stage = (Stage) goBackButton.getScene().getWindow();
        stage.close();
    }

    private void refresh() {
        enterSecretKeyField.setText("");
        enterSecretKeyField1.setText("");
    }

    private void createKey() {
        labelMsg1.setVisible(false);
        labelMsg2.setVisible(false);
        labelMsg3.setVisible(false);
        String key = enterSecretKeyField.getText().trim();
        String key2 = enterSecretKeyField1.getText().trim();
        KeyAnalyzer analyzer = new KeyAnalyzer(key);
        if (keysAreSame(key, key2)) {
            if (Controller.limitFlag) {
                if (analyzer.validLetters()) {
                    if (analyzer.analyze()) {
                        Controller.legalSecretKey = key;
                        goBack();
                    } else {
                        Shake shakeOk = new Shake(okButton);
                        shakeOk.playAnimation();
                        labelMsg2.setVisible(true);
                    }
                } else {
                    Shake shakeOk = new Shake(okButton);
                    shakeOk.playAnimation();
                    labelMsg3.setVisible(true);
                }
            } else {
                if (analyzer.validLetters()) {
                    Controller.legalSecretKey = key;
                    goBack();
                } else {
                    Shake shakeOk = new Shake(okButton);
                    shakeOk.playAnimation();
                    labelMsg3.setVisible(true);
                }
            }
        } else {
            Shake shakeOk = new Shake(okButton);
            shakeOk.playAnimation();
            labelMsg1.setVisible(true);
        }
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

    public void openNewModalScene(String window, String title) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(window));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(title);
        stage.setResizable(false);
        stage.setScene(new Scene(root));
        stage.show();
    }
}
