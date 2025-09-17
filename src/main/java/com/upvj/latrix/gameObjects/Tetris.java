package com.upvj.latrix.gameObjects;

import com.upvj.latrix.GraphicObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;



public class Tetris implements GraphicObject {





    private Image Sprite = null;


    public Tetris() {
        String imagePath = "/com/upvj/latrix/block.png";
        java.net.URL imageUrl = Tetris.class.getResource(imagePath);

        if (imageUrl == null) {

            System.err.println("Putain !");
        }


        if (imageUrl != null) {
            Sprite =  new Image(imageUrl.toExternalForm());
            System.out.println(Sprite);
        } else {
            System.err.println("Error: Missing image not found!");
        }


    }

    @Override
    public boolean draw(GraphicsContext gc) {
        gc.drawImage(Sprite,32,32);
        return true;
    }


}
