package projektnaipz.aplikacjapraktyczna;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class MainController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onMakeFormButtonClick() throws IOException {
        // przejście do ekranu tworzenia ankiety
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("makeForm-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        Stage stageTheLabelBelongs = (Stage) welcomeText.getScene().getWindow();
        stageTheLabelBelongs.setScene(scene);
        stageTheLabelBelongs.show();
    }

    @FXML
    protected void onTakeButtonClick() throws IOException {
        // przejście do ekranu tworzenia ankiety
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("take-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        Stage stageTheLabelBelongs = (Stage) welcomeText.getScene().getWindow();
        stageTheLabelBelongs.setScene(scene);
        stageTheLabelBelongs.show();
    }

    @FXML
    protected void onYourFormsButtonClick() throws IOException {
        // przejście do ekranu tworzenia ankiety
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("userForms-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        Stage stageTheLabelBelongs = (Stage) welcomeText.getScene().getWindow();
        stageTheLabelBelongs.setScene(scene);
        stageTheLabelBelongs.show();
    }

    @FXML
    protected void onLogoutButtonClick() throws IOException {
        // powrót do ekranu logowania
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        Stage stageTheLabelBelongs = (Stage) welcomeText.getScene().getWindow();
        stageTheLabelBelongs.setScene(scene);
        stageTheLabelBelongs.show();
    }
}