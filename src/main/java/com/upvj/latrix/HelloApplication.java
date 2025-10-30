package com.upvj.latrix;

import com.upvj.latrix.graphicObjects.GameCanvas;
import com.upvj.latrix.graphicObjects.MenuCanvas;
import com.upvj.latrix.graphicObjects.Rectangles.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.*;
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


        stage.setTitle("Latrix");
        stage.setScene(s);
        stage.show();

        for (int i = 1; i <= 3; i++) {
            RectangleLabel Test = new RectangleLabel(s);


            Paint gameBackground = new RadialGradient(
                    0,                // focusAngle
                    0,                // focusDistance
                    0.5,              // centerX (0–1, relative to shape)
                    0.5,              // centerY
                    0.5,              // radius (0–1, relative)
                    true,             // proportional (true = use relative coordinates)
                    CycleMethod.NO_CYCLE, // don't repeat
                    new Stop(0, Color.LIMEGREEN),   // center color
                    new Stop(1, Color.rgb(0,50,0))    // edge color
            );

            gameCanvas.setBackgroundColor(gameBackground);

            menu.StartRender();
            menu.toFront();

            menu.getStartButton().setOnClick(event -> {
                menu.StopRender();
                System.out.println("Still working ?");

                gameCanvas.toFront();
                gameCanvas.Start();
                gameCanvas.StartRender();


            });

        }


    }

    public static void main(String[] args)
    {
        launch();
    }



}