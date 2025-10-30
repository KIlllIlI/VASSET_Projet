package com.upvj.latrix.gameObjects;

import com.upvj.latrix.GraphicObject;
import com.upvj.latrix.graphicObjects.GameCanvas;
import com.upvj.latrix.graphicObjects.Rectangles.ImageLabel;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Tetris implements GraphicObject {

    // ======== STATIC HELPERS ========
    private static final Integer[][][] ALL_BLOCKS = {
            ShapeMatrix.L.getMatrix(),
            ShapeMatrix.I.getMatrix(),
            ShapeMatrix.O.getMatrix(),
            ShapeMatrix.S.getMatrix(),
            ShapeMatrix.T.getMatrix(),
            ShapeMatrix.rS.getMatrix(),
            ShapeMatrix.rL.getMatrix(),
    };

    private static final Color[] BLOCK_COLORS = {
            Color.BLUE, Color.hsb((double) 300 /360,1,1), Color.LIME, Color.CYAN, Color.YELLOW, Color.hsb((double) 278 /360,1,1), Color.ORANGE
    };

    private static final Map<Integer[][], Image> BLOCK_IMAGES = new HashMap<>();

    private final Image[][] colorMap; // same size as map

    // Load colored images for each block at startup
    public static void initBlockImages(Image baseSprite) {
        for (int i = 0; i < ALL_BLOCKS.length; i++) {
            BLOCK_IMAGES.put(ALL_BLOCKS[i], ImageLabel.screen(baseSprite, BLOCK_COLORS[i]));
        }
    }

    private static Integer[][] copyMatrix(Integer[][] src) {
        Integer[][] copy = new Integer[src.length][src[0].length];
        for (int i = 0; i < src.length; i++) {
            System.arraycopy(src[i], 0, copy[i], 0, src[i].length);
        }
        return copy;
    }

    private static Integer[][] randomShape() {
        return copyMatrix(ALL_BLOCKS[new Random().nextInt(ALL_BLOCKS.length)]);
    }

    // ======== INSTANCE FIELDS ========
    private final Scene scene;
    private final Integer[][] map;
    private Integer[][] activeBlock;
    private Image activeBlockImage;

    private final int rows = 20;
    private final int cols = 10;

    private int blockX;
    private int blockY;

    private final int fallSpeed = 600; // ms per step
    private Timeline timeline;
    private double timeAccumulator = 0; // counter for DOWN key reset

    private boolean running = false;

    private final java.util.List<Integer[][]> blockBag = new java.util.ArrayList<>();
    private final Random random = new Random();

    // ======== CONSTRUCTOR ========
    public Tetris(Scene scene) {
        this.scene = scene;
        this.map = ShapeMatrix.MAP.getMatrix();
        this.colorMap = new Image[rows][cols];

        initBlockImages(ImageLabel.getImageFromResource("block.png"));
        spawnNewBlock();
        setupInput();
        startGameLoop();
    }

    // ======== GAME LOOP ========
    private void startGameLoop() {
        timeline = new Timeline(new KeyFrame(Duration.millis(16), e -> update(16)));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        running = true;
    }

    private void update(double deltaMillis) {
        timeAccumulator += deltaMillis;

        // Forced fall based on fallSpeed
        if (timeAccumulator >= fallSpeed) {
            moveDown();
            timeAccumulator = 0;
        }
    }

    public void stop() {
        if (timeline != null) timeline.stop();
        running = false;
    }

    public boolean isRunning() {
        return running;
    }

    // ======== INPUT HANDLING ========
    private void setupInput() {
        scene.setOnKeyPressed(e -> {
            if (activeBlock == null || !running) return;

            KeyCode key = e.getCode();
            switch (key) {
                case LEFT -> moveHorizontal(-1);
                case RIGHT -> moveHorizontal(1);
                case DOWN -> {
                    moveDown();
                    // Reset the counter to delay the next forced down
                    timeAccumulator = 0;
                }
                case UP -> rotateBlock(); // Rotate the block
                case SPACE -> {
                    hardDrop();
                    timeAccumulator = 0;
                }
            }
        });
    }

    private void refillBag() {
        blockBag.clear();
        // Add all blocks to the bag
        for (Integer[][] block : ALL_BLOCKS) {
            blockBag.add(block);
        }
        // Shuffle the bag
        java.util.Collections.shuffle(blockBag, random);
    }

    // ======== BLOCK MANAGEMENT ========
    private void spawnNewBlock() {
        if (blockBag.isEmpty()) refillBag(); // refill bag if empty

        // Pop the next block from the bag
        Integer[][] original = blockBag.removeFirst();

        // Copy for gameplay
        activeBlock = copyMatrix(original);

        // Assign its image
        activeBlockImage = BLOCK_IMAGES.get(original);

        // Center horizontally
        blockX = cols / 2 - activeBlock[0].length / 2;
        blockY = 0;

        // Immediate collision check (game over)
        if (!canPlace(activeBlock, blockX, blockY)) stop();
    }

    private boolean canPlace(Integer[][] block, int newX, int newY) {
        for (int i = 0; i < block.length; i++) {
            for (int j = 0; j < block[i].length; j++) {
                if (block[i][j] == 1) {
                    int x = newX + j;
                    int y = newY + i;

                    if (x < 0 || x >= cols || y >= rows) return false;
                    if (y >= 0 && map[y][x] == 1) return false;
                }
            }
        }
        return true;
    }

    private void placeBlock() {
        for (int i = 0; i < activeBlock.length; i++) {
            for (int j = 0; j < activeBlock[i].length; j++) {
                if (activeBlock[i][j] == 1) {
                    int x = blockX + j;
                    int y = blockY + i;
                    if (y >= 0 && y < rows && x >= 0 && x < cols) {
                        map[y][x] = 1;                 // mark as static block
                        colorMap[y][x] = activeBlockImage; // store the image for this block
                    }
                }
            }
        }
        activeBlock = null;
        activeBlockImage = null;

        checkFullLinesAndColumns();
    }

    // ======== ROTATION ========
    private void rotateBlock() {
        // Rotate clockwise
        Integer[][] rotated = new Integer[activeBlock[0].length][activeBlock.length];
        for (int i = 0; i < activeBlock.length; i++) {
            for (int j = 0; j < activeBlock[i].length; j++) {
                rotated[j][activeBlock.length - 1 - i] = activeBlock[i][j];
            }
        }
        if (canPlace(rotated, blockX, blockY)) {
            activeBlock = rotated;
        }
    }

    // ======== MOVEMENT ========
    private void moveDown() {
        if (activeBlock == null) {
            spawnNewBlock();
            return;
        }

        if (canPlace(activeBlock, blockX, blockY + 1)) {
            blockY++;
        } else {
            placeBlock(); // Block fixed
            spawnNewBlock();
        }
    }

    private void moveHorizontal(int dx) {
        if (canPlace(activeBlock, blockX + dx, blockY)) {
            blockX += dx;
        }
    }

    private void hardDrop() {
        while (canPlace(activeBlock, blockX, blockY + 1)) {
            blockY++;
        }
        placeBlock();
        spawnNewBlock();
    }

    // ======== CHECK FOR FULL LINES AND COLUMNS ========
    private void checkFullLinesAndColumns() {
        // Check full lines
        for (int i = 0; i < rows; i++) {
            boolean fullLine = true;
            for (int j = 0; j < cols; j++) {
                if (map[i][j] == 0) fullLine = false;
            }
            if (fullLine) {
                // Full line detected -> clear the line
                for (int j = 0; j < cols; j++) {
                    map[i][j] = 0;
                    colorMap[i][j] = null; // clear the color/image too
                }

                // Move all lines above down by one
                for (int k = i - 1; k >= 0; k--) {
                    for (int j = 0; j < cols; j++) {
                        map[k + 1][j] = map[k][j];
                        colorMap[k + 1][j] = colorMap[k][j];
                    }
                }

                // Clear the top line after shift
                for (int j = 0; j < cols; j++) {
                    map[0][j] = 0;
                    colorMap[0][j] = null;
                }

                i--; // recheck the same row index because it now contains the shifted row
            }
        }

        // Check full columns
        for (int j = 0; j < cols; j++) {
            boolean fullCol = true;
            for (int i = 0; i < rows; i++) {
                if (map[i][j] == 0) fullCol = false;
            }
            if (fullCol) {
                // Full column detected -> clear the column
                for (int i = 0; i < rows; i++) {
                    map[i][j] = 0;
                    colorMap[i][j] = null;
                }

                // Move all columns left by one
                for (int k = j + 1; k < cols; k++) {
                    for (int i = 0; i < rows; i++) {
                        map[i][k - 1] = map[i][k];
                        colorMap[i][k - 1] = colorMap[i][k];
                    }
                }

                // Clear the rightmost column after shift
                for (int i = 0; i < rows; i++) {
                    map[i][cols - 1] = 0;
                    colorMap[i][cols - 1] = null;
                }

                j--; // recheck the same column index because it now contains the shifted column
            }
        }
    }


    // ======== RENDERING ========
    @Override
    public boolean draw(GraphicsContext gc) {
        gc.setImageSmoothing(false);

        double blockSize = scene.getHeight() / rows;
        double width = blockSize * cols;
        double offsetX = (scene.getWidth() - width) / 2;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (map[i][j] == 1) {
                    gc.drawImage(colorMap[i][j], offsetX + j * blockSize, i * blockSize, blockSize, blockSize);
                } else {
                    gc.setFill(Color.hsb(0,0,0,0.5));
                    gc.fillRect(offsetX + j * blockSize, i * blockSize, blockSize, blockSize);
                    gc.setStroke(Color.gray(0.4));
                    gc.strokeRect(offsetX + j * blockSize, i * blockSize, blockSize, blockSize);
                }
            }
        }

        // Draw active block on top
        if (activeBlock != null) {
            for (int i = 0; i < activeBlock.length; i++) {
                for (int j = 0; j < activeBlock[i].length; j++) {
                    if (activeBlock[i][j] == 1) {
                        double x = offsetX + (blockX + j) * blockSize;
                        double y = (blockY + i) * blockSize;
                        gc.drawImage(activeBlockImage, x, y, blockSize, blockSize);
                    }
                }
            }
        }
        return true;
    }
}
