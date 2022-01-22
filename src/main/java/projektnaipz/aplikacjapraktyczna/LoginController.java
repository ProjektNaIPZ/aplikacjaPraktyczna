package projektnaipz.aplikacjapraktyczna;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
        Boolean allFieldsFilled = false;
        Boolean passwordsMatch = false;

        // czyszczenie pola tekstowego
        welcomeText.setText("");

        // czy wszystkie pola są wypełnione
        if(login.isEmpty() || pass.isEmpty()){
            welcomeText.setText("Wypełnij wszystkie pola!");
        }else {
            allFieldsFilled = true;
        }

        // jesli nie znaleziono użytkownika o danym loginie, to błąd
        String loginDB = "admin";

        if(!login.equals(loginDB)){
            welcomeText.setText("\nNie znaleziono użytkownika o podanym loginie!");
        } 
        else // znaleziono login w bazie danych 
        {
            // pobranie hasła z bazy danych dla podanego loginu
            String passDB = "admin";

            // jesli hasła się nie zgadzają
            if(!pass.equals(passDB)){
                welcomeText.setText("\nPodane hasło jest nieprawidłowe!");
            }else {
                passwordsMatch = true;
            }

            // jeśli wszystko ok
            if ( allFieldsFilled && passwordsMatch){
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
    protected void onRegisterButtonClick() throws IOException {
        // przejście do ekranu rejestracji
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("register-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        Stage stageTheLabelBelongs = (Stage) welcomeText.getScene().getWindow();
        stageTheLabelBelongs.setScene(scene);
        stageTheLabelBelongs.show();
    }
}