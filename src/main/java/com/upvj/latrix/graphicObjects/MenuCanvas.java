package com.upvj.latrix.graphicObjects;

import com.upvj.latrix.RenderableCanvas;
import com.upvj.latrix.graphicObjects.Rectangles.ButtonLabel;
import com.upvj.latrix.graphicObjects.Rectangles.ImageLabel;
import com.upvj.latrix.graphicObjects.Rectangles.TextLabel;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class MenuCanvas extends RenderableCanvas {

    GraphicsContext GC;

    public MenuCanvas(Scene scene) {
        super(scene);
        //assert that the scene is not null



        GC = this.getGraphicsContext2D();

        GC.setFill(Color.hsb(0,1,0));
        GC.fillRect(0,0,this.getWidth(),this.getHeight());

        TextLabel TL = new TextLabel(scene,"Jouer");

        TL.setSize(0,0);
        TL.setSizeOffset(150,50);
        Paint OffWhite = Color.ANTIQUEWHITE;
        Paint GreyWhite = Color.GREY;
        TL.setPosition(0.5,0.5);
        TL.setAnchor(0.5,0.5);
        TL.setOffset(0,0);
        TL.setBackgroundColor(OffWhite);

        ButtonLabel<TextLabel> Button = new ButtonLabel<TextLabel>(scene,TL);

        super.InsertInRenderList(Button);

        Button.setOnHoverEnter(() -> {
            TL.setBackgroundColor(GreyWhite);
        });
        Button.setOnHoverExit(() -> {
            TL.setBackgroundColor(OffWhite);
        });

        ImageLabel Logo = new ImageLabel(scene,"Logo.png");

        Logo.setAnchor(0.5,0);
        Logo.setPosition(0.5,0);

        Logo.setSizeOffsetToImageSize();
        //Distance from the top of the screen
        Logo.setOffset(Logo.getOffset().getKey(),Logo.getOffset().getValue()+10);

        super.InsertInRenderList(Logo);


        }


    }

