package projektnaipz.aplikacjapraktyczna.JavaFxController;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import projektnaipz.aplikacjapraktyczna.App;

import java.io.IOException;

public class GiveCodeController {
    @FXML
    private Label welcomeText;
    @FXML
    private TextField kodAnkietyTextField;
    @FXML
    protected void onContinueButtonClick() throws IOException {

        TakeFormController.kodAnkiety = kodAnkietyTextField.getText();

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("take-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        Stage stageTheLabelBelongs = (Stage) welcomeText.getScene().getWindow();
        stageTheLabelBelongs.setScene(scene);
        stageTheLabelBelongs.show();
    }

    @FXML
    protected void onBackButtonClick() throws IOException {
        // powrót do głównego menu
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        Stage stageTheLabelBelongs = (Stage) welcomeText.getScene().getWindow();
        stageTheLabelBelongs.setScene(scene);
        stageTheLabelBelongs.show();
    }
}