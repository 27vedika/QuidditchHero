package com.example.test;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;


public class FallController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    private Character mainCharacter;
    private MainGameController manager;
    private Scene mainScene;

    @FXML private Button reviveButton;
    @FXML private Button quitButton;
    @FXML private Label snitch_label;
    @FXML private Label score_label;
    @FXML private Label highScore_label;

    @FXML
    public void displayFall(){
        score_label.setText(String.valueOf(mainCharacter.getScore()));
        highScore_label.setText(String.valueOf(mainCharacter.getHighScore()));
        snitch_label.setText(String.valueOf(mainCharacter.getSnitches()));
        if (mainCharacter.getSnitches()<3){
            reviveButton.setVisible(false);
        }
    }

    @FXML
    public void onReviveButtonClick(ActionEvent event) throws IOException {
        mainCharacter.revive();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main-game.fxml"));
        manager.setController(manager);
        loader.load();
        stage.setScene(mainScene);
        stage.show();
        manager.saveContext(mainScene, mainCharacter);
        System.out.print("After revival: ");
        manager.spawnPillar();
    }

    @FXML
    public void OnRestartButtonClick() throws IOException {
        mainCharacter.setScore((mainCharacter.getLevel()-1)*10);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main-game.fxml"));
        manager.setController(manager);
        loader.load();
        stage.setScene(mainScene);
        stage.show();
        manager.saveContext(mainScene, mainCharacter);
        System.out.print("After revival: ");
        manager.spawnPillar();

    }

    @FXML
    protected void onQuitButtonClick(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

        public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void mainContext(MainGameController manager, Scene scene, Character character){
        this.manager = manager;
        this.mainScene = scene;
        this.mainCharacter = character;
    }
}