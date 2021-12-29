package projektnaipz.aplikacjapraktyczna;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegisterController {

    @FXML
    private Label errorText;
    private TextField loginField;
    private TextField passField;
    private TextField repeatField;

    
    @FXML
    protected void onRegisterButtonClick() throws IOException {
        
        errorText.setText("Podaj dane!");
    }
}
