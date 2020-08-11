package sprites;

import game.GameLevel;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import geometry.Velocity;
import listeners.HitListener;
import listeners.HitNotifier;
import biuoop.DrawSurface;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * A sprites.Block object, which has a rectangular shape a color , and a hit counter.
 *
 * @author : mohammed Elesawi.
 */
public class Block implements Collidable, Sprite, HitNotifier {
    private List<HitListener> hitListeners;
    private Rectangle shape;
    private Image blockImg = null;
    private java.awt.Color color;
    private int hitsCounter;
    private Map<Integer, String> map;
    private Color stroke;


    /**
     * the constructor.
     *
     * @param rectangle   : the block's shape.
     * @param color       : the blocks color.
     * @param hitsCounter : marks the number of hits needed to get an "X" mark.
     */
    public Block(Rectangle rectangle, java.awt.Color color, int hitsCounter) {
        this.shape = rectangle;
        this.color = color;
        this.hitsCounter = hitsCounter;
        this.hitListeners = new ArrayList<>();
    }

    /**
     * Instantiates a new Block.
     *
     * @param rec         the rec
     * @param stroke      the stroke
     * @param map         the map
     * @param hitsCounter the hits counter
     */
    public Block(Rectangle rec, Color stroke, Map<Integer, String> map, int hitsCounter) {
        this.shape = rec;
        this.stroke = stroke;
        this.hitListeners = new ArrayList<>();
        this.map = map;
        this.hitsCounter = hitsCounter;
    }

    /**
     * Instantiates a new Block.
     *
     * @param rectangle   the rectangle
     * @param map         the map
     * @param stroke      the stroke
     * @param hitsCounter the hits counter
     */
    public Block(Rectangle rectangle, Map<Integer, String> map, Color stroke, int hitsCounter) {
        this.shape = rectangle;
        this.map = map;
        this.stroke = stroke;
        this.hitsCounter = hitsCounter;
        this.hitListeners = new ArrayList<>();
        ColorsParser c = new ColorsParser();
        this.color = c.colorFromString(this.map.get(0));
    }

    /**
     * Instantiates a new Block.
     *
     * @param rec the rec
     * @param img the img
     */
    public Block(Rectangle rec, Image img) {
        this.shape = rec;
        this.stroke = Color.black;
        this.hitListeners = new ArrayList<>();
        this.map = null;
        this.blockImg = img;
        this.color = null;
    }

    /**
     * @return returning the blocks shape.
     */
    public Rectangle getCollisionRectangle() {
        return this.shape;
    }

    /**
     * Notify the block that we collided with it at a certain collisionPoint with a given velocity , if collided with
     * it from below or above , then we will turn the vertical direction , if collided with it from left or right ,
     * then we will turn the horizontal direction , and if collided with it from the it's angles , then we will turn
     * the vertical and horizontal directions.
     *
     * @param collisionPoint  : the point where the object has hit the ball.
     * @param currentVelocity : the object's current velocity.
     * @param hitter          : the ball that is currently hitting the block.
     * @return the new velocity depending on where the object has hit the block.
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        //decrease the number on the block when collision occurs.
        if (this.hitsCounter >= 1) {
            this.hitsCounter--;
        }
        // blocks lines.
        Line upLine = this.shape.getUpLine();
        Line lowLine = this.shape.getLowLine();
        Line leftLine = this.shape.getLeftLine();
        Line rightLine = this.shape.getRightLine();
        int radnomColorBound = 0xFFFFFF;
        Random random = new Random();
        double updatedDx = 0, updatedDy = 0;
        double collisionX = collisionPoint.getX(), collisionY = collisionPoint.getY();
        // changing the blocks color according to the fill map
        if (this.hitsCounter >= 1) {
            this.setBackground();
        }
        // if  we have hit the block's angles.
        if (collisionX == leftLine.start().getX() && collisionY == upLine.start().getY()
                || collisionX == leftLine.start().getX() && collisionY == lowLine.start().getY()
                || collisionX == rightLine.start().getX() && collisionY == upLine.start().getY()
                || collisionX == rightLine.start().getX() && collisionY == lowLine.start().getY()) {

            updatedDx = currentVelocity.getdx() * -1;
            updatedDy = currentVelocity.getdY() * -1;
            hitter.setColor(new Color(random.nextInt(radnomColorBound)));

            // if we have hit the block from above or below.
        } else if (collisionY == upLine.start().getY() || collisionY == lowLine.start().getY()) {
            updatedDx = currentVelocity.getdx();
            updatedDy = -1 * (currentVelocity.getdY());
            hitter.setColor(new Color(random.nextInt(radnomColorBound)));
            // if we have hit the block from right or left.
        } else if (collisionX == rightLine.start().getX() || collisionX == leftLine.start().getX()) {
            updatedDx = -1 * (currentVelocity.getdx());
            updatedDy = currentVelocity.getdY();
            hitter.setColor(new Color(random.nextInt(radnomColorBound)));
        }


        this.notifyHit(hitter);

        return new Velocity(updatedDx, updatedDy);

    }

    /**
     * drawing the block on a given DrawSurface.
     *
     * @param surface : the surface to draw the block on.
     */

