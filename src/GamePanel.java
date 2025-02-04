import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements ActionListener{

	//Declare all the variables used in the code
	static int SCREEN_WIDTH = 600;
	static int SCREEN_HEIGHT = 600;
	static int UNIT_SIZE = 25;
	static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
	static final int DELAY = 75;
	final int x[]=new int[GAME_UNITS];
	final int y[]=new int[GAME_UNITS];
	int bodyParts = 6;
	int appleEaten;
	int appleX;
	int appleY;
	char direction = 'R';
	boolean running = false;
	Timer timer;
	Random random;
	
	private GameFrame gameFrame;
	private HighScoreManager highScoreManager;
	
	public GamePanel(GameFrame frame, Dimension screenSize, HighScoreManager hsm) {
	    this.gameFrame = frame;
	    this.highScoreManager = hsm;
	    SCREEN_WIDTH = screenSize.width;
	    SCREEN_HEIGHT = screenSize.height;
	    random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
		this.setBackground(Color.black);
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();;
	}
	
	
	public void startGame() {
		newApple();
		running = true;
		timer = new Timer(DELAY,this);
		timer.start();
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
		
	}
	public void draw(Graphics g) {
		if(running) {
			/* Shows the grid lines for easier resize
			for(int i = 0; i<SCREEN_HEIGHT/UNIT_SIZE; i++) {
				g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
				g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
			}
			*/
			g.setColor(Color.red);
			g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
			

			
			for(int i = 0; i< bodyParts; i++) {
				if(i==0) {
					g.setColor(Color.green);
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}
				else {
					g.setColor(new Color(45,100,0));
					g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}
			}
			g.setColor(Color.red);
			g.setFont(new Font("Ink Free",Font.BOLD,40));
			FontMetrics metrics = getFontMetrics(g.getFont());
			g.drawString("Score: "+appleEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: "+appleEaten))/2, g.getFont().getSize());
		}
		else {
		gameOver(g);
		}
		
	}
	public void newApple() {
	    boolean validPosition;
	    do {
	        appleX = random.nextInt(SCREEN_WIDTH/UNIT_SIZE) * UNIT_SIZE;
	        appleY = random.nextInt(SCREEN_HEIGHT/UNIT_SIZE) * UNIT_SIZE;
	        validPosition = true;
	        
	        // Check against snake body
	        for(int i = 0; i < bodyParts; i++) {
	            if(appleX == x[i] && appleY == y[i]) {
	                validPosition = false;
	                break;
	            }
	        }
	    } while(!validPosition);
	}
	
	public void move() {
	    // Update body positions (follow the head)
	    for(int i = bodyParts; i > 0; i--) {
	        x[i] = x[i-1];
	        y[i] = y[i-1];
	    }

	    // Update head position based on direction
	    switch(direction) {
	        case 'U':
	            y[0] -= UNIT_SIZE;
	            break;
	        case 'D':
	            y[0] += UNIT_SIZE;
	            break;
	        case 'L':
	            x[0] -= UNIT_SIZE;
	            break;
	        case 'R':
	            x[0] += UNIT_SIZE;
	            break;
	    }
	}
	
	public void checkApple() {
		if((x[0] == appleX)&&(y[0] == appleY)) {
			bodyParts++;
			appleEaten++;
			newApple();
		}
		
	}
	public void checkCollison() {
		//checks if head collides with body
		for (int i = bodyParts; i > 0; i--) {
			if((x[0] == x[i] && y[0] == y[i])) {
				running = false;
			}
		}
		//check if head touches left border
		if(x[0] < 0) {
			running = false;
		}
		//check if head touches right border
		if(x[0] > SCREEN_WIDTH) {
			running = false;
		}
		//check if head touches top border
		if(y[0] < 0) {
			running = false;
		}
		//check if head touches bottom border
		if(y[0] > SCREEN_HEIGHT) {
			running = false;
		}
		if (!running) {
			timer.stop();
		}
		
	}
	public void gameOver(Graphics g) {
		highScoreManager.checkAndUpdateHighScore(appleEaten);
		//Display the score
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free",Font.BOLD,40));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("Score: "+appleEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score: "+appleEaten))/2, g.getFont().getSize());
		
		//Game Over text
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free",Font.BOLD,75));
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		g.drawString("Game Over", (SCREEN_WIDTH - metrics2.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);
		
	}
	public void adjustSize(Dimension newSize) {
	    SCREEN_WIDTH = newSize.width;
	    SCREEN_HEIGHT = newSize.height;
	    UNIT_SIZE = Math.min(SCREEN_WIDTH, SCREEN_HEIGHT) / 24; // Dynamic unit size
	    repaint();
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(running) {
			move();
			checkApple();
			checkCollison();
			
		}
		repaint();

		
	}
	public class MyKeyAdapter extends KeyAdapter{
		@Override
		//To assign the key and their functions
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if(direction != 'R') {
					direction = 'L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(direction != 'L') {
					direction = 'R';
				}
				break;
			case KeyEvent.VK_UP:
				if(direction != 'D') {
					direction = 'U';
				}
				break;
			case KeyEvent.VK_DOWN:
				if(direction != 'U') {
					direction = 'D';
				}
				break;
				
			case KeyEvent.VK_SPACE:
				if (!running) {
                    // Full game reset
                    timer.stop();          // Stop existing timer
                    bodyParts = 6;         // Reset snake length
                    appleEaten = 0;        // Reset score
                    direction = 'R';       // Force initial direction
                    initializeSnake();     // Reset positions
                    newApple();            // Generate new apple
                    running = true;        // Enable game loop
                    timer.start();         // Start fresh timer
                }
                break;
			}
		}

		private void initializeSnake() {
			int startX = (SCREEN_WIDTH / 2 / UNIT_SIZE) * UNIT_SIZE;  // Grid-aligned X
		    int startY = (SCREEN_HEIGHT / 2 / UNIT_SIZE) * UNIT_SIZE; // Grid-aligned Y
		    for (int i = 0; i < bodyParts; i++) {
		        x[i] = startX - (i * UNIT_SIZE);
		        y[i] = startY;
	    }
		}
	}
}


