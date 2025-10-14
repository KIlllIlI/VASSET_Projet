package com.upvj.latrix.gameObjects;

import com.upvj.latrix.GraphicObject;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.Comparator;

public class RenderableCanvas extends Canvas {
    GraphicsContext GC;

    ArrayList<GraphicObject> RenderList = new ArrayList<>();

    Comparator<GraphicObject> CompareOnZ = Comparator.comparingInt(GraphicObject::zIndex);

    // Method used in the MainGraphicLoop
    public void Render()  {
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

    public void StartRender(){
        MainGraphicLoop.start();

    }

    public void StopRender(){
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

    public void InsertInRenderList(GraphicObject Go) {
        if (!RenderList.contains(Go)){
            RenderList.add(Go);
            RenderList.sort(CompareOnZ);

            System.out.println("Renderlist now has "+RenderList.size()+" elements");
        }
    }




}
