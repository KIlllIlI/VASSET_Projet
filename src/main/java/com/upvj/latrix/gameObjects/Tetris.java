package com.upvj.latrix.gameObjects;

import com.upvj.latrix.GraphicObject;
import com.upvj.latrix.SoundHelper;
import com.upvj.latrix.graphicObjects.GameCanvas;
import com.upvj.latrix.graphicObjects.Rectangles.ImageLabel;
import com.upvj.latrix.graphicObjects.Rectangles.RectangleLabel;
import com.upvj.latrix.graphicObjects.Rectangles.TextLabel;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.util.Duration;

import java.util.*;

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

    private static final int removedFromBottom = 64;
    private static final int movedFromTop = 16;

    private static final Paint transparentGray = Color.hsb(0,0,0,0.5);
    private static final Paint gray = Color.gray(0.4);

    private static final Color[] BLOCK_COLORS = {
            Color.BLUE, Color.MAGENTA, Color.LIME, Color.CYAN, Color.YELLOW, Color.hsb( ((double) 278 /360),1,1), Color.ORANGE
    };

    private static final Map<Integer[][], Image> BLOCK_IMAGES = new HashMap<>();

    private boolean exploding = false;
    private final List<double[]> explodingPieces = new ArrayList<>(); // {x, y, vx, vy}

    private final Image[][] colorMap; // same size as map

    // ======== LABELS

    private final TextLabel ScoreValue;
    private final RectangleLabel NextBlockLabel;




    private int Score = 0;

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
    private Integer[][] nextBlock;


    private Image activeBlockImage;
    private Image nextBlockImage;
    private final Image LaserImage = ImageLabel.getImageFromResource("LaserGun.png");

    private final int rows = 20;
    private final int cols = 10;



    private int blockX;
    private int blockY;

    private int fallSpeed = 600; // ms per step
    private Timeline timeline;
    private double timeAccumulator = 0; // counter for DOWN key reset

    private boolean running = false;

    private final java.util.List<Integer[][]> blockBag = new java.util.ArrayList<>();
    private final Random random = new Random();

    private final ArrayList<double[]> Lasers = new ArrayList<>();

    // ======== CONSTRUCTOR ========
    public Tetris(GameCanvas canvas) {
        this.scene = canvas.getScene();
        this.map = ShapeMatrix.MAP.getMatrix();
        this.colorMap = new Image[rows][cols];

        initBlockImages(ImageLabel.getImageFromResource("block.png"));
        spawnNewBlock();
        setupInput();

        // ======== SETUP LABELS FOR SCORE AND NEXT BLOCKS ========

        // ======== RightBox ========

        RectangleLabel RightBox = new RectangleLabel(scene);

        RightBox.setAnchor(1,0.5);

        RightBox.setPosition(0.9,0.5);

        RightBox.setSize(0.2,0.8);
        canvas.InsertInRenderList(RightBox);

        // ======== ScoreText ========

        TextLabel ScoreText = new TextLabel(scene);

        ScoreText.setAnchor(1,0);

        ScoreText.setPosition(0.8,0.15);
        ScoreText.setSize(0.1,0);
        ScoreText.setSizeOffset(0,50);

        ScoreText.setBackgroundColor(Color.TRANSPARENT);
        ScoreText.setBorderColor(Color.TRANSPARENT);

        ScoreText.setText("Score :");

        ScoreText.setZindex(1);
        canvas.InsertInRenderList(ScoreText);

        // ======== ScoreValue ========

        ScoreValue = new TextLabel(scene);

        ScoreValue.setAnchor(0,0);

        ScoreValue.setBackgroundColor(Color.TRANSPARENT);
        ScoreValue.setBorderColor(Color.TRANSPARENT);

        ScoreValue.setPosition(0.8,0.15);
        ScoreValue.setSize(0.1,0);
        ScoreValue.setSizeOffset(0,50);

        ScoreValue.setText("0");

        ScoreValue.setZindex(1);
        canvas.InsertInRenderList(ScoreValue);

        // ======== NextBlockText ========

        NextBlockLabel = new RectangleLabel(scene);

        NextBlockLabel.setAnchor(0.5,0.5);
        NextBlockLabel.setPosition(0.8,0.5);
        NextBlockLabel.setSizeOffset(200,250);

        NextBlockLabel.setBackgroundColor(Color.BLACK);
        NextBlockLabel.setZindex(1);
        canvas.InsertInRenderList(NextBlockLabel);

        startGameLoop();

    }

    // ======== GAME LOOP ========
    private void startGameLoop() {
        timeline = new Timeline(new KeyFrame(Duration.millis(16), e -> update(16)));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        running = true;
    }



    private final double baseLaserSpeed = 0.08;
    private double currentLaserSpeed = baseLaserSpeed;

    private int laserTimerGoal = 1000;
    private int laserTimerAccumulator = 0;

    private double laserPosition = 0.0;

    private void shootLaser(double offsetX, double blockSize) {
        // Snap X to closest column
        int snappedCol = (int) Math.round(laserPosition);
        double laserX = offsetX + snappedCol * blockSize;

        // Start laser at bottom line
        double startY = movedFromTop + rows * blockSize;
        Lasers.add(new double[]{laserX, startY});
    }

    private void updateLasers(double deltaMillis) {
        double blockSize = (scene.getHeight() - removedFromBottom) / rows;
        double width = blockSize * cols;
        double offsetX = (scene.getWidth() - width) / 2;



        List<double[]> lasersToRemove = new ArrayList<>();
        boolean explosionTriggered = false;

        for (double[] laser : Lasers) {
            laser[1] -= Math.abs(currentLaserSpeed) * blockSize * 2;

            if (!explosionTriggered && activeBlock != null) {
                double activeX = offsetX + blockX * blockSize;
                double activeY = movedFromTop + blockY * blockSize;

                for (int i = 0; i < activeBlock.length; i++) {
                    for (int j = 0; j < activeBlock[i].length; j++) {
                        if (activeBlock[i][j] == 1) {
                            double bx = activeX + j * blockSize;
                            double by = activeY + i * blockSize;

                            if (laser[0] >= bx && laser[0] < bx + blockSize &&
                                    laser[1] <= by + blockSize && laser[1] > by) {

                                lasersToRemove.add(laser);
                                explosionTriggered = true;
                                break;
                            }
                        }
                    }
                    if (explosionTriggered) break;
                }
            }

            if (laser[1] < movedFromTop)
                lasersToRemove.add(laser);
        }

// remove safely after iteration
        Lasers.removeAll(lasersToRemove);

// trigger explosion once after loop
        if (explosionTriggered) {
            triggerExplosion(offsetX + blockX * blockSize, movedFromTop + blockY * blockSize, blockSize);
            SoundHelper.play(SoundHelper.Sound.EXPLOSION);
        }
    }

    private void triggerExplosion(double blockXpx, double blockYpx, double blockSize) {
        if (activeBlock == null) return;

        // Compute block center
        double centerX = blockXpx + (activeBlock[0].length * blockSize) / 2;
        double centerY = blockYpx + (activeBlock.length * blockSize) / 2;

        // Create 4 small pieces flying in different directions
        double speed = blockSize * 0.3;
        explodingPieces.clear();
        explodingPieces.add(new double[]{centerX, centerY, -speed, -speed}); // top-left
        explodingPieces.add(new double[]{centerX, centerY, speed, -speed});  // top-right
        explodingPieces.add(new double[]{centerX, centerY, -speed, speed});  // bottom-left
        explodingPieces.add(new double[]{centerX, centerY, speed, speed});   // bottom-right

        // Immediately remove current block and spawn a new one
        activeBlock = null;
        exploding = false;
        spawnNewBlock();
    }

    private void dropExplodedPieces(double blockSize) {
        for (double[] p : explodingPieces) {
            int col = (int) ((p[0] - (scene.getWidth() - cols * blockSize) / 2) / blockSize);
            int row = rows - 1; // drop to bottom for simplicity

            if (col >= 0 && col < cols) {
                map[row][col] = 1;
                colorMap[row][col] = ImageLabel.getImageFromResource("block.png");
            }
        }
        explodingPieces.clear();
    }

    // GAME LOGIC RUN EVERY 16 MILIS
    private void update(double deltaMillis) {
        if (exploding) return;
        timeAccumulator += deltaMillis;
        laserTimerAccumulator += deltaMillis;

        // Forced fall
        if (timeAccumulator >= fallSpeed) {
            moveDown();
            timeAccumulator = 0;
        }

        // Laser gun movement
        laserPosition += currentLaserSpeed;
        if (laserPosition >= cols - 1 || laserPosition < 0) {
            currentLaserSpeed = -currentLaserSpeed;
        }

        // Fire laser periodically
        if (laserTimerAccumulator >= laserTimerGoal) {
            laserTimerAccumulator = 0;
            double blockSize = (scene.getHeight() - removedFromBottom) / rows;
            double width = blockSize * cols;
            double offsetX = (scene.getWidth() - width) / 2;

            shootLaser(offsetX, blockSize);
        }

        // Move lasers upward
        updateLasers(deltaMillis);
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
        blockBag.addAll(Arrays.asList(ALL_BLOCKS));
        // Shuffle the bag
        java.util.Collections.shuffle(blockBag, random);
    }

    // ======== BLOCK MANAGEMENT ========
    private void spawnNewBlock() {
        if (blockBag.isEmpty()) refillBag(); // refill bag if empty

        if (nextBlock != null) {
            // promote preview to active
            activeBlock = nextBlock;
            activeBlockImage = nextBlockImage;

            // new preview from the bag
            if (blockBag.isEmpty()) refillBag();
            Integer[][] nextOriginal = blockBag.removeFirst();
            nextBlock = copyMatrix(nextOriginal);
            nextBlockImage = BLOCK_IMAGES.get(nextOriginal);

        } else {
            // first spawn: take two blocks
            Integer[][] first = blockBag.removeFirst();
            activeBlock = copyMatrix(first);
            activeBlockImage = BLOCK_IMAGES.get(first);

            if (blockBag.isEmpty()) refillBag();
            Integer[][] second = blockBag.removeFirst();
            nextBlock = copyMatrix(second);
            nextBlockImage = BLOCK_IMAGES.get(second);
        }

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
        int linesRemoved = 0;
        for (int i = 0; i < rows; i++) {
            boolean fullLine = true;
            for (int j = 0; j < cols; j++) {
                if (map[i][j] == 0) {
                    fullLine = false;
                    break;
                }
            }
            if (fullLine) {
                // Full line detected -> clear the line
                linesRemoved++;



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

        Score += 10 * (linesRemoved*linesRemoved);
        ScoreValue.setText(String.valueOf(Score));

        // Check full columns
        for (int j = 0; j < cols; j++) {
            boolean fullCol = true;
            for (int i = 0; i < rows; i++) {
                if (map[i][j] == 0) {
                    fullCol = false;
                    break;
                }
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

    private void drawBlock(GraphicsContext gc, Integer[][] block, Image image,
                           double posX, double posY, double blockSize) {
        for (int i = 0; i < block.length; i++) {
            for (int j = 0; j < block[i].length; j++) {
                if (block[i][j] == 1) {
                    double x = posX + j * blockSize;
                    double y = posY + i * blockSize;
                    gc.drawImage(image, x, y, blockSize, blockSize);
                }
            }
        }
    }

    private void drawNextBlock(GraphicsContext gc, Integer[][] block, Image image) {

        // Define padding (fraction of the label size)
        double padding = 0.1; // 10% padding on each side

        // Available width/height after padding
        double availableWidth = NextBlockLabel.AbsoluteWidth * (1 - 2 * padding);
        double availableHeight = NextBlockLabel.AbsoluteHeight * (1 - 2 * padding);

        // Compute block size to fit inside the available space
        double blockSize = Math.min(
                availableWidth / block[0].length,
                availableHeight / block.length
        );

        // Compute top-left coordinates to center the block
        double posX = NextBlockLabel.AbsoluteX + (NextBlockLabel.AbsoluteWidth - block[0].length * blockSize) / 2;
        double posY = NextBlockLabel.AbsoluteY + (NextBlockLabel.AbsoluteHeight - block.length * blockSize) / 2;

        // Draw the block
        drawBlock(gc, block, image, posX, posY, blockSize);
    }

    @Override
    public boolean draw(GraphicsContext gc) {
        gc.setImageSmoothing(false);

        double blockSize = (scene.getHeight() - removedFromBottom) / rows;
        double width = blockSize * cols;
        double offsetX = (scene.getWidth() - width) / 2;

        // Draw board grid
        gc.save();
        gc.translate(0, movedFromTop);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                double x = offsetX + j * blockSize;
                double y = i * blockSize;
                if (map[i][j] == 1) {
                    gc.drawImage(colorMap[i][j], x, y, blockSize, blockSize);
                } else {
                    gc.setFill(transparentGray);
                    gc.fillRect(x, y, blockSize, blockSize);
                    gc.setStroke(gray);
                    gc.strokeRect(x, y, blockSize, blockSize);
                }
            }
        }
        gc.restore();

        // Draw active block
        if (activeBlock != null) {
            double posX = offsetX + blockX * blockSize;
            double posY = movedFromTop + blockY * blockSize;
            drawBlock(gc, activeBlock, activeBlockImage, posX, posY, blockSize);
        }

        // Draw next block (preview)
        if (nextBlock != null) {
            drawNextBlock(gc, nextBlock, nextBlockImage );

        }

        //Draw laserLine

        double lineWidth = cols*blockSize;

        double lineY = movedFromTop*2 + blockSize * rows ;
        double lineHeight = movedFromTop;

        gc.setFill(gray);
        gc.fillRect(offsetX, lineY, lineWidth, lineHeight);

        //Draw laser

        double laserY = lineY + (lineHeight - blockSize) / 2;

        gc.drawImage(LaserImage, offsetX + laserPosition * blockSize, laserY, blockSize, blockSize);

        for (double[] laser : Lasers) {
            gc.setFill(Color.RED);
            gc.fillRect(laser[0] + blockSize * 0.4, laser[1], blockSize * 0.2, blockSize);
        }

        //Draw explosion

        if (exploding) {
            gc.setFill(Color.ORANGE);
            for (double[] p : explodingPieces) {
                p[0] += p[2];
                p[1] += p[3];
                gc.fillOval(p[0] - blockSize / 4, p[1] - blockSize / 4, blockSize / 2, blockSize / 2);
            }
        }



        return true;
    }

    @Override
    public int zIndex() {
        return 10;
    }
}
