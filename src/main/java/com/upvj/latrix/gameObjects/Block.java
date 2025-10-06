package com.upvj.latrix.gameObjects;

import com.upvj.latrix.GraphicObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;

import java.net.URL;


public class Block implements GraphicObject {
    private Image Sprite = null;
    private Integer size = 64;
    private Integer posX = 0;
    private Integer posY = 0;

    public Integer getPosX() {
        return posX;
    }

    public void setPosX(Integer posX) {
        this.posX = posX;
    }

    public Integer getPosY() {
        return posY;
    }

    public void setPosY(Integer posY) {
        this.posY = posY;
    }

    public Block() {
        String imagePath = "/com/upvj/latrix/block.png";
        URL imageUrl = Block.class.getResource("/com/upvj/latrix/block.png");
        if (imageUrl != null) {
            Sprite = new Image(imageUrl.toExternalForm());
        } else {
            System.err.println("Error: block.png not found in resources/com/upvj/latrix/");
        }
    }

    @Override
    public boolean draw(GraphicsContext gc) {
        if (Sprite != null) {
            gc.drawImage(Sprite, this.posX*this.size, this.posY*this.size, this.size, this.size);
        }
        return true;
    }


}
