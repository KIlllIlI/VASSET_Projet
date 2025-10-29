package com.upvj.latrix;

import com.upvj.latrix.graphicObjects.GameCanvas;
import com.upvj.latrix.graphicObjects.RectangleLabel;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import com.upvj.latrix.gameObjects.*;
import javafx.util.Duration;


import java.io.IOException;

public class HelloApplication extends Application
{
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));

        Group root = new Group();
        Scene s = new Scene(root, 1500, 800, Color.WHITE);

        final GameCanvas canvas = new GameCanvas(s);
        //final MenuCanvas menu = new MenuCanvas(s);

        root.getChildren().add(canvas);
        //root.getChildren().add(menu);
        stage.setTitle("Latrix!");
        stage.setScene(s);
        stage.show();

        for (int i=1;i<=3;i++)
        {
            RectangleLabel Test = new RectangleLabel(canvas);

            Test.setSize(0,0);
            Test.setSizeOffset(150,50);
            Test.setPosition(0.5,0.5+i*0.08);
            Test.setAnchor(0.5,0.5);
            Test.setOffset(0,0);
            canvas.InsertInRenderList(Test);
        }
        canvas.setBackgroundColor(Color.WHITE);
        canvas.StartRender();


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
        //Integer[][] currentBlock = block.getRandomBlockType();

        //Timer pour la descente progressive des blocs
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(fallSpeed), event -> {
            //Faire descendre le bloc avec un vecteur
            block.moveDownBlock();
        }));


        //répéter indifiniment
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
}