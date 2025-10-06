package com.upvj.latrix;

import com.upvj.latrix.gameObjects.Block;
import com.upvj.latrix.gameObjects.Tetris;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

public class GameScene extends Scene {

    public GameScene(Group root)
        {
        super(root, 64*5, 64*5, Color.WHITE);
        }

    public void KeyPressed(Block block, Tetris tetris) {
        super.setOnKeyPressed(e -> { //faire en sorte d'avoir une fonction pour delete, set, move
            if (e.getCode() == KeyCode.Q || e.getCode() == KeyCode.LEFT) {
                if (block.getPosX() != 0) {
                    tetris.deleteBlock(block.getPosX(), block.getPosY());
                    block.setPosX(block.getPosX() - 1);
                    tetris.moveBlock(block.getPosX(), block.getPosY());
                }
            } else if (e.getCode() == KeyCode.S || e.getCode() == KeyCode.DOWN) {
                if (block.getPosY() != 4) {
                    tetris.deleteBlock(block.getPosX(), block.getPosY());
                    block.setPosY(block.getPosY() + 1);
                    tetris.moveBlock(block.getPosX(), block.getPosY());

                }
            } else if (e.getCode() == KeyCode.D || e.getCode() == KeyCode.RIGHT) {
                if (block.getPosX() != 4) {
                    tetris.deleteBlock(block.getPosX(), block.getPosY());
                    block.setPosX(block.getPosX() + 1);
                    tetris.moveBlock(block.getPosX(), block.getPosY());
                }
            } else if (e.getCode() == KeyCode.Z || e.getCode() == KeyCode.UP) {
                if (block.getPosY() != 0) {
                    tetris.deleteBlock(block.getPosX(), block.getPosY());
                    block.setPosY(block.getPosY() - 1);
                    tetris.moveBlock(block.getPosX(), block.getPosY());
                }
            }
            System.out.println(e.getCode());
            for (Boolean[] b : tetris.getShapeMatrix()) {
                for (Boolean o : b) {
                    System.out.print(o);
                }
                System.out.println();
            }
        });
    }
}
