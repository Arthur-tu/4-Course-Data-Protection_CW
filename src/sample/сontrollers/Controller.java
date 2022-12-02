package sample.сontrollers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.Main;
import sample.animations.Shake;
import sample.logic.LimitObject;
import sample.logic.MD4AlgoExecutor;

import java.io.*;

public class Controller {
    @FXML
    private CheckBox fileBox;
    @FXML
    private CheckBox messageBox;
    @FXML
    private Label saveHashLabel;
    @FXML
    private Label nicehashLabel;
    @FXML
    private Label badhashLabel;
    @FXML
    private Button compareHashButton;
    @FXML
    private MenuItem makeSecretKey;
    @FXML
    private Button enterkeyButton;
    @FXML
    private CheckBox isSecretKey;
    @FXML
    private Button saveHashButton;
    @FXML
    private Label chooseFileLabel;
    @FXML
    private Button chooseFileButton;
    @FXML
    private TextField hashField1;
    @FXML
    private TextField textField;
    @FXML
    private Button makeHashButton;
    @FXML
    private MenuItem closeMenuItem;
    @FXML
    private MenuItem menuAboutItem;

    public static String legalSecretKey;
    public static boolean flag = false;
    public static boolean limitFlag = false;
    public static LimitObject limitObject;

    @FXML
    void initialize() {
        makeHashButton.setOnAction(event -> makeHash());

        makeSecretKey.setOnAction(actionEvent -> createSecretKey());

        closeMenuItem.setOnAction(actionEvent -> closeProgram());

        menuAboutItem.setOnAction(actionEvent -> showAbout());

        chooseFileButton.setOnAction(actionEvent -> chooseFile());

        saveHashButton.setOnAction(actionEvent -> saveHash());

        enterkeyButton.setOnAction(actionEvent -> enterSecretKey());

        compareHashButton.setOnAction(actionEvent -> compareHash());
    }

    private void compareHash() {
        nicehashLabel.setVisible(false);
        badhashLabel.setVisible(false);
        String oldHash = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(".\\Полученное хеш значение.txt"))) {
            oldHash = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String newHash = hashField1.getText();
        if (oldHash.equals(newHash)) {
            nicehashLabel.setVisible(true);
        } else {
            badhashLabel.setVisible(true);
        }
    }

    public void change(ActionEvent actionEvent) {
        if (isSecretKey.isSelected()) {
           enterkeyButton.setDisable(false);
        } else {
           enterkeyButton.setDisable(true);
        }
    }

    private void saveHash() {
        try {
            File file = new File("Полученное хеш значение.txt");
            PrintWriter pw = new PrintWriter(file);
            pw.println(hashField1.getText());
            pw.close();
            saveHashLabel.setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void chooseFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(
                ".\\"));
        fileChooser.setTitle("Выберите файл для хеширования");
        fileChooser.getExtensionFilters()
                .add(new FileChooser.ExtensionFilter("Любое расширение","*.*"));
        File file = fileChooser.showOpenDialog(Main.stage);

        try {
            makeHashFile(file);
        } catch (Exception e) {
            Shake shakeChooseFileButton = new Shake(chooseFileButton);
            shakeChooseFileButton.playAnimation();
            chooseFileLabel.setVisible(true);
        }
    }

    private void makeHashFile(File file) {
        if (!isSecretKey.isSelected()) {
            MD4AlgoExecutor md4 = new MD4AlgoExecutor(file);
            String hash = md4.generateHashFile();
            if (hash.equals("Ошибка хеширования")) {
                Shake shakeChooseFileButton = new Shake(chooseFileButton);
                shakeChooseFileButton.playAnimation();
                chooseFileLabel.setVisible(true);
            } else {
                chooseFileLabel.setVisible(false);
            }
            hashField1.setText(hash);
        } else {
            MD4AlgoExecutor md4 = new MD4AlgoExecutor(file, legalSecretKey);
            String hash = md4.generateHashFileWithKey();
            if (hash.equals("Ошибка хеширования")) {
                Shake shakeChooseFileButton = new Shake(chooseFileButton);
                shakeChooseFileButton.playAnimation();
                chooseFileLabel.setVisible(true);
            } else {
                chooseFileLabel.setVisible(false);
            }
            hashField1.setText(hash);
        }
    }

    private void makeHash() {
        if (!isSecretKey.isSelected()) {
            String userText = textField.getText().trim();
            MD4AlgoExecutor md4 = new MD4AlgoExecutor(userText);
            String hash = md4.generateHashText();
            hashField1.setText(hash);
        } else {
            String userText = textField.getText().trim() + legalSecretKey;
            MD4AlgoExecutor md4 = new MD4AlgoExecutor(userText);
            String hash = md4.generateHashText();
            hashField1.setText(hash);
        }
    }

    private void showAbout() {
        openNewModalScene("/sample/fxml/about.fxml", "О программе");
    }

    private void createSecretKey() {
        openNewModalScene("/sample/fxml/createSecretKeyForm.fxml", "Создайте секретный ключ");
    }

    private void enterSecretKey() {
        openNewModalScene("/sample/fxml/enterSecretKeyForm.fxml", "Введите секретный ключ");
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

    private void closeProgram() {
        System.exit(1);
    }

    public void onFileChange(ActionEvent actionEvent) {
        if (fileBox.isSelected()) {
            messageBox.setSelected(false);
            chooseFileButton.setDisable(false);
            textField.setDisable(true);
            makeHashButton.setDisable(true);
        }
    }

    public void onMessageChange(ActionEvent actionEvent) {
        if (messageBox.isSelected()) {
            fileBox.setSelected(false);
            chooseFileButton.setDisable(true);
            textField.setDisable(false);
            makeHashButton.setDisable(false);
        }
    }
}
