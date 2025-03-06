module game.game_leveldevil {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens game.game_leveldevil to javafx.fxml;
    exports game.game_leveldevil;
}