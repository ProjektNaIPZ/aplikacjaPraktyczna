package projektnaipz.aplikacjapraktyczna;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import projektnaipz.aplikacjapraktyczna.db.Uzytkownik;

import java.io.IOException;

public class LoginController {
    @FXML
    private Label welcomeText;
    @FXML
    private TextField loginField;
    @FXML
    private TextField passField;

    @FXML
    protected void onLoginButtonClick() throws IOException {
        // pobranie wartości z pól tekstowych
        String login = loginField.getText();
        String pass = passField.getText();

        // falgi
        boolean allFieldsFilled = false;
        boolean passwordsMatch = false;

        // czyszczenie pola tekstowego
        welcomeText.setText("");

        // czy wszystkie pola są wypełnione
        if(login.isEmpty() || pass.isEmpty()){
            welcomeText.setText("Wypełnij wszystkie pola!");
        }else {
            allFieldsFilled = true;
        }

        // jesli nie znaleziono użytkownika o danym loginie, to błąd
        Uzytkownik u = App.db.findUserByLogin(login);
        if(u == null) {
            welcomeText.setText("\nNie znaleziono użytkownika o podanym loginie!");
        }
        else // znaleziono login w bazie danych 
        {
            // jesli hasła się nie zgadzają
            if(!pass.equals(u.getHaslo())){
                welcomeText.setText("\nPodane hasło jest nieprawidłowe!");
            }else {
                passwordsMatch = true;
            }
            // jeśli wszystko ok
            if (allFieldsFilled && passwordsMatch){
                welcomeText.setText("Zalogowano.");
                // przejście do głównego menu
                FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("main-view.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 800, 600);
                Stage stageTheLabelBelongs = (Stage) welcomeText.getScene().getWindow();
                stageTheLabelBelongs.setScene(scene);
                stageTheLabelBelongs.show();
            }
        }
    }

    @FXML
    protected void onGoToRegistrationButtonClick() throws IOException {
        // przejście do ekranu rejestracji
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("register-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        Stage stageTheLabelBelongs = (Stage) welcomeText.getScene().getWindow();
        stageTheLabelBelongs.setScene(scene);
        stageTheLabelBelongs.show();
    }
}