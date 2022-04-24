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
import projektnaipz.aplikacjapraktyczna.db.model.Uzytkownik;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TakeFormController {

    static String kodAnkiety;

    @FXML
    private Label welcomeText;
    @FXML
    private Label title;
    @FXML
    private Label madeBy;
    @FXML
    private VBox vbox;

    private List<Label> listaLicznikow = new ArrayList<>();
    private List<Button> buttons = new ArrayList<>();

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
                PlusBtn btn1 = new PlusBtn(counter, lpkt);
                MinusBtn btn2 = new MinusBtn(counter, lpkt);
                hbox.getChildren().addAll(new Label(p.getListaOdp().get(j)),counter,btn1,btn2);
                odpBox.getChildren().add(hbox);
                buttons.add(btn1);
                buttons.add(btn2);
            }
            pytBox.getChildren().addAll(pyt, pktHbox, odpBox);
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

    static class PlusBtn extends Button {
        private final Label lo;
        private final Label lp;

        public PlusBtn(Label licznikOdp, Label licznikPyt){
            setText("+");
            this.lo = licznikOdp;
            this.lp = licznikPyt;
            setOnAction(event -> click());
        }

        private void click(){
            int pyt = Integer.parseInt(lp.getText());
            if(pyt > 0){
                int odp = Integer.parseInt(lo.getText());
                pyt--;
                odp++;
                lp.setText(String.valueOf(pyt));
                lo.setText(String.valueOf(odp));
            }
        }
    }

    static class MinusBtn extends Button {
        private final Label lo;
        private final Label lp;

        public MinusBtn(Label licznikOdp, Label licznikPyt){
            setText("-");
            this.lo = licznikOdp;
            this.lp = licznikPyt;
            setOnAction(event -> click());
        }

        private void click(){
            int odp = Integer.parseInt(lo.getText());
            if(odp > 0){
                int pyt = Integer.parseInt(lp.getText());
                pyt++;
                odp--;
                lp.setText(String.valueOf(pyt));
                lo.setText(String.valueOf(odp));
            }
        }
    }

    @FXML
    protected void onApplyButtonClick() throws IOException {
        List<Integer> listaPkt = new ArrayList<>();

        for(Button btn: buttons){
            btn.setDisable(true);
        }
        for(Label lo : listaLicznikow){
            listaPkt.add(Integer.parseInt(lo.getText()));
        }

        Odpowiedz odp = new Odpowiedz();
        odp.setIdAnkietowanego(App.zalogowany.getId());
        odp.setIdAnkiety(Integer.parseInt(kodAnkiety));
        odp.setListaPkt(listaPkt);

        boolean flag = App.db.checkForAnswer(odp.getIdAnkietowanego(), odp.getIdAnkiety());

        if (flag){
            welcomeText.setText("Już udzieliłeś odpowiedzi do tej ankiety!");
            return;
        }

        App.db.addOdp(odp);
        welcomeText.setText("Odpowiedź została wysłana!");

        // powrót do głównego menu
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        Stage stageTheLabelBelongs = (Stage) welcomeText.getScene().getWindow();
        stageTheLabelBelongs.setScene(scene);
        stageTheLabelBelongs.show();
    }
}