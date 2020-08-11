/**
 * part of the game package.
 */
package game;

import animation.Animation;
import animation.AnimationRunner;
import animation.CountdownAnimation;
import animation.KeyPressStoppableAnimation;
import animation.PauseScreen;
import geometry.Point;
import geometry.Rectangle;
import geometry.Velocity;
import listeners.BallAdder;
import listeners.BallRemover;
import listeners.BlockRemover;
import listeners.ScoreTrackingListener;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import sprites.Collidable;
import sprites.Counter;
import sprites.Sprite;
import sprites.SpriteCollection;
import sprites.Block;
import sprites.ScoreIndicator;
import sprites.LevelNameDisplayer;
import sprites.LivesIndicator;
import sprites.Ball;
import sprites.Paddle;

import java.awt.Color;
import java.awt.Image;
import java.util.List;

/**
 * The game class.
 * in charge of the game animation and contains the collection of sprites and collaidables.
 *
 * @author : mohammed Elesawi.
 */
public class GameLevel implements Animation {
    private Counter remainedBlocks;
    private Counter remainedBalls;
    private Counter score;
    private Counter numberOfLives;
    private SpriteCollection sprites;
    private GameEnvironment environment;
    private AnimationRunner runner;
    private boolean running;
    private KeyboardSensor keyboard;
    private LevelInformation currentLevel;


    /**
     * Instantiates a new Game level.
     *
     * @param animationRunner :the animation runner.
     * @param keyboard        :the keyboard.
     * @param currentLevel    :the current level.
     * @param score           :the score.
     * @param numberOfLives   :the number of lives.
     */
    public GameLevel(AnimationRunner animationRunner, KeyboardSensor keyboard, LevelInformation currentLevel,
                     Counter score, Counter numberOfLives) {
        this.environment = new GameEnvironment();
        this.sprites = new SpriteCollection();
        this.remainedBlocks = new Counter();
        this.remainedBalls = new Counter();
        this.score = score;
        this.numberOfLives = numberOfLives;
        this.running = true;
        this.runner = animationRunner;
        this.keyboard = keyboard;
        this.currentLevel = currentLevel;
    }

    /**
     * adding a collidable object to the game's environment.
     *
     * @param c : the collidable that needs to be added.
     */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    /**
     * removing a collidable object to the game's environment.
     *
     * @param c : the collidable that needs to be removed.
     */
    public void removeCollidable(Collidable c) {
        this.environment.removeCollidable(c);
    }


    /**
     * adding a sprite object to the sprite collection.
     *
     * @param s : the sprite object that needs to be added.
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    /**
     * removing a sprite object to the sprite collection.
     *
     * @param s : the sprite object that needs to be removed.
     */
    public void removeSprite(Sprite s) {
        this.sprites.removeSprite(s);
    }

    /**
     * Initializing the game by creating the blocks , the balls and the rest of the sprites , and adding them to the
     * game.
     */
    public void initialize() {
        Image backgroundImage = this.currentLevel.getBackgroundImg();
        Color backGroundColor = this.currentLevel.getBackgroundColor();
        if (backGroundColor == null) {
            Block imageBlock = new Block(new Rectangle(new Point(0, 0), 802, 602), backgroundImage);
            this.sprites.addSprite(imageBlock);
            imageBlock.setHitPoints(-1);
        } else {
            Block backGround = new Block(new Rectangle(new Point(0, 0), 800, 800), backGroundColor, -1);
            this.sprites.addSprite(backGround);
        }
        BallRemover ballRemover = new BallRemover(this, this.remainedBalls);
        BallAdder ballAdder = new BallAdder(this, this.remainedBalls, this.environment);
        ScoreTrackingListener scoreTrackingListener = new ScoreTrackingListener(this.score);
        ScoreIndicator scoreIndicator = new ScoreIndicator(scoreTrackingListener.getCurrentScore());
        this.addSprite(scoreIndicator);
        LevelNameDisplayer levelNameDisplayer = new LevelNameDisplayer(this.currentLevel.levelName());
        this.addSprite(levelNameDisplayer);
        BlockRemover blockRemover = new BlockRemover(this, this.remainedBlocks, scoreTrackingListener);
        blockRemover.addHitListener(scoreTrackingListener);
        LivesIndicator livesIndicator = new LivesIndicator(this.numberOfLives);
        this.addSprite(livesIndicator);
        int surroundingBlocksWidth = 30, eachBlockWidth = 50;
        int eachBlockHeight = 30, surfaceHeight = 600, surfaceWidth = 800, drawX = -1;

        // the current level sprites
        List<Sprite> levelSprites = this.currentLevel.getSprites();
        for (Sprite levelSprite : levelSprites) {
            this.addSprite(levelSprite);
        }
        // the current level Balls
        Ball[] levelBalls = this.currentLevel.ballsArray();
        this.remainedBalls.increase(levelBalls.length);
        for (Ball levelBall : levelBalls) {
            levelBall.setGameEnvironment(this.environment);
            levelBall.addToGame(this);
        }
//         the current level Blocks
        List<Block> blocks = this.currentLevel.blocks();
        this.remainedBlocks.increase(blocks.size());
        for (Block block : blocks) {
            block.addToGame(this);
            block.addHitListener(blockRemover);
        }



        /* creating the blocks that surround the surface according to screen lengths , adding them to the game
          , and creating the "bonusdeath" and "life" blocks. */

        Block topBlock = new Block(new Rectangle(new Point(0, 30), surfaceWidth, surroundingBlocksWidth),
                Color.GRAY, drawX);

        topBlock.addToGame(this);

        Block deathBlock = new Block(new Rectangle(new Point(surroundingBlocksWidth,
                surfaceHeight), surfaceWidth - surroundingBlocksWidth * 2,
                surroundingBlocksWidth), Color.GRAY, drawX);

        deathBlock.addHitListener(ballRemover);
        deathBlock.addToGame(this);

        // adding the special blocks according to the levels.
        Block bonusDeathBlock = new Block(new Rectangle(new Point(surroundingBlocksWidth,
                surfaceHeight - (10 * surroundingBlocksWidth) + 90), eachBlockWidth * 1.5,
                eachBlockHeight), Color.BLACK, -2);
        Block lifeBlock = new Block(new Rectangle(new Point(surfaceWidth - (3.50 * surroundingBlocksWidth),
                surfaceHeight - (10 * surroundingBlocksWidth) + 90), eachBlockWidth * 1.5,
                eachBlockHeight), Color.BLUE, -2);
        bonusDeathBlock.addToGame(this);
        bonusDeathBlock.addHitListener(ballRemover);
        lifeBlock.addHitListener(ballAdder);
        lifeBlock.addToGame(this);

        Block leftBlock = new Block(new Rectangle(new Point(0, surroundingBlocksWidth + 30), surroundingBlocksWidth,
                surfaceHeight - surroundingBlocksWidth), Color.GRAY, drawX);
        leftBlock.addToGame(this);

        Block rightBlock = new Block(new Rectangle(new Point(surfaceWidth - surroundingBlocksWidth,
                surroundingBlocksWidth + 30), surroundingBlocksWidth, surfaceHeight - surroundingBlocksWidth),
                Color.GRAY, drawX);
        rightBlock.addToGame(this);

    }

