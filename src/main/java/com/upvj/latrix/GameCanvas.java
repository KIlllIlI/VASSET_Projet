package com.upvj.latrix;

import javafx.scene.Scene;
import javafx.scene.canvas.*;

import java.util.ArrayList;
import java.util.Comparator;

public class GameCanvas extends Canvas {

    GraphicsContext GC;

    ArrayList<GraphicObject> RenderList = new ArrayList<>();

    Comparator<GraphicObject> CompareOnZ = Comparator.comparingInt(GraphicObject::zIndex);

    // Method used in the MainGraphicLoop
    private boolean MainGraphicLoopStatus = false;
    public void Render()  {
        while(MainGraphicLoopStatus){

            RenderList
                    .forEach(
                            (Go)->{
                                if (!Go.draw(GC)) {
                                    System.err.println("Object "+Go.getClass().getName()+" has failed to draw");
                                };
                            }
                    );
            try {
                Thread.sleep(17);
            } catch ( InterruptedException ignored) {

            }
            ;


        }


    }



    Thread MainGraphicLoop = new Thread(this::Render);

    public void StartRender(){
        if(!MainGraphicLoopStatus){
            MainGraphicLoop.start();
            MainGraphicLoopStatus=true;
        }else{
            System.err.println("MainGraphicLoop already started.");
        }

    }

    public void StopRender(){

    }


    public GameCanvas(Scene scene){
        super();
        this.widthProperty().bind(scene.widthProperty());
        this.heightProperty().bind(scene.heightProperty());

        GC = this.getGraphicsContext2D();

        MainGraphicLoop.setDaemon(true);



    }

    public void InsertInRenderList(GraphicObject Go) {
        if (!RenderList.contains(Go)){
            RenderList.add(Go);
            RenderList.sort(CompareOnZ);
        }
    }




}
