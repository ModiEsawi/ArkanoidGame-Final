package game;

import geometry.Point;
import geometry.Velocity;
import sprites.Ball;
import sprites.Block;
import sprites.Sprite;


import java.awt.Color;
import java.awt.Image;
import java.util.List;

/**
 * The interface Level information.
 */
public interface LevelInformation {
    /**
     * Number of balls int.
     *
     * @return the int
     */
    int numberOfBalls();

    /**
     * Initial ball velocities list.
     *
     * @return the list
     */
// The initial velocity of each ball
    // Note that initialBallVelocities().size() == numberOfBalls()
    List<Velocity> initialBallVelocities();

    /**
     * Paddle speed int.
     *
     * @return the int
     */
    int paddleSpeed();

    /**
     * Paddle width int.
     *
     * @return the int
     */
    int paddleWidth();

    /**
     * Level name string.
     *
     * @return the string
     */
// the level name will be displayed at the top of the screen.
    String levelName();


    /**
     * Blocks list.
     *
     * @return the list
     */
// The Blocks that make up this level, each block contains
    // its size, color and location.
    List<Block> blocks();

    /**
     * Number of blocks to remove int.
     *
     * @return the int
     */
// Number of levels that should be removed
    // before the level is considered to be "cleared".
    // This number should be <= blocks.size();
    int numberOfBlocksToRemove();

    /**
     * Initializing every level details.
     */
    void initialize();

    /**
     * the balls of every level.
     *
     * @return array of the balls in every level.
     */
    Ball[] ballsArray();

    /**
     * all the sprites in each level.
     *
     * @return the sprites
     */
    List<Sprite> getSprites();

    /**
     * setting the upper left point of the paddle.
     *
     * @return the new upper left point of the paddle.
     */
    Point paddleUpperLeft();

    /**
     * setting the height of the paddle.
     *
     * @return the height of the paddle.
     */
    int paddleHeight();

    /**
     * the starting point of each ball X coordinate.
     *
     * @return the starting X coordinate of the ball.
     */
    int ballsStartX();

    /**
     * the starting point of each ball Y coordinate.
     *
     * @return the starting Y coordinate of the ball.
     */
    int ballsStartY();

    /**
     * determining flag of the levels.
     *
     * @return the flag
     */
    int flag();

    /**
     * the starting color of the ball.
     *
     * @return the starting color of the ball.
     */
    Color startingBallsColor();

    /**
     * Each balls velocity .
     *
     * @return the velocity of each ball.
     */
    Velocity eachBallsVelocity();

    /**
     * Gets background color.
     *
     * @return the background color
     */
     Color getBackgroundColor();

    /**
     * Gets background image.
     *
     * @return the background img
     */
     Image getBackgroundImg();
}
