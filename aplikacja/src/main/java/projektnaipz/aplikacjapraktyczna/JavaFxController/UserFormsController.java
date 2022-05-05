package projektnaipz.aplikacjapraktyczna.JavaFxController;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import projektnaipz.aplikacjapraktyczna.App;
import projektnaipz.aplikacjapraktyczna.db.model.Ankieta;

import java.io.IOException;
import java.util.List;

public class UserFormsController {

    List<Ankieta> ankiety;

    @FXML
    VBox vbox;

    @FXML
    public void initialize() {
       ankiety = App.db.getAnkietyByUser(App.zalogowany.getId());
       if (ankiety == null ){
           vbox.getChildren().add(new Label("Nie stworzyłeś żadnych ankiet."));
       } else {
           for(Ankieta a : ankiety){
               PrzyciskAnkiety ankietaBtn = new PrzyciskAnkiety(a);
               vbox.getChildren().add(ankietaBtn);
           }
       }
    }

    @FXML
    protected void onBackButtonClick() throws IOException {
        // powrót do głównego menu
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        Stage stageTheLabelBelongs = (Stage) vbox.getScene().getWindow();
        stageTheLabelBelongs.setScene(scene);
        stageTheLabelBelongs.show();
    }

    class PrzyciskAnkiety extends Button {
        Ankieta ankieta;

        public PrzyciskAnkiety(Ankieta a) {
            this.setText(a.getTytulAnkiety());
            this.setMinSize(200, 100);
            this.setMaxHeight(100);
            this.ankieta = a;
            this.setOnAction(event -> click());
        }

        private void click(){
            ViewUserForm.ankieta = ankieta;

            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("viewUserForm.fxml"));
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