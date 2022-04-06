package projektnaipz.aplikacjapraktyczna.JavaFxController;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import projektnaipz.aplikacjapraktyczna.App;

import java.io.IOException;

public class MainController {

    @FXML
    private Label welcomeText;

    @FXML
    protected void onMakeFormButtonClick() throws IOException {
        // przejście do ekranu tworzenia ankiety
        loadWindow("makeForm-view.fxml");
    }

    @FXML
    protected void onTakeButtonClick() throws IOException {
        // przejście do ekranu tworzenia ankiety
        loadWindow("take-view.fxml");
    }

    @FXML
    protected void onYourFormsButtonClick() throws IOException {
        // przejście do ekranu tworzenia ankiety
        loadWindow("userForms-view.fxml");
    }

    @FXML
    protected void onLogoutButtonClick() throws IOException {
        // powrót do ekranu logowania
        App.zalogowany = null;
        loadWindow("login-view.fxml");
    }

    public void loadWindow(String fmxlView) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fmxlView));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        Stage stageTheLabelBelongs = (Stage) welcomeText.getScene().getWindow();
        stageTheLabelBelongs.setScene(scene);
        stageTheLabelBelongs.show();
    }
}