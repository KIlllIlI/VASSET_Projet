package com.upvj.latrix;

import com.upvj.latrix.GraphicObject;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.ArrayList;
import java.util.Comparator;

public class RenderableCanvas extends Canvas {
    protected GraphicsContext GC;

    protected ArrayList<GraphicObject> RenderList = new ArrayList<>();

    protected static Comparator<GraphicObject> CompareOnZ = Comparator.comparingInt(GraphicObject::zIndex);

    protected Paint BackgroundColor = Color.TRANSPARENT;

    // Method used in the MainGraphicLoop
    public void Render()  {
        GC.setFill(BackgroundColor);
        GC.fillRect(0,0,this.getWidth(),this.getHeight());
        RenderList
                .forEach(
                        (Go)->{
                            if (!Go.draw(GC)) {
                                System.err.println("Object "+Go.getClass().getName()+" has failed to draw");
                            };
                        }
                );
    }



    private final AnimationTimer MainGraphicLoop;

    private boolean _isRendering = false;

    public boolean isRendering() {
        return _isRendering;
    }

    public void StartRender(){
        _isRendering = true;
        MainGraphicLoop.start();

    }

    public void StopRender(){
        _isRendering = false;
        MainGraphicLoop.stop();
    }


    public RenderableCanvas(Scene scene){
        super(scene.getWidth(), scene.getHeight());
        this.widthProperty().bind(scene.widthProperty());
        this.heightProperty().bind(scene.heightProperty());

        GC = this.getGraphicsContext2D();



        MainGraphicLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                Render();
            }
        };





    }

    public void DisposeRenderables() {
        RenderList.clear();
    }

    public void InsertInRenderList(GraphicObject Go) {
        if (!RenderList.contains(Go)){
            RenderList.add(Go);
            RenderList.sort(CompareOnZ);
        }
    }

    public Paint getBackgroundColor() {
        return BackgroundColor;
    }

    public void setBackgroundColor(Paint backgroundColor) {
        BackgroundColor = backgroundColor;
    }
}
