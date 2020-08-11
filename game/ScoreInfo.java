package game;

/**
 * The Score info.
 */
public class ScoreInfo {

    private String playerName;
    private int playerScore;

    /**
     * Instantiates a new Score info.
     *
     * @param name  the name of the player.
     * @param score the score of the player.
     */
    public ScoreInfo(String name, int score) {
        this.playerName = name;
        this.playerScore = score;
    }

    /**
     * returns the players name.
     *
     * @return the name
     */
    public String getName() {
        return this.playerName;
    }

    /**
     * return the player score.
     *
     * @return the score
     */
    public int getScore() {
        return this.playerScore;
    }
}