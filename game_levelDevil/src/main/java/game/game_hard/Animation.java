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
                System.out.println("Player X: " + (human.getLayoutX() + human.getTranslateX()));
                if ((human.getLayoutX() + human.getTranslateX()) >= setLocBoxMove){
                    updateBoxPosition();
                }
            }
        };
        timer.start();
    }


    public void handleKeyboard(KeyEvent e) {
        TranslateTransition transition = new TranslateTransition(Duration.millis(200), human);
//        transition.setCycleCount(1);
        transition.setAutoReverse(false);

        switch (e.getCode()) {
            case A:
                movePlayerX(-10);
                break;
            case D:
                movePlayerX(10);
                break;
            case W:
                if (human.getLayoutY() == 346) {
                    Timeline timeline = new Timeline();
                    timeline.stop();
                    timeline.getKeyFrames().clear();

                    timeline.getKeyFrames().addAll(
                            new KeyFrame(Duration.ZERO,
                                    new KeyValue(human.translateYProperty(), human.getTranslateY())),
                            new KeyFrame(Duration.millis(200),
                                    new KeyValue(human.translateYProperty(), human.getTranslateY() - 30)),
                            new KeyFrame(Duration.millis(400),
                                    new KeyValue(human.translateYProperty(), human.getTranslateY()))
                    );
                    timeline.play();
                }
                break;
            default:
                break;
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
        if (human != null) {
            if (isPressed(KeyCode.A)) {
                movePlayerX(-10);
            }
            if (isPressed(KeyCode.D)) {
                movePlayerX(10);
            }
        }
    }
    private void updateBoxPosition() {
        if (box_move != null) {
            double newY = box_move.getTranslateY() + boxSpeed;
            box_move.setTranslateY(newY);

            if (newY > 500) {
                box_move.setTranslateY(500);
                boxSpeed = 0.0;
            }
        }
    }
}