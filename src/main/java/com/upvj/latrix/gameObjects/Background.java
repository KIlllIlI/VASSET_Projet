package com.upvj.latrix.gameObjects;

import com.upvj.latrix.GraphicObject;
import javafx.scene.canvas.GraphicsContext;

public class Background implements GraphicObject {
    int width;
    int height;

    public Background(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public boolean draw(GraphicsContext gc) {
        gc.fillRect(0,0, width, height);
        return true;
    }
}
