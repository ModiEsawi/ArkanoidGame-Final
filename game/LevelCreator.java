package game;

import geometry.Velocity;
import sprites.ColorsParser;

import javax.imageio.ImageIO;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.ArrayList;
import java.io.Reader;
import java.awt.Color;
import java.awt.Image;
import java.io.IOException;

/**
 * The type Level creator.
 */
public class LevelCreator {
    private java.io.Reader blocksDef;
    private Image backgroundImg = null;
    private Color backgroundColor = null;
    private List<String> level;
    private String levelName;
    private List<Velocity> ballsVelocity;
    private int blocksStartX;
    private int blocksStartY;
    private int paddleSpeed;
    private int paddleWidth;
    private int numOfBlock;
    private int rowHeight;

    /**
     * Instantiates a new Level creator.
     *
     * @param level the level to create.
     */
    public LevelCreator(List<String> level) {
        this.level = level;
        try {
            this.setLevelDetails();
        } catch (Exception e) {
            System.out.println("problem was found with setting the levels details");
        }
    }

    /**
     * Sets the level details.
     * getting every detail there is to get from the level definition file.
     *
     * @throws Exception the exception
     */
    public void setLevelDetails() throws Exception {
        for (String currentLine : this.level) {
            if (currentLine.startsWith("ball_velocities")) { // get a list of balls velocities.
                try {
                    String velocities = currentLine.substring(16);
                    List<Integer> commasInLine = new ArrayList<>();
                    List<Integer> spacesInLine = new ArrayList<>();
                    List<Velocity> velocityList = new ArrayList<>();
                    int speed, angle;

                    for (int k = 0; k < velocities.length(); k++) { // getting the spaces in the line
                        if (velocities.charAt(k) == ' ') {
                            spacesInLine.add(k);
                        } else if (velocities.charAt(k) == ',') {
                            commasInLine.add(k);
                        }
                    }
                    spacesInLine.add(velocities.length());
                    speed = Integer.parseInt(velocities.substring(commasInLine.get(0) + 1, spacesInLine.get(0)));
                    angle = Integer.parseInt(velocities.substring(0, commasInLine.get(0)));
                    velocityList.add(Velocity.fromAngleAndSpeed(angle, speed));
                    for (int j = 1; j < commasInLine.size(); j++) { //get velocities.
                        speed = Integer.parseInt(velocities.substring(commasInLine.get(j) + 1, spacesInLine.get(j)));
                        angle = Integer.parseInt(velocities.substring(spacesInLine.get(j - 1) + 1,
                                commasInLine.get(j)));
                        velocityList.add(Velocity.fromAngleAndSpeed(angle, speed));
                    }
                    this.ballsVelocity = velocityList;
                } catch (Exception e) {
                    throw new Exception("invalid value in ball velocity");
                }
            }
            if (currentLine.startsWith("level_name")) {
                try {
                    this.levelName = currentLine.substring(11);
                } catch (Exception e) {
                    throw new Exception("invalid level name!");
                }
                continue;
            }
            if (currentLine.startsWith("row_height")) { //get row height value.
                try {
                    this.rowHeight = Integer.parseInt(currentLine.substring(11));
                    if (this.rowHeight <= 0) {
                        throw new Exception("row height must be a positive integer value");
                    }
                } catch (Exception e) {
                    throw new Exception("invalid value of row_height");
                }
                continue;
            }
            if (currentLine.startsWith("background")) {
                if (currentLine.contains("color")) {
                    try { //invalid format if color
                        ColorsParser colorsParser = new ColorsParser();
                        String colorString = currentLine.substring(17, currentLine.length() - 1);
                        this.backgroundColor = colorsParser.colorFromString(colorString);
                        if (backgroundColor == null) {
                            throw new Exception("invalid back ground color");
                        }
                    } catch (Exception e) {
                        throw new Exception("invalid value of background color");
                    }
                    continue;
                } else if (currentLine.contains("image")) { //if background is an image
                    try {
                        String imageString = currentLine.substring(17, currentLine.length() - 1);
                        Image img = ImageIO.read(ClassLoader.getSystemClassLoader()
                                .getResourceAsStream(imageString));
                        this.backgroundImg = img;
                    } catch (IOException e) {
                        throw new Exception("invalid background image value or image not found!");
                    }
                }
            }
            if (currentLine.startsWith("blocks_start_x")) {
                try {
                    this.blocksStartX = Integer.parseInt(currentLine.substring(15));
                    if (this.blocksStartX < 0) {
                        throw new Exception("invalid x coordinate");
                    }
                } catch (Exception e) {
                    throw new Exception("x must be positive");
                }
                continue;
            }
            if (currentLine.startsWith("blocks_start_y")) {
                try {
                    this.blocksStartY = Integer.parseInt(currentLine.substring(15));
                    if (this.blocksStartY < 0) {
                        throw new Exception("invalid y coordinate");
                    }
                } catch (Exception e) {
                    throw new Exception("y must be positive");
                }
                continue;
            }
            if (currentLine.startsWith("num_blocks")) {
                try {

                    this.numOfBlock = Integer.parseInt(currentLine.substring(11));
                    if (this.numOfBlock <= 0) {
                        throw new Exception("block's num must be a positive integer!");
                    }
                } catch (Exception e) {
                    throw new Exception("invalid block's num value");
                }
                continue;
            }
            if (currentLine.startsWith("paddle_speed")) { //get paddle speed
                try {
                    this.paddleSpeed = Integer.parseInt(currentLine.substring(13));
                    if (this.paddleSpeed < 0) {
                        throw new Exception("paddle speed must be a positive integer!");
                    }
                } catch (Exception e) {
                    throw new Exception("invalid paddle speed value");
                }
                continue;
            }
            if (currentLine.startsWith("paddle_width")) { //get paddle width
                try {
                    this.paddleWidth = Integer.parseInt(currentLine.substring(13));
                    if (this.paddleWidth <= 0) {
                        throw new Exception("paddle width must be a positive integer!");
                    }
                } catch (Exception e) {
                    throw new Exception("invalid paddle width value");
                }
            }
            if (currentLine.startsWith("block_definitions")) { //get blocks def file.
                String blockDefString = (currentLine.substring(18));
                String[] splitedLine = blockDefString.trim().split("/");
                String definer = splitedLine[0] + "/" + splitedLine[1];
                BufferedReader br;
                try {
                    br = new BufferedReader(new InputStreamReader(ClassLoader.
                            getSystemClassLoader().getResourceAsStream(definer)));
                    this.blocksDef = br;
                } catch (Exception e) {
                    throw new Exception("problem in block_definitions file");
                }
            }
        }
    }

