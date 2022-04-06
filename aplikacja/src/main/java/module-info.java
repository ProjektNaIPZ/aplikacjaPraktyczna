module projektnaipz.aplikacjapraktyczna {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;


    opens projektnaipz.aplikacjapraktyczna to javafx.fxml;
    exports projektnaipz.aplikacjapraktyczna;
    exports projektnaipz.aplikacjapraktyczna.JavaFxController;
    exports projektnaipz.aplikacjapraktyczna.db;
    opens projektnaipz.aplikacjapraktyczna.JavaFxController to javafx.fxml;
    exports projektnaipz.aplikacjapraktyczna.db.model;
}