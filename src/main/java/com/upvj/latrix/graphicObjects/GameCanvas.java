package com.upvj.latrix.graphicObjects;

import com.upvj.latrix.RenderableCanvas;
import com.upvj.latrix.gameObjects.Tetris;
import com.upvj.latrix.graphicObjects.Rectangles.ImageLabel;
import com.upvj.latrix.graphicObjects.Rectangles.TextLabel;
import javafx.animation.PauseTransition;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.*;
import javafx.util.Duration;

public class GameCanvas extends RenderableCanvas {

    private MenuCanvas menu;
    private Tetris tetris;
    private Scene scene;


    public GameCanvas(Scene s, MenuCanvas Menu) {
        super(s);
        this.scene = s;
        this.menu = Menu;

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

        this.setBackgroundColor(gameBackground);



    }

    public void Start() {
        // If old game exists, clean it up
        if (tetris != null) {
            tetris.dispose();
            this.DisposeRenderables();
            tetris = null;
        }

        // Start new game
        tetris = new Tetris(this);
        InsertInRenderList(tetris);
        StartRender();
    }

    public void onGameOver(Tetris tetris) {
        // Insert "GAME OVER" label
        TextLabel gameOverLabel = new TextLabel(scene, "GAME OVER");
        gameOverLabel.setSize(1,1);
        gameOverLabel.setTextColor(Color.RED);
        gameOverLabel.setFONT_SIZE(100);
        gameOverLabel.setZindex(200);
        InsertInRenderList(gameOverLabel);

        // Pause 3 seconds before cleanup
        PauseTransition delay = new PauseTransition(Duration.seconds(3));
        delay.setOnFinished(event -> {
            this.DisposeRenderables();
            StopRender();           // stop canvas rendering
            menu.toFront();
            menu.StartRender();     // resume menu

            // Optional: nullify reference so next game is clean
            this.tetris = null;
        });
        delay.play();
    }



}
