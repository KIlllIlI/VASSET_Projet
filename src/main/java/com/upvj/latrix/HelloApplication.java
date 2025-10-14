package com.upvj.latrix;

import com.upvj.latrix.graphicObjects.GameCanvas;
import com.upvj.latrix.graphicObjects.MenuCanvas;
import com.upvj.latrix.graphicObjects.RectangleLabel;
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
        Scene s = new Scene(root, 1500, 800, Color.WHITE);

        final GameCanvas canvas = new GameCanvas(s);
        final MenuCanvas menu = new MenuCanvas(s);



        root.getChildren().add(canvas);
        //root.getChildren().add(menu);







        stage.setTitle("Latrix!");
        stage.setScene(s);
        stage.show();

        Tetris blok = new Tetris();
        canvas.InsertInRenderList(blok);

        for (int i=1;i<=3;i++) {
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






    }

    public static void main(String[] args) {
        launch();
    }
}