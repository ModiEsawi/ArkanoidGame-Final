package sprites;

/**
 * The interface - Block creator.
 */
public interface BlockCreator {
    /**
     * Create block.
     * Create a block at the specified location.
     *
     * @param xpos the x - position.
     * @param ypos the y - position.
     * @return the new - created block
     */

    Block create(int xpos, int ypos);
}