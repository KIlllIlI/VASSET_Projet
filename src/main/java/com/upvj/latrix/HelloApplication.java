package com.upvj.latrix;


import com.upvj.latrix.graphicObjects.GameCanvas;
import com.upvj.latrix.graphicObjects.MenuCanvas;
import com.upvj.latrix.graphicObjects.Rectangles.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import com.upvj.latrix.gameObjects.*;


import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        Group root = new Group();
        stage.setMinWidth(1281);
        stage.setMinHeight(720);
        Scene s = new Scene(root, 1281, 720, Color.WHITE);

        final GameCanvas gameCanvas = new GameCanvas(s);
        final MenuCanvas menu = new MenuCanvas(s);



        root.getChildren().add(gameCanvas);
        root.getChildren().add(menu);







        stage.setTitle("Latrix!");
        stage.setScene(s);
        stage.show();

        Tetris blok = new Tetris();
        gameCanvas.InsertInRenderList(blok);



        gameCanvas.setBackgroundColor(Color.WHITE);

        menu.StartRender();
        menu.toFront();

        menu.getStartButton().setOnClick(event -> {
            menu.StopRender();
            System.out.println("Still working ?");

            gameCanvas.toFront();
            gameCanvas.StartRender();

        });








    }

    public static void main(String[] args) {
        launch();
    }
}