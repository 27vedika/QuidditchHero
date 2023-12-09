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
import java.io.FileOutputStream;
import java.io.IOException;

public class HelloController {

    @FXML private ImageView background;
    @FXML private Label headerLabel;
    @FXML private ImageView harrypotterImage;
    @FXML private Button resumeButton;
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
        try{
            in = new FileInputStream("SaveGame.txt");

            int score = in.read();
            int snitch = in.read();
            int highscore = in.read();
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