    public void drawOn(DrawSurface surface) {
        if (this.color != null) {
            surface.setColor(this.color);
        }
        surface.fillRectangle((int) this.shape.getUpperLeft().getX(), (int) this.shape.getUpperLeft().getY(),
                (int) this.shape.getWidth(), (int) this.shape.getHeight());

        //drawing the frame of the surrounding blocks
        surface.setColor(Color.black);
        surface.drawRectangle((int) this.shape.getUpperLeft().getX(), (int) this.shape.getUpperLeft().getY(),
                (int) this.shape.getWidth(), (int) this.shape.getHeight());


        //drawing the hitsCounter on the middle of the block

        Point mid = this.shape.getLeftLine().middle();
        int y = (int) mid.getY();
        int x = (int) mid.getX() + (int) this.shape.getWidth() / 2;
        Point firstLevelUpperLeft = new Point(386, 170);
        // drawing meaningful text on the life and "Bonusdeath" blocks
        if (this.shape.getLeftLine().start().getX() == 695 && this.shape.getLeftLine().start().getY() == 390) {
            surface.setColor(Color.white);
            surface.drawText(x - 30, y + 5, "New Ball!", 17);
            return;
        } else if (this.shape.getLeftLine().start().getX() == 30 && this.shape.getLeftLine().start().getY() == 390) {
            surface.setColor(Color.white);
            surface.drawText(x - 30, y, "Ball Is ", 15);
            surface.drawText(x - 30, y + 15, "Gone!", 15);
            return;
        }
        if (this.blockImg != null) {
            surface.drawImage((int) this.shape.getUpperLeft().getX(), (int) this.shape.getUpperLeft().getY(),
                    this.blockImg);
        }
        if (this.hitsCounter == -1 || this.shape.getUpperLeft().equals(firstLevelUpperLeft) || this.blockImg != null) {
            return;
        }
        if (this.color != null) {
            surface.setColor(this.color);
            surface.fillRectangle((int) this.shape.getUpperLeft().getX(), (int) this.shape.getUpperLeft().getY(),
                    (int) this.shape.getWidth(), (int) this.shape.getHeight());

        }
        if (this.stroke != null) {
            surface.setColor(this.stroke);
            surface.drawRectangle((int) this.shape.getUpperLeft().getX(), (int) this.shape.getUpperLeft().getY(),
                    (int) this.shape.getWidth(), (int) this.shape.getHeight());
        }

    }


    /**
     * adding the block to specified game , (the block is also a collidable and a sprite).
     *
     * @param g : our "Arkanoid" game.
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
        g.addCollidable(this);
    }

    /**
     * removing the block from specified game , (the block is also a collidable and a sprite).
     *
     * @param game : our "Arkanoid" game.
     */
    public void removeFromGame(GameLevel game) {
        game.removeCollidable(this);
        game.removeSprite(this);
    }

    /**
     * adding a hitListener the the listeners list.
     *
     * @param hl : the listener the will be added to the listeners list.
     */
    public void addHitListener(HitListener hl) {
        this.hitListeners.add(hl);
    }

    /**
     * removing the hitListener from the listeners list.
     *
     * @param hl : the listener the will be removed from the listeners list.
     */

    public void removeHitListener(HitListener hl) {
        this.hitListeners.remove(hl);
    }

    /**
     * Notifying all listeners about a hit event , (we made a copy of te hitListeners before iterating over them to
     * avoid exception errors.
     *
     * @param hitter : the ball the made the hit.
     */
    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }

    /**
     * returns the blocks current hitPoints.
     *
     * @return : the blocks current hitPoints.
     */
    public int getHitPoints() {
        return this.hitsCounter;
    }

    /**
     * notify the block that time has passed.
     */

    public void timePassed() {
    }

    /**
     * Gets width.
     *
     * @return the width
     */
    public double getWidth() {
        return this.shape.getWidth();
    }

    /**
     * Sets hit points.
     *
     * @param hitPoints the hit points
     */
    public void setHitPoints(int hitPoints) {
        this.hitsCounter = hitPoints;
    }

    /**
     * Sets background.
     */
    public void setBackground() {
        if (this.map != null) { // each hit points result specific background of the block.
            if (this.map.containsKey(this.hitsCounter)) { // specific hit points.
                this.getBackgroundColor(this.map.get(this.hitsCounter));  // check if this strings define a color
            } else { //default fill value.
                this.getBackgroundColor(this.map.get(0));
            }
        }
    }

    /**
     * getting the background color.
     *
     * @param givenString : string.
     * @return
     */
    public void getBackgroundColor(String givenString) {
        try {
            ColorsParser givenColor = new ColorsParser();
            Color newColor = givenColor.colorFromString(givenString);
            if (newColor != null) {
                this.color = newColor;
                this.blockImg = null;
            } else {
                Image img = ImageIO.read(ClassLoader.getSystemClassLoader()
                        .getResourceAsStream(givenString));
                this.blockImg = img;
            }
        } catch (Exception e) { //if not define a color , define an image.
            Image img;
            try {
                img = ImageIO.read(ClassLoader.getSystemClassLoader().getResourceAsStream(givenString));
                this.blockImg = img;
            } catch (Exception ex) {
                this.blockImg = null;
            }
        }
    }
}

