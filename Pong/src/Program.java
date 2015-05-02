import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.LineBorder;


public class Program extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private final int WIDTH = 250;
	private final int HEIGHT = 380;
	
	private JPanel topPanel;
	private PlayGroundPanel playGroundPanel;

	
	public static void main(String[] args) {
		new Program();
	}
	
	
	public Program() {
		
		super("Pong v1.23"); // title
		
		topPanel = new JPanel();
		playGroundPanel = new PlayGroundPanel();

		getContentPane().add(topPanel, BorderLayout.NORTH);
		getContentPane().add(playGroundPanel, BorderLayout.CENTER);
		setJMenuBar(new Menu());
		playGroundPanel.setFocusable(true);

		/** Create the frame */
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Point middle = new Point(screenSize.width / 2, screenSize.height / 2);
		Point newLocation = new Point(middle.x - (getWidth() / 2) - WIDTH,
				middle.y - (getHeight() / 2) - HEIGHT / 2);
		setLocation(newLocation);
		
		//setLocationRelativeTo(null);
		setAlwaysOnTop(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setSize(WIDTH, HEIGHT);
		setVisible(true);		
	}

	
	private class PlayGroundPanel extends JPanel {

		private static final long serialVersionUID = 1L;
		
		private final int BALL_SIZE = 10;
		private final int PLAYER_LENGTH = 40;
		private final int PLAYER_HEIGHT = 10;
		private final int COMP_LENGTH = 40;
		private final int COMP_HEIGHT = 10;
		private final int DELAY = 15;
		
		/* Default Values */
		private final Color BALL_DEFAULT_COLOR = new Color(240,175,19);
		private final int PLAYER_DEFAULT_JUMPS = 6;
		private final int COMP_DEFAULT_JUMPS = 1;
		private final int BALL_DEFAULT_JUMPS = 2;
		
		private Color ballColor = BALL_DEFAULT_COLOR;
		private int ballX, ballY, playerX, compX;
		private int ball_jumps = BALL_DEFAULT_JUMPS, player_keyboard_jumps = PLAYER_DEFAULT_JUMPS;
		private int comp_jumps = COMP_DEFAULT_JUMPS, compScore = 0, playerScore = 0;
		private boolean x_right = true, y_down = false, isNewRound = true;
		private boolean isPlayerScore = false, isCompScore = false, gameON = false, waitForAction = true;
		private Timer timer = new Timer(DELAY, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ballMovement();
				computerMovement();
				repaint();
			}
		});

		public PlayGroundPanel() {
			createTopPanel();

			addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent e)  {
					switch (e.getKeyCode()) {
					case KeyEvent.VK_RIGHT: playerKeyboardMovement(1); break;
					case KeyEvent.VK_LEFT:	playerKeyboardMovement(-1); break;
					default:				playerKeyboardMovement(0);
					}
				}
			});
			
			addMouseMotionListener(new MouseMotionAdapter() {
				public void mouseMoved(MouseEvent e) {
					if (waitForAction) {
						playerX = e.getX() - PLAYER_LENGTH/2 ;
						ballX = playerX + PLAYER_LENGTH/2 - BALL_SIZE/2;
					} else if (gameON)
						playerX = e.getX() - PLAYER_LENGTH/2 ;
					repaint();
				}
			});
			
			addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e) {
					if (e.getButton() == 1) {
						if (waitForAction) {
							timer.start();
							gameON = true;
							waitForAction = false;
						}
					}
				}
			});
			
		}

		private void createTopPanel() {
			topPanel.setPreferredSize(new Dimension(500,40));
			topPanel.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));

			JButton start = new JButton("Start");
			topPanel.add(start);
			start.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					start();
				}
			});
			//start.setMnemonic('S');

			JButton pause = new JButton("Pause");
			topPanel.add(pause);
			pause.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					pause();
				}
			});
			//pause.setMnemonic('P');

			JButton stop = new JButton("Stop");
			topPanel.add(stop);
			stop.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					stop();
				}
			});
			//stop.setMnemonic('O');

		}

		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			requestFocusInWindow();

			if (isNewRound) {
				newRound();
				isNewRound = false;
			}

