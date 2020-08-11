package tasks;

import game.GameFlow;
import game.LevelInformation;

import java.util.List;

/**
 * in charge of running the game.
 */
public class GameStarterTask implements Task<Void> {
    private GameFlow gameFlow;
    private List<LevelInformation> levels;
    private int flag;

    /**
     * Constructor.
     *
     * @param levels   : levels informations.
     * @param gameFlow : the game flow.
     * @param flag     : a flag to decide which gameStarter Object to run.
     */
    public GameStarterTask(List<LevelInformation> levels, GameFlow gameFlow, int flag) {
        this.gameFlow = gameFlow;
        this.levels = levels;
        this.flag = flag;
    }

    /**
     * Constructor.
     *
     * @param levels   : levels informations.
     * @param gameFlow : the game flow.
     */
    public GameStarterTask(List<LevelInformation> levels, GameFlow gameFlow) {
        this.gameFlow = gameFlow;
        this.levels = levels;
    }


    /**
     * running the levels.
     *
     * @return null;
     * @throws Exception problem in running the game.
     */
    public Void run() throws Exception {
        gameFlow.runLevels(this.levels);
        return null;
    }

    /**
     * returns the flag.
     *
     * @return : the flag.
     */
    public int getFlag() {
        return this.flag;
    }
}
