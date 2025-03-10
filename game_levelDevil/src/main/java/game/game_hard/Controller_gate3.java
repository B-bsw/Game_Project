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

public class Controller_gate3 extends Animation{
    private Scene scene;
    private Stage stage;
    private List<Box> group_Box;
    private Animation gate3;
    private boolean pass = true;
    private static boolean passBox = false;
    private static boolean passDoor ;

    @FXML private AnchorPane anchorPane3;
    @FXML private ImageView human3,door3;
    @FXML  public Box box1 , box2 , box3 , box4 , box5 , box6 , box7 , box8 , box9;

    public void initialize(Scene scene) throws IOException {
        this.scene = scene;
        this.stage = (Stage) scene.getWindow();
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

        gate3 = new Animation(human3,group_Box,scene,anchorPane3,null);
        gate3.setStage((Stage)scene.getWindow());
        gate3.initialize(scene);
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
        double speedBox = 1;
        if (human3 != null){
            if (pass){
                if (human3.getTranslateX() > 480){
                    System.out.println(" door : " + door3.getTranslateY());
                    if (human3.getTranslateX() - 750 > door3.getTranslateX()){
                        passDoor = true;
                        door3.setLayoutX(136);
                    }
                }
            }
            if (passDoor){
                if (human3.getTranslateX() <= 250){
                    passBox = true;
                    pass = false;
                }
            }
            if (passBox){
                box5.setTranslateY(box5.getTranslateY() + speedBox);
                if (human3.getTranslateX() == 180){
                    door3.setLayoutX(992);
                }
            }
        }
        if (door3.getBoundsInParent().intersects(human3.getBoundsInParent())){
            System.out.println(human3.getTranslateX());
            if (human3.getTranslateX() > 840){
                System.out.println("END GAME");
                //go to gate4
            }
        }
    }

}
