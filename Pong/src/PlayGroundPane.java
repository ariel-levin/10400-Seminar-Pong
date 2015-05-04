import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;


public class PlayGroundPane extends Pane {

	private final int BALL_SIZE = 10;
	private final int PLAYER_LENGTH = 40;
	private final int PLAYER_HEIGHT = 10;
	private final int COMP_LENGTH = 40;
	private final int COMP_HEIGHT = 10;
	private final int DELAY = 15;
	
	/* Default Values */
	private final Color BALL_DEFAULT_COLOR = Color.rgb(240,175,19);
	private final int PLAYER_DEFAULT_JUMPS = 6;
	private final int COMP_DEFAULT_JUMPS = 1;
	private final int BALL_DEFAULT_JUMPS = 2;
	
	private Color ballColor = BALL_DEFAULT_COLOR;
	private double ballX, ballY, playerX, compX;
	private int ball_jumps = BALL_DEFAULT_JUMPS, player_keyboard_jumps = PLAYER_DEFAULT_JUMPS;
	private int comp_jumps = COMP_DEFAULT_JUMPS, compScore = 0, playerScore = 0;
	private boolean x_right = true, y_down = false, isNewRound = true;
	private boolean isPlayerScore = false, isCompScore = false, gameON = false, waitForAction = true;
	private Timeline timeline;
	
	private Pane topPane;
	
	
	

	public PlayGroundPane(Pane topPane) {
		this.topPane = topPane;
		
		timeline = new Timeline(new KeyFrame(Duration.millis(DELAY), ae -> timeLineAction()));
		timeline.setCycleCount(Animation.INDEFINITE);
		
		createTopPanel();
		
		setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent ke) {
				if (ke.getCode() == KeyCode.RIGHT)
					playerKeyboardMovement(1);
				else if (ke.getCode() == KeyCode.LEFT)
					playerKeyboardMovement(-1);
				else
					playerKeyboardMovement(0);
			}
		});

		setOnMouseMoved(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent me) {
				if (waitForAction) {
					playerX = me.getX() - PLAYER_LENGTH/2 ;
					ballX = playerX + PLAYER_LENGTH/2 - BALL_SIZE/2;
				} else if (gameON)
					playerX = me.getX() - PLAYER_LENGTH/2 ;
			}
		});
		
		setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent me) {
				if (me.getButton() == MouseButton.PRIMARY) {
					if (waitForAction) {
						timeline.play();
						gameON = true;
						waitForAction = false;
					}
				}
			}
		});
		
		placeComponents();
	}

	private void createTopPanel() {
		//topPanel.setPreferredSize(new Dimension(500,40));
		//topPanel.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));

		Button start = new Button("Start");
		topPane.getChildren().add(start);
		start.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				start();
			}
		});
//		start.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//				start();
//			}
//		});
		//start.setMnemonic('S');

		Button pause = new Button("Pause");
		topPane.getChildren().add(pause);
		pause.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				pause();
			}
		});
		//pause.setMnemonic('P');

		Button stop = new Button("Stop");
		topPane.getChildren().add(stop);
		stop.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				stop();
			}
		});
		//stop.setMnemonic('O');

	}
	
	private void timeLineAction() {
		ballMovement();
		computerMovement();
		//repaint();
	}
	
	private void placeComponents() {
		if (isNewRound) {
			newRound();
			isNewRound = false;
		}

		g.setFont(new Font("Arial",Font.BOLD,12));
		g.setColor(Color.rgb(65,21,126));
		g.drawString("Player:",10,60);
		g.setColor(Color.rgb(50,120,60));
		g.drawString("Computer:",getWidth()-66,60);
		g.setColor(Color.rgb(209,26,220));
		g.setFont(new Font("Arial",Font.BOLD,15));
		g.drawString(String.format("%03d",playerScore),16,80);
		g.drawString(String.format("%03d",compScore),getWidth()-48,80);

		g.setColor(Color.rgb(50,120,60));
		g.fillRect(compX, 0, COMP_LENGTH, COMP_HEIGHT);

		g.setColor(Color.rgb(65,21,126));
		g.fillRect(playerX, getHeight()-PLAYER_HEIGHT, PLAYER_LENGTH, PLAYER_HEIGHT);

		g.setColor(ballColor);
		g.fillOval(ballX, ballY, BALL_SIZE, BALL_SIZE);
	}

