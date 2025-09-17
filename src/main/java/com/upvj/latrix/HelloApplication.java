package com.upvj.latrix;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));

        Group root = new Group();
        Scene s = new Scene(root, 300, 300, Color.WHITE);

        final Canvas canvas = new GameCanvas(s);

        root.getChildren().add(canvas);


        stage.setTitle("Latrix!");
        stage.setScene(s);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}