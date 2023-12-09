package com.example.test;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.util.Objects;
import java.util.Random;

import static javafx.scene.paint.Color.rgb;


//SINGLETON
class Manager{
    private static Manager object = null;
    private MainGameController manager;
    private int managerSet = 0;

    private Manager(){}

    public static Manager getInstance(){
        if (object==null){
            object = new Manager();
        }
        return object;
    }

    public MainGameController getManager(){
        return this.manager;
    }
    public void setManager(MainGameController manager){
        if(managerSet<2){
            this.manager = manager;
            managerSet++;
        }
    }
}

public class MainGameController{

    private Timeline timeline;
    private Stage stage;
    private Scene scene;
    private Parent root;
    private Random random = new Random();

    @FXML private Label snitchLabel;
    @FXML private Rectangle stick;
    private final Stick s1 = Stick.getInstance();
    @FXML private Rectangle pillar1;
    @FXML private Rectangle pillar2;
    private Pillar p1;
    private Pillar p2;
    @FXML private Label scoreLabel;
    private boolean mousePressed = false;
    @FXML private Button pauseButton;
    @FXML private ImageView snitchCollectible;

    @FXML private ImageView character_img;
    private Character character;

    @FXML
    public void onPauseButtonClick(ActionEvent event) throws IOException {
        FileOutputStream out = null;
        try{
            out = new FileOutputStream("SaveGame.txt");

            out.write(character.getScore());
            out.write(character.getSnitches());
            out.write(character.getHighScore());

            root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        finally {
            if (out!=null)
                out.close();
        }
    }

    public void updateLabels(int score, int snitch){
        scoreLabel.setText(String.valueOf(score));
        snitchLabel.setText(String.valueOf(snitch));

    }

    private void spawnSnitch(Rectangle pillar1, Rectangle pillar2){
        if (random.nextBoolean()){
            int start = (int) ((int) pillar1.getLayoutX() + pillar1.getWidth());
            int end = (int) pillar2.getLayoutX();
            int dist = random.nextInt(start, end);
            snitchCollectible.setLayoutX(dist);
            if (random.nextBoolean())
                snitchCollectible.setLayoutY(180);
            else
                snitchCollectible.setLayoutY(220);
            snitchCollectible.setVisible(true);
        }
        else
            snitchCollectible.setVisible(false);
    }

    public void spawnPillar(){
        updateLabels(character.getScore(), character.getSnitches());
        System.out.println("Spawn Pillar called.");
        pillar1.setWidth(pillar2.getWidth());
        System.out.println(pillar1.getWidth());
        character_img.setLayoutX(pillar1.getLayoutX()+pillar1.getWidth()-40);
        System.out.println("CHARACTER: "+character_img.getLayoutX());
        System.out.println(character_img.getLayoutX());
        pillar2.setWidth(random.nextInt(40,100));
        System.out.println(pillar2.getWidth());
        pillar2.setLayoutX(random.nextInt((int)pillar1.getLayoutX()+(int)pillar1.getWidth()+10, (int)pillar1.getLayoutX()+(int)pillar1.getWidth()+200));

        if (p1 == null)
            p1 = new Pillar(null, pillar1);
        if (p2 == null)
            p2 = new Pillar(pillar1, pillar2);

        p1.setWidth((int)pillar1.getWidth());
        p2.setWidth((int)pillar2.getWidth());
        p2.setPrevDistance((int) (pillar2.getLayoutX() - pillar1.getLayoutX() - pillar1.getWidth()));

        spawnSnitch(pillar1, pillar2);

        stick.setFill(Color.BLACK);
        stick.setWidth(3);
        stick.setHeight(0);
        System.out.println("STICK HEIGHT: "+stick.getHeight());
        stick.setLayoutX(pillar1.getLayoutX()+pillar1.getWidth()-3);
        stick.setLayoutY(215);
        s1.setLength(0);
        mousePressHandler(this.scene);
    }

    private void mousePressHandler(Scene scene) {
        scene.setOnMousePressed(event -> {
            if (event.getButton() == MouseButton.PRIMARY && !character.isRunning()) {
                mousePressed = true;
                extendStick();
            }
            else if (event.getButton() == MouseButton.PRIMARY && character.isRunning()){
                System.out.println("MOUSE CLICKED");
                character_img.setScaleY(-1*character_img.getScaleY());
                if (character.getPositionY()==1){
                    character.setPositionY(-1);
                    character_img.setLayoutY(character_img.getLayoutY()+62);
                }
                else if (character.getPositionY()== -1){
                    character.setPositionY(1);
                    character_img.setLayoutY(character_img.getLayoutY()-62);
                }
            }
        });

        scene.setOnMouseReleased(event -> {
            if (event.getButton() == MouseButton.PRIMARY && !character.isRunning()) {
                mousePressed = false;
                s1.setLength((int) stick.getHeight());
                stick.setFill(rgb(23, 23, 23));
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
                    if (mousePressed && !Objects.equals(stick.getFill(), rgb(23, 23, 23)) && !character.isRunning()) {
                        stick.setHeight(stick.getHeight()+2);
                        stick.setLayoutY(stick.getLayoutY()-2);
                    }
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public void startGame(Scene scene) throws IOException {
        Manager.getInstance().setManager(this);
        s1.setStick_rectangle(stick);
        character = new Character(this, character_img);
        loadStatusNewGame();
        updateLabels(character.getScore(), character.getSnitches());
        this.scene=scene;
        character_img.setLayoutX(pillar1.getLayoutX()+pillar1.getWidth()-40);
        pillar2.setWidth(random.nextInt(40,100));
        pillar2.setLayoutX(random.nextInt((int)pillar1.getLayoutX()+(int)pillar1.getWidth()+10, (int)pillar1.getLayoutX()+(int)pillar1.getWidth()+200));
        p1 = new Pillar(null, pillar1);
        p2 = new Pillar(pillar1, pillar2);
        mousePressHandler(this.scene);
    }

    public void resumeGame(Scene scene, int score, int snitches, int highScore){
        Manager.getInstance().setManager(this);
        s1.setStick_rectangle(stick);
        character = new Character(this, character_img);
        character.setScore(score);
        System.out.println("Score set: "+ character.getScore());
        character.setSnitches(snitches);
        character.setHighScore(highScore);
        updateLabels(character.getScore(), character.getSnitches());
        this.scene=scene;
        character_img.setLayoutX(pillar1.getLayoutX()+pillar1.getWidth()-40);
        pillar2.setWidth(random.nextInt(40,100));
        pillar2.setLayoutX(random.nextInt((int)pillar1.getLayoutX()+(int)pillar1.getWidth()+10, (int)pillar1.getLayoutX()+(int)pillar1.getWidth()+200));
        p1 = new Pillar(null, pillar1);
        p2 = new Pillar(pillar1, pillar2);
        updateLabels(score, snitches);
        mousePressHandler(this.scene);
    }

    public void run(){
        System.out.println("Calling run with s1: "+s1.getLength());
        System.out.println("Score seen by run: "+character.getScore());
        character.run(s1,p2, stick, snitchCollectible);
    }

    public void switchToFall() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fall-view.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        FallController fallController = loader.getController();
        Stage stage = (Stage) character_img.getScene().getWindow();
        fallController.setStage(stage);
        Scene fallScene = new Scene(root);
        stage.setScene(fallScene);
        stage.show();
        fallController.mainContext(this.scene, this.character);
        fallController.displayFall();
    }

    //load status on revival
    public void saveContext(Scene scene, Character character){
        this.scene = scene;
        this.character = character;
    }

    @FXML
    public void setController(MainGameController controller){
        new FXMLLoader(getClass().getResource("main-game.fxml")).setController(controller);
    }

    public Character getCharacter(){
        return this.character;
    }

    //load status on restarting a game (after terminating once)
    public void loadStatusNewGame() throws IOException {
        FileInputStream in2 = null;

        try{
            in2 = new FileInputStream("SaveGame.txt");
            if(in2.read()!=-1) {
                int snitch = in2.read();
                int hscore = in2.read();
                character.setHighScore(hscore);
                character.setSnitches(snitch);
            }
        }
        finally{
            if (in2!=null)
                in2.close();
        }
    }
}