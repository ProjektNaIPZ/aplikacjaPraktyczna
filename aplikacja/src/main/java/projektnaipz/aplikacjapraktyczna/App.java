package projektnaipz.aplikacjapraktyczna;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import projektnaipz.aplikacjapraktyczna.db.DbController;

import java.io.IOException;

public class App extends Application {

    static DbController db;

    @Override
    public void start(Stage stage) throws IOException {
        // uruchomienie aplikacji na oknie logowania
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("Aplikacja praktyczna");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        db = new DbController();
        launch();
    }
}
