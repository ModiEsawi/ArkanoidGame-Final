package geometry;
import biuoop.DrawSurface;
import sprites.Sprite;
import java.awt.Color;

/**
 * The oval class.
 * draws the oval.
 */

public class Oval implements Sprite {

    private int x;
    private int y;
    private int width;
    private int height;
    private Color color;

    /**
     * @param x      : x-coordinate of the center point.
     * @param y      : y-coordinate of the center point.
     * @param width  : oval width.
     * @param height : oval height.
     * @param color  : oval color.
     */
    public Oval(double x, double y, int width, int height, Color color) {
        this.x = (int) x;
        this.y = (int) y;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    /**
     * draws the oval in given surface.
     *
     * @param d : given surface
     */
    public void drawOn(DrawSurface d) {
        d.setColor(this.color);
        d.fillOval(this.x, this.y, this.width, this.height);
        d.setColor(Color.black);
    }

    /**
     * nothing to do.
     */
    public void timePassed() {

    }
}
