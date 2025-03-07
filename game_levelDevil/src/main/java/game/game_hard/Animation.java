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
    private ImageView human;

    private HashMap<KeyCode, Boolean> keys = new HashMap<>();
    private Timeline timeline = new Timeline();
    private Scene scene;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    private List<Node> platforms = new ArrayList<>();

    public void initialize(Scene scene) {
        if (scene == null) {
            throw new IllegalArgumentException("Scene cannot be null");
        }

        this.scene = scene;

        scene.setOnKeyPressed(event -> keys.put(event.getCode(), true));
        scene.setOnKeyReleased(event -> keys.put(event.getCode(), false));

        scene.getRoot().requestFocus();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };
        timer.start();
    }

    @FXML
    public void initialize() {
        if (anchorPane != null && anchorPane.getScene() != null) {
            initialize(anchorPane.getScene());
        }
    }

    public void handleKeyboard(KeyEvent e) {
        TranslateTransition transition = new TranslateTransition(Duration.millis(200), human);
        transition.setCycleCount(1);
        transition.setAutoReverse(false);

        switch (e.getCode()) {
            case A:
                movePlayerX(-10);
                break;
            case D:
                movePlayerX(10);
                break;
            case W:
                if (human.getY() <= 346) {
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

    private void movePlayerX(int value) {
        double newX = human.getTranslateX() + value;
        human.setTranslateX(newX);
    }

    private boolean isPressed(KeyCode keyCode) {
        return keys.getOrDefault(keyCode, false);
    }

    private void update() {
        if (isPressed(KeyCode.A)) {
            movePlayerX(-10);
        }
        if (isPressed(KeyCode.D)) {
            movePlayerX(10);
        }
    }
}