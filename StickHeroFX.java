package com.example.StickHeroFX;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.*;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 600;
    private static final int PILLAR_WIDTH1 = 80;
    private static final int PILLAR_WIDTH2 = 60;
    private static final int PILLAR_HEIGHT = 150;
    private static final int CHARACTER_WIDTH = 50;
    private static final int CHARACTER_HEIGHT = 100;
    private static final double BACKGROUND_OPACITY = 0.5;
    private static final Color PILLAR_COLOR = Color.rgb(50, 50, 50);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Pane root = new Pane();

        Image backgroundImage = new Image(getClass().getResourceAsStream("/quidditch.jpg"));
        ImageView backgroundImageView = new ImageView(backgroundImage);
        backgroundImageView.setFitWidth(WINDOW_WIDTH);
        backgroundImageView.setFitHeight(WINDOW_HEIGHT);

        Rectangle backgroundRectangle = new Rectangle(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT);
        backgroundRectangle.setFill(Color.rgb(200, 200, 200, BACKGROUND_OPACITY));

        root.getChildren().addAll(backgroundImageView, backgroundRectangle);

        Rectangle pillar1 = createPillar(50, WINDOW_HEIGHT - PILLAR_HEIGHT, PILLAR_WIDTH1, PILLAR_HEIGHT);
        pillar1.setFill(PILLAR_COLOR);
        root.getChildren().add(pillar1);

        Rectangle character = createCharacter(pillar1.getX() + PILLAR_WIDTH1 / 2 - CHARACTER_WIDTH / 2, pillar1.getY() - CHARACTER_HEIGHT + 10);
        root.getChildren().add(character);

        Rectangle pillar2 = createPillar(250, WINDOW_HEIGHT - PILLAR_HEIGHT, PILLAR_WIDTH2, PILLAR_HEIGHT);
        pillar2.setFill(PILLAR_COLOR);
        root.getChildren().add(pillar2);

        Line stick = createStick(pillar1.getX() + pillar1.getWidth(), pillar1.getY() - 2.5, pillar2.getX() + pillar2.getWidth() / 2, pillar1.getY() - 2.5);
        root.getChildren().add(stick);

        Circle goldenCircle = createGoldenSnitch((pillar1.getX() + pillar2.getX() + pillar2.getWidth()) / 2 + 40, pillar1.getY() - 30);
        root.getChildren().add(goldenCircle);

        Button extendButton = new Button("Extend");
        Button stopButton = new Button("Stop");
        Button restartButton = new Button("Restart");

        HBox buttonsBox = new HBox(10);
        buttonsBox.getChildren().addAll(extendButton, stopButton, restartButton);
        buttonsBox.setLayoutX(WINDOW_WIDTH - 220);
        buttonsBox.setLayoutY(20);

        root.getChildren().add(buttonsBox);

        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Quidditch Hero");

        primaryStage.show();
    }

    private Rectangle createPillar(double x, double y, double width, double height) {
        return new Rectangle(x, y, width, height);
    }

    private Rectangle createCharacter(double x, double y) {
        Image harryPotterImage = new Image(getClass().getResourceAsStream("/harry_potter.png")); // Replace with the path to your image file
        ImageView harryPotterImageView = new ImageView(harryPotterImage);
        harryPotterImageView.setFitWidth(CHARACTER_WIDTH);
        harryPotterImageView.setFitHeight(CHARACTER_HEIGHT);
        Rectangle character = new Rectangle(x, y, CHARACTER_WIDTH, CHARACTER_HEIGHT);
        character.setFill(new ImagePattern(harryPotterImage));
        return character;
    }

    private Circle createGoldenSnitch(double x, double y) {
        Circle goldenSnitch = new Circle(x, y, 10);
        goldenSnitch.setFill(Color.GOLD);
        return goldenSnitch;
    }

    private Line createStick(double startX, double startY, double endX, double endY) {
        Line stick = new Line(startX, startY, endX, endY);
        stick.setStroke(Color.BLACK);
        stick.setStrokeWidth(5);
        return stick;
    }
}
