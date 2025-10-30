package com.upvj.latrix.graphicObjects;

import com.upvj.latrix.RenderableCanvas;
import com.upvj.latrix.gameObjects.Tetris;
import com.upvj.latrix.graphicObjects.Rectangles.ImageLabel;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class GameCanvas extends RenderableCanvas {

    private Tetris tetris;


    public GameCanvas(Scene s) {
        super(s);



    }

    public void Start( ) {
        this.tetris = new Tetris(this);

        this.InsertInRenderList(tetris);




    }



}
