package com.example.test;

import java.io.IOException;
import java.io.Serializable;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

interface CharacterInterface{
    public void run(Stick s1, Pillar p1, Rectangle sRec, ImageView snitch);
    public void fall() throws IOException;
    public void revive();
} //to ensure that character will implement these methods


class Pillar {
    private int width;
    private int prevDistance;
    private Rectangle pillar_rectangle;

    Pillar(Rectangle prev, Rectangle rectangle){
        this.pillar_rectangle = rectangle;
        this.width = (int)rectangle.getWidth();
        if (prev==null){
            this.prevDistance = 0;
        }
        else {
            this.prevDistance = (int) (rectangle.getLayoutX() - prev.getLayoutX() - prev.getWidth());
        }
    }

    public int getWidth() {
        return this.width;
    }
    public int getPrevDistance() {
        return this.prevDistance;
    }
    public void setWidth(int w){
        this.width=w;
    }
    public void setPrevDistance(int d){
        this.prevDistance=d;
    }

    public Rectangle getPillar_rectangle() {
        return pillar_rectangle;
    }
}


//stick IS A rectangle
class Stick extends Rectangle{
    private int length;
    private Rectangle stick_rectangle;

    Stick(Rectangle rect){
        super();
        this.stick_rectangle = rect;
    }

    public int getLength(){
        return this.length;
    }
    public void setLength(int l){
        this.length=l;
    }
    public Rectangle getStick_rectangle(){
        return this.stick_rectangle;
    }
}

public class Character implements CharacterInterface{
    private int score;
    private int level;
    private int snitches;
    private int highScore;
//    private int lives;
    private ImageView img;
    private boolean isDown;
    private MainGameController manager;
    private boolean isRunning;
    private int positionY;

    Character(ImageView img, MainGameController manager){
        this.score=0;
        this.level=1;
        this.snitches=0;
        this.highScore=0;
//        this.lives=3;
        this.img = img;
        this.isDown = false;
        this.manager=manager;
        this.isRunning = false;
        this.positionY = 1;
        manager.updateLabels(score, snitches);
    }

    public void run(Stick s1, Pillar p1, Rectangle sRec, ImageView snitch){
//        if (this.isRunning){
//            System.out.println("Already running");
//            return;
//        }
        int oHeight = (int)sRec.getHeight();
        int oWidth = (int)sRec.getWidth();
        int oLayoutY = (int)sRec.getLayoutY();
        sRec.setHeight(sRec.getWidth());
        sRec.setWidth(oHeight);
        sRec.setLayoutY(215);

        System.out.println("Running");
        this.isRunning = true;
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(12.5), event -> {
                    this.img.setLayoutX(this.img.getLayoutX()+1);
                    if (snitch.isVisible() && this.img.getLayoutX()+40==snitch.getLayoutX() && ((this.positionY==1 && snitch.getLayoutY()==180) || (this.positionY==-1 && snitch.getLayoutY()==220))){
                        this.snitches+=1;
                        snitch.setVisible(false);
                    }
                })
        );
        timeline.setCycleCount(s1.getLength());
        timeline.play();

        timeline.setOnFinished(event -> {
            timeline.stop();
            Timeline pause = new Timeline(
                    new KeyFrame(Duration.millis(500), event2 -> {
                        this.img.setLayoutX(this.img.getLayoutX());
                    })
            );
            pause.setCycleCount(1);
            pause.play();

            pause.setOnFinished(event2 -> {
                pause.stop();
                System.out.println("Stopped running");

                this.isRunning = false;
                if (this.isDown || s1.getLength() < p1.getPrevDistance() || s1.getLength() > p1.getPrevDistance() + p1.getWidth() || this.positionY==-1) {
                    try {
                        this.fall();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    this.score++;
                    System.out.println("Score: "+this.score);
                    if (this.score % 10 == 0)
                        this.level = this.score / 10 + 1; // level up
                    if (this.score > this.highScore)
                        this.highScore = this.score;
                    sRec.setWidth(oWidth);
                    sRec.setHeight(oHeight);
                    sRec.setLayoutY(oLayoutY);
                    manager.spawnPillar();
                }
            });
        });
    } //handle cherry collection, upside down
    public void revive() {
        this.snitches-=3;
    }
    public void fall() throws IOException {
        System.out.println("Fell");
        if(positionY==-1){
            img.setScaleY(-1*img.getScaleY());
            img.setLayoutY(img.getLayoutY()-62);
            positionY =1;
        }
        manager.switchToFall();
//        this.score=this.level*10;

    } //handle revival, high score, level, etc
    public int getScore() {
        return score;
    }
    public void setScore(int x){
        this.score = x;
    }
    public int getSnitches(){
        return this.snitches;
    }
    public int getHighScore(){
        return this.highScore;
    }
    public int getLevel(){
        return this.level;
    }
    public boolean isRunning() {
        return isRunning;
    }

    public void getSavedParameters(int score, int snitches, int highscore){
        this.score=score;
        this.snitches=snitches;
        this.highScore=highscore;
    }

    public void setSnitches(int snitch) {
        this.snitches = snitch;
    }

    public void setHighScore(int highscore) {
        this.highScore = highscore;
    }

    public void setPositionY (int x){
        this.positionY = x;
    }
    public int getPositionY(){
        return this.positionY;
    }
}