package animation;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

import java.awt.Color;

/**
 * The type Pause screen.
 * pause the screen and display a appropriate message on the screen.
 */
public class PauseScreen implements Animation {
    private KeyboardSensor keyboard;
    private boolean stop;

    /**
     * Instantiates a new Pause screen.
     *
     * @param k the k
     */
    public PauseScreen(KeyboardSensor k) {
        this.keyboard = k;
        this.stop = false;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        // draw the "play" image on the pause screen.
        int fillStartX = 351, fillstartY = 364, fillEndX = 351, fillEndY = 241, counter = 62;
        d.setColor(Color.BLACK);
        d.drawRectangle(240, 226, 300, 150);
        d.drawRectangle(239, 227, 300, 150);
        d.setColor(Color.red);
        d.fillRectangle(240, 227, 300, 150);
        d.setColor(Color.white);
        // filling the triangle.
        while (counter != 0) {
            d.drawLine(fillStartX, fillstartY, fillEndX, fillEndY);
            fillStartX += 2;
            fillstartY--;
            fillEndX = fillStartX;
            fillEndY++;
            counter--;
        }
        d.setColor(Color.RED);
        d.drawText(229, 472, "Press space to continue", 32);
        d.setColor(Color.black);
        d.drawText(228, 471, "Press space to continue", 32);
    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }
}
