package game.game_hard;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Box;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Controller_gate4  extends Animation{
    @FXML private ImageView human4,door4 , dog;
    @FXML private Box box1 , box2 , box3 , box4 , box5 , box6 , box7 , box8 , box9;
    @FXML private AnchorPane anchorPane4;

    private List<Box> group_Box;
    private Scene scene;
    private Stage stage;
    private Animation gate4;

    @Override
    public void initialize(Scene scene) throws IOException {
        this.scene = scene;
        this.stage = (Stage)scene.getWindow();
        group_Box = new ArrayList<>();
        group_Box.add(box1);
        group_Box.add(box2);
        group_Box.add(box3);
        group_Box.add(box4);
        group_Box.add(box5);
        group_Box.add(box6);
        group_Box.add(box7);
        group_Box.add(box8);
        group_Box.add(box9);
        gate4 = new Animation(human4,group_Box,scene,anchorPane4,null);
        gate4.setStage((Stage)scene.getWindow());
        gate4.setStage((Stage)scene.getWindow());
        gate4.initialize(scene);
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };
        timer.start();
    }

    @Override
    protected void update() {
        if (human4.getTranslateX() > 100){
            dog.setVisible(true);
            dog.setLayoutX(dog.getLayoutX() - 10);
        }
    }
}
