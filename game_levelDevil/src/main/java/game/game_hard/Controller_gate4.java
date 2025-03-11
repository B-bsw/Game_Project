package game.game_hard;

import game.game_hard.Animation;
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

public class Controller_gate4 extends Animation {
    @FXML
    private ImageView human4, door4, dog;
    @FXML
    private Box box1, box2, box3, box4, box5, box6, box7, box8, box9, box10, box11;
    @FXML
    private AnchorPane anchorPane4;

    private List<Box> group_Box;
    private Scene scene;
    private Stage stage;
    private Animation gate4;
    private double speedDog = 10.0;
    private boolean pass = false;
    private boolean passDog = false;
    private boolean passTheDog = false;
    private boolean passFate2 = false;
    private boolean passFate3 = false;
    private boolean passFate4 = false;
    private boolean passFate5 = false;
    private boolean passEndGame = false;
    private boolean passLast = false;
    private double speedBox = 2.0;

    @Override
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
        group_Box.add(box10);
        group_Box.add(box11);
        gate4 = new Animation(human4, group_Box, scene, anchorPane4, null);
        gate4.setStage((Stage) scene.getWindow());
        gate4.initialize(scene);
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
    protected void update() throws IOException {
//        System.out.println(human4.getTranslateX() + " " + human4.getTranslateY());
        if (human4.getTranslateX() > 100) {
            pass = true;
            dog.setVisible(true);

        }
        if (pass) {
            if (dog.getTranslateX() < 1) {
                dog.setTranslateX(dog.getTranslateX() - speedDog);
                passDog = true;
                passLast = true;
            }
            if (passDog) {
                if (dog.getTranslateX() == -550) {
                }
                if (passDog) {
                    if (dog.getTranslateX() == -500) {
                        speedDog = 0.0;
                        dog.setVisible(false);
                        dog.setLayoutX(1);
                        dog.setLayoutY(1);
                        passTheDog = true;
                    }
                }
            }
            if (passLast) {
                if (dog.getBoundsInParent().intersects(human4.getBoundsInParent())) {
                    pass = false;
                    passDog = false;
                    passFate2 = false;
                    passLast = false;
                    passFate3 = false;
                    dead();
                }
            }
            if (passTheDog) {
                if (human4.getTranslateX() > 390) {
                    passFate2 = true;
                    passTheDog = false;
                }
            }
            if (passFate2) {
//            System.out.println(box3.getTranslateX());
                if (box3.getTranslateX() != -860 + 20) {
                    box3.setTranslateX(box3.getTranslateX() - speedBox);
                    passFate3 = true;
                } else {
                    passFate2 = false;
                }
            }

            if (human4.getTranslateY() == 300) {
                dead();
            }

            if (passFate3) { //
//            System.out.println(box4.getTranslateY());
                if (box3.getTranslateX() == -840) {
                    passFate2 = false;
                    passFate4 = true;
                }
                if (passFate4) {
                    box4.setTranslateY(box4.getTranslateY() + speedBox);
                }
                if (box4.getTranslateY() > 500) {
                    if (box3.getTranslateX() > -15) {
                        speedBox = 0.0;
                    }
                    box3.setTranslateX(box3.getTranslateX() + speedBox);
                }
                if (passFate4 && box4.getTranslateY() > 500) {
                    box3.setTranslateX(box3.getTranslateX() + speedBox);
                    passFate5 = true;
                }
                if (box3.getBoundsInParent().intersects(human4.getBoundsInParent())) {
                    passFate2 = false;
                    box3.setTranslateX(840);
                    dead();
                    passFate3 = false;
                }
            }
            if (passFate5) {
                if (door4.getBoundsInParent().intersects(human4.getBoundsInParent())) {
                    passEndGame = true;
                    passFate3 = false;
                    passFate5 = false;
                    door4.setLayoutX(10000);
                }
            }
            if (passEndGame) {
                passEndGame = false;
                passFate5 = false;
                endGame();
            }
            if(dog.getBoundsInParent().intersects(human4.getBoundsInParent())){
                dead();
            }
        }
    }

}

