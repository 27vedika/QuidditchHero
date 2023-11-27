package com.example.test;

import java.util.Random;
import javafx.scene.shape.Rectangle;

class Pillar{
    private int width;
    private int prevDistance;

    Pillar(Rectangle prev, Rectangle rectangle){
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
}

class Stick{
    private int speed;
    private int length;

    public void startGrowing(){}
    public void stopGrowing(){}

    public int getLength(){
        return this.length;
    }
}

public class Character {
    private int score;
    private int level;
    private int cherries;
    private int positionX;
    private int positionY;
    private int highScore;
    private int lives;

    Character(){
        this.score=0;
        this.level=1;
        this.cherries=0;
        this.positionX = 0;
        this.positionY = 1;
        this.highScore=0;
        this.lives=3;
    }

    public void run(Stick s1, Pillar p1){
        int x = this.positionX;
        for(int i=0; i< s1.getLength(); i++) {
            x++;
        }
        this.positionX=x;
        if(this.positionY==-1 || s1.getLength()<p1.getPrevDistance() || s1.getLength()>p1.getPrevDistance()+p1.getWidth()){
            this.fall();
        }
        else {
            this.score++;
            if (this.score % 10 == 0)
                this.level = this.score / 10 + 1; //level up
            if (this.score>this.highScore)
                this.highScore = this.score;
        }
    } //handle cherry collection, upside down
    public void revive(){};
    public void fall(){} //handle case revival, high score, level, etc
    public int getScore() {
        return score;
    }
}