//			g.setColor(new Color(45,93,190));
//			g.setFont(new Font("Comic Sans MS",0,20));
//			g.drawString("Ariel Levin",getWidth()/2-53,180);
//			g.drawString("Ariel Levin",getWidth()/2-53,170);
//			g.setFont(new Font("Comic Sans MS",Font.BOLD,14));
//			g.setFont(new Font("Comic Sans MS",Font.BOLD,12));
//			g.drawString("ariel2011@gmail.com",getWidth()/2-72,205);
//			g.drawString("ariel2011@gmail.com",getWidth()/2-60,190);
//			g.setFont(new Font("Comic Sans MS",Font.BOLD,14));
//			g.drawString("www.ariel-levin.co.nr",getWidth()/2-73,210);

			g.setFont(new Font("Arial",Font.BOLD,12));
			g.setColor(new Color(65,21,126));
			g.drawString("Player:",10,60);
			g.setColor(new Color(50,120,60));
			g.drawString("Computer:",getWidth()-66,60);
			g.setColor(new Color(209,26,220));
			g.setFont(new Font("Arial",Font.BOLD,15));
			g.drawString(String.format("%03d",playerScore),16,80);
			g.drawString(String.format("%03d",compScore),getWidth()-48,80);

			g.setColor(new Color(50,120,60));
			g.fillRect(compX, 0, COMP_LENGTH, COMP_HEIGHT);

			g.setColor(new Color(65,21,126));
			g.fillRect(playerX, getHeight()-PLAYER_HEIGHT, PLAYER_LENGTH, PLAYER_HEIGHT);

			g.setColor(ballColor);
			g.fillOval(ballX, ballY, BALL_SIZE, BALL_SIZE);

		}

		private void playerKeyboardMovement(int dir) {
			if (waitForAction) {
				timer.start();
				gameON = true;
				waitForAction = false;
			}
			if (gameON) {
				if (dir==1 && (playerX+(PLAYER_LENGTH/2) < getWidth()))
					playerX += player_keyboard_jumps;
				if (dir==(-1) && (playerX-player_keyboard_jumps+(PLAYER_LENGTH/2)+5 > 0))
					playerX -= player_keyboard_jumps;
			}
		}

		private void computerMovement() {
			if (x_right && (compX+(COMP_LENGTH/2) < getWidth()))
				compX += comp_jumps;
			else if (compX-comp_jumps+(COMP_LENGTH/2)+5 > 0)
				compX -= comp_jumps;
		}

		private void ballMovement() {
			/* determine X position according to it's current place */
			if (isPlayerScore) {
				if (ballY+BALL_SIZE > 0) {
					ballY -= ball_jumps;
					if (x_right)
						ballX += ball_jumps;
					else
						ballX -= ball_jumps;
				} else {
					playerScore++;
					isNewRound = true;
					isPlayerScore = false;
				}
			} else if (isCompScore) {
				if (ballY < getHeight()) {
					ballY += ball_jumps;
					if (x_right)
						ballX += ball_jumps;
					else
						ballX -= ball_jumps;
				} else {
					compScore++;
					isNewRound = true;
					isCompScore = false;
				}
			} else {

				if (x_right) {
					if (getWidth() > ballX + BALL_SIZE + ball_jumps)
						ballX += ball_jumps;
					else {
						ballX = getWidth()-BALL_SIZE;
						x_right = false;
					}
				} else {
					if (ballX - ball_jumps > 0)
						ballX -= ball_jumps;
					else {
						ballX = 0;
						x_right = true;
					}
				}

				/* determine Y position according to it's current place */
				if (y_down) {
					if (getHeight() > ballY + BALL_SIZE + ball_jumps + PLAYER_HEIGHT)
						ballY += ball_jumps;
					else {
						if ((ballX >= playerX-BALL_SIZE) && (ballX <= playerX+PLAYER_LENGTH+(BALL_SIZE/2))) {
							ballY = getHeight()-BALL_SIZE-PLAYER_HEIGHT;	
							y_down = false;
							x_right = (ballX+(BALL_SIZE/2) > playerX+(PLAYER_LENGTH/2)) ? true : false ;
						}
						else
							isCompScore = true;
					}
				} else {
					if (ballY - ball_jumps - COMP_HEIGHT > 0)
						ballY -= ball_jumps;
					else {
						if ((ballX >= compX-BALL_SIZE) && (ballX <= compX+COMP_LENGTH+(BALL_SIZE/2))) {
							ballY = COMP_HEIGHT;
							y_down = true;
							x_right = (ballX+(BALL_SIZE/2) > compX+(COMP_LENGTH/2)) ? true : false ;
						}
						else
							isPlayerScore = true;
					}
				}

			}

		}

		private void newRound() {
			checkScore();			
			ballX = getWidth()/2 - BALL_SIZE/2;
			ballY = getHeight() - PLAYER_HEIGHT - BALL_SIZE;
			playerX = getWidth()/2 - PLAYER_LENGTH/2;
			compX = getWidth()/2 - COMP_LENGTH/2;
			y_down = false;
			x_right = (int)(Math.random()*2) == 1 ? true : false ;
			timer.stop();
			gameON = false;
			waitForAction = true;
		}
		
		private void checkScore() {
			if (playerScore == 5) {
				ball_jumps = BALL_DEFAULT_JUMPS+1;
				ballColor = new Color(78,210,48);
			}
			if (playerScore == 20) {
				ball_jumps = BALL_DEFAULT_JUMPS+2;
				player_keyboard_jumps = PLAYER_DEFAULT_JUMPS+1;
				comp_jumps = COMP_DEFAULT_JUMPS+1;
				ballColor = new Color(18,184,225);
			}
			if (playerScore == 40) {
				ball_jumps = BALL_DEFAULT_JUMPS+3;
				player_keyboard_jumps = PLAYER_DEFAULT_JUMPS+2;
				comp_jumps = COMP_DEFAULT_JUMPS+2;
				ballColor = new Color(240,29,228);
			}
			if (playerScore == 80) {
				ball_jumps = BALL_DEFAULT_JUMPS+4;
				player_keyboard_jumps = PLAYER_DEFAULT_JUMPS+3;
				comp_jumps = COMP_DEFAULT_JUMPS+3;
				ballColor = new Color(245,0,58);
			}
			if (playerScore == 160) {
				ball_jumps = BALL_DEFAULT_JUMPS+5;
				player_keyboard_jumps = PLAYER_DEFAULT_JUMPS+4;
				comp_jumps = COMP_DEFAULT_JUMPS+4;
				ballColor = new Color(0,42,255);
			}
		}

		public void start() {
			if (gameON == false) {
				timer.start();
				gameON = true;
				waitForAction = false;
			}
		}
		
		public void pause() {
			if (gameON) {
				timer.stop();
				gameON = false;
				waitForAction = false;
			}
		}
		
		public void stop() {
			if (gameON)
				timer.stop();
			newRound();
			
			compScore = 0;
			playerScore = 0;
			
			player_keyboard_jumps = PLAYER_DEFAULT_JUMPS;
			comp_jumps = COMP_DEFAULT_JUMPS;
			ball_jumps = BALL_DEFAULT_JUMPS;
			ballColor = BALL_DEFAULT_COLOR;
			
			gameON = false;
			waitForAction = false;
			repaint();
		}
		
	}


	private class Menu extends JMenuBar {
		
		private static final long serialVersionUID = 1L;

		
		public Menu() {
			
			JMenu actionMenu = new JMenu("Actions");
			
			JMenuItem startMenuItem = new JMenuItem("Start");
			startMenuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {

					playGroundPanel.start();
				}
			});
			actionMenu.add(startMenuItem);
			
			JMenuItem pauseMenuItem = new JMenuItem("Pause");
			pauseMenuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {

					playGroundPanel.pause();
				}
			});
			actionMenu.add(pauseMenuItem);
			
			JMenuItem stopMenuItem = new JMenuItem("Stop");
			stopMenuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {

					playGroundPanel.stop();
				}
			});
			actionMenu.add(stopMenuItem);
			
			JMenuItem exitMenuItem = new JMenuItem("Exit");
			exitMenuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {

					System.exit(0);
				}
			});
			actionMenu.add(exitMenuItem);
			
			this.add(actionMenu);

			
			/////////////////////////////////////////////////////////////////////////////////////

			JMenu helpMenu = new JMenu("Help");
			
			JMenuItem aboutItem = new JMenuItem("About");
			aboutItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String msg = 	"Ariel Levin\n" +
									"ariel.lvn89@gmail.com\n" +
									"http://about.me/ariel.levin";
					
					JOptionPane.showMessageDialog(null,msg,"About Pong",JOptionPane.INFORMATION_MESSAGE);
				}
			});
			helpMenu.add(aboutItem);
			
			this.add(helpMenu);
			
		}
	}
}
