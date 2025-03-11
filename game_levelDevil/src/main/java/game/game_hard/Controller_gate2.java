package game.game_hard;

import javafx.animation.AnimationTimer;
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

public class Controller_gate2 extends Animation{
    @FXML
    private Box box1,box2,box3,box4;
    @FXML
    private ImageView human2,door2;
    @FXML
    private AnchorPane anchorPane;
    private Scene scene;
    private Stage stage;
    private List<Box> group_Box;
    private Animation gate2;

    private boolean pass = false;
    private boolean passR = false;
    private double speedBox = 7.0;
    private boolean passDead = true;

    @Override
     public void initialize(Scene scene) throws IOException {
         group_Box = new ArrayList<>();
        group_Box.add(box1);
        group_Box.add(box2);
        group_Box.add(box3);
        group_Box.add(box4);
        this.scene = scene;
        this.stage = (Stage) scene.getWindow();
        gate2 = new Animation(human2,group_Box,scene,anchorPane,null);
         gate2.setStage((Stage) scene.getWindow());
         gate2.initialize(scene);
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                try {
                    update();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        timer.start();
     }

     @Override
     public void update() throws IOException {
        if (human2 != null){
            if (human2.getTranslateX() > 400){
                pass = true;
                box2.setWidth(pass ? box2.getWidth() + speedBox : 0);
            }
            if (door2.getBoundsInParent().intersects(human2.getBoundsInParent())){
                if (human2.getTranslateX() > 840 && door2.getTranslateY() == 0){
                    speedBox = 0.0;
                    super.sw2Gate3();
                }
            }
        }
        if (box2.getBoundsInParent().intersects(human2.getBoundsInParent())){
            if (passDead){
                box2.setWidth(0);
                box2.setHeight(0);
                dead();
                passDead = false;
            }
        }
     }
}
