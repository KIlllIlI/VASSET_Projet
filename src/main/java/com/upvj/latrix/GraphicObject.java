package com.upvj.latrix;


import javafx.scene.canvas.GraphicsContext;

public interface GraphicObject {
    boolean draw(GraphicsContext gc);
    //Method used to draw on the canvas during the rending loop. Takes the graphic context
    // and returns True if no problems or False if an error happened.
     default int zIndex() {return 0;};


}
