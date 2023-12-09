package com.example.test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

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

    public static void main(String[] args) {
        launch();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            //Before termination (pauses the game)
            FileOutputStream out = null;

            try{
                out = new FileOutputStream("SaveGame.txt");

                out.write(Manager.getInstance().getManager().getCharacter().getScore());
                out.write(Manager.getInstance().getManager().getCharacter().getSnitches());
                out.write(Manager.getInstance().getManager().getCharacter().getHighScore());
            }
            catch (Exception e){
                System.out.println("Some error");
            }
            finally{
                if (out!=null){
                    try {
                        out.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }));
    }
}