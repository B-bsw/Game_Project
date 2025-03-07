module game.game_hard {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens game.game_hard to javafx.fxml;
    exports game.game_hard;
}