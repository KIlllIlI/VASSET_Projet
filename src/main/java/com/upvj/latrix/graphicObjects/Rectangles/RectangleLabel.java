package com.upvj.latrix.graphicObjects.Rectangles;

import com.upvj.latrix.GraphicObject;
import com.upvj.latrix.RenderableCanvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class RectangleLabel extends ARectangleLabel {

    private Paint BackgroundColor = Color.WHITE;

    private Paint BorderColor = Color.BLACK;

    public RectangleLabel(RenderableCanvas parent) {
        super(parent);
    }

    @Override
    public boolean draw(GraphicsContext gc) {
        try{
            gc.setStroke(BorderColor);
            gc.setFill(BackgroundColor);

            double ParentWidth = super.Parent.getWidth();
            double ParentHeight = Parent.getHeight();

            AbsoluteWidth = ParentWidth * SizeX + SizeOffsetX;
            AbsoluteHeight = ParentHeight * SizeY + SizeOffsetY;

            AbsoluteX = ParentWidth * PositionX - (AbsoluteWidth * AnchorX) + OffsetX;
            AbsoluteY = ParentHeight * PositionY - (AbsoluteHeight * AnchorY) + OffsetY;

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
