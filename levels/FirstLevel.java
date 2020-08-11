package levels;

import game.LevelInformation;
import geometry.Line;
import geometry.Rectangle;
import geometry.Velocity;
import sprites.Sprite;
import sprites.Block;
import sprites.Ball;

import geometry.Point;
import geometry.Circles;

import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;


/**
 * The First level.
 */
public class FirstLevel implements LevelInformation {

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
    private Color ballsStartColor;

    /**
     * Instantiates the new First level.
     *
     * @param screenHeight : the screen height.
     * @param screenWidth  : the screen width.
     * @param flag         : the determining flag.
     */
    public FirstLevel(int screenHeight, int screenWidth, int flag) {
        // the level details
        this.numberOfBalls = 1;
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        this.blocks = new ArrayList<>();
        this.sprites = new ArrayList<>();
        this.levelName = "Direct Hit";
        this.eachBallVelocity = new Velocity(0, -5);
        this.ballsRadius = 5;
        this.ballsArray = new Ball[this.numberOfBalls];
        this.ballsStartX = 400;
        this.ballsStartY = 695;
        this.flag = flag;
        this.ballsStartColor = Color.RED;
    }

    @Override
    public void initialize() {
        Point paddleUpperleft = new Point(355, 730);
        this.paddleWidth = 95;
        this.paddleHeight = 20;
        this.paddleSpeed = 10;
        this.paddleUpperLeft = paddleUpperleft;
        this.numberOfBlocksToRemove = 1;
        this.gameBackdround = new Block(new Rectangle(new Point(0, 0), screenWidth,
                screenHeight), Color.black, -1);
        // creating the first level balls
        for (int i = 0; i < this.numberOfBalls(); i++) {
            Ball ball = new Ball(this.ballsStartX * (i + 1), this.ballsStartY * (i + 1), this.ballsRadius, Color.white);
            // creating balls will increase the current ball counter.
            ball.setVelocity(this.eachBallVelocity);
            ball.setColor(this.ballsStartColor);
            this.ballsArray[i] = ball;
        }
        // drawing the background of the level and the center block
        Block centerBlock = new Block(new Rectangle(new Point(386, 170), 28, 28), Color.RED, 1);
        this.blocks.add(centerBlock);
        Line firstLine = new Line(260, 181, 380, 181, Color.BLUE);
        Line secondLine = new Line(541, 181, 419, 181, Color.BLUE);
        Line thirdLine = new Line(400, 200, 400, 327, Color.BLUE);
        Line fourthLine = new Line(400, 56, 400, 180, Color.BLUE);
        Circles firstCircle = new Circles(400, 181, 60, Color.BLUE, "dont fill");
        Circles secondCircle = new Circles(400, 181, 90, Color.blue, "dont fill");
        Circles thirdCircle = new Circles(400, 181, 120, Color.BLUE, "dont fill");
        // adding them as sprites
        sprites.add(firstLine);
        sprites.add(secondLine);
        sprites.add(thirdLine);
        sprites.add(fourthLine);
        sprites.add(firstCircle);
        sprites.add(secondCircle);
        sprites.add(thirdCircle);
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
        return this.ballsStartColor;
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
