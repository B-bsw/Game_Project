package game.game_hard;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Box;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Animation {
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Box box_floor, box_1, box_2, box_3, box_4;
    @FXML
    private Box box_move;
    @FXML
    private ImageView human, door;

    private boolean once = false;
    private double boxSpeed = 0.2;
    private double humanSpeedY = 0.0;
    private double JUMP_SPEED = 0.0;
    private int keyA = -2;
    private int keyD = 2;
    private boolean canJump = true;
    private Point2D playerVelocity = new Point2D(0, 0);
    private Stage stage;
    private Scene scene;
    private boolean pass = true;
    private boolean passR = false;

    private HashMap<KeyCode, Boolean> keys = new HashMap<>();
    private List<Box> group_Box = new ArrayList<>();

    public Animation() {}
    public Animation(ImageView human, List<Box> group_Box, Scene scene, AnchorPane anchorPane, Stage stage) {
        this.human = human;
        this.group_Box = group_Box;
        this.scene = scene;
        this.anchorPane = anchorPane;
        this.stage = stage;
    }

    protected void initialize(Scene scene) throws IOException {
        if (box_floor != null) group_Box.add(box_floor);
        if (box_1 != null) group_Box.add(box_1);
        if (box_2 != null) group_Box.add(box_2);
        if (box_3 != null) group_Box.add(box_3);
        if (box_4 != null) group_Box.add(box_4);
        if (box_move != null) group_Box.add(box_move);

        this.scene = scene;

        scene.setOnKeyPressed(this::handleKeyboard);
        scene.setOnKeyReleased(this::handleKeyRelease);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (now - 0 >= 16_666_667){
                    try {
                        update();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        };
        timer.start();
    }

    private void handleKeyboard(KeyEvent e) {
        keys.put(e.getCode(), true);
        if (e.getCode() == KeyCode.W && (isOnBox(group_Box) || isOnBox(group_Box))) {
            humanSpeedY = JUMP_SPEED;
        }
    }

    private void handleKeyRelease(KeyEvent e) {
        keys.put(e.getCode(), false);
    }

    private void movePlayerX(int value) {
        boolean movingRight = value > 0;
        human.setTranslateX(human.getTranslateX() + (movingRight ? Math.min(value, 2) : Math.max(value, -2)));

        for (Box box : group_Box) {
            if (box.getBoundsInParent().intersects(human.getBoundsInParent())) {
                if (movingRight) {
                    human.setTranslateX(human.getTranslateX() - Math.abs(value));
                } else {
                    human.setTranslateX(human.getTranslateX() + Math.abs(value));
                }
                break;
            }
        }
    }

    private void movePlayerY(int value) {
        boolean movingDown = value > 0;
        human.setTranslateY(human.getTranslateY() + (movingDown ? Math.min(value, 1) : Math.max(value, -1)));

        for (Box box : group_Box) {
            if (box.getBoundsInParent().intersects(human.getBoundsInParent())) {
                if (movingDown) {
                    human.setTranslateY(human.getTranslateY() - 1);
                    canJump = true;
                } else {
                    human.setTranslateY(human.getTranslateY() + 1);
                }
                break;
            }
        }
    }

    private void jumpPlayer() {
        if (canJump) {
            playerVelocity = playerVelocity.add(0, -50);
            canJump = false;
        }
    }

    private boolean isPressed(KeyCode keyCode) {
        return keys.getOrDefault(keyCode, false);
    }

    protected void update() throws IOException {
        if (human != null) {
            if (isPressed(KeyCode.A)) {
                movePlayerX(-2);
                human.setScaleX(-1);
            }
            if (isPressed(KeyCode.D)) {
                movePlayerX(2);
                human.setScaleX(1);
            }
            if (isPressed(KeyCode.W)) {
                jumpPlayer();
            }

            if (playerVelocity.getY() < 10) {
                playerVelocity = playerVelocity.add(0, 1);
            }

            movePlayerY((int) playerVelocity.getY() - 2);

            if (box_move != null) {
                if (human.getLayoutX() + human.getTranslateX() > 400) {
                    once = true;
                }
                if (once) {
                    double newBoxY = box_move.getTranslateY() + boxSpeed;
                    box_move.setTranslateY(newBoxY);
                    boxSpeed += 0.001;

                    if (newBoxY >= 500) {
                        box_move.setTranslateY(500);
                        boxSpeed = 0.0;
                    }
                }
            }
            if (human.getTranslateY() == 500){
                System.out.println("check");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("gate1.fxml"));
                Parent root = loader.load();
//                stage.getScene().setRoot(root); //
                reset(stage);
            }

            if (door != null && door.getBoundsInParent().intersects(human.getBoundsInParent())) {
                if (human.getTranslateX() > 870 && human.getTranslateY() == 2) {
                    System.out.println("to gate 2");
                    if (pass){
                        pass = false;
                        sw2Gate2();
                    }
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

    private void sw2Gate2() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("gate2.fxml"));
        Parent root = loader.load();

        Controller_gate2 controller = loader.getController();
        Scene newScene = new Scene(root);
        controller.setStage(stage);
        controller.initialize(newScene);

        stage.setScene(newScene);
        stage.setTitle("Gate 2");
        stage.show();
    }

    public void sw2Gate3() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("gate3.fxml"));
        Parent root = loader.load();
        scene = new Scene(root);

        Animation controller = loader.getController();
        controller.setStage(stage);
        controller.initialize(scene);

        stage.setScene(scene);
        stage.setTitle("Gate3");
        stage.show();
    }

    public void sw2Gate4() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("gate4.fxml"));
        Parent root = loader.load();

        Controller_gate4 controller = loader.getController();
        Scene newScene = new Scene(root);
        controller.setStage(stage);
        controller.initialize(newScene);

        stage.setScene(newScene);
        stage.setTitle("Gate 4");
        stage.show();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setMoving(int value){
        value = Math.abs(value);
        keyA = -1 * value;
        keyD = value;
    }
    public int getMoving(){
        return keyD;
    }

    protected void dead() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("gate1.fxml"));
        Parent root = loader.load();
        scene = new Scene(root);

        Animation controller = loader.getController();
        controller.setStage(stage);
        controller.initialize(scene);

        stage.setScene(scene);
        stage.setTitle("Gate1");
        stage.show();
    }

    protected void reset(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("gate1.fxml"));
        Parent root = loader.load();

        Animation controller = loader.getController();
        Scene newScene = new Scene(root);
        controller.setStage(stage);
        controller.initialize(newScene);

        stage.setScene(newScene);
        stage.setTitle("Gate 1");
        stage.show();
    }

    protected void endGame() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("shinchan_home.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        EndGame con = loader.getController();
        con.setStage(stage);
        stage.setScene(scene);
        stage.setTitle("End Game");
        stage.show();
    }
}