//	protected void paintComponent(Graphics g) {
//		super.paintComponent(g);
//		requestFocusInWindow();
//
//		if (isNewRound) {
//			newRound();
//			isNewRound = false;
//		}
//
//		g.setFont(new Font("Arial",Font.BOLD,12));
//		g.setColor(Color.rgb(65,21,126));
//		g.drawString("Player:",10,60);
//		g.setColor(Color.rgb(50,120,60));
//		g.drawString("Computer:",getWidth()-66,60);
//		g.setColor(Color.rgb(209,26,220));
//		g.setFont(new Font("Arial",Font.BOLD,15));
//		g.drawString(String.format("%03d",playerScore),16,80);
//		g.drawString(String.format("%03d",compScore),getWidth()-48,80);
//
//		g.setColor(Color.rgb(50,120,60));
//		g.fillRect(compX, 0, COMP_LENGTH, COMP_HEIGHT);
//
//		g.setColor(Color.rgb(65,21,126));
//		g.fillRect(playerX, getHeight()-PLAYER_HEIGHT, PLAYER_LENGTH, PLAYER_HEIGHT);
//
//		g.setColor(ballColor);
//		g.fillOval(ballX, ballY, BALL_SIZE, BALL_SIZE);
//
//	}

	private void playerKeyboardMovement(int dir) {
		if (waitForAction) {
			timeline.play();
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
		timeline.stop();
		gameON = false;
		waitForAction = true;
	}
	
	private void checkScore() {
		if (playerScore == 5) {
			ball_jumps = BALL_DEFAULT_JUMPS+1;
			ballColor = Color.rgb(78,210,48);
		}
		if (playerScore == 20) {
			ball_jumps = BALL_DEFAULT_JUMPS+2;
			player_keyboard_jumps = PLAYER_DEFAULT_JUMPS+1;
			comp_jumps = COMP_DEFAULT_JUMPS+1;
			ballColor = Color.rgb(18,184,225);
		}
		if (playerScore == 40) {
			ball_jumps = BALL_DEFAULT_JUMPS+3;
			player_keyboard_jumps = PLAYER_DEFAULT_JUMPS+2;
			comp_jumps = COMP_DEFAULT_JUMPS+2;
			ballColor = Color.rgb(240,29,228);
		}
		if (playerScore == 80) {
			ball_jumps = BALL_DEFAULT_JUMPS+4;
			player_keyboard_jumps = PLAYER_DEFAULT_JUMPS+3;
			comp_jumps = COMP_DEFAULT_JUMPS+3;
			ballColor = Color.rgb(245,0,58);
		}
		if (playerScore == 160) {
			ball_jumps = BALL_DEFAULT_JUMPS+5;
			player_keyboard_jumps = PLAYER_DEFAULT_JUMPS+4;
			comp_jumps = COMP_DEFAULT_JUMPS+4;
			ballColor = Color.rgb(0,42,255);
		}
	}

	public void start() {
		if (gameON == false) {
			timeline.play();
			gameON = true;
			waitForAction = false;
		}
	}
	
	public void pause() {
		if (gameON) {
			timeline.stop();
			gameON = false;
			waitForAction = false;
		}
	}
	
	public void stop() {
		if (gameON)
			timeline.stop();
		newRound();
		
		compScore = 0;
		playerScore = 0;
		
		player_keyboard_jumps = PLAYER_DEFAULT_JUMPS;
		comp_jumps = COMP_DEFAULT_JUMPS;
		ball_jumps = BALL_DEFAULT_JUMPS;
		ballColor = BALL_DEFAULT_COLOR;
		
		gameON = false;
		waitForAction = false;
		//repaint();
	}
	
}

