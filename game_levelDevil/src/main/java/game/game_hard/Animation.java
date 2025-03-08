package game.game_hard;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Box;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Animation {
    @FXML
    private Label welcomeText;
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Box b;
    @FXML
    private Box box_move;
    @FXML
    private ImageView human;

    private double boxSpeed = 5.0;
    private int setLocBoxMove = 380;
    private double humanSpeedY = 0.0;
    private final double GRAVITY = 0.5;
    private final double JUMP_SPEED = -10.0;

    private HashMap<KeyCode, Boolean> keys = new HashMap<>();
    private Scene scene;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    private List<Node> platforms = new ArrayList<>();


    public void initialize(Scene scene){
        this.scene = scene;

        scene.setOnKeyPressed(this::handleKeyboard);
        scene.setOnKeyReleased(this::handleKeyRelease);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
//                System.out.println("Player X: " + (human.getLayoutX() + human.getTranslateX()));
                    update();
            }
        };
        timer.start();
    }


    public void handleKeyboard(KeyEvent e) {
        keys.put(e.getCode(), true);
        if (e.getCode() == KeyCode.W && (isOnBox(b) || isOnBox(box_move))) {
            humanSpeedY = JUMP_SPEED;
        }
    }

    private void handleKeyRelease(KeyEvent e) {
        keys.put(e.getCode(), false);
    }

    private void movePlayerX(int value) {
        double newX = human.getTranslateX() + value;
        human.setTranslateX(newX);

        double playerX = human.getLayoutX() + human.getTranslateX();
        if (playerX >= setLocBoxMove) {
            boxSpeed = 5.0;
        } else {
            boxSpeed = 0.0;
        }
    }
    private boolean isPressed(KeyCode keyCode) {
        return keys.getOrDefault(keyCode,false);
    }

    private void update() {
        if (human != null && box_move != null) {
            if (isPressed(KeyCode.A)) {
                movePlayerX(-5);
            }
            if (isPressed(KeyCode.D)) {
                movePlayerX(5);
            }

            humanSpeedY += 0.04;
            if (human.getLayoutX() + human.getTranslateX() > 400){

                double newHumanY = human.getTranslateY() + humanSpeedY;
                human.setTranslateY(newHumanY);

                double newBoxY = box_move.getTranslateY() + boxSpeed;
                box_move.setTranslateY(newBoxY);

                if (newBoxY >= 500) {
                    box_move.setTranslateY(500);
                    boxSpeed = 0.0;
                }

                if (isOnBox(b)) {
                    double boxTop = box_move.getTranslateY() - (human.getFitHeight() / 2);
                    human.setTranslateY(boxTop);
                    humanSpeedY = 0.0;
                } else if (human.getTranslateY() > 500) {
                    human.setTranslateY(500);
                    humanSpeedY = 0.0;
                }
            }

        }
    }

    private boolean isOnBox(Box box) {
        if (human == null || box == null) return false;

        double humanLeft = human.getLayoutX() + human.getTranslateX();
        double humanRight = humanLeft + human.getFitWidth();
        double humanBottom = human.getTranslateY() + (human.getFitHeight() / 2);

        double boxLeft = box.getTranslateX();
        double boxRight = boxLeft + box.getWidth();
        double boxTop = box.getTranslateY();

        boolean onBox = humanBottom >= boxTop - 5 &&
                humanBottom <= boxTop + 10 &&
                humanRight > boxLeft &&
                humanLeft < boxRight;
        return onBox;
    }

}