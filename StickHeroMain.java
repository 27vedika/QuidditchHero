import java.util.ArrayList;
import java.util.Random;

class Character{
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
            //cherries
            for (Cherry c: GameManager.getCherryList()){
                if(c.getPositionX() == x && c.getPositionY()==this.positionY && !c.isCollected()){
                    c.setCollected(true);
                    this.cherries++;
                    break;
                }
            }
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

    public int getLives() {
        return lives;
    }
    public void setLives(int lives) {
        this.lives = lives;
    }
}

class Cherry{
    private int positionX;
    private int positionY;
    private boolean isCollected;

    Cherry(int start, int end){
        Random random = new Random();
        this.isCollected=false;
        this.positionX = random.nextInt(start, end);
        this.positionY = random.nextBoolean() ? 1 : -1;
    }

    public int getPositionX() {
        return positionX;
    }
    public int getPositionY(){
        return this.positionY;
    }
    public boolean isCollected() {
        return isCollected;
    }
    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }
    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }
    public void setCollected(boolean x){
        this.isCollected=x;
    }
}

class Pillar{
    private final int width;
    private int prevDistance;

    Pillar(){
        Random random = new Random();
        this.width= random.nextInt(1, 5);
        this.prevDistance = random.nextInt(1,10);
    }

    public int getWidth() {
        return this.width;
    }
    public int getPrevDistance() {
        return this.prevDistance;
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

class GameManager{
    private ArrayList<Character> characterList;
    private static ArrayList<Cherry> cherryList;
    private boolean gameRunning;

    GameManager(){
        this.characterList = new ArrayList<Character>();
        cherryList = new ArrayList<Cherry>();
        this.gameRunning = false;
    }

    public static ArrayList<Cherry> getCherryList() {
        return cherryList;
    }

    public void startGame(){gameRunning=true;} //handle other functionalities
    public void endGame(){
        this.gameRunning=false;
    } //end game screen displays
    private void spawnPillar(){} //called in startGame, also spawn cherry here

}

public class Main {
    public static void main(String[] args) {
    }
}
