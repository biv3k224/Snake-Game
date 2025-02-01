import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuPanel extends JPanel {
    public MainMenuPanel(GameFrame frame) {
        setLayout(new GridBagLayout());
        setBackground(Color.BLACK);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.insets = new Insets(10, 0, 10, 0);

        JLabel title = new JLabel("SNAKE GAME");
        title.setFont(new Font("Arial", Font.BOLD, 48));
        title.setForeground(Color.GREEN);

        JButton startButton = createMenuButton("Start Game", () -> frame.startNewGame());
        JButton scoresButton = createMenuButton("High Scores", () -> frame.showHighScores());
        JButton exitButton = createMenuButton("Exit Game", () -> System.exit(0));

        add(title, gbc);
        add(startButton, gbc);
        add(scoresButton, gbc);
        add(exitButton, gbc);
    }

    private JButton createMenuButton(String text, Runnable action) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 24));
        button.setPreferredSize(new Dimension(200, 50));
        button.addActionListener(e -> action.run());
        return button;
    }
}