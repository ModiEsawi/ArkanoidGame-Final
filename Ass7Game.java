import tasks.GameStarterTask;
import tasks.ShowHiScoresTask;
import tasks.Task;
import animation.AnimationRunner;
import animation.MenuAnimation;
import animation.KeyPressStoppableAnimation;
import animation.HighScoresAnimation;
import animation.SubMenuAnimation;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import game.GameFlow;
import game.EachLevelSet;
import game.HighScoresTable;
import game.LevelInformation;
import game.Menu;
import game.LevelSpecificationReader;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;


/**
 * arkanoid game.
 * mohamad elesawi.
 */
public class Ass7Game {

    /**
     * main method.
     *
     * @param args : levels number from 1 to 4 ,ignore other wise.
     */
    public static void main(String[] args) {
        int screenWidth = 800;
        int screenHeight = 600;

        GUI gui = new GUI("Arkanoid Game", screenWidth, screenHeight);
        biuoop.KeyboardSensor keyboard = gui.getKeyboardSensor();
        AnimationRunner animationRunner = new AnimationRunner(gui);
        final File scoresFile = new File("highscores.txt");
        final HighScoresTable highScoresTable;
        final int tableSize = 10;

        try { //creating the high scores table if it does not exist.
            if (!scoresFile.exists()) {
                //noinspection ResultOfMethodCallIgnored
                scoresFile.createNewFile();
                highScoresTable = new HighScoresTable(tableSize);
                highScoresTable.save(scoresFile);
            } else { // we will load it if it exists
                highScoresTable = HighScoresTable.loadFromFile(scoresFile);
            }
        } catch (Exception e) {
            System.out.println("problems were found in loading or creating the high scores table.");
            return;
        }

        Task<Void> quit = new Task<Void>() {
            @Override
            public Void run() {
                System.exit(0);
                return null;
            }

            @Override
            public int getFlag() {
                return -1;
            }
        };
        String defualtPath = ("defualt_path.txt");
        List<EachLevelSet> sets;
        try {
            sets = getSets(defualtPath, args);
        } catch (Exception e) {
            e.getMessage();
            gui.close();
            return;
        }
        // the sub menu
        SubMenuAnimation<Task<Void>> subMenu = new SubMenuAnimation<>("Level Sets", keyboard, getMenuImages(2));
        // the list of gameStarterTasks
        List<GameStarterTask> starterTasks = new ArrayList<>();

        for (EachLevelSet set : sets) {
            try {
                GameStarterTask startGameTask = new GameStarterTask(getLevelInfo(set.getPath()),
                        getGame(animationRunner, keyboard, gui, highScoresTable));
                subMenu.addSelection(set.getKey(), set.getValue(), startGameTask);
                starterTasks.add(startGameTask);

            } catch (Exception e) {
                System.out.println(e.getMessage());
                gui.close();
                return;
            }
        }
        // the start game task

        Task<Void> startGameTask = new Task<Void>() {
            @Override
            public Void run() throws Exception {
                animationRunner.run(subMenu);
                Task<Void> task = subMenu.getStatus();
                task.run();
                return null;
            }

            @Override
            public int getFlag() {
                return -1;
            }
        };
        // the main menu
        Menu<Task<Void>> menu = new MenuAnimation<>("Arkanoid", keyboard, getMenuImages(1));
        menu.addSelection("s", "StartGame", startGameTask);
        menu.addSelection("h", "High scores", new ShowHiScoresTask(animationRunner,
                new KeyPressStoppableAnimation(keyboard, KeyboardSensor.SPACE_KEY,
                        new HighScoresAnimation(highScoresTable))));
        menu.addSelection("q", "Quit", quit);

        // run the animation
        while (true) {
            animationRunner.run(menu);
            Task<Void> task = menu.getStatus();
            try {
                task.run();
            } catch (Exception e) {
                System.out.println(e.getMessage());
                gui.close();
                return;
            }

        }

    }

