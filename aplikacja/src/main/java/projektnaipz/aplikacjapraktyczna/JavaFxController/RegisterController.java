package projektnaipz.aplikacjapraktyczna.JavaFxController;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import projektnaipz.aplikacjapraktyczna.App;

public class RegisterController {

    @FXML
    private Label errorText;
    @FXML
    private TextField loginField;
    @FXML
    private TextField passField;
    @FXML
    private TextField repeatField;
    @FXML
    private TextField sumField;
    @FXML
    private Button registerBtn;

    
    @FXML
    protected void onRegisterButtonClick() throws IOException {

        // pobranie wartości z pól tekstowych
        String login = loginField.getText();
        String pass = passField.getText();
        String repeat = repeatField.getText();
        String sum = sumField.getText();

        // falgi
        boolean allFieldsFilled = false;
        boolean passwordsMatch = false;
        boolean sumCorrect = false;

        // czyszczenie pola tekstowego
        errorText.setText("");

        // czy wszystkie pola są wypełnione
        if(login.isEmpty() || pass.isEmpty() || repeat.isEmpty() || sum.isEmpty()){
            errorText.setText("Wypełnij wszystkie pola!");
        }else {
            allFieldsFilled = true;
        }
        // czy podane hasła są takie same
        if(!pass.equals(repeat)){
            String tekst = errorText.getText();
            errorText.setText(tekst + "\nHasła nie są takie same!");
        }else {
            passwordsMatch = true;
        }
        // czy suma bezpieczenstwa poprawna
        if(!sum.equals("5")){
            String tekst = errorText.getText();
            errorText.setText(tekst + "\nBłędnie podana suma!");
        }else {
            sumCorrect = true;
        }

        // jeśli wszystko ok
        if (allFieldsFilled && passwordsMatch && sumCorrect ){
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("login-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 800, 600);
            Stage stageTheLabelBelongs = (Stage) errorText.getScene().getWindow();

            App.db.register(login, pass);
            stageTheLabelBelongs.setScene(scene);
            stageTheLabelBelongs.show();
        }
    }

    @FXML
    protected void onBackButtonClick() throws IOException {
        // przejście do ekranu logowania
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        Stage stageTheLabelBelongs = (Stage) errorText.getScene().getWindow();
        stageTheLabelBelongs.setScene(scene);
        stageTheLabelBelongs.show();
    }
}
