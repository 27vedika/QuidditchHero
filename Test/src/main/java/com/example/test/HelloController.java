package com.example.test;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;

public class HelloController {

    @FXML private ImageView background;
    @FXML private Label headerLabel;
    @FXML private ImageView harrypotterImage;
    @FXML private static Button resumeButton;
    @FXML private Button startButton;
    @FXML private ImageView character_img;

    @FXML
    public void onStartButtonClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main-game.fxml"));
        Parent root = loader.load();
        MainGameController manager = loader.getController();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        manager.startGame(scene);
    }

    @FXML
    public void onResumeButtonClick(ActionEvent event) throws IOException, ClassNotFoundException {
        FileInputStream in = null;
        int score, snitch, highscore;
        try{
            in = new FileInputStream("SaveGame.txt");
            score = in.read();
            if(score==-1){
                score=0;
                snitch=0;
                highscore=0;
            }
            else {
                snitch = in.read();
                highscore = in.read();
            }
//            MainGameController manager = MainGameController.getInstance();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("main-game.fxml"));
            Parent root = loader.load();
            MainGameController manager = loader.getController();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            manager.resumeGame(scene, score, snitch, highscore);
        }
        finally{
            if (in!=null)
                in.close();
        }
    }
}