    /**
     * getting the level information.
     *
     * @param path : the path to read from.
     * @return : the list of level information.
     */
    private static List<LevelInformation> getLevelInfo(String path) {
        BufferedReader bufferedReader = null;
        List<LevelInformation> levelInfo = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(ClassLoader.getSystemClassLoader().
                    getResourceAsStream(path)));
        } catch (Exception e) {
            System.out.println("cannot find the path choice file");
        }
        LevelSpecificationReader levelSpecificationReader;
        try { // reading all the levels definition and blocks definition files.
            levelSpecificationReader = new LevelSpecificationReader();
            levelInfo = levelSpecificationReader.getdetails(bufferedReader);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return levelInfo;
    }

    /**
     * creating the game flow.
     *
     * @param runner : the animation runner.
     * @param ks     : the keyboard.
     * @param gui    : the gui.
     * @param table  : the high score table.
     * @return a new game flow.
     */
    private static GameFlow getGame(AnimationRunner runner, KeyboardSensor ks, GUI gui, HighScoresTable table) {
        return new GameFlow(runner, ks, gui, table);
    }

    /**
     * getting the menu background images.
     *
     * @param flag : decides which image to return.
     * @return the menu image
     */
    private static Image getMenuImages(int flag) {
        if (flag == 1) { // first menu
            String path = "background_images/menuPhoto.jpg";
//            String path = "menuPhoto.jpg";
            try {
                return ImageIO.read(ClassLoader.getSystemClassLoader().getResourceAsStream(path));
            } catch (Exception e) {
                System.out.println("cannot load the main photo");
            }
            return null;
        } else if (flag == 2) { // the sub menu
            String path = "background_images/subMenuPhoto.jpg";
//            String path = "subMenuPhoto.jpg";
            try {
                return ImageIO.read(ClassLoader.getSystemClassLoader().getResourceAsStream(path));
            } catch (Exception e) {
                System.out.println("cannot load the main photo");
            }
            return null;
        }
        return null;
    }

    /**
     * Gets the level sets.
     *
     * @param defaultLevelPath :the default level path.
     * @param args             :the args.
     * @return : the level sets.
     */
    public static List<EachLevelSet> getSets(String defaultLevelPath, String[] args) {
        List<EachLevelSet> sets;

        if (args.length != 1) { // if there are no arguments

            try {
                BufferedReader reader = new BufferedReader(new
                        InputStreamReader(ClassLoader.getSystemClassLoader().
                        getResourceAsStream(defaultLevelPath)));
                sets = setSets(reader);
            } catch (Exception e) {
                throw e;
            }
        } else { // if there are arguments.
            BufferedReader reader = new BufferedReader(new InputStreamReader(ClassLoader.getSystemClassLoader().
                    getResourceAsStream(args[0])));
            try {
                sets = setSets(reader);
            } catch (Exception e) {
                throw e;
            }
        }
        return sets;
    }

    /**
     * this function sets each set .
     *
     * @param buffredReader : the reader.
     * @return the list of sets.
     */
    public static List<EachLevelSet> setSets(java.io.Reader buffredReader) {
        List<EachLevelSet> sets = new ArrayList<>();
        String line;
        EachLevelSet cureentSet = null;
        LineNumberReader lineNumberReader = null;
        try {
            lineNumberReader = new LineNumberReader(buffredReader);
            while ((line = lineNumberReader.readLine()) != null) {
                if (lineNumberReader.getLineNumber() % 2 != 0) {
                    String[] splitedLine = line.trim().split(":");
                    cureentSet = new EachLevelSet();
                    cureentSet.setKey(splitedLine[0]);
                    cureentSet.setValue(splitedLine[1]);
                } else {
                    cureentSet.setPath(line.trim());
                    sets.add(cureentSet);
                    cureentSet = null;
                }
            }
        } catch (Exception e) {
            System.out.println("cannot set sets!");
        } finally {
            if (lineNumberReader != null) {
                try {
                    lineNumberReader.close();
                } catch (Exception e) {
                    System.out.println("cannot close the lineNumberReader");
                }
            }
        }
        return sets;
    }
}