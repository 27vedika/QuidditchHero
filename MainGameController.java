package com.example.test;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Random;

public class MainGameController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    private Random random = new Random();
    @FXML private Rectangle pillar1;
    @FXML private Rectangle pillar2;

    @FXML
    protected void onQuitButtonClick(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void spawnPillar(){
        pillar1.setWidth(pillar2.getWidth());
        pillar2.setWidth(random.nextInt(20,100));
        pillar2.setLayoutX(random.nextInt((int)pillar1.getLayoutX()+(int)pillar1.getWidth(), (int)pillar1.getLayoutX()+(int)pillar1.getWidth()+100));
    }

    public void startGame(){
        pillar1.setWidth(random.nextInt(20, 100));
        pillar2.setWidth(random.nextInt(20,100));
        pillar2.setLayoutX(random.nextInt((int)pillar1.getLayoutX()+(int)pillar1.getWidth()+20, (int)pillar1.getLayoutX()+(int)pillar1.getWidth()+100));
    }
}