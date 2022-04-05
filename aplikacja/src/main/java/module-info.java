module projektnaipz.aplikacjapraktyczna {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens projektnaipz.aplikacjapraktyczna to javafx.fxml;
    exports projektnaipz.aplikacjapraktyczna;
}