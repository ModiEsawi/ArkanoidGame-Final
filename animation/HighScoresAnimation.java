package animation;

import biuoop.DrawSurface;
import game.HighScoresTable;
import game.ScoreInfo;

import java.awt.Color;
import java.util.List;

/**
 * The High scores animation.
 */
public class HighScoresAnimation implements Animation {
    private HighScoresTable scoresTable;

    /**
     * Instantiates a new High scores animation.
     *
     * @param scores the scores to show
     */
    public HighScoresAnimation(HighScoresTable scores) {
        this.scoresTable = scores;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        List<ScoreInfo> scoreInfos = this.scoresTable.getHighScores();
        d.setColor(Color.red);
        d.drawText(230, 521, "Press space to continue", 30);
        d.setColor(Color.black);
        d.drawText(228, 519, "Press space to continue", 30); //stroke

        d.setColor(Color.BLACK);
        d.drawText(631, 126, "Score", 30);
        d.drawText(71, 126, "Player Name", 30);
        d.drawText(291, 54, "High Scores", 40);
        d.drawText(61, 141, "_________________________________________________", 25);
        for (int i = 0; i < scoreInfos.size(); i++) {
            d.drawText(631, 181 + (i * 35), String.valueOf(scoreInfos.get(i).getScore()), 29);
            d.drawText(71, 181 + (i * 35), scoreInfos.get(i).getName(), 29);
        }

    }

    @Override
    public boolean shouldStop() {
        return false;
    }

}