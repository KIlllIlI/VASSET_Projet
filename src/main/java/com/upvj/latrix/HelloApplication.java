package com.upvj.latrix;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import com.upvj.latrix.gameObjects.*;


import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));

        Group root = new Group();

        final GameScene scene = new GameScene(root);

        final GameCanvas canvas = new GameCanvas(scene);

        Tetris tetris = new Tetris();



        root.getChildren().add(canvas);




        stage.setTitle("Latrix!");
        stage.setScene(scene);
        stage.show();

        Background background = new Background(64*5,64*5);
        canvas.InsertInRenderList(background);

        Block blok = new Block();

        tetris.moveBlock(blok.getPosX(), blok.getPosY());

        canvas.InsertInRenderList(tetris);

        canvas.StartRender();

        scene.KeyPressed(blok, tetris);

        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        launch();
    }
}