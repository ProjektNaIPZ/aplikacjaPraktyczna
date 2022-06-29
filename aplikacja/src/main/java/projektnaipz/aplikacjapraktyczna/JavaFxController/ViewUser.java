package projektnaipz.aplikacjapraktyczna.JavaFxController;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import projektnaipz.aplikacjapraktyczna.App;
import projektnaipz.aplikacjapraktyczna.db.model.Uzytkownik;

import java.io.IOException;

public class ViewUser {

    static Uzytkownik uzytkownik;

    @FXML
    private Label title;
    @FXML
    private VBox vbox;

    @FXML
    public void initialize() {
        title.setText(uzytkownik.getLogin());
        title.setStyle("-fx-font-weight: bold");
        Label a = new Label("Czy jest adminem:");
        Label admin = new Label(String.valueOf(uzytkownik.isAdmin()));
        admin.setStyle("-fx-font-weight: bold");

        vbox.getChildren().addAll(a, admin);

    }

    @FXML
    protected void onBackButtonClick() throws IOException {
        // powrót do głównego menu
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("userList-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        Stage stageTheLabelBelongs = (Stage) title.getScene().getWindow();
        stageTheLabelBelongs.setScene(scene);
        stageTheLabelBelongs.show();
    }
}
