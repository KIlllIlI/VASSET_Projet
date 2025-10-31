package com.upvj.latrix.graphicObjects.Rectangles;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class RectangleLabel extends ARectangleLabel {

    private Paint BackgroundColor = Color.hsb(0,0,0,0.5);

    private Paint BorderColor = Color.gray(0.4);

    public RectangleLabel(Scene scene) {
        super(scene);
    }

    @Override
    public boolean draw(GraphicsContext gc) {
        try{
            super.draw(gc);
            gc.setStroke(BorderColor);
            gc.setFill(BackgroundColor);

            gc.fillRect(AbsoluteX,AbsoluteY,AbsoluteWidth,AbsoluteHeight);
            gc.strokeRect(AbsoluteX,AbsoluteY,AbsoluteWidth,AbsoluteHeight);
            return true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
    }


    public void setBackgroundColor(Paint backgroundColor) {
        BackgroundColor = backgroundColor;
    }



    public void setBorderColor(Paint borderColor) {
        BorderColor = borderColor;
    }
}
