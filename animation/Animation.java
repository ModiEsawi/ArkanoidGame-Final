package animation;

import biuoop.DrawSurface;

/**
 * The Animation interface.
 */
public interface Animation {
    /**
     * Do one frame.
     *
     * @param d the d
     */
    void doOneFrame(DrawSurface d);

    /**
     * decides wither the game play should stop.
     *
     * @return true if should stop, false otherwise.
     */
    boolean shouldStop();
}
