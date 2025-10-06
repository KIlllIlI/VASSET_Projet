package com.upvj.latrix.gameObjects;

import com.upvj.latrix.GraphicObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.net.URL;

public class Tetris implements GraphicObject {
    private Boolean[][] ShapeMatrix = {
            {false, false, false, false, false},
            {false, false, false, false, false},
            {false, false, false, false, false},
            {false, false, false, false, false},
            {false, false, false, false, false},
    };

    private Image Sprite;
    private Integer size = 64;

    public Tetris() {
        String imagePath = "/com/upvj/latrix/block.png";
        URL imageUrl = Tetris.class.getResource("/com/upvj/latrix/block.png");
        if (imageUrl != null) {
            Sprite = new Image(imageUrl.toExternalForm());
        } else {
            System.err.println("Error: block.png not found in resources/com/upvj/latrix/");
        }
    }

    public Boolean[][] getShapeMatrix(){
        return this.ShapeMatrix;
    }

    public void moveBlock(Integer posX, Integer posY){
        this.ShapeMatrix[posY][posX] = true;
    }

    public void deleteBlock(Integer posX, Integer posY){
        this.ShapeMatrix[posY][posX] = false;
    }

    @Override
    public boolean draw(GraphicsContext gc) {
        if (Sprite != null) {
            for (int y = 0; y < ShapeMatrix.length; y++) {
                for (int x = 0; x < ShapeMatrix[y].length; x++) {
                    if (ShapeMatrix[y][x]) {
                        gc.drawImage(Sprite, x*this.size, y*this.size, this.size, this.size);
                    }
                }
            }
        }
        return true;
    }
}
