package game.game_hard;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("gate4.fxml"));
        Parent root = loader.load();
        scene = new Scene(root);

        Animation controller = loader.getController();
        controller.setStage(stage);
        controller.initialize(scene);

        stage.setScene(scene);
        stage.setTitle("Gate1");
        stage.show();
    }
    public static void setScene(String x) throws IOException {

    }

    public static void main(String[] args) {
        launch();
    }

}