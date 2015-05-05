import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;



/**
 * 
 * @author 	Ariel Levin
 * 			<br/><a href="http://about.me/ariel.levin">about.me/ariel.levin</a>
 * 			<br/><a href="mailto:ariel.lvn89@gmail.com">ariel.lvn89@gmail.com</a><br/><br/>
 *
 * */
public class PlayGroundPane extends Pane {

	private final int BALL_RADIUS = 5;
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
	private int ball_jumps = BALL_DEFAULT_JUMPS, player_keyboard_jumps = PLAYER_DEFAULT_JUMPS;
	private int comp_jumps = COMP_DEFAULT_JUMPS, compScore = 0, playerScore = 0;
	private boolean x_right = true, y_down = false, isNewRound = true;
	private boolean isPlayerScore = false, isCompScore = false, gameON = false, waitForAction = true;
	private Timeline timeline;
	private Pane buttonPane, scorePane;
	private Label lblPlayer, lblComp;
	private Rectangle player, comp;
	private Circle ball;
	//private Program program;
	

	public PlayGroundPane(Pane buttonPane, Pane scorePane, Program program) {
		this.buttonPane = buttonPane;
		this.scorePane = scorePane;
		//this.program = program;
		
		timeline = new Timeline(new KeyFrame(Duration.millis(DELAY), ae -> timeLineAction()));
		timeline.setCycleCount(Animation.INDEFINITE);
		
		createButtonPane();
		createScorePane();
		
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
					player.setX(me.getX() - PLAYER_LENGTH/2);
					ball.setCenterX(player.getX() + PLAYER_LENGTH/2 - BALL_RADIUS/2);
				} else if (gameON)
					player.setX(me.getX() - PLAYER_LENGTH/2);
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
		
	}

	private void createButtonPane() {

		Button start = new Button("Start");
		buttonPane.getChildren().add(start);
		start.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				start();
			}
		});

		Button pause = new Button("Pause");
		buttonPane.getChildren().add(pause);
		pause.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				pause();
			}
		});

		Button stop = new Button("Stop");
		buttonPane.getChildren().add(stop);
		stop.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				stop();
			}
		});

	}
	
	private void createScorePane() {
		lblPlayer = new Label("Player: " + playerScore);
		GridPane.setConstraints(lblPlayer, 1, 1);
		scorePane.getChildren().add(lblPlayer);
		lblComp = new Label("Computer: " + compScore);
		GridPane.setConstraints(lblComp, 2, 1);
		scorePane.getChildren().add(lblComp);
	}
	
	private void timeLineAction() {
		if (isNewRound) {
			newRound();
			isNewRound = false;
		}
		ballMovement();
		computerMovement();
		requestFocus();
	}
	
	public void placeComponents() {

		comp = new Rectangle();
		comp.setY(0);
		comp.setWidth(COMP_LENGTH);
		comp.setHeight(COMP_HEIGHT);
		comp.setFill(Color.rgb(50,120,60));

		player = new Rectangle();
		player.yProperty().bind(heightProperty().subtract(PLAYER_HEIGHT));
		player.setWidth(PLAYER_LENGTH);
		player.setHeight(PLAYER_HEIGHT);
		player.setFill(Color.rgb(65,21,126));

		ball = new Circle();
		ball.setRadius(BALL_RADIUS);
		ball.setFill(ballColor);
		
		getChildren().addAll(comp, player, ball);
		
		if (isNewRound) {
			newRound();
			isNewRound = false;
		}
	}
	
	private void newRound() {
		checkScore();			
		ball.setCenterX(widthProperty().doubleValue()/2 - BALL_RADIUS/2);
		ball.setCenterY(heightProperty().doubleValue() - PLAYER_HEIGHT - BALL_RADIUS);
		player.setX(widthProperty().doubleValue()/2 - PLAYER_LENGTH/2);
		comp.setX(widthProperty().doubleValue()/2 - COMP_LENGTH/2);
		y_down = false;
		x_right = (int)(Math.random()*2) == 1 ? true : false ;
		timeline.stop();
		gameON = false;
		waitForAction = true;
	}
	
	private void refreshScore() {
		lblPlayer.setText("Player: " + playerScore);
		lblComp.setText("Computer: " + compScore);
	}

	private void playerKeyboardMovement(int dir) {
		if (waitForAction) {
			timeline.play();
			gameON = true;
			waitForAction = false;
		}
		if (gameON) {
			if (dir==1 && (player.getX()+(PLAYER_LENGTH/2) < getWidth()))
				player.setX(player.getX() + player_keyboard_jumps);
			if (dir==(-1) && (player.getX()-player_keyboard_jumps+(PLAYER_LENGTH/2)+5 > 0))
				player.setX(player.getX() - player_keyboard_jumps);
		}
	}

	private void computerMovement() {
		if (x_right && (comp.getX()+(COMP_LENGTH/2) < getWidth()))
			comp.setX(comp.getX() + comp_jumps);
		else if (comp.getX()-comp_jumps+(COMP_LENGTH/2)+5 > 0)
			comp.setX(comp.getX() - comp_jumps);;
	}

	private void ballMovement() {
		/* determine X position according to it's current place */
		if (isPlayerScore) {
			if (ball.getCenterY() + BALL_RADIUS > 0) {
				ball.setCenterY(ball.getCenterY() - ball_jumps);
				if (x_right)
					ball.setCenterX(ball.getCenterX() + ball_jumps);
				else
					ball.setCenterX(ball.getCenterX() - ball_jumps);
			} else {						// player score
				playerScore++;
				isNewRound = true;
				isPlayerScore = false;
				refreshScore();
			}
		} else if (isCompScore) {
			if (ball.getCenterY() < getHeight()) {
				ball.setCenterY(ball.getCenterY() + ball_jumps);
				if (x_right)
					ball.setCenterX(ball.getCenterX() + ball_jumps);
				else
					ball.setCenterX(ball.getCenterX() - ball_jumps);
			} else {						// comp score
				compScore++;
				isNewRound = true;
				isCompScore = false;
				refreshScore();
			}
		} else {

			if (x_right) {
				if (getWidth() > ball.getCenterX() + BALL_RADIUS + ball_jumps)
					ball.setCenterX(ball.getCenterX() + ball_jumps);
				else {
					ball.setCenterX(getWidth()-BALL_RADIUS);
					x_right = false;
				}
			} else {
				if (ball.getCenterX() - ball_jumps > 0)
					ball.setCenterX(ball.getCenterX() - ball_jumps);
				else {
					ball.setCenterX(ball.getRadius());
					x_right = true;
				}
			}

			/* determine Y position according to it's current place */
			if (y_down) {
				if (getHeight() > ball.getCenterY() + BALL_RADIUS + ball_jumps + PLAYER_HEIGHT)
					ball.setCenterY(ball.getCenterY() + ball_jumps);
				else {
					if ((ball.getCenterX() >= player.getX()-BALL_RADIUS) && (ball.getCenterX() <= player.getX()+PLAYER_LENGTH+(BALL_RADIUS/2))) {
						ball.setCenterY(getHeight()-BALL_RADIUS-PLAYER_HEIGHT);
						y_down = false;
						x_right = (ball.getCenterX()+(BALL_RADIUS/2) > player.getX()+(PLAYER_LENGTH/2)) ? true : false ;
					}
					else
						isCompScore = true;
				}
			} else {
				if (ball.getCenterY() - ball.getRadius() - ball_jumps - COMP_HEIGHT > 0) {
					ball.setCenterY(ball.getCenterY() - ball_jumps);
				} else {
					if ((ball.getCenterX() >= comp.getX()-BALL_RADIUS) && (ball.getCenterX() <= comp.getX()+COMP_LENGTH+(BALL_RADIUS/2))) {
						ball.setCenterY(COMP_HEIGHT + ball.getRadius());
						y_down = true;
						x_right = (ball.getCenterX()+(BALL_RADIUS/2) > comp.getX()+(COMP_LENGTH/2)) ? true : false ;
					}
					else
						isPlayerScore = true;
				}
			}

		}

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
	}
	
}

