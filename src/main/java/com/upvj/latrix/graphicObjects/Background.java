package com.upvj.latrix.graphicObjects;

import com.upvj.latrix.GraphicObject;
import com.upvj.latrix.graphicObjects.Rectangles.ImageLabel;

import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


public class Background implements GraphicObject {

    private double blockDisplacement = 0;

    private static final int blockSize = 100;

    private final ArrayList<ImageLabel> Tetrises = new ArrayList<>();

    private Image tileImage;

    private Scene scene;



    public Background(Scene scene) {
        this.scene = scene;

        Image input = ImageLabel.getImageFromResource("block.png");

        int w = (int) input.getWidth();
        int h = (int) input.getHeight();

        tileImage = ImageLabel.getImageFromResource("tileBlock.png");

        //Blocks on screen



        String[] blockNames = {"Block.png","BZigZag.png","L.png","PZigZag.png","RT.png","Stick.png","YT.png"};


        int blockIndex = 0;

        Random random = new Random();

        for(String blockName : blockNames) {
            blockIndex++;
            ImageLabel Block = new ImageLabel(scene, "LogoBlocks/"+blockName);



            int blockWidth = (int) (Block.getSizeOffset().getKey()*1.5);
            int blockHeight = (int) (Block.getSizeOffset().getValue()*1.5);

            Block.setSizeOffset(blockWidth, blockHeight);


            Block.setPosition((double) blockIndex / (blockNames.length+1), random.nextDouble());



            Block.setImage(ImageLabel.moveHSV(Block.getImage(),0,-0.3,-0.5));

            Tetrises.add(Block);
        }















    }

    @Override
    public boolean draw(GraphicsContext gc) {
        //double SceneLength = scene.getWidth();
        //double SceneHeight = scene.getHeight();
        gc.setImageSmoothing(false);
        blockDisplacement+=0.5;
        if (blockDisplacement >= blockSize) {
            blockDisplacement = 0;
        }

        for (int i = -blockSize; i < scene.getWidth(); i+=blockSize) {
            for (int j = -blockSize; j < scene.getHeight(); j+=blockSize) {
                gc.drawImage(tileImage, i+blockDisplacement, j+blockDisplacement, blockSize, blockSize);
            }
        }

        Tetrises.forEach(t -> {
            t.draw(gc);

            double newY = t.getPosition().getValue()+0.0005;

            if (newY > 1) {
                t.setPosition(t.getPosition().getKey(), -t.AbsoluteHeight/ scene.getHeight());
            }else{
                t.setPosition(t.getPosition().getKey(), newY);
            }

        });


        return true;
    }




    @Override
    public int zIndex() {
        return -10;
    }
}
