package game;

import animation.AnimationRunner;
import animation.EndScreen;
import animation.HighScoresAnimation;
import animation.KeyPressStoppableAnimation;
import biuoop.DialogManager;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import sprites.Counter;

import java.io.File;
import java.io.IOException;
import java.util.List;


/**
 * the GameFlow class
 * moving from one level to the next level in the game according to the given arguments .
 */


public class GameFlow {
    private AnimationRunner animationRunner;
    private KeyboardSensor keyBoard;
    private GUI gui;
    private Counter score;
    private Counter numberOfLives;
    private HighScoresTable scoresTable;

    /**
     * Constructor.
     *
     * @param animationRunner :the animation runner.
     * @param keyBoard        : keyboard sensor.
     * @param gui             : the screen to display the game on.
     * @param scoresTable     : the table that shows the games high scores.
     */

    public GameFlow(AnimationRunner animationRunner, KeyboardSensor keyBoard, GUI gui, HighScoresTable scoresTable) {
        this.animationRunner = animationRunner;
        this.keyBoard = keyBoard;
        this.gui = gui;
        this.score = new Counter();
        this.scoresTable = scoresTable;
        this.numberOfLives = new Counter();
        numberOfLives.increase(7);
    }

    /**
     * the method used to run the levels.
     *
     * @param levels : all the levels according to the arguments.
     */
    public void runLevels(List<LevelInformation> levels) {

        for (int i = 0; i < levels.size(); i++) { // Levels runner
            GameLevel level = new GameLevel(this.animationRunner, this.keyBoard, levels.get(i), score, numberOfLives);
            levels.get(i).initialize();
            level.initialize();
            level.run();
            AnimationRunner runner = level.getRunner();
            EndScreen winningGameEndScreen = new EndScreen(this.keyBoard, level.getScore(), 1);
            EndScreen losingGameEndScreen = new EndScreen(this.keyBoard, level.getScore(), 0);
            int blocksNumber = level.getremaningBlocks();
            while (level.getLives() != 0 && level.getremaningBlocks() != 0) { //
                level.playOneTurn();
            }
            if (blocksNumber == 0 && i == levels.size() - 1) { // winning game message
                runner.run(new KeyPressStoppableAnimation(this.keyBoard, this.keyBoard.SPACE_KEY,
                        winningGameEndScreen));
                int playerScore = level.getScore().getValue();
                if (this.scoresTable.getRank(playerScore) <= this.scoresTable.size()) {
                    File highScores = new File("highscores.txt");
                    DialogManager dialog = this.gui.getDialogManager(); // getting the player details.
                    String playerName = dialog.showQuestionDialog("Name", "What is your name?", "");
                    ScoreInfo newInformation = new ScoreInfo(playerName, playerScore);
                    this.scoresTable.add(newInformation);
                    try {
                        this.scoresTable.save(highScores);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            } else if (level.getLives() == 0) { // losing game message.
                int playerScore = level.getScore().getValue();
                if (this.scoresTable.getRank(playerScore) <= this.scoresTable.size()) {
                    File highScores = new File("highscores.txt");
                    DialogManager dialog = this.gui.getDialogManager(); // getting the player details.
                    String playerName = dialog.showQuestionDialog("Name", "What is your name?", "Anonymous");
                    ScoreInfo newInformation = new ScoreInfo(playerName, playerScore);
                    this.scoresTable.add(newInformation);
                    try {
                        this.scoresTable.save(highScores);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                runner.run(new KeyPressStoppableAnimation(this.keyBoard, this.keyBoard.SPACE_KEY, losingGameEndScreen));
                break;
            }
        }
        HighScoresAnimation highScoresAnimation = new HighScoresAnimation(this.scoresTable);
        this.animationRunner.run(new KeyPressStoppableAnimation(this.keyBoard, this.keyBoard.SPACE_KEY,
                highScoresAnimation));
    }
}