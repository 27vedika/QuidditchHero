package com.example.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Quidditch Hero");
        stage.setScene(scene);
        stage.show();
    }

    public static void saveData(Character character) throws IOException {
        FileOutputStream out2 = null;

        try{
            out2 = new FileOutputStream("QuitFile.txt");
            out2.write(character.getHighScore());
            out2.write(character.getSnitches());
        }
        finally{
            if (out2!=null)
                out2.close();
        }
    }

    public static void main(String[] args) {
        launch();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            // Code to be executed before the program is terminated
            System.out.println("Shutdown hook executed. Perform cleanup tasks here.");
            //call Manager.shutdown();
        }));
    }
}