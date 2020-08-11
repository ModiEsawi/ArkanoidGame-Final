package animation;

import biuoop.DrawSurface;
import sprites.Sprite;

import java.awt.Color;

/**
 * The Back ground drawer.
 * draws certain background according to each level.
 */
public class BackGroundDrawer implements Sprite {
    private int flag;

    /**
     * Instantiates a new Back ground drawer.
     *
     * @param flag : determines which background to draw according to the flag.
     */
    public BackGroundDrawer(int flag) {
        this.flag = flag;
    }

    @Override
    public void drawOn(DrawSurface surface) {
        if (this.flag == 3) {
            surface.setColor(new Color(0xFFC3F0F6, true));
            surface.setColor(Color.BLACK);
            surface.fillOval(120, 120, 550, 300);
            surface.fillRectangle(120, 423, 530, 50);

            //draw Super WASH
            for (int i = 0; i < 3; i++) {
                surface.setColor(new Color(0xFF3496BA, true));
                surface.drawText(170 + (i / 2), 250 + (i / 2), "S", 200);
                surface.drawText(300 + (i / 2), 250 + (i / 2), "u", 140);
                surface.drawText(380 + (i / 2), 250 + (i / 2), "p", 140);
                surface.drawText(460 + (i / 3), 250 + (i / 3), "e", 140);
                surface.drawText(535 + (i / 3), 250 + (i / 3), "r", 130);
                surface.setColor(Color.WHITE);
                surface.drawText(230 + (i / 3), 360 + (i / 3), "W", 100);
                surface.drawText(325 + (i / 3), 360 + (i / 3), "A", 100);
                surface.drawText(390 + (i / 3), 360 + (i / 3), "S", 100);
                surface.drawText(460 + (i / 3), 360 + (i / 3), "H", 100);
            }
            surface.drawText(250, 450, "American Car Wash", 30);
            surface.drawText(350, 500, "TAXI", 20);
        } else if (flag == 4) {
            //board
            surface.setColor(new Color(0xFF2BFF6C, true));
            surface.fillCircle(400, 250, 150); //head
            surface.fillRectangle(250, 280, 300, 320); //body
            surface.setColor(Color.BLACK);
            surface.fillRectangle(250, 270, 300, 20);
            surface.setColor(Color.white);
            surface.fillCircle(330, 190, 15); //left eye
            surface.fillCircle(470, 190, 15); //right eye
            //left hand
            surface.setColor(new Color(0xFF2BFF6C, true));
            surface.fillOval(140, 290, 110, 250);
            surface.setColor(Color.BLACK);
            surface.fillRectangle(140, 290, 20, 250);
            surface.fillRectangle(230, 290, 20, 250);
            //right hand
            surface.setColor(new Color(0xFF2BFF6C, true));
            surface.fillOval(550, 290, 110, 250);
            surface.setColor(Color.BLACK);
            surface.fillRectangle(550, 290, 20, 250);
            surface.fillRectangle(640, 290, 20, 250);
            //left leg
            surface.setColor(new Color(0xFF2BFF6C, true));
            surface.fillOval(300, 550, 80, 150);
            surface.setColor(Color.BLACK);
            surface.fillRectangle(300, 600, 10, 100);
            surface.fillRectangle(370, 600, 10, 100);
            //right leg
            surface.setColor(new Color(0xFF2BFF6C, true));
            surface.fillOval(430, 550, 80, 150);
            surface.setColor(Color.BLACK);
            surface.fillRectangle(430, 600, 10, 100);
            surface.fillRectangle(500, 600, 10, 100);
            //ears
            surface.setColor(new Color(0xFF2BFF6C, true));
            for (int i = 0; i <= 40; i++) {
                surface.drawLine(470, 120 + (i / 2), 485, 80 + (i / 2));
                surface.drawLine(330, 120 + (i / 2), 315, 80 + (i / 2));
            }
        }

    }

    /**
     * the time passed function.
     * do nothing.
     */
    public void timePassed() {

    }
}


