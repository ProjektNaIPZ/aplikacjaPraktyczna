package projektnaipz.aplikacjapraktyczna;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MakeFormController {
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
    List<String> listaPytan = new ArrayList<String>();

    // pytanie o indeksie 'i' ma 'x' możliwych odpowiedzi
    List<Integer> liczbaOdp = new ArrayList<Integer>();

    // pytanie o indeksie 'i' ma 'x' punktów na odpowiedzi
    List<Integer> listaPunktow = new ArrayList<Integer>();

    // przechowuje odpowiedzi
    List<String> listaOdp = new ArrayList<String>();

    @FXML
    protected void addQuestion() throws IOException{
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
        listaPunktow.add(punktyOdp);
        liczbaPytan++;
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
        for(Node nodeIn:((VBox)vbox).getChildren()){
            if(nodeIn instanceof TextField){
                listaOdp.add(((TextField)nodeIn).getText());
            }
        }



        
        /// TUTAJ WYSYLAMY TRANSAKCJE I WPIS DO BAZY DANYCH




        // potwierdzenie wysłania ankiety
        welcomeText.setText("Ankieta wysłana poprawnie!");
        welcomeText.setTextFill(Color.web("Red"));
    }
}