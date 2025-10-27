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
//    Random random = new Random();
    Integer[][] randomBlockType;

    final int raw = 20;
    final int col = 10;
//    Integer[][] map = new Integer[raw][col];

    //définition de la map
    private final Integer[][] ShapeMatrisMap = {
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
    };

    private int blockX = 3;
    private int blockY = 0;

    int[] vector = {0, 1};
//    bX += vector[0];
//    bY += vector[1];

    //Constructeur constituant les bases d'un bloc
    public Tetris()
    {
        String imagePath = "/com/upvj/latrix/block.png";
        URL imageUrl = Tetris.class.getResource("/com/upvj/latrix/block.png");
        URL image2 = Tetris.class.getResource("/com/upvj/latrix/Logo.png");
        if (image2 != null)
        {
            Sprite2 = new Image(image2.toExternalForm());
        } else {
            System.err.println("Error: block.png not found in resources/com/upvj/latrix/");
        }
        if (imageUrl != null)
        {
            Sprite = new Image(imageUrl.toExternalForm());
        } else {
            System.err.println("Error: block.png not found in resources/com/upvj/latrix/");
        }

        //Apparition d'un bloc
        spawnNewBlock();
    }

    @Override
    public boolean draw(GraphicsContext gc)
    {
        final int blockSize = 40;

        if (Sprite == null)
        {
            return false;
        }

        if (ShapeMatrisMap != null)
        {
            for (int i = 0; i < ShapeMatrisMap.length; i++)
            {
                for (int j = 0; j < ShapeMatrisMap[i].length; j++)
                {
                    if (ShapeMatrisMap[i][j] == 1)
                    {
                        gc.drawImage(Sprite, j * blockSize, i * blockSize, blockSize, blockSize);
                    } else if (ShapeMatrisMap[i][j] == 2) {
                        gc.drawImage(Sprite2, j * blockSize, i * blockSize, blockSize, blockSize);
                    }
                }
            }
        }


        if (randomBlockType != null)
        {
            int y = blockY * blockSize;
            for (Integer[] lineBloc : this.randomBlockType)
            {
                int x = blockX * blockSize;
                for (Integer bloc : lineBloc)
                {
                    if (bloc == 1)
                    {
                        gc.drawImage(Sprite, x, y, blockSize, blockSize);
                    } else if (bloc == 2 && Sprite2 != null) {
                        gc.drawImage(Sprite2, x, y, blockSize, blockSize);
                    }
                    x += blockSize;
                }
                y += blockSize;
            }
        }
        return true;
    }

    //Méthode de déplacement d'un bloc vers le bas
    public void moveDownBlock(Integer[][] block, Integer[][] map)
    {
        int newY = blockY + 1;

        if (canMoveDown(block, map, newY))
        {
            blockY = newY;
        } else {
            //le block se fixe sur la carte s'il ne peut plus descendre
            placeBlock(block, map);

            //fait appraître un nouveau bloc
            spawnNewBlock();
        }
    }

    //Méthode de vérification de si le bloc peut descendre
    private boolean canMoveDown(Integer[][] block, Integer[][] map, int newY)
    {
        for(int i = 0; i < block.length; i++)
        {
            for(int j = 0; j < block[i].length; j++)
            {
                if(block[i][j] == 1){
                    int mapY = newY + i;
                    int mapX = blockX + j;

                    //Si dépasse la dernière ligne
                    if(mapY >= map.length)
                    {
                        return false;
                    }

                    if (mapX < 0 || mapX >= map[0].length)
                    {
                        return false;
                    }

                    //Si touche un bloc déjà posé
                    if(map[mapY][mapX] == 1)
                    {
                        return false;
                    }
                }
            }
        }
        return true;
    };

    //Méthode d'immobilisation du bloc sur la map
    private void placeBlock(Integer[][] block, Integer[][] map)
    {
        for (int i = 0; i < block.length; i++)
        {
            for (int j = 0; j < block[i].length; j++)
            {
                if (block[i][j] == 1)
                {
                    int mapX = blockX + j;
                    int mapY = blockY + i;
                    map[mapY][mapX] = 1;
                }
            }
        }
    }

    //Méthode d'apparition d'un bloc aléatoire
    private void spawnNewBlock()
    {
        Random random = new Random();
        randomBlockType = ALL_BLOCKS[random.nextInt(ALL_BLOCKS.length)];
        blockX = 3;
        blockY = 0;
    }

    //Getter pour la map
    public Integer[][] getShapeMatrisMap()
    {
        return ShapeMatrisMap;
    }

    //Getter pour le bloc courant
    public Integer[][] getRandomBlockType()
    {
        return randomBlockType;
    }

    //Getter pour la position X du bloc
    public int getBlockX()
    {
        return blockX;
    }

    //Getter pour la position Y du bloc
    public int getBlockY()
    {
        return blockY;
    }
}
