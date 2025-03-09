package game.game_hard;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Box;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Controller_gate2 {
    @FXML
    private Box box1,box2,box3,box4;
    @FXML
    private ImageView human;
    @FXML
    private AnchorPane anchorPane;
    private Scene scene;
    private List<Box> group_Box;
    private Stage stage;
    private Animation gate2;

     public void initialize(Scene scene) throws IOException {
         this.scene = scene;
         group_Box = new ArrayList<>();
         group_Box.add(box1);
         group_Box.add(box2);
         group_Box.add(box3);
         group_Box.add(box4);
         gate2 = new Animation(human,group_Box,scene,anchorPane,null);
         gate2.setStage((Stage) scene.getWindow());
         gate2.initialize(scene);
     }
}
