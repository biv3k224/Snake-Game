import javax.swing.*;
import java.awt.*;

public class HighScoresPanel extends JDialog {
    public HighScoresPanel(HighScoreManager highScoreManager) {
        setTitle("High Scores");
        setSize(300, 200);
        setLayout(new BorderLayout());
        
        JLabel scoreLabel = new JLabel("Highest Score: " + highScoreManager.getHighScore());
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 24));
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        add(scoreLabel, BorderLayout.CENTER);
        setLocationRelativeTo(null);
        setModal(true);
    }
}