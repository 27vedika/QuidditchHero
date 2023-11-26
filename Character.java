package com.example.test;

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


    public int getScore() {
        return score;
    }
}