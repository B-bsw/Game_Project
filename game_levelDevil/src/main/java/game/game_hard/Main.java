package game.game_hard;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private static Stage stageS;

    @Override
    public void start(Stage stage) throws IOException {
        stageS = stage;
        switchPage("first.fxml");
        stageS.setTitle("MyApp");
        stageS.show();
    }

    public void switchPage(String x) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(x));
        Scene scene = new Scene(fxmlLoader.load(),1200,800);
        stageS.setScene(scene);
    }

    public static void main(String[] args) {
        launch();
    }
}