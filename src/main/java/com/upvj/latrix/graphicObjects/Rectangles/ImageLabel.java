package com.upvj.latrix.graphicObjects.Rectangles;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Objects;

public class ImageLabel extends ARectangleLabel{

    private Image image;




    public ImageLabel(Scene scene, Image image) {
        super(scene);
        this.image = image;
    }

    public ImageLabel(Scene scene, String filePath) {
        super(scene);
        this.image = new Image(
                Objects.requireNonNull(
                        getClass().getResource( "/com/upvj/latrix/" + filePath)
                ).toExternalForm()
        );
    }

    @Override
    public boolean draw(GraphicsContext gc) {
        try{
            super.draw(gc);


            gc.drawImage(image,AbsoluteX,AbsoluteY,AbsoluteWidth,AbsoluteHeight);
            return true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setSizeOffsetToImageSize(){
        super.SizeOffsetX = (int) image.getWidth();
        super.SizeOffsetY = (int) image.getHeight();
    }
}
