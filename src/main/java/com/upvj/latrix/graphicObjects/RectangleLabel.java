package com.upvj.latrix.graphicObjects;

import com.upvj.latrix.GraphicObject;
import com.upvj.latrix.RenderableCanvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.util.Pair;

public class RectangleLabel implements GraphicObject { //Fancy wrapper for GraphiContext.fillRectangle()

    private double AnchorX,AnchorY = 0.0;

    private double PositionX, PositionY = 0.0;

    private int OffsetX,OffsetY = 0;

    private double SizeX,SizeY = 0.0;

    private int SizeOffsetX,SizeOffsetY = 0;

    private Paint BackgroundColor = Color.WHITE;

    private Paint BorderColor = Color.BLACK;

    private RenderableCanvas Parent;

    @Override
    public boolean draw(GraphicsContext gc) {
        try{
            gc.setStroke(BorderColor);
            gc.setFill(BackgroundColor);

            double ParentWidth = Parent.getWidth();
            double ParentHeight = Parent.getHeight();

            double AbsoluteWidth = ParentWidth * SizeX + SizeOffsetX;
            double AbsoluteHeight = ParentHeight * SizeY + SizeOffsetY;

            double AbsoluteX = ParentWidth * PositionX - (AbsoluteWidth * AnchorX) + OffsetX;
            double AbsoluteY = ParentHeight * PositionY - (AbsoluteHeight * AnchorY) + OffsetY;

            gc.fillRect(AbsoluteX,AbsoluteY,AbsoluteWidth,AbsoluteHeight);
            gc.strokeRect(AbsoluteX,AbsoluteY,AbsoluteWidth,AbsoluteHeight);


            return true;
        }catch(Exception e){
            System.err.println(e.getMessage());
            return false;
        }

    }

    @Override
    public int zIndex() {
        return GraphicObject.super.zIndex();
    }

    public RectangleLabel(RenderableCanvas parent) {
        Parent = parent;
    }





    // Anchor
    public Pair<Double,Double> getAnchor() {
        return new Pair<>(AnchorX,AnchorY)  ;
    }

    public void setAnchor(double anchorX,double anchorY) {
        AnchorX = anchorX;
        AnchorY = anchorY;
    }

    public void setAnchor(Pair<Double,Double> pair) {
        AnchorX = pair.getKey();
        AnchorY = pair.getValue();
    }

    // --- Position

    public double getPositionX() {
        return PositionX;
    }

    public void setPosition(double positionX,double positionY) {
        PositionX = positionX;
        PositionY = positionY;
    }

    public void setPosition(Pair<Double,Double> pair) {
        PositionX = pair.getKey();
        PositionY = pair.getValue();
    }

    public Pair<Double,Double> getPosition() {
        return new Pair<>(PositionX,PositionY)  ;
    }

    // --- Offset

    public void setOffset(int offsetX,int offsetY) {
        OffsetX = offsetX;
        OffsetY = offsetY;
    }

    public void setOffset(Pair<Integer,Integer> pair) {
        OffsetX = pair.getKey();
        OffsetY = pair.getValue();
    }

    public Pair<Integer,Integer> getOffset() {
        return new Pair<>(OffsetX,OffsetY)  ;
    }

    // --- Size
    public void setSize(double sizeX, double sizeY) {
        SizeX = sizeX;
        SizeY = sizeY;
    }

    public void setSize(Pair<Double, Double> pair) {
        SizeX = pair.getKey();
        SizeY = pair.getValue();
    }

    public Pair<Double, Double> getSize() {
        return new Pair<>(SizeX, SizeY);
    }

    // --- Size Offset
    public void setSizeOffset(int sizeOffsetX, int sizeOffsetY) {
        SizeOffsetX = sizeOffsetX;
        SizeOffsetY = sizeOffsetY;
    }

    public void setSizeOffset(Pair<Integer, Integer> pair) {
        SizeOffsetX = pair.getKey();
        SizeOffsetY = pair.getValue();
    }

    public Pair<Integer, Integer> getSizeOffset() {
        return new Pair<>(SizeOffsetX, SizeOffsetY);
    }

    // --- Colors

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

    // --- Parent Canvas

    public RenderableCanvas getParent() {
        return Parent;
    }

    public void setParent(RenderableCanvas parent) {
        Parent = parent;
    }


}
