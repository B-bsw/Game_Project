package game.game_hard;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Box;
import javafx.util.Duration;

import java.util.ArrayList;
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

    private Timeline timeline = new Timeline();

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
    private List<Node> platforms = new ArrayList<>();

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
                movePlayerX(-10);
//                transition.setToX(human.getTranslateX() - 10);
//                transition.play();
                break;
            case D:
                movePlayerX(10);
//                transition.setToX(human.getTranslateX() + 10);
//                transition.play();
                break;
            case W:
                if (human.getY() > 346){
                    break;
                }
                timeline.getKeyFrames().add(
                        new KeyFrame(Duration.millis(200),
                                new KeyValue(human.yProperty(), human.getY() - 30))
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
    private void movePlayerX(int value) {
        boolean movingRight = value > 0;
        int step = movingRight ? 1 : -1;
            human.setTranslateX(human.getTranslateX() + value);
    }


}