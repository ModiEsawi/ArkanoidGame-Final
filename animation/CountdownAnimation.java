package animation;

import biuoop.DrawSurface;
import biuoop.Sleeper;
import game.LevelInformation;
import sprites.SpriteCollection;

import java.awt.Color;
import java.util.Random;

/**
 * The Countdown animation.
 */
public class CountdownAnimation implements Animation {
    private double numOfSeconds;
    private int countFrom;
    private SpriteCollection gameScreen;
    private int screenWidth;
    private int screenHeight;
    private boolean stopSign;
    private int constantTime;
    private LevelInformation currentLevel;

    /**
     * Instantiates a new Countdown animation.
     *
     * @param numOfSeconds : the total number of seconds.
     * @param countFrom    : number to start counting down from.
     * @param gameScreen   :the game screen.
     * @param screenWidth  :the screen width.
     * @param screenHeight :the screen height.
     * @param currentLevel :the current level.
     */
    public CountdownAnimation(double numOfSeconds, int countFrom, SpriteCollection gameScreen, int screenWidth,
                              int screenHeight, LevelInformation currentLevel) {
        this.numOfSeconds = numOfSeconds;
        this.countFrom = countFrom;
        this.gameScreen = gameScreen;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.stopSign = false;
        this.constantTime = 3;
        this.currentLevel = currentLevel;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        Random random = new Random();
        int radnomColorBound = 0xFFFFFF;
        gameScreen.drawAllOn(d);
        long eachNumberTimeApperance = (long) ((this.numOfSeconds) / (this.constantTime) * (1000));
        int currentNum = this.countFrom;
        Sleeper sleeper = new biuoop.Sleeper();
        if (currentNum != 0) {
            if (currentNum == 3) { // first time.
                eachNumberTimeApperance = (long) ((this.numOfSeconds) / (this.constantTime));
            }
            d.setColor(new Color(random.nextInt(radnomColorBound))); // random colors for the countdown.
            d.drawText(this.screenWidth / 2, this.screenHeight / 2, Integer.toString(currentNum), 65);
        } else {
            this.stopSign = true;
        }
        // sleeping for
        sleeper.sleepFor((eachNumberTimeApperance));
        this.stopSign = true;
    }

    @Override
    public boolean shouldStop() {
        return this.stopSign;
    }
}
