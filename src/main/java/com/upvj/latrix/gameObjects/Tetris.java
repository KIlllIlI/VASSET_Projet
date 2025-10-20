package com.upvj.latrix.gameObjects;

import com.upvj.latrix.GraphicObject;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.ColorInput;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.Random;


public class Tetris implements GraphicObject {
    private Image Sprite = null;
    private Image Sprite2 = null;

    //bloc en forme de L
    private static Integer[][] ShapeMatrisBlock_L = {
            {1, 0, 0, 0},
            {1, 0, 0, 0},
            {1, 0, 0, 0},
            {1, 1, 0, 0}
    };

    //bloc en forme de I
    private static Integer[][] ShapeMatrisBlock_I = {
            {1, 0, 0, 0},
            {1, 0, 0, 0},
            {1, 0, 0, 0},
            {1, 0, 0, 0}
    };

    //bloc en forme de O
    private static Integer[][] ShapeMatrisBlock_O = {
            {0, 0, 0, 0},
            {0, 0, 0, 0},
            {1, 1, 0, 0},
            {1, 1, 0, 0}
    };

    //bloc en forme de S
    private static Integer[][] ShapeMatrisBlock_S = {
            {1, 0, 0, 0},
            {1, 1, 0, 0},
            {0, 1, 0, 0},
            {0, 0, 0, 0}
    };

    //bloc en forme de T
    private static Integer[][] ShapeMatrisBlock_T = {
            {1, 1, 1, 0},
            {0, 1, 0, 0},
            {0, 0, 0, 0},
            {0, 0, 0, 0}
    };

    private static final Integer[][][] ALL_BLOCKS = {
            ShapeMatrisBlock_L,
            ShapeMatrisBlock_I,
            ShapeMatrisBlock_O,
            ShapeMatrisBlock_S,
            ShapeMatrisBlock_T
    };

    //Apparition d'un bloc au hasard
    Random random = new Random();
    Integer[][] randomBlockType = ALL_BLOCKS[random.nextInt(ALL_BLOCKS.length)];

    //définition de la map
    private Integer[][] ShapeMatrisMap = {
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
    };

    public Tetris() {
        String imagePath = "/com/upvj/latrix/block.png";
        URL imageUrl = Tetris.class.getResource("/com/upvj/latrix/block.png");
        URL image2 = Tetris.class.getResource("/com/upvj/latrix/Logo.png");
        if (image2 != null) {
            Sprite2 = new Image(image2.toExternalForm());
        } else {
            System.err.println("Error: block.png not found in resources/com/upvj/latrix/");
        }
        if (imageUrl != null) {
            Sprite = new Image(imageUrl.toExternalForm());
        } else {
            System.err.println("Error: block.png not found in resources/com/upvj/latrix/");
        }
    }

    @Override
    public boolean draw(GraphicsContext gc) {

        if (Sprite != null)
        {
            int y = 40;
            for(Integer[] lineBloc : this.randomBlockType) {
                int x = 40;
                for(Integer bloc : lineBloc) {
                    if (bloc == 1) {
                        gc.drawImage(Sprite, x, y, 40, 40);
                    } else if (bloc == 2) {
                        gc.drawImage(Sprite2, x, y, 40, 40);
                    }
                    x += 40;
                }
                y += 40;
            }
        }
        return true;
    }
}
