import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class GameFrame extends JFrame {
	
    
        private GamePanel gamePanel;
        private MainMenuPanel menuPanel;
        private HighScoreManager highScoreManager;

        public GameFrame() {
            highScoreManager = new HighScoreManager();
            initializeFrame();
            createPanels();
            showMainMenu();
            setVisible(true);
        }

        private void initializeFrame() {
            setTitle("Snake Game");
            setExtendedState(JFrame.MAXIMIZED_BOTH); // Full-screen
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLayout(new CardLayout());
            setMinimumSize(new Dimension(800, 600));
            setLocationRelativeTo(null);
            
            addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    if(gamePanel != null) {
                        gamePanel.adjustSize(getContentPane().getSize());
                    }
                }
            });
        }

        private void createPanels() {
            menuPanel = new MainMenuPanel(this);
            add(menuPanel, "Menu");
        }

        public void showMainMenu() {
            ((CardLayout) getContentPane().getLayout()).show(getContentPane(), "Menu");
            revalidate();
            repaint();
        }

        public void startNewGame() {
            if(gamePanel != null) {
                remove(gamePanel);
            }
            gamePanel = new GamePanel(this, getContentPane().getSize(), highScoreManager);
            add(gamePanel, "Game");
            
            // Validate container after adding new component
            getContentPane().validate();
            ((CardLayout) getContentPane().getLayout()).show(getContentPane(), "Game");
            gamePanel.requestFocusInWindow();
        }

        public void showHighScores() {
            SwingUtilities.invokeLater(() -> {
                HighScoresPanel scoresPanel = new HighScoresPanel(highScoreManager);
                scoresPanel.setVisible(true);
            });
    }
}
 