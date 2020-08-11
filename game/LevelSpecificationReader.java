package game;

import geometry.Point;
import geometry.Velocity;
import sprites.BlocksDefinitionReader;
import sprites.Sprite;
import sprites.Block;
import sprites.Ball;
import sprites.BlocksFromSymbolsFactory;

import java.awt.Color;
import java.awt.Image;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Level specification reader.
 */
public class LevelSpecificationReader {
    private List<List<String>> blocksLayOut;

    /**
     * Gets the deatails of the level in a way of a level information list.
     *
     * @param reader the reader.
     * @return the list of level information.
     * @throws Exception the exception.
     */
    public List<LevelInformation> getdetails(java.io.Reader reader) throws Exception {
        List<LevelInformation> levelsInformation = new ArrayList<>();

        List<List<String>> levels = this.levelsSplitter(reader);
        for (int i = 0; i < levels.size(); i++) { // getting to each level.
            LevelCreator specification = new LevelCreator(levels.get(i)); // from strings to values.
            BlocksDefinitionReader blocksDefinitionReader = new BlocksDefinitionReader(); // reading the blocks def
            BlocksFromSymbolsFactory fromSymbolsFactory = blocksDefinitionReader.blocksDetails(specification.
                    getBlocksDef()); // factory with maps according to symbols.
            LevelInformation levelInfo = this.getLevelInfo(specification, fromSymbolsFactory, i);
            levelsInformation.add(levelInfo); // adding to the list of levels.
        }
        return levelsInformation; //return the list of the levels info.
    }

    /**
     * creating a level information list from the levels.
     *
     * @param level    : a level creator.
     * @param factoryB : a BlocksFromSymbolsFactory object.
     * @param index    : an index.
     * @return a level information.
     */
    private LevelInformation getLevelInfo(LevelCreator level, BlocksFromSymbolsFactory factoryB, int index) {
        List<Block> blocks = new ArrayList<>();
        String line;
        int blockX = level.getBlocksStartX();
        int blockY = level.getBlocksStartY();
        int rowHeight = level.getRowHeight();
        int rowsCounter = 0;
        boolean rowsFlag = false;
        for (int j = 0; j < this.blocksLayOut.get(index).size(); j++) { //reading blocksLayout

            line = this.blocksLayOut.get(index).get(j);
            if (line.isEmpty() || line.startsWith("#")) { //empty or note lines.
                continue;
            } else {
                if (rowsFlag) {
                    rowsCounter++;
                } else {
                    rowsFlag = true;
                }
            }
            if (factoryB.isSpaceSymbol(line)) { // if its a space symbol , continue
                continue;
            }
            char[] charArr = line.toCharArray();
            for (char symbol : charArr) {
                if (factoryB.isSpaceSymbol(symbol + "")) {
                    blockX = blockX + factoryB.getSpaceWidth(symbol + "");
                } else if (factoryB.isBlockSymbol(symbol + "")) {
                    Block newBlock = factoryB.getBlock(symbol + "", blockX, blockY + (rowHeight * rowsCounter));
                    blocks.add(newBlock);
                    blockX += newBlock.getWidth();
                }
            }
            blockX = level.getBlocksStartX();
        }


        //get level information.
        LevelInformation information = new LevelInformation() {


            @Override
            public int numberOfBalls() {
                return 0;
            }

            @Override
            public List<Velocity> initialBallVelocities() {
                return level.getBallsVelocity();
            }

            @Override
            public int paddleSpeed() {
                return level.getPaddleSpeed();
            }

            @Override
            public int paddleWidth() {
                return level.getPaddleWidth();
            }

            @Override
            public String levelName() {
                return level.getLevelName();
            }


            @Override
            public Color getBackgroundColor() {
                return level.getBackgroundColor();
            }

            @Override
            public Image getBackgroundImg() {
                return level.getBackgroundImg();
            }

            @Override
            public List<Block> blocks() {
                return blocks;
            }

            @Override
            public int numberOfBlocksToRemove() {
                return level.getNumOfBlock();
            }

            @Override
            public void initialize() {

            }

            @Override
            public Ball[] ballsArray() {
                Ball[] ballsArray = new Ball[level.getBallsVelocity().size()];
                List<Velocity> ballsVelocity = level.getBallsVelocity();
                for (int i = 0; i < level.getBallsVelocity().size(); i++) {
                    Ball ball = new Ball(400, 480, 5, Color.white);
                    ball.setVelocity(ballsVelocity.get(i));
                    ball.setColor(Color.red);
                    ballsArray[i] = ball;
                }
                return ballsArray;
            }

            @Override
            public List<Sprite> getSprites() {
                List<Sprite> sprites = new ArrayList<>();
                return sprites;
            }

            @Override
            public Point paddleUpperLeft() {
                return new Point((400 - (this.paddleWidth() >> 1)), 505);
            }

            @Override
            public int paddleHeight() {
                return 10;
            }

            @Override
            public int ballsStartX() {
                return (400 + (this.paddleWidth() >> 1));
            }

            @Override
            public int ballsStartY() {
                return 480;
            }

            @Override
            public int flag() {
                return 0;
            }

            @Override
            public Color startingBallsColor() {
                return Color.white;
            }

            @Override
            public Velocity eachBallsVelocity() {
                return null;
            }

        };
        return information;
    }

    /**
     * Levels splitter list.
     * splitting the levels from the file so we can work on each level separately.
     *
     * @param reader the reader.
     * @return the list of list of levels.
     * @throws Exception the exception.
     */
    public List<List<String>> levelsSplitter(java.io.Reader reader) throws Exception {
        BufferedReader bufferedReader = (BufferedReader) reader;
        String line;
        int fourChecks = 0;
        List<List<String>> levels = new ArrayList<>();
        List<String> level = new ArrayList<>();
        List<String> currentBlockLayOut = new ArrayList<>();
        boolean levelBool = false;
        boolean blocksBool = false;
        this.blocksLayOut = new ArrayList<>();
        try {
            while ((line = bufferedReader.readLine()) != null) {
                if (line.startsWith("#") || line.startsWith(" ")) { // empty lines and comments
                    continue;
                }
                if (line.equals("START_LEVEL")) {
                    levelBool = true;
                    fourChecks++;
                    continue;
                }
                if (levelBool) {
                    level.add(line);
                }
                if (line.equals("START_BLOCKS") && !blocksBool) {
                    blocksBool = true;
                    fourChecks++;
                    continue;
                }
                if (blocksBool) {
                    currentBlockLayOut.add(line);
                }
                if (line.equals("END_BLOCKS")) {
                    blocksBool = false;
                    blocksLayOut.add(currentBlockLayOut);
                    fourChecks++;
                }
                if (line.equals("END_LEVEL")) {
                    levelBool = false;
                    fourChecks++;
                    if (fourChecks != 4) {
                        throw new Exception("levels definition format is wrong!");
                    }
                    fourChecks = 0;
                    levels.add(level);
                    level = new ArrayList<>();
                    currentBlockLayOut = new ArrayList<>();
                }
            }
        } catch (Exception e) {
            throw new Exception("problems were found during reading the levels definition file");
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (Exception e) {
                    throw new Exception("problems were found while closing the levels definition file");
                }
            }
        }
        return levels;
    }
}
