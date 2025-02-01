import java.io.*;
import java.util.prefs.Preferences;

public class HighScoreManager {
    private static final String HIGH_SCORE_KEY = "snake_high_score";
    private int highScore;

    public HighScoreManager() {
        loadHighScore();
    }

    public void checkAndUpdateHighScore(int score) {
        if(score > highScore) {
            highScore = score;
            saveHighScore();
        }
    }

    private void loadHighScore() {
        highScore = Preferences.userRoot().getInt(HIGH_SCORE_KEY, 0);
    }

    private void saveHighScore() {
        Preferences.userRoot().putInt(HIGH_SCORE_KEY, highScore);
    }

    public int getHighScore() {
        return highScore;
    }
}