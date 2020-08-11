package game;

//import java.io.;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

/**
 * The type High scores table.
 */
public class HighScoresTable {
    private List<ScoreInfo> scoresTable;
    private int tableSize;
    private Boolean loadState;

    /**
     * Instantiates a new High scores table.
     * Create an empty high-scores table with the specified size.
     * The size means that the table holds up to size top scores.
     *
     * @param size the size of the table.
     */

    public HighScoresTable(int size) {
        scoresTable = new ArrayList<>();
        this.tableSize = size;
        this.loadState = false;
    }

    /**
     * Adding a high score if possible.
     *
     * @param score the score
     */

    public void add(ScoreInfo score) {
        this.scoresTable.add(score);
        sortListByScore();
    }

    /**
     * Return table size.
     *
     * @return the table size.
     */

    public int size() {
        return this.tableSize;
    }

    /**
     * Return the current high scores.
     * The list is sorted such that the highest
     * scores come first.
     *
     * @return the high scores
     */

    public List<ScoreInfo> getHighScores() {
        return this.scoresTable;
    }

    /**
     * return the rank of the current score.
     *
     * @param score the score.
     * @return the rank.
     */
// return the rank of the current score: where will it
    // be on the list if added?
    // Rank 1 means the score will be highest on the list.
    // Rank `size` means the score will be lowest.
    // Rank > `size` means the score is too low and will not
    //      be added to the list.
    public int getRank(int score) {
        if (this.scoresTable.size() == 0) {
            return 1;
        }
        for (int i = 0; i < this.scoresTable.size(); i++) {
            if (i < this.tableSize) {
                if (score > this.scoresTable.get(i).getScore()) {
                    return i + 1;
                }
                return i + 1;
            }
        }
        return this.tableSize + 1;
    }

    /**
     * Clears the table.
     */

    public void clear() {
        this.scoresTable.clear();
    }

    /**
     * Load table data from file.
     * Current table data is cleared.
     *
     * @param filename the filename
     * @throws IOException the io exception
     */

    public void load(File filename) throws IOException {
        String playersName = null;
        int playersScore;
        this.clear();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename)))) {
            String line;
            // jumping between lines using Booleans.
            while ((line = reader.readLine()) != null) {
                if (!this.loadState) {
                    playersName = line;
                    this.loadState = true;
                    continue;
                }
                playersScore = Integer.parseInt(line);
                ScoreInfo newInfo = new ScoreInfo(playersName, playersScore);
                this.scoresTable.add(newInfo);
                this.loadState = false;
            }
        } catch (IOException e) {
            throw e;
        }
    }

    /**
     * Save table data to the specified file..
     *
     * @param filename the filename
     * @throws IOException the io exception
     */

    public void save(File filename) throws IOException {
        try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(filename)))) {
            for (ScoreInfo scoreInfo : this.scoresTable) {
                writer.println(scoreInfo.getName());
                writer.println(scoreInfo.getScore());
            }
        } catch (IOException exciption) {
            throw exciption;
        }
    }

    /**
     * Read a table from file and return it.
     * If the file does not exist, or there is a problem with
     * reading it, an empty table is returned.
     *
     * @param filename the filename.
     * @return the high scores table.
     */

    public static HighScoresTable loadFromFile(File filename) {
        int tableSize = 10;
        HighScoresTable scoresTable = new HighScoresTable(tableSize);
        try {
            scoresTable.load(filename);
        } catch (IOException exciption) {
            return null;
        }
        return scoresTable;
    }

    /**
     * sorting the table of high scores by score values.
     */
    private void sortListByScore() {
        for (int i = 0; i < this.scoresTable.size(); i++) {
            for (int j = 0; j < this.scoresTable.size() - 1 - i; j++) {
                if (this.scoresTable.get(j).getScore() < this.scoresTable.get(j + 1).getScore()) {
                    ScoreInfo tempScoreInfo = this.scoresTable.get(j);
                    this.scoresTable.set(j, this.scoresTable.get(j + 1));
                    this.scoresTable.set(j + 1, tempScoreInfo);
                }
            }
        }
        // remove one member if it exceeded the table's size.
        while (this.scoresTable.size() > this.tableSize) {
            this.scoresTable.remove(this.scoresTable.size() - 1);
        }
    }
}