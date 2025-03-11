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
    private static Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main_menu.fxml"));
        Parent root = loader.load();
        scene = new Scene(root);

        MainMenu con = loader.getController();
//        EndGame con = loader.getController();
//        con.setStage(stage);
//        con.setScene(scene);
//        Animation controller = loader.getController();
//        controller.setStage(stage);
//        controller.initialize(scene);

        stage.setScene(scene);
        this.stage = stage;
        stage.setTitle("Gate1");
        stage.show();
    }

    public static Stage getStage() {
        return stage;
    }

    public static void main(String[] args) {
        launch();
    }

}