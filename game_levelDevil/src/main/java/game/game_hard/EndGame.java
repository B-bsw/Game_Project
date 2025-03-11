package game.game_hard;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class EndGame extends Animation{
    @FXML
    private AnchorPane anchorPane;
    private Stage stage;
    private Scene scene;

    @FXML
    public void handleReset() throws IOException {
        dead();
    }
    public void setScene(Scene scene){
        this.scene = scene;
    }
    public void setStage(Stage stage){
        this.stage = stage;
    }

    @Override
    protected void dead() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("gate1.fxml"));
        Parent root = loader.load();
        scene = new Scene(root);

        Animation controller = loader.getController();
        controller.setStage(stage);
        controller.initialize(scene);

        stage.setScene(scene);
        stage.setTitle("Gate1");
        stage.show();
    }
}
