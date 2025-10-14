package com.upvj.latrix;

import com.upvj.latrix.gameObjects.RenderableCanvas;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class MenuCanvas extends RenderableCanvas {

    GraphicsContext GC;

    public MenuCanvas(Scene scene) {
        super(scene);
        GC = this.getGraphicsContext2D();

        GC.setFill(Color.hsb(0,1,0));
        GC.fillRect(0,0,this.getWidth(),this.getHeight());


    }

}
