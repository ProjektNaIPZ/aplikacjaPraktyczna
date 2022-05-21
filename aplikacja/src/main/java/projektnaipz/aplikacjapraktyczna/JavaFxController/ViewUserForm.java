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
import projektnaipz.aplikacjapraktyczna.db.model.Odpowiedz;
import projektnaipz.aplikacjapraktyczna.db.model.Pytanie;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ViewUserForm {

    static Ankieta ankieta;
    private List<Label> listaLicznikow = new ArrayList<>();
    private List<Integer> suma = new ArrayList<>();
    private Label czyOtwarta;
    private Label warningLabel;
    private Button otwartaBtn;

    @FXML
    private Label title;
    @FXML
    private VBox vbox;

    @FXML
    public void initialize() {
        System.out.println(ankieta.getKodAnkiety());
        title.setText(ankieta.getTytulAnkiety());
        title.setStyle("-fx-font-weight: bold");

        List<Odpowiedz> odpowiedzi = new ArrayList<>();
        odpowiedzi = App.db.getOdpByKodAnkiety(ankieta.getKodAnkiety());

        // stworzenie i dodanie elementow pytań i odp
        for (int i = 0; i < ankieta.getListaPytan().size(); i++){
            VBox pytBox = new VBox();
            pytBox.setPadding(new Insets(0, 0, 0, 10));
            Pytanie p = ankieta.getListaPytan().get(i);
            Label pyt = new Label(p.getTrescPytania());
            pyt.setStyle("-fx-font-weight: bold");
            HBox pktHbox = new HBox();
            Label lpkt = new Label(p.getLiczbaPunktow().toString());
            pktHbox.getChildren().addAll(new Label("Liczba punktów: "), lpkt);

            VBox odpBox = new VBox();
            odpBox.setPadding(new Insets(10, 0, 15, 10));
            for(int j = 0; j < p.getListaOdp().size(); j++) {
                HBox hbox = new HBox();
                hbox.setSpacing(15);
                hbox.setPadding(new Insets(5, 0, 5, 10));
                Label counter = new Label("0");
                listaLicznikow.add(counter);
                hbox.getChildren().addAll(new Label(p.getListaOdp().get(j)),counter);
                odpBox.getChildren().add(hbox);
            }
            pytBox.getChildren().addAll(pyt, pktHbox, odpBox);
            vbox.getChildren().add(pytBox);
        }

        // reszta danych ankiety
        Label kod = new Label("Kod ankiety: " + ankieta.getKodAnkiety());
        czyOtwarta = new Label("Czy otwarta: " + ankieta.getCzyOtwarta());
        Label udzielone = new Label("Liczba wypełnień ankiety: " + odpowiedzi.size());
        czyOtwarta.setStyle("-fx-font-weight: bold"); kod.setStyle("-fx-font-weight: bold"); udzielone.setStyle("-fx-font-weight: bold");
        otwartaBtn = new Button();
        otwartaBtn.setOnAction(event -> clickDostep());
        if(ankieta.getCzyOtwarta()){
            otwartaBtn.setText("Zamknij ankietę");
        } else {
            if(App.zalogowany.isAdmin()) {
                otwartaBtn.setText("Otwórz ankietę");
            } else {
                otwartaBtn.setText("Ankieta została zamknięta");
                otwartaBtn.setDisable(true);
            }
        }
        warningLabel = new Label("Otwarcie ankiety będzie później niemożliwe!");
        warningLabel.setOpacity(0);
        vbox.getChildren().addAll(kod, udzielone, czyOtwarta, otwartaBtn, warningLabel);

        if(!odpowiedzi.isEmpty()){
            // liczenie głosów
            for(int i = 0; i < odpowiedzi.size(); i++){
                List<Integer> pkt = odpowiedzi.get(i).getListaPkt();
                if (i == 0)
                    suma.addAll(pkt);
                else {
                    for(int j = 0; j < pkt.size(); j++){
                        suma.set(j, suma.get(j) + pkt.get(j));
                    }
                }
            }
            // zapisanie wyników
            for(int i = 0; i < listaLicznikow.size(); i++){
                listaLicznikow.get(i).setText(String.valueOf(suma.get(i)) + "pkt");
            }
        }
    }

    void clickDostep(){
        App.db.flipAccessByKodAnkiety(ankieta.getKodAnkiety());
        ankieta.setCzyOtwarta(!ankieta.getCzyOtwarta());
        czyOtwarta.setText("Czy otwarta: " + ankieta.getCzyOtwarta());
        if(ankieta.getCzyOtwarta()){
            otwartaBtn.setText("Zamknij ankietę");
        } else {
            if(App.zalogowany.isAdmin()) {
                otwartaBtn.setText("Otwórz ankietę");
            } else {
                otwartaBtn.setText("Cofnij");
                warningLabel.setOpacity(1);
            }
        }
    }

    @FXML
    protected void onBackButtonClick() throws IOException {
        // powrót do głównego menu
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("userForms-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        Stage stageTheLabelBelongs = (Stage) title.getScene().getWindow();
        stageTheLabelBelongs.setScene(scene);
        stageTheLabelBelongs.show();
    }

}
