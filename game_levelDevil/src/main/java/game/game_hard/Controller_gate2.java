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
import java.util.Map;

public class Controller_gate2 extends Animation{
    @FXML
    private Box box1,box2,box3,box4;
    @FXML
    private ImageView human2,door2;
    @FXML
    private AnchorPane anchorPane;
    private Scene scene;
    private List<Box> group_Box;
    private Animation gate2;

    private boolean pass = false;
    private double speedBox = 7.0;

    @Override
     public void initialize(Scene scene) throws IOException {
         this.scene = scene;
         group_Box = new ArrayList<>();
         group_Box.add(box1);
         group_Box.add(box2);
         group_Box.add(box3);
         group_Box.add(box4);
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
//            System.out.println(human2.getTranslateX());
            if (human2.getTranslateX() > 400){
                pass = true;
                box2.setWidth(pass ? box2.getWidth() + speedBox : 0);
            }
            if (door2.getBoundsInParent().intersects(human2.getBoundsInParent())){
            System.out.println(human2.getTranslateX() + " " + door2.getTranslateY());
                if (human2.getTranslateX() > 840 && door2.getTranslateY() == 0){
                    System.out.println("OK");
                    speedBox = 0.0;
                }
            }
        }
     }

}
