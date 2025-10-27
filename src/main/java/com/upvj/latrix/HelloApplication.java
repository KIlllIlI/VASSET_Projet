package com.upvj.latrix;


import com.upvj.latrix.graphicObjects.GameCanvas;
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
        Scene s = new Scene(root, 1500, 800, Color.WHITE);

        final GameCanvas canvas = new GameCanvas(s);
        //final MenuCanvas menu = new MenuCanvas(s);



        root.getChildren().add(canvas);
        //root.getChildren().add(menu);







        stage.setTitle("Latrix!");
        stage.setScene(s);
        stage.show();

        Tetris blok = new Tetris();
        canvas.InsertInRenderList(blok);

        for (int i=1;i<=3;i++) {
            TextLabel TL = new TextLabel(canvas,"Small");
            ButtonLabel<TextLabel> Button = new ButtonLabel<TextLabel>(canvas,TL);

            TL.setSize(0,0);
            TL.setSizeOffset(150,50);

            Paint OffWhite = Color.ANTIQUEWHITE;

            Paint GreyWhite = Color.GREY;


            TL.setPosition(0.5,0.5);

            TL.setAnchor(0.5,0.5);

            TL.setOffset(0,i*55);

            TL.setBackgroundColor(OffWhite);



            canvas.InsertInRenderList(Button);

            Button.setOnHoverEnter(() -> {
                TL.setBackgroundColor(GreyWhite);
            });

            Button.setOnHoverExit(() -> {
                TL.setBackgroundColor(OffWhite);
            });


        }

        canvas.setBackgroundColor(Color.WHITE);

        canvas.StartRender();








    }

    public static void main(String[] args) {
        launch();
    }
}