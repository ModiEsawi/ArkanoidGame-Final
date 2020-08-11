package geometry;

import biuoop.DrawSurface;
import sprites.Sprite;

import java.awt.Color;

/**
 * A circle class.
 * draws a circle.
 */
public class Circles implements Sprite {

    private int centerX;
    private int centerY;
    private int radius;
    private Color circlesColor;
    private String fillingStatus;

    /**
     * Constructor.
     *
     * @param centerX       : centerX-coordinate of circle's center point.
     * @param centerY       : centerY-coordinate of circle's center point.
     * @param circleRadius  : radius.
     * @param circlesColor  : circle circlesColor.
     * @param fillingStatus : decides wither to fill or not.
     */
    public Circles(double centerX, double centerY, int circleRadius, Color circlesColor, String fillingStatus) {
        this.centerX = (int) centerX;
        this.centerY = (int) centerY;
        this.fillingStatus = fillingStatus;
        this.radius = circleRadius;
        this.circlesColor = circlesColor;
    }

    /**
     * do nothing.
     */

    public void timePassed() {

    }


    /**
     * draws a circle in the given surface.
     *
     * @param d : the given surface.
     */
    public void drawOn(DrawSurface d) {
        d.setColor(this.circlesColor);
        if (this.fillingStatus.equals("dont fill")) { // fill the circle
            d.drawCircle(this.centerX, this.centerY, this.radius);
        } else if (this.fillingStatus.equals("Fill")) { // dont  fill circle
            d.fillCircle(this.centerX, this.centerY, this.radius);
        }
    }
}