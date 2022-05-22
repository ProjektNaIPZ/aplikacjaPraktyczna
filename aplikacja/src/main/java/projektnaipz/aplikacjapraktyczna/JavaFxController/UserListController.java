package projektnaipz.aplikacjapraktyczna.JavaFxController;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import projektnaipz.aplikacjapraktyczna.App;
import projektnaipz.aplikacjapraktyczna.db.model.Uzytkownik;

import java.io.IOException;
import java.util.List;

public class UserListController {

    List<Uzytkownik> uzytkownicy;

    @FXML
    VBox vbox;

    @FXML
    public void initialize() {

        uzytkownicy = App.db.getAllUsers();

        if (uzytkownicy == null) {
            vbox.getChildren().add(new Label("Brak użytkownikow w bazie danych."));
        } else {
            for (Uzytkownik u : uzytkownicy) {
                PrzyciskUzytkownika userBtn = new PrzyciskUzytkownika(u);
                vbox.getChildren().add(userBtn);
            }
        }
    }

    @FXML
    protected void onBackButtonClick() throws IOException {
        // powrót do głównego menu
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("userForms-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        Stage stageTheLabelBelongs = (Stage) vbox.getScene().getWindow();
        stageTheLabelBelongs.setScene(scene);
        stageTheLabelBelongs.show();
    }

    class PrzyciskUzytkownika extends Button {
        Uzytkownik uzytkownik;

        public PrzyciskUzytkownika(Uzytkownik u) {
            this.setText(u.getLogin());
            this.setMinSize(200, 100);
            this.setMaxHeight(100);
            this.uzytkownik = u;
            this.setOnAction(event -> click());
        }

        private void click(){
            ViewUser.uzytkownik = uzytkownik;

            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("viewUser.fxml"));
            Scene scene = null;
            try {
                scene = new Scene(fxmlLoader.load(), 800, 600);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage stageTheLabelBelongs = (Stage) this.getScene().getWindow();
            stageTheLabelBelongs.setScene(scene);
            stageTheLabelBelongs.show();
        }
    }
}
