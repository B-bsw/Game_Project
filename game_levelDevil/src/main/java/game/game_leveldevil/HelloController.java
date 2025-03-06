package game.game_leveldevil;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.util.Duration;

import java.io.IOException;


public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Box b;

    @FXML
    private ImageView human;

    private Timeline timeline = new Timeline();

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    public void initialize(){
        b.setOnKeyPressed(this::handleKeyboard);
        b.setFocusTraversable(true);
        b.requestFocus();
    }

    public void handleKeyboard(KeyEvent e) {
        TranslateTransition transition = new TranslateTransition(Duration.millis(200), human);
        transition.setCycleCount(0);
        transition.setAutoReverse(false);
        switch (e.getCode()){
            case A:
                transition.setToX(human.getTranslateX() - 10);
                transition.play();
                break;
            case D:
                transition.setToX(human.getTranslateX() + 10);
                transition.play();
                break;
            case W:
                if (human.getY() > 346){
                    break;
                }
                timeline.getKeyFrames().add(
                        new KeyFrame(Duration.millis(200),
                                new KeyValue(human.yProperty(), human.getY() - 20))
                );
                timeline.getKeyFrames().add(
                        new KeyFrame(Duration.millis(400),
                                new KeyValue(human.yProperty(), human.getY()))
                );
                timeline.play();
                break;
            default:
                break;
        }
    }

}