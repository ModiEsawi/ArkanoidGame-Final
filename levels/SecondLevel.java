package levels;

import game.LevelInformation;
import geometry.Oval;
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
 * The Second level.
 */
public class SecondLevel implements LevelInformation {

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
     * Instantiates the new Second level.
     *
     * @param screenHeight : the screen height.
     * @param screenWidth  : the screen width.
     * @param flag         : the determining flag.
     */
    public SecondLevel(int screenHeight, int screenWidth, int flag) {
        // the level details
        this.numberOfBalls = 5;
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        this.blocks = new ArrayList<>();
        this.sprites = new ArrayList<>();
        this.levelName = "Sweet Arthur";
        this.eachBallVelocity = new Velocity(0, 7);
        this.ballsRadius = 5;
        this.ballsArray = new Ball[this.numberOfBalls];
        this.flag = flag;
        this.ballStartingColor = Color.RED;
    }

    @Override
    public void initialize() {
        Point paddleUpperleft = new Point(150, 730);
        this.paddleWidth = 420;
        this.paddleHeight = 8;
        this.paddleSpeed = 10;
        this.paddleUpperLeft = paddleUpperleft;
        this.numberOfBlocksToRemove = 15;
        this.ballsStartX = 210;
        this.ballsStartY = 600;

        for (int i = 0; i < this.numberOfBalls(); i++) {
            Ball ball = new Ball(this.ballsStartX + (i * 90), this.ballsStartY, this.ballsRadius, Color.white);
            // creating balls will increase the current ball counter.
            ball.setVelocity(this.eachBallVelocity);
            ball.setColor(this.ballStartingColor);
            this.ballsArray[i] = ball;
        }
        // level background
        this.gameBackdround = new Block(new Rectangle(new Point(0, 0), 800, 800),
                new Color(0xFFF6F29B, true), 2);
        // drawing arthur
        // ears
        Oval oval1 = new Oval(210, 40, 130, 200, (new Color(0xFFF6B272, true)));
        Oval oval2 = new Oval(440, 40, 130, 200, (new Color(0xFFF6B272, true)));
        Oval oval3 = new Oval(240, 70, 80, 200, (new Color(0xFFDA9DBF, true)));
        Oval oval4 = new Oval(465, 70, 80, 200, (new Color(0xFFDA9DBF, true)));
        sprites.add(oval1);
        sprites.add(oval2);
        sprites.add(oval3);
        sprites.add(oval4);
        // head
        Oval ovalHead = new Oval(200, 100, 400, 500, (new Color(0xFFF6B272, true)));
        sprites.add(ovalHead);
        //eyes
        Circles circle1 = new Circles(300, 300, 80,
                (new Color(0xFFA06527, true)), "Fill");
        Circles circle2 = new Circles(500, 300, 80,
                (new Color(0xFFA06527, true)), "Fill");
        Circles circle3 = new Circles(300, 300, 65,
                (new Color(0xFFFEFEFE, true)), "Fill");
        Circles circle4 = new Circles(500, 300, 65,
                (new Color(0xFFFEFEFE, true)), "Fill");
        Circles circles5 = new Circles(300, 300, 17,
                (new Color(0xFF060606, true)), "Fill");
        Circles circle6 = new Circles(500, 300, 17,
                (new Color(0xFF060606, true)), "Fill");
        sprites.add(circle1);
        sprites.add(circle2);
        sprites.add(circle3);
        sprites.add(circle4);
        sprites.add(circles5);
        sprites.add(circle6);
        // glasses
        Color linesColor = new Color(0xFFA06527, true);
        for (int i = 0; i < 40; i++) {
            Line firstLine = new Line(375, 285 + (i >> 1), 425, 285 + (i >> 2), linesColor);
            Line secondLine = new Line(210, 265 + (i >> 2), 235, 280 + (i >> 2), linesColor);
            Line thirdLine = new Line(570, 280 + (i >> 2), 590, 265 + (i >> 2), linesColor);
            sprites.add(firstLine);
            sprites.add(secondLine);
            sprites.add(thirdLine);
        }

        // mouth
        Oval mouth1 = new Oval(300, 430, 200, 120, (new Color(0xFFA01615, true)));
        Oval mouth2 = new Oval(300, 430, 200, 80, (new Color(0xFFF6B272, true)));
        sprites.add(mouth1);
        sprites.add(mouth2);
        // nose
        Color lineColor = new Color(0xFF060606, true);
        Line line1 = new Line(410, 400 + 10, 420, 420 + 10, lineColor);
        Line line2 = new Line(390, 400 + 10, 380, 420 + 10, lineColor);
        Line line3 = new Line(380, 420 + 10, 420, 420 + 10, lineColor);
        sprites.add(line1);
        sprites.add(line2);
        sprites.add(line3);
        int surroundingBlocksWidth = 30, totalBlockNum = 15, eachBlockWidth = 50;
        int eachBlockHeight = 30;
        Random rand = new Random();
        for (int i = 0; i < totalBlockNum; i++) {
            Color randomColor = new Color(rand.nextInt(0xFFFFFF));
            Block block = new Block(new Rectangle(new Point(surroundingBlocksWidth + (i * eachBlockWidth),
                    300), eachBlockWidth, eachBlockHeight), randomColor, 1);
            this.blocks.add(block);
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

