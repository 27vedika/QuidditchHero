package com.example.test;

import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

class Pillar{
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

class Stick{
    private int length;
    private Rectangle stick_rectangle;

    Stick(Rectangle rect){
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

public class Character {
    private int score;
    private int level;
    private int snitches;
    private int highScore;
    private int lives;
    private ImageView img;
    private boolean isDown;
    private MainGameController manager;
    private boolean isRunning;

    Character(ImageView img, MainGameController manager){
        this.score=0;
        this.level=1;
        this.snitches=15;
        this.highScore=0;
        this.lives=3;
        this.img = img;
        this.isDown = false;
        this.manager=manager;
        this.isRunning = false;
    }

    public void run(Stick s1, Pillar p1, Rectangle sRec){
        if (this.isRunning){
            System.out.println("Already running");
            return;
        }

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

                sRec.setWidth(oWidth);
                sRec.setHeight(oHeight);
                sRec.setLayoutY(oLayoutY);

                this.isRunning = false;
                if (this.isDown || s1.getLength() < p1.getPrevDistance() || s1.getLength() > p1.getPrevDistance() + p1.getWidth()) {
                    this.fall();
                } else {
                    this.score++;
                    System.out.println("Score: "+this.score);
                    if (this.score % 10 == 0)
                        this.level = this.score / 10 + 1; // level up
                    if (this.score > this.highScore)
                        this.highScore = this.score;
                    manager.spawnPillar();
                }
            });
        });
    } //handle cherry collection, upside down
    public void revive(){};
    public void fall(){
        System.out.println("Fell");
        if(this.snitches>=3){

        }
        this.score=this.level*10;
    } //handle case revival, high score, level, etc
    public int getScore() {
        return score;
    }
    public int getSnitches(){
        return this.snitches;
    }

    public boolean isRunning() {
        return isRunning;
    }
}
