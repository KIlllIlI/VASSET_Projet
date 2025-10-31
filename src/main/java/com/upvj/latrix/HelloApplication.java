package com.upvj.latrix;

import com.upvj.latrix.graphicObjects.GameCanvas;
import com.upvj.latrix.graphicObjects.MenuCanvas;
import com.upvj.latrix.graphicObjects.Rectangles.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.*;
import javafx.stage.Stage;



import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) {
        SoundHelper.init();

        Group root = new Group();
        stage.setMinWidth(1281);
        stage.setMinHeight(720);
        Scene s = new Scene(root, 1281, 720, Color.WHITE);

        Image icon = ImageLabel.getImageFromResource("LaserGun.png");

        stage.getIcons().add(icon);

        final MenuCanvas menu = new MenuCanvas(s);
        final GameCanvas gameCanvas = new GameCanvas(s,menu);


        root.getChildren().add(gameCanvas);
        root.getChildren().add(menu);

        stage.setTitle("Latrix");
        stage.setScene(s);
        stage.show();

        menu.getStartButton().setOnClick(event -> {
            menu.StopRender();
            System.out.println("Still working ?");

            gameCanvas.toFront();
            gameCanvas.Start();
            gameCanvas.StartRender();


        });

        menu.StartRender();
        menu.toFront();


    }

    public static void main(String[] args)
    {
        launch();
    }



}