package projektnaipz.aplikacjapraktyczna.JavaFxController;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import projektnaipz.aplikacjapraktyczna.App;
import projektnaipz.aplikacjapraktyczna.db.model.Ankieta;
import projektnaipz.aplikacjapraktyczna.db.model.Pytanie;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MakeFormController {
    @FXML
    public TextField title;
    @FXML
    private Label welcomeText;
    @FXML
    private TextField question;
    @FXML
    private TextField answers;
    @FXML
    private TextField points;
    @FXML
    private VBox vbox;

    // liczba dodanych w ankiecie pytan
    private int liczbaPytan = 0;

    // przechowuje treść pytań
    List<String> listaPytan = new ArrayList<>();

    // pytanie o indeksie 'i' ma 'x' możliwych odpowiedzi
    List<Integer> liczbaOdp = new ArrayList<>();

    // pytanie o indeksie 'i' ma 'x' punktów na odpowiedzi
    List<Integer> listaPunktow = new ArrayList<>();

    // przechowuje odpowiedzi
    List<String> listaOdp = new ArrayList<>();

    @FXML
    protected void addQuestion() {
        // pobranie wartości z pól tekstowych
        String pytanie = question.getText();
        int odp = Integer.parseInt(answers.getText());
        int punktyOdp = Integer.parseInt(points.getText());

        // stworzenie i dodanie elementow
        vbox.getChildren().add(new Label(pytanie));
        for (int i = 0; i < odp; i++){
            vbox.getChildren().add(new TextField( "odpowiedz " + (i+1)));
        }

        // dane
        liczbaOdp.add(odp);
        listaPytan.add(pytanie);
        listaPunktow.add(punktyOdp);
        liczbaPytan++;

        title.setDisable(true);
        question.clear();
        answers.clear();
        points.clear();
    }

    @FXML
    protected void onBackButtonClick() throws IOException {
        // powrót do głównego menu
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        Stage stageTheLabelBelongs = (Stage) welcomeText.getScene().getWindow();
        stageTheLabelBelongs.setScene(scene);
        stageTheLabelBelongs.show();
    }

    @FXML
    protected void onApplyButtonClick() throws IOException {

        // pobranie wartosci z pól odpowiedzi
        for(Node nodeIn: vbox.getChildren()){
            if(nodeIn instanceof TextField){
                listaOdp.add(((TextField)nodeIn).getText());
            }
        }

        List<Pytanie> listaObiektowPytanie = new ArrayList<>();

        // tworzenie obiektow pytanie
        int zapisaneOdp = 0;
        int odpLimit;
        for(int i = 0; i < listaPytan.size(); i++){
            List<String> odp = new ArrayList<>();
            odpLimit = zapisaneOdp + liczbaOdp.get(i);
            for(; zapisaneOdp < odpLimit; zapisaneOdp++){
                odp.add(listaOdp.get(zapisaneOdp));
            }
            Pytanie p = new Pytanie(listaPytan.get(i), odp, listaPunktow.get(i));
            listaObiektowPytanie.add(p);
        }

        String kodAnkiety = Integer.toString(getRandomNumber(100000, 999999));

        Ankieta ankieta = new Ankieta(title.getText(),
                App.zalogowany.getId(),
                kodAnkiety,
                listaObiektowPytanie);

        App.db.addAnkietaToDb(ankieta);

        // potwierdzenie wysłania ankiety
        welcomeText.setText("Ankieta wysłana poprawnie!");
        welcomeText.setTextFill(Color.web("Red"));

        // powrót do głównego menu
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        Stage stageTheLabelBelongs = (Stage) welcomeText.getScene().getWindow();
        stageTheLabelBelongs.setScene(scene);
        stageTheLabelBelongs.show();
    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}