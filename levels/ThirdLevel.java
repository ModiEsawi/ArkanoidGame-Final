package levels;

import animation.BackGroundDrawer;
import game.LevelInformation;
import geometry.Point;
import geometry.Circles;
import geometry.Rectangle;
import geometry.Velocity;
import geometry.Line;
import sprites.Ball;
import sprites.Block;
import sprites.Sprite;

import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The Third level.
 */
public class ThirdLevel implements LevelInformation {

    private int screenWidth;
    private int screenHeight;
    private int numberOfBalls;
    private int paddleSpeed;
    private Point paddleUpperLeft;
    private int paddleHeight;
    private int paddleWidth;
    private String levelName;
    private Block gameBackdround;
    private List<Block> blocks;
    private int numberOfBlocksToRemove;
    private List<Sprite> sprites;
    private int ballsRadius;
    private Velocity eachBallVelocity;
    private Ball[] ballsArray;
    private int ballsStartX;
    private int ballsStartY;
    private int flag;
    private Color ballStartingColor;


    /**
     * Instantiates a new Third level.
     *
     * @param screenHeight :the screen height.
     * @param screenWidth  :the screen width.
     * @param flag         :the determining flag.
     */
    public ThirdLevel(int screenHeight, int screenWidth, int flag) {
        // the level details
        this.numberOfBalls = 3;
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        this.blocks = new ArrayList<>();
        this.sprites = new ArrayList<>();
        this.levelName = "Its All About The Hygiene";
        this.eachBallVelocity = new Velocity(0, -7);
        this.ballsRadius = 5;
        this.ballsArray = new Ball[this.numberOfBalls];
        this.flag = flag;
    }

    @Override
    public void initialize() {
        Point paddleUpperleft = new Point(270, 730);
        this.paddleWidth = 280;
        this.paddleHeight = 8;
        this.paddleSpeed = 10;
        this.paddleUpperLeft = paddleUpperleft;
        this.numberOfBlocksToRemove = 40;
        this.ballsStartX = 216;
        this.ballsStartY = 600;
        this.ballStartingColor = Color.RED;
        for (int i = 0; i < this.numberOfBalls(); i++) {
            Ball ball = new Ball(this.ballsStartX * (i + 1), this.ballsStartY, this.ballsRadius, Color.white);
            // creating balls will increase the current ball counter.
            ball.setVelocity(this.eachBallVelocity);
            ball.setColor(ballStartingColor);
            this.ballsArray[i] = ball;
        }
        BackGroundDrawer backGroundDrawer = new BackGroundDrawer(this.flag);
        // drawing the background
        this.gameBackdround = new Block(new geometry.Rectangle(new Point(0, 0), 800, 800),
                (new Color(0xFF4B55F6, true)), -1);
        // drawing the car
        Block block1 = new Block(new Rectangle(new Point(340, 480), 60, 20),
                (new Color(0xFF000000, true)), -1);
        this.sprites.add(block1);
        Color blockColor = new Color(0xFFE7E846, true);
        Block block2 = new Block(new Rectangle(new Point(200, 500), 250, 150),
                blockColor, -1);
        Block block3 = new Block(new Rectangle(new Point(450, 580), 50, 70),
                blockColor, -1);
        Block block4 = new Block(new Rectangle(new Point(170, 570), 100, 80),
                blockColor, -1);
        Color secondBlocksColor = new Color(0xFF727476, true);
        Block block5 = new Block(new Rectangle(new Point(500, 590), 10, 20),
                secondBlocksColor, -1);
        Block block6 = new Block(new Rectangle(new Point(500, 620), 10, 20),
                secondBlocksColor, -1);
        // car windows
        Color thirdBlockColor = new Color(0xFFFCFCFC, true);
        Block block7 = new Block(new Rectangle(new Point(220, 530), 90, 50),
                thirdBlockColor, -1);
        Block block8 = new Block(new Rectangle(new Point(340, 530), 90, 50),
                thirdBlockColor, -1);
        Color linesColor = new Color(0xFF000000, true);
        this.sprites.add(block2);
        this.sprites.add(block3);
        this.sprites.add(block4);
        this.sprites.add(block5);
        this.sprites.add(block6);
        this.sprites.add(block7);
        this.sprites.add(block8);
        // background sprites
        Line line = new Line(410, 570, 430, 580, linesColor);
        Line line2 = new Line(410, 560, 400, 570, linesColor);
        this.sprites.add(line);
        this.sprites.add(line2);
        Color circlesColor = new Color(0xFF6B6B6B, true);
        Circles circle = new Circles(250, 650, 30, circlesColor, "Fill");
        Circles circle2 = new Circles(400, 650, 30, circlesColor, "Fill");
        Color secondCirclesColor = new Color(0xFFA1A1A1, true);
        Circles circle3 = new Circles(250, 650, 20, secondCirclesColor, "Fill");
        Circles circle4 = new Circles(400, 650, 20, secondCirclesColor, "Fill");
        Color thirdCirclesColor = new Color(0xFF000000, true);
        Circles circle5 = new Circles(250, 650, 30, thirdCirclesColor, "Fill");
        Circles circle6 = new Circles(250, 650, 20, thirdCirclesColor, "Fill");
        Circles circle7 = new Circles(400, 650, 30, thirdCirclesColor, "Fill");
        Circles circle8 = new Circles(400, 650, 20, thirdCirclesColor, "Fill");
        // adding the sprites to the game.
        this.sprites.add(circle);
        this.sprites.add(circle2);
        this.sprites.add(circle3);
        this.sprites.add(circle4);
        this.sprites.add(circle5);
        this.sprites.add(circle6);
        this.sprites.add(circle7);
        this.sprites.add(circle8);
        // background blocks
        Color fourthBlocksColor = (Color.GRAY);
        Block block9 = new Block(new Rectangle(new Point(30, 680), 750, 80),
                fourthBlocksColor, -1);
        Color fifthBlocksColor = (Color.white);
        Block block10 = new Block(new Rectangle(new Point(40, 710), 100, 15),
                fifthBlocksColor, -1);
        Block block11 = new Block(new Rectangle(new Point(170, 710), 100, 15),
                fifthBlocksColor, -1);
        Block block12 = new Block(new Rectangle(new Point(300, 710), 100, 15),
                fifthBlocksColor, -1);
        Block block13 = new Block(new Rectangle(new Point(430, 710), 100, 15),
                fifthBlocksColor, -1);
        Block block14 = new Block(new Rectangle(new Point(430, 710), 100, 15),
                fifthBlocksColor, -1);
        Block block15 = new Block(new Rectangle(new Point(430, 710), 100, 15),
                fifthBlocksColor, -1);
        Block block16 = new Block(new Rectangle(new Point(430, 710), 100, 15),
                fifthBlocksColor, -1);
        Block block17 = new Block(new Rectangle(new Point(550, 710), 100, 15),
                fifthBlocksColor, -1);
        Block block18 = new Block(new Rectangle(new Point(670, 710), 100, 15),
                fifthBlocksColor, -1);
        // adding the background to blocks to the game
        this.sprites.add(block9);
        this.sprites.add(block10);
        this.sprites.add(block11);
        this.sprites.add(block12);
        this.sprites.add(block13);
        this.sprites.add(block14);
        this.sprites.add(block15);
        this.sprites.add(block16);
        this.sprites.add(block17);
        this.sprites.add(block18);
        sprites.add(backGroundDrawer);
        int surroundingBlocksWidth = 30, totalBlockRows = 5, blocksInFirstRow = 10, eachBlockWidth = 50;
        int eachBlockHeight = 30, startingYlocation = 145;
        Random rand = new Random();
        int surfaceWidth = 800, topBlocksHits = 2, otherBlockHits = 1;

        // creating the blocks in a certain order and placment.
        for (int i = 0; i < totalBlockRows; i++, blocksInFirstRow--) {
            Color randomColor = new Color(rand.nextInt(0xFFFFFF));
            for (int j = 0; j < blocksInFirstRow; j++) {

                Point upperLeft = new Point(surfaceWidth - (eachBlockWidth * (j + 1)) - surroundingBlocksWidth,
                        startingYlocation + (eachBlockHeight * i));
                if (i != 0) {
                    Block block = new Block(new geometry.Rectangle(upperLeft, eachBlockWidth, eachBlockHeight),
                            randomColor, otherBlockHits);
                    this.blocks.add(block);

                } else {
                    Block block = new Block(new Rectangle(upperLeft, eachBlockWidth, eachBlockHeight), randomColor,
                            topBlocksHits);
                    this.blocks.add(block);
                }
            }
        }

    }


    @Override
    public int numberOfBalls() {
        return this.numberOfBalls;
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        List<Velocity> ballsVelocity = new ArrayList<>();
        ballsVelocity.add(this.eachBallVelocity);
        return ballsVelocity;
    }

    @Override
    public Point paddleUpperLeft() {
        return this.paddleUpperLeft;
    }

    @Override
    public int paddleHeight() {
        return this.paddleHeight;
    }

    @Override
    public int ballsStartX() {
        return this.ballsStartX;
    }

    @Override
    public int ballsStartY() {
        return this.ballsStartY;
    }

    @Override
    public int flag() {
        return this.flag;
    }

    @Override
    public Color startingBallsColor() {
        return this.ballStartingColor;
    }

    @Override
    public Velocity eachBallsVelocity() {
        return this.eachBallVelocity;
    }

    @Override
    public Color getBackgroundColor() {
        return null;
    }

    @Override
    public Image getBackgroundImg() {
        return null;
    }

    @Override
    public int paddleSpeed() {
        return this.paddleSpeed;
    }

    @Override
    public int paddleWidth() {
        return this.paddleWidth;
    }

    @Override
    public String levelName() {
        return this.levelName;
    }


    @Override
    public List<Block> blocks() {
        return this.blocks;
    }

    @Override
    public int numberOfBlocksToRemove() {
        return this.numberOfBlocksToRemove;
    }

    @Override
    public Ball[] ballsArray() {
        return this.ballsArray;
    }

    @Override
    public List<Sprite> getSprites() {
        return this.sprites;
    }

}

