package com.upvj.latrix.graphicObjects;

import com.upvj.latrix.RenderableCanvas;
import com.upvj.latrix.graphicObjects.Rectangles.ButtonLabel;
import com.upvj.latrix.graphicObjects.Rectangles.ImageLabel;
import com.upvj.latrix.graphicObjects.Rectangles.TextLabel;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

public class MenuCanvas extends RenderableCanvas {

    GraphicsContext GC;

    ButtonLabel<TextLabel> startButton;

    public MenuCanvas(Scene scene) {
        super(scene);
        //assert that the scene is not null

        this.InsertInRenderList(new Background(scene));



        GC = this.getGraphicsContext2D();

        GC.setFill(Color.hsb(0,1,0));
        GC.fillRect(0,0,this.getWidth(),this.getHeight());

        TextLabel TL = new TextLabel(scene,"Jouer");

        TL.setSize(0,0);
        TL.setSizeOffset(150,50);
        Paint GreyWhite = Color.GREY;
        TL.setPosition(0.5,0.5);
        TL.setAnchor(0.5,0.5);
        TL.setOffset(0,0);

        Color background = Color.hsb(0,0,0,0.4);
        Color backgroundHover = Color.hsb(0,0,0,0.8);


        TL.setBackgroundColor(background);
        TL.setBorderColor(Color.WHITE);

        TL.setTextColor(Color.WHITE);

        startButton = new ButtonLabel<TextLabel>(scene,this,TL);

        super.InsertInRenderList(startButton);

        startButton.setOnHoverEnter(() -> {
            TL.setBackgroundColor(backgroundHover);
        });
        startButton.setOnHoverExit(() -> {
            TL.setBackgroundColor(background);
        });

        ImageLabel Logo = new ImageLabel(scene,"Logo.png");

        Logo.setAnchor(0.5,0);
        Logo.setPosition(0.5,0);

        Logo.setSizeOffsetToImageSize();
        //Distance from the top of the screen
        Logo.setOffset(Logo.getOffset().getKey(),Logo.getOffset().getValue()+10);

        super.InsertInRenderList(Logo);
    }

    public ButtonLabel<TextLabel> getStartButton() {
        return startButton;
    }




}