    /**
     * returns the balls velocity.
     *
     * @return balls velocities.
     */
    public List<Velocity> getBallsVelocity() {
        return this.ballsVelocity;
    }

    /**
     * returns the row height.
     *
     * @return row height.
     */
    public int getRowHeight() {
        return rowHeight;
    }

    /**
     * returns the paddle width.
     *
     * @return paddle width.
     */
    public int getPaddleWidth() {
        return this.paddleWidth;
    }

    /**
     * returns the level name.
     *
     * @return level name.
     */
    public String getLevelName() {
        return this.levelName;
    }

    /**
     * returns the background color.
     *
     * @return back ground color if there. if not , return null.
     */
    public Color getBackgroundColor() {
        return backgroundColor;
    }

    /**
     * returns the paddle speed.
     *
     * @return paddle speed.
     */
    public int getPaddleSpeed() {
        return this.paddleSpeed;
    }

    /**
     * returns the background img.
     *
     * @return background image if there, null if not.
     */
    public Image getBackgroundImg() {
        return this.backgroundImg;
    }

    /**
     * returns the blocks def.
     *
     * @return block def file.
     */
    public Reader getBlocksDef() {
        return this.blocksDef;
    }

    /**
     * returns the blocks start x.
     *
     * @return block start x-coordinate.
     */
    public int getBlocksStartX() {
        return this.blocksStartX;
    }

    /**
     * returns the num of block.
     *
     * @return num of blocks should remove to pass the level.
     */
    public int getNumOfBlock() {
        return this.numOfBlock;
    }

    /**
     * returns the blocks start y.
     *
     * @return block start y-coordinate.
     */
    public int getBlocksStartY() {
        return this.blocksStartY;
    }
}
