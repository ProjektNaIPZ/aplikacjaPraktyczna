package projektnaipz.aplikacjapraktyczna;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class LoginController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onLoginButtonClick() {
        welcomeText.setText("Zalogowałeś się!");
    }
    @FXML
    protected void onRegisterButtonClick() {
        welcomeText.setText("Zarejestrowałeś się!");
    }
}