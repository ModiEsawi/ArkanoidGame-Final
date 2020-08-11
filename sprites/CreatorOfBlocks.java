package sprites;

import geometry.Point;
import geometry.Rectangle;

import java.awt.Color;
import java.util.Map;

/**
 * The type Creator of blocks.
 */
public class CreatorOfBlocks implements BlockCreator {
    private Map<Integer, String> map;
    private Color stroke;
    private int blockHeight;
    private int blockWidth;
    private int blockHits;

    /**
     * Instantiates a new Creator of blocks.
     *
     * @param map         the map of "fills".
     * @param stroke      the blocks stroke.
     * @param blockHeight the blocks height.
     * @param blockWidth  the blocks width.
     * @param blockHits   the blocks hits.
     */
    public CreatorOfBlocks(Map<Integer, String> map, Color stroke, int blockHeight, int blockWidth, int blockHits) {
        this.map = map;
        this.stroke = stroke;
        this.blockHeight = blockHeight;
        this.blockWidth = blockWidth;
        this.blockHits = blockHits;
    }

    @Override
    public Block create(int xpos, int ypos) {
        Block newBlock = new Block(new Rectangle(new Point(xpos, ypos), this.blockWidth, this.blockHeight),
                this.stroke, this.map, this.blockHits);

        newBlock.setBackground();

        return newBlock; //return the new block.
    }
}
