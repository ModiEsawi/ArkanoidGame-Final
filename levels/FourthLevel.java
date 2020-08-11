package levels;

import animation.BackGroundDrawer;
import game.LevelInformation;
import geometry.Oval;
import geometry.Point;
import geometry.Circles;
import geometry.Rectangle;
import geometry.Velocity;
import sprites.Ball;
import sprites.Block;
import sprites.Sprite;

import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The Fourth level.
 */
public class FourthLevel implements LevelInformation {


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
    private Color ballsStartingColor;


    /**
     * Instantiates the new Fourth level.
     *
     * @param screenHeight the screen height.
     * @param screenWidth  the screen width.
     * @param flag         the determining flag.
     */
    public FourthLevel(int screenHeight, int screenWidth, int flag) {
        // the level details
        this.numberOfBalls = 5;
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        this.blocks = new ArrayList<>();
        this.sprites = new ArrayList<>();
        this.levelName = "We Are All Androids";
        this.eachBallVelocity = new Velocity(0, 7);
        this.ballsRadius = 5;
        this.ballsArray = new Ball[this.numberOfBalls];
        this.flag = flag;
        this.ballsStartingColor = Color.RED;
    }

    @Override
    public void initialize() {
        Point paddleUpperleft = new Point(240, 730);
        this.paddleWidth = 280;
        this.paddleHeight = 8;
        this.paddleSpeed = 10;
        this.paddleUpperLeft = paddleUpperleft;
        this.numberOfBlocksToRemove = 105;
        this.ballsStartX = 280;
        this.ballsStartY = 700;

        for (int i = 0; i < this.numberOfBalls(); i++) {
            Ball ball = new Ball(this.ballsStartX + (i * 50), this.ballsStartY, this.ballsRadius, Color.white);
            // creating balls will increase the current ball counter.
            ball.setVelocity(this.eachBallVelocity);
            ball.setColor(this.ballsStartingColor);
            this.ballsArray[i] = ball;
        }

        BackGroundDrawer backGroundDrawer = new BackGroundDrawer(this.flag);
        this.gameBackdround = new Block(new geometry.Rectangle(new Point(0, 0), 800, 800),
                Color.black, -1);

        //clouds
        Circles c1 = new Circles(600, 620, 35, Color.lightGray, "Fill");
        Circles c2 = new Circles(640, 588, 42, Color.lightGray, "Fill");
        Circles c3 = new Circles(620, 660, 41, Color.lightGray, "Fill");
        Circles c4 = new Circles(665, 652, 36, Color.lightGray, "Fill");
        Circles c5 = new Circles(710, 636, 35, Color.lightGray, "Fill");
        Circles c6 = new Circles(700, 588, 42, Color.lightGray, "Fill");
        Circles c7 = new Circles(88, 620, 35, Color.lightGray, "Fill");
        Circles c8 = new Circles(128, 588, 42, Color.lightGray, "Fill");
        Circles c9 = new Circles(108, 660, 41, Color.lightGray, "Fill");
        Circles c10 = new Circles(153, 652, 36, Color.lightGray, "Fill");
        Circles c11 = new Circles(198, 636, 35, Color.lightGray, "Fill");
        Circles c12 = new Circles(188, 588, 42, Color.lightGray, "Fill");
        // adding the clouds as sprites
        this.sprites.add(c1);
        this.sprites.add(c2);
        this.sprites.add(c3);
        this.sprites.add(c4);
        this.sprites.add(c5);
        this.sprites.add(c6);
        this.sprites.add(c7);
        this.sprites.add(c8);
        this.sprites.add(c9);
        this.sprites.add(c10);
        this.sprites.add(c11);
        this.sprites.add(c12);

        //rain
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Oval rain = new Oval(108 + (12 * j) + j,
                        714 + (i * 14) - (j * 4),
                        3, 13, Color.blue);
                this.sprites.add(rain);
            }
        }
        //rain
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Oval rain = new Oval(616 + (12 * j) + j,
                        714 + (i * 14) - (j * 4), 3, 13, Color.blue);
                this.sprites.add(rain);
            }
        }

        int surroundingBlocksWidth = 30, totalBlockRows = 7, blocksInRow = 15, eachBlockWidth = 50;
        int eachBlockHeight = 30, startingYlocation = 145;
        Random rand = new Random();
        int topBlocksHits = 2;
        for (int i = 0; i < totalBlockRows; i++) { // initialize the blocks
            Color randomColor = new Color(rand.nextInt(0xFFFFFF));
            for (int j = 0; j < blocksInRow; j++) {
                Block block = new Block(new Rectangle(new Point(surroundingBlocksWidth + (j * eachBlockWidth),
                        startingYlocation + (eachBlockHeight * i)), eachBlockWidth, eachBlockHeight), randomColor,
                        topBlocksHits);
                this.blocks.add(block);
            }
        }
        sprites.add(backGroundDrawer);
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
        return this.ballsStartingColor;
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


