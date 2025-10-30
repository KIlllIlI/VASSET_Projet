package com.upvj.latrix.graphicObjects.Rectangles;

import com.upvj.latrix.GraphicObject;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class RectangleLabel extends ARectangleLabel {

    private Paint BackgroundColor = Color.WHITE;

    private Paint BorderColor = Color.BLACK;

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

    public Paint getBackgroundColor() {
        return BackgroundColor;
    }

    public void setBackgroundColor(Paint backgroundColor) {
        BackgroundColor = backgroundColor;
    }

    public Paint getBorderColor() {
        return BorderColor;
    }

    public void setBorderColor(Paint borderColor) {
        BorderColor = borderColor;
    }
}
