package projektnaipz.aplikacjapraktyczna;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import projektnaipz.aplikacjapraktyczna.db.DbController;
import projektnaipz.aplikacjapraktyczna.db.model.Uzytkownik;

import java.io.IOException;

public class App extends Application {

    static public DbController db;
    static public Uzytkownik zalogowany;

    @Override
    public void start(Stage stage) throws IOException {
        // uruchomienie aplikacji na oknie logowania
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        stage.setTitle("Aplikacja praktyczna");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws IOException, JsonParseException, JsonMappingException {
        db = new DbController();
        System.out.println(db.getJsonUserById(3));
        launch();
    }
}
