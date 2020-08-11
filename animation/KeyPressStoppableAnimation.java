package animation;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

/**
 * The Key press stoppable animation.
 * wraps an existing animation and add a "waiting-for-key" behavior to it.
 */
public class KeyPressStoppableAnimation implements Animation {

    private KeyboardSensor keyBoard;
    private String key;
    private Animation animation;
    private Boolean stop;
    private Boolean isAlreadyPressed;

    /**
     * Instantiates a new Key press stoppable animation.
     *
     * @param keyBoard  :the key board
     * @param key       :the string that controls the animation movement.
     * @param animation :the animation
     */
    public KeyPressStoppableAnimation(KeyboardSensor keyBoard, String key, Animation animation) {
        this.keyBoard = keyBoard;
        this.key = key;
        this.animation = animation;
        this.stop = false;
        this.isAlreadyPressed = true;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        this.animation.doOneFrame(d); // draws the animation
        if (this.keyBoard.isPressed(this.key) && !this.isAlreadyPressed) { // checks if the specific key is pressed.
            this.stop = true;
        }
        if (!this.keyBoard.isPressed(this.key)) {
            this.isAlreadyPressed = false;
        }
    }

    @Override
    public boolean shouldStop() {
        if (this.stop) {
            this.stop = false;
            return true;
        }
        return false;
    }
}
