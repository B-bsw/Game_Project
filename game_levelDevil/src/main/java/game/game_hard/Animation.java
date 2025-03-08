package game.game_hard;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Box;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Animation {
    @FXML
    private Label welcomeText;
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Box box_floor , box_1 , box_2 , box_3 , box_4;

    @FXML
    private Box box_move;
    @FXML
    private ImageView human , door;

    private boolean once = false;
    private double boxSpeed = 1.0;
    private double humanSpeedY = 0.0;
    private double JUMP_SPEED = -10.0;
    private boolean canJump = true;
    private Point2D playerVelocity = new Point2D(0, 0);

    private HashMap<KeyCode, Boolean> keys = new HashMap<>();
    private List<Box> group_Box = new ArrayList<>();
    private Scene scene;


    public void initialize(Scene scene){
        group_Box.add(box_floor);
        group_Box.add(box_1);
        group_Box.add(box_2);
        group_Box.add(box_3);
        group_Box.add(box_4);
        group_Box.add(box_move);
        this.scene = scene;

        scene.setOnKeyPressed(this::handleKeyboard);
        scene.setOnKeyReleased(this::handleKeyRelease);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };
        timer.start();
        if (human.getBoundsInParent().intersects(door.getBoundsInParent())){
            System.out.println("THE END");
        }
    }


    public void handleKeyboard(KeyEvent e) {
        keys.put(e.getCode(), true);
        if (e.getCode() == KeyCode.W && (isOnBox(group_Box) || isOnBox(group_Box))) {
            humanSpeedY = JUMP_SPEED;
        }
    }

    private void handleKeyRelease(KeyEvent e) {
        keys.put(e.getCode(), false);
    }

    private void movePlayerX(int value) {
        boolean movingRight =  value > 0;
        for (int i = 0; i < Math.abs(value); i++) {
            for (Box box : group_Box) {

                if (box.getBoundsInParent().intersects(human.getBoundsInParent())) {
                    double threshold = 1.0;

                    if (movingRight) {
                        if (human.getTranslateX() > box.getTranslateX()) { //right
                            return;
                        }
                    } else {
                        if (box.getTranslateX() > human.getTranslateX()) { //left
                            return;
                        }
                    }
                }
            }
            human.setTranslateX(human.getTranslateX() + (value > 0 ? 2 : -2));
        }
    }
    private void movePlayerY(int value) {
        boolean movingDown = value > 0;
        for (int i = 0; i < Math.abs(value); i++) {
            for (Box box : group_Box) {
                if (box.getBoundsInParent().intersects(human.getBoundsInParent())) {
                    double threshold = 1.0;

                    if (movingDown) {
                        if (human.getTranslateY()  > box.getTranslateY()) {
                            human.setTranslateY(human.getTranslateY() - 1);
                            canJump = true;
                            return;
                        }
                    } else {
                        if (human.getTranslateY() == box.getTranslateY()) {
                            return;
                        }
                    }
                }
            }
            human.setTranslateY(human.getTranslateY() + (value > 0 ? 1 : -1));
        }
    }
    private void jumpPlayer() {
        if (canJump) {
            playerVelocity = playerVelocity.add(0, -20);
            canJump = false;
        }
    }

    private boolean isPressed(KeyCode keyCode) {
        return keys.getOrDefault(keyCode,false);
    }

    private void update() {
        if (human != null && box_move != null) {
            if (isPressed(KeyCode.A)) {
                movePlayerX(-2);
            }
            if (isPressed(KeyCode.D)) {
                movePlayerX(2);
            }
            if (isPressed(KeyCode.W)) {
                jumpPlayer();
            }

            if (playerVelocity.getY() < 10) {
                playerVelocity = playerVelocity.add(0, 1);
            }

            movePlayerY((int)playerVelocity.getY());

            if (human.getLayoutX() + human.getTranslateX() > 400) {
                once = true;
            }
            if (once) {
                double newBoxY = box_move.getTranslateY() + boxSpeed;
                box_move.setTranslateY(newBoxY);
                human.setTranslateY(newBoxY + 25);

                if (newBoxY >= 500) {
                    box_move.setTranslateY(500);
                    boxSpeed = 0.0;
                }
            }
        }
    }


    private boolean isOnBox(List<Box> b) {
        for (Box box : b) {
            if (human == null || box == null) return false;

            double humanLeft = human.getLayoutX() + human.getTranslateX();
            double humanRight = humanLeft + human.getFitWidth();
            double humanBottom = human.getTranslateY() + human.getFitHeight();

            double boxLeft = box.getLayoutX() + box.getTranslateX();
            double boxRight = boxLeft + box.getWidth();
            double boxTop = box.getLayoutY() + box.getTranslateY();
            double boxBottom = boxTop + box.getHeight();

            boolean onBox = (humanBottom >= boxTop - 5) &&
                    (humanBottom <= boxTop + 10) &&
                    (humanRight > boxLeft) &&
                    (humanLeft < boxRight);

            if (onBox) {
                if (box == box_move) {
                    human.setTranslateY(human.getTranslateY() + boxSpeed);
                }
                return true;
            }
        }
        return false;
    }


}