package animation;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import sprites.Counter;

import java.awt.Color;

/**
 * The End screen.
 */
public class EndScreen implements Animation {
    private KeyboardSensor keyboard;
    private boolean stop;
    private Counter finalScore;
    private int endGameStatus;

    /**
     * Instantiates a new End screen.
     *
     * @param keyboard      :   the keyboard.
     * @param finalScore    :  the final score.
     * @param endGameStatus : the end game status to determine the next moves.
     */
    public EndScreen(KeyboardSensor keyboard, Counter finalScore, int endGameStatus) {
        this.keyboard = keyboard;
        this.stop = false;
        this.finalScore = finalScore;
        this.endGameStatus = endGameStatus;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        d.setColor(Color.BLACK);
        int scoreValue = this.finalScore.getValue();
        if (this.endGameStatus == 1) {
            d.drawText(180, 272, "You Win! Your score is " + scoreValue + ".", 32);
        } else {
            d.drawText(180, 272, "Game Over. Your score is " + scoreValue + ".", 32);
        }
        d.setColor(Color.RED);
        d.drawText(229, 472, "Press space to exit", 32);
        d.setColor(Color.black);
        d.drawText(228, 471, "Press space to exit", 32);
        if (this.keyboard.isPressed(KeyboardSensor.SPACE_KEY)) {
            this.stop = true;
        }
    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }
}

