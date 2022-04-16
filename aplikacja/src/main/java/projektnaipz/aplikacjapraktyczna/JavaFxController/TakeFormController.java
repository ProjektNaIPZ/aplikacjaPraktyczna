package projektnaipz.aplikacjapraktyczna.JavaFxController;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import projektnaipz.aplikacjapraktyczna.App;
import projektnaipz.aplikacjapraktyczna.db.model.Ankieta;
import projektnaipz.aplikacjapraktyczna.db.model.Pytanie;
import projektnaipz.aplikacjapraktyczna.db.model.Uzytkownik;

import java.io.IOException;

public class TakeFormController {
    static String kodAnkiety;

    @FXML
    private Label title;
    @FXML
    private Label madeBy;
    @FXML
    private VBox vbox;

    @FXML
    public void initialize() {
        Ankieta ankieta = App.db.getAnkietaByKod(kodAnkiety);
        title.setText(ankieta.getTytulAnkiety());
        title.setStyle("-fx-font-weight: bold");
        Uzytkownik autor = App.db.getUserById(ankieta.getAutor());
        madeBy.setText("Ankieta stworzona przez: " + autor.getLogin());

        // stworzenie i dodanie elementow
        for (int i = 0; i < ankieta.getListaPytan().size(); i++){
            VBox pytBox = new VBox();
            pytBox.setPadding(new Insets(0, 0, 0, 10));
            Pytanie p = ankieta.getListaPytan().get(i);
            Label pyt = new Label(p.getTrescPytania());
            pyt.setStyle("-fx-font-weight: bold");
            Label lpkt = new Label("Liczba punktów: " + p.getLiczbaPunktow());

            VBox odpBox = new VBox();
            odpBox.setPadding(new Insets(10, 0, 15, 10));
            for(int j = 0; j < p.getListaOdp().size(); j++) {
                HBox hbox = new HBox();
                hbox.setSpacing(15);
                hbox.setPadding(new Insets(5, 0, 5, 10));
                Button plusBtn = new Button("+");
                Button minusBtn = new Button("-");
                Label counter = new Label("0");
                hbox.getChildren().addAll(new Label(p.getListaOdp().get(j)),counter,plusBtn,minusBtn);
                odpBox.getChildren().add(hbox);
            }
            pytBox.getChildren().addAll(pyt, lpkt, odpBox);
            vbox.getChildren().add(pytBox);
        }

    }

    @FXML
    protected void onBackButtonClick() throws IOException {
        // powrót do głównego menu
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        Stage stageTheLabelBelongs = (Stage) title.getScene().getWindow();
        stageTheLabelBelongs.setScene(scene);
        stageTheLabelBelongs.show();
    }

    @FXML
    protected void onApplyButtonClick() throws IOException {

    }
}