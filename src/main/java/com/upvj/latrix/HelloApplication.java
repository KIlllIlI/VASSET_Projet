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

        for (int i=1;i<=3;i++) {
            RectangleLabel Test = new RectangleLabel(gameCanvas);



        gameCanvas.setBackgroundColor(Color.WHITE);

        menu.StartRender();
        menu.toFront();

        menu.getStartButton().setOnClick(event -> {
            menu.StopRender();
            System.out.println("Still working ?");

            gameCanvas.toFront();
            gameCanvas.StartRender();

        });




        //Execution du jeu
        ExecuteGame(canvas);
    }

    public static void main(String[] args)
    {
        launch();
    }

    public static void ExecuteGame(GameCanvas canvas)
    {
        boolean isInExecution = true;
        int fallSpeed = 1000;

        Tetris block = new Tetris();

        canvas.InsertInRenderList(block);

        //Récuperation du bloc par son getter
        Integer[][] currentBlock = block.getRandomBlockType();

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(fallSpeed), event -> {
            //Faire descendre le bloc avec un vecteur
            block.moveDownBlock(currentBlock, block.getShapeMatrisMap());
        }));
//        do{
//            try {
//                //Récuperation de la map et du bloc par les getter
//                Integer[][] currentBlock = block.getRandomBlockType();
//                Integer[][] currentMap = block.getShapeMatrisMap();
//
//                //Faire descendre le bloc avec un vecteur
//                block.moveDownBlock(currentBlock, currentMap);
//
//
//                //On attend à la fin de la boucle
//                Thread.sleep(fallSpeed);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        } while (isInExecution == false);
        //Créer une boucle while do avec le mouvement vers le bas

        //répéter indifiniment
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
}