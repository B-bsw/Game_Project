package game.game_leveldevil;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    private static Stage stageS;

    @Override
    public void start(Stage stage) throws IOException {
        stageS = stage;
        switchPage("hello-view.fxml");
        stage.setTitle("MyApp");
        stage.show();
    }

    public void switchPage(String x) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(x));
        Scene scene = new Scene(fxmlLoader.load(),500,500);
        stageS.setScene(scene);
    }

    public static void main(String[] args) {
        launch();
    }
}