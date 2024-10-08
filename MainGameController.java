package com.example.test;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Random;


public class MainGameController {
    private Timeline timeline;
    private Stage stage;
    private Scene scene;
    private Parent root;
    private Random random = new Random();

    @FXML private Rectangle stick;
    private final Stick s1 = new Stick(stick);;
    @FXML private Rectangle pillar1;
    @FXML private Rectangle pillar2;
    private Pillar p1;
    private Pillar p2;
    private boolean mousePressed = false;

    @FXML private ImageView character_img;
    private Character character;

    @FXML
    protected void onQuitButtonClick(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void spawnPillar(){
        pillar1.setWidth(pillar2.getWidth());
        character_img.setLayoutX(pillar1.getLayoutX()+pillar1.getWidth()-40);
        pillar2.setWidth(random.nextInt(40,100));
        pillar2.setLayoutX(random.nextInt((int)pillar1.getLayoutX()+(int)pillar1.getWidth()+10, (int)pillar1.getLayoutX()+(int)pillar1.getWidth()+200));
        p1.setWidth((int)pillar1.getWidth());
        p2.setWidth((int)pillar2.getWidth());
        p2.setPrevDistance((int) (pillar2.getLayoutX() - pillar1.getLayoutX() - pillar1.getWidth()));

        stick.setFill(Color.BLACK);
        stick.setHeight(0);
        stick.setLayoutX(pillar1.getLayoutX()+pillar1.getWidth()-3);
        stick.setLayoutY(215);
        s1.setLength(0);
        mousePressHandler();
    }

    private void mousePressHandler() {
        scene.setOnMousePressed(event -> {
            if (event.getButton() == MouseButton.PRIMARY) { // Check for left mouse button
                mousePressed = true;
                extendStick();
            }
        });

        scene.setOnMouseReleased(event -> {
            if (event.getButton() == MouseButton.PRIMARY) { // Check for left mouse button
                mousePressed = false;
                s1.setLength((int)stick.getHeight());
                stick.setFill(Color.WHITE);
                run();

            }
        });
    }

    private void extendStick(){
        if (timeline!=null){
            timeline.stop();
        }

        timeline = new Timeline(
                new KeyFrame(Duration.millis(25), event -> {
                    if (mousePressed && stick.getFill()!=Color.WHITE && !character.isRunning()) {
                        stick.setHeight(stick.getHeight()+2);
                        stick.setLayoutY(stick.getLayoutY()-2);
                    }
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public void startGame(Scene scene){
        character = new Character(character_img, this);
        this.scene=scene;
        character_img.setLayoutX(pillar1.getLayoutX()+pillar1.getWidth()-40);
        pillar2.setWidth(random.nextInt(40,100));
        pillar2.setLayoutX(random.nextInt((int)pillar1.getLayoutX()+(int)pillar1.getWidth()+10, (int)pillar1.getLayoutX()+(int)pillar1.getWidth()+200));
        p1 = new Pillar(null, pillar1);
        p2 = new Pillar(pillar1, pillar2);
        mousePressHandler();
    }

    public void run(){
        System.out.println("Calling run with s1: "+s1.getLength());
        character.run(s1,p2, stick);
    }
}
