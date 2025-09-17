package com.upvj.latrix.gameObjects;

import com.upvj.latrix.GraphicObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.ColorInput;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.net.URL;


public class Tetris implements GraphicObject {
    private Image Sprite = null;
    private Boolean[][] ShapeMatrix = {
            {false,false,false},
            {false,false,false},
            {false,false,false}
    };


    public Tetris() {
        String imagePath = "/com/upvj/latrix/block.png";
        URL imageUrl = Tetris.class.getResource("/com/upvj/latrix/block.png");
        if (imageUrl != null) {
            Sprite = new Image(imageUrl.toExternalForm());
        } else {
            System.err.println("Error: block.png not found in resources/com/upvj/latrix/");
        }




    }

    @Override
    public boolean draw(GraphicsContext gc) {
        if (Sprite != null) {

            gc.drawImage(Sprite, 0, 0,64,64);
        }
        return true;
    }


}
