module projektnaipz.aplikacjapraktyczna {
    requires javafx.controls;
    requires javafx.fxml;


    opens projektnaipz.aplikacjapraktyczna to javafx.fxml;
    exports projektnaipz.aplikacjapraktyczna;
}