    /**
     * "run" method that updates the number of lives , and returns accordingly.
     */

    public void run() {
        int surroundingBlocksWidth = 30, surfaceWidth = 800;
        KeyboardSensor sensor = this.keyboard; // this.gui.getKeyboardSensor();
        Point paddleUpperleft = this.currentLevel.paddleUpperLeft();
        int paddleWidth = this.currentLevel.paddleWidth();
        int paddleHeight = this.currentLevel.paddleHeight();
        int paddleMove = this.currentLevel.paddleSpeed();
        Paddle paddle = new Paddle(new Rectangle(paddleUpperleft, paddleWidth, paddleHeight), Color.YELLOW,
                sensor, paddleMove, surroundingBlocksWidth, surfaceWidth);
        paddle.addToGame(this);


        //setting the paddle back to the middle and calling the "playOneTurn" method again.
        for (int i = 0; i < 4; i++) {
            if (this.numberOfLives.getValue() != 0 && this.remainedBlocks.getValue() != 0) {
                paddle.setUpperLeft(paddleUpperleft);
                playOneTurn();
            } else {
                return;
            }
        }
    }

    /**
     * The playOneTurn() method returns when either there are no more balls or no more blocks.
     * the method also controls a life lost aftermath.
     */
    public void playOneTurn() {
        // running the game down (3,2,1)
        for (int i = 3; i >= 0; i--) {
            this.runner.run(new CountdownAnimation(2, i, this.sprites, 800, 800,
                    this.currentLevel));
        }
        this.running = true;
        // use our runner to run the current animation -- which is one turn of
        // the game.

        this.runner.run(this);
    }

    @Override
    public boolean shouldStop() {
        return !this.running;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        this.sprites.drawAllOn(d);
        this.sprites.notifyAllTimePassed();
        // stop the animation if "p" is pressed , continue if followed by a space.
        if (this.keyboard.isPressed("p")) {
            PauseScreen pauseScreen = new PauseScreen(this.keyboard);
            this.runner.run(new KeyPressStoppableAnimation(this.keyboard, this.keyboard.SPACE_KEY, pauseScreen));
        }

        // if there are no more balls, we will decrease the lives counter and start again .
        if (this.remainedBalls.getValue() == 0) {
            this.numberOfLives.decrease(1);
            Ball[] ballsArray = new Ball[this.currentLevel.ballsArray().length];
            List<Velocity> ballsVelocity = this.currentLevel.initialBallVelocities();
            for (int i = 0; i < this.currentLevel.ballsArray().length; i++) {
                Ball ball = new Ball(this.currentLevel.ballsStartX(), this.currentLevel.ballsStartY(),
                        5, Color.white);
                this.remainedBalls.increase(1);
                ball.setVelocity(ballsVelocity.get(i));
                ball.setColor(currentLevel.startingBallsColor());
                ballsArray[i] = ball;
                ballsArray[i].setGameEnvironment(this.environment);
                ballsArray[i].addToGame(this);
                this.running = false;
            }
        }
        // if there are no more blocks , we will increase the score by 100 and end the game.
        if (this.remainedBlocks.getValue() == 0) {
            this.score.increase(100);
            this.running = false;
        }

    }

    /**
     * returns the current lives.
     *
     * @return the lives
     */
    public int getLives() {
        return this.numberOfLives.getValue();
    }

    /**
     * returns the current blocks.
     *
     * @return the blocks
     */
    public int getremaningBlocks() {
        return this.remainedBlocks.getValue();
    }

    /**
     * returns the current score.
     *
     * @return the score
     */
    public Counter getScore() {
        return this.score;
    }

    /**
     * returns the current runner.
     *
     * @return the runner
     */
    public AnimationRunner getRunner() {
        return this.runner;
    }


}
