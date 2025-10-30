package com.upvj.latrix.graphicObjects.Rectangles;

import com.upvj.latrix.GraphicObject;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.util.Pair;

public abstract class ARectangleLabel implements GraphicObject { //Fancy wrapper for GraphiContext.fillRectangle()

    protected double AnchorX,AnchorY = 0.0;

    protected double PositionX, PositionY = 0.0;

    protected int OffsetX,OffsetY = 0;

    protected double SizeX,SizeY = 0.0;

    protected int SizeOffsetX,SizeOffsetY = 0;

    protected int _Zindex = 0;



    protected Scene scene;

    public double AbsoluteWidth;
    public double AbsoluteHeight;
    public double AbsoluteX;
    public double AbsoluteY;




    @Override
    public boolean draw(GraphicsContext gc) {
        double ParentWidth = scene.getWidth();
        double ParentHeight = scene.getHeight();

        AbsoluteWidth = ParentWidth * SizeX + SizeOffsetX;
        AbsoluteHeight = ParentHeight * SizeY + SizeOffsetY;

        AbsoluteX = ParentWidth * PositionX - (AbsoluteWidth * AnchorX) + OffsetX;
        AbsoluteY = ParentHeight * PositionY - (AbsoluteHeight * AnchorY) + OffsetY;
        return true;
    }

    @Override
    public int zIndex() {
        return _Zindex;
    }

    public void setZindex(int zindex) {
        this._Zindex = zindex;
    }

    public ARectangleLabel(Scene scene) {
        this.scene = scene;
    }

    public boolean contains(double x, double y) {
        return x > AbsoluteX && x < AbsoluteX + AbsoluteWidth && y > AbsoluteY && y < AbsoluteY + AbsoluteHeight;
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



    // --- Parent Canvas

    public Scene getScene() {
        return scene;
    }

    public void setParent(Scene scene) {
        this.scene = scene;
    }






}
