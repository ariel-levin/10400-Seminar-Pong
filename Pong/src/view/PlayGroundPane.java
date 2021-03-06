package view;

import events.PongEvents;
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
public class PlayGroundPane extends Pane implements PongEvents {

	private final int BALL_RADIUS = 5;
	private final int PLAYER_LENGTH = 40;
	private final int PLAYER_HEIGHT = 10;
	private final int COMP_LENGTH = 40;
	private final int COMP_HEIGHT = 10;
	private final int DELAY = 15;

	private final Color BALL_DEFAULT_COLOR = Color.rgb(240,175,19);
	private final float PLAYER_DEFAULT_JUMPS = 6f;
	private final float COMP_DEFAULT_JUMPS = 0.6f;
	private final float BALL_DEFAULT_JUMPS = 3.5f;
	
	private float 			ball_jumps = BALL_DEFAULT_JUMPS, player_keyboard_jumps = PLAYER_DEFAULT_JUMPS;
	private float 			comp_jumps = COMP_DEFAULT_JUMPS;
	private int 			compScore = 0, playerScore = 0, level = 1;
	private double 			xHitPrediction;
	private boolean 		ball_x_right = true, ball_y_down = false, isNewRound = true, useCompHitPrediction = false;
	private boolean 		isPlayerScore = false, isCompScore = false, gameON = false, waitForAction = true;
	private Timeline 		timeline;
	private Label 			lblPlayer, lblComp, lblLevel;
	private Rectangle 		player, comp;
	private Circle 			ball;
	private GameState		gameState = GameState.STOP;
	private PongViewJavaFX 	pongView;
	

	public PlayGroundPane(PongViewJavaFX pongView) {
		this.pongView = pongView;
		
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
					ball.setCenterX(playerCenterX());
				} else if (gameON)
					player.setX(me.getX() - PLAYER_LENGTH/2);
			}
		});
		
		setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent me) {
				if (me.getButton() == MouseButton.PRIMARY) {
					if (waitForAction) {
						play();
						if (useCompHitPrediction)
							xHitPrediction = getBallXhitPrediction();
					}
				}
			}
		});
		
	}

	private void createButtonPane() {

		Button play = new Button("Play");
		play.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				play();
			}
		});

		Button pause = new Button("Pause");
		pause.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				pause();
			}
		});

		Button stop = new Button("Stop");
		stop.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				stop();
			}
		});
		
		pongView.getButtonPane().getChildren().addAll(play, pause, stop);
	}
	
	private void createScorePane() {
		lblPlayer = new Label();
		lblPlayer.setStyle("-fx-font-weight: bold;");
		pongView.getScorePane().getChildren().add(lblPlayer);
		lblLevel = new Label();
		lblLevel.setStyle("-fx-font-weight: bold;");
		pongView.getScorePane().getChildren().add(lblLevel);
		lblComp = new Label();
		lblComp.setStyle("-fx-font-weight: bold;");
		pongView.getScorePane().getChildren().add(lblComp);
	}
	
	private void timeLineAction() {
		ballMovement();
		computerMovement();
		if (isNewRound) {
			newRound();
			isNewRound = false;
		}
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
		ball.setFill(BALL_DEFAULT_COLOR);
		
		getChildren().addAll(comp, player, ball);
		
		if (isNewRound) {
			newRound();
			isNewRound = false;
		}
	}
	
	private void newRound() {
		timeline.stop();
		refreshScore();
		ball.setCenterX(widthProperty().doubleValue()/2 - BALL_RADIUS/2);
		ball.setCenterY(heightProperty().doubleValue() - PLAYER_HEIGHT - BALL_RADIUS);
		player.setX(widthProperty().doubleValue()/2 - PLAYER_LENGTH/2);
		comp.setX(widthProperty().doubleValue()/2 - COMP_LENGTH/2);
		ball_y_down = false;
		ball_x_right = (int)(Math.random()*2) == 1 ? true : false ;
		gameON = false;
		waitForAction = true;
	}
	
	private void refreshScore() {
		lblPlayer.setText("Player: " + playerScore);
		lblComp.setText("Computer: " + compScore);
		lblLevel.setText("Level: " + level);
	}

	private void playerKeyboardMovement(int dir) {
		if (waitForAction) {
			play();
		} else if (gameON) {
			if (dir==1 && (playerCenterX() < getWidth()))
				player.setX(player.getX() + player_keyboard_jumps);
			if (dir==(-1) && (playerCenterX() - player_keyboard_jumps + 5 > 0))
				player.setX(player.getX() - player_keyboard_jumps);
		}
	}

	private void computerMovement() {
		
		if (useCompHitPrediction) {
			if (ball_x_right) {
				if (compCenterX() < xHitPrediction)
					comp.setX(comp.getX() + comp_jumps);
				else
					comp.setX(comp.getX() - comp_jumps);
			} else {
				if (compCenterX() > xHitPrediction)
					comp.setX(comp.getX() - comp_jumps);
				else
					comp.setX(comp.getX() + comp_jumps);
			}
		} else {
			if (ball_x_right && (compCenterX() < getWidth()))
				comp.setX(comp.getX() + comp_jumps);
			else if (compCenterX() - comp_jumps + 5 > 0)
				comp.setX(comp.getX() - comp_jumps);
		}
	}
	
	private double compCenterX() {
		return comp.getX() + (COMP_LENGTH/2);
	}
	
	private double playerCenterX() {
		return player.getX() + (PLAYER_LENGTH/2);
	}
	
	private double getBallXhitPrediction() {
		double a = Math.sqrt(Math.pow(ball_jumps,2) + Math.pow(ball_jumps,2));
		double theta = Math.asin(ball_jumps / a);
		double xhit = ball.getCenterY() * Math.tan(theta);
		if (ball_x_right) {
			if (ball.getCenterX() + xhit > getWidth()) {	// will have wall hit
				double yhit = ball.getCenterY() - (getWidth() - ball.getCenterX()) * Math.tan(theta);
				xhit = getWidth() - yhit * Math.tan(theta);
				return xhit;
			} else
				return ball.getCenterX() + xhit;
		} else {
			if (ball.getCenterX() - xhit < 0) {		// will have wall hit
				double yhit = ball.getCenterY() - ball.getCenterX() * Math.tan(theta);
				xhit = yhit * Math.tan(theta);
				return xhit;
			} else
				return ball.getCenterX() - xhit;
		}
	}

	private void ballMovement() {
		/* determine X position according to it's current place */
		if (isPlayerScore) {
			if (ball.getCenterY() + BALL_RADIUS > 0) { // goal, but still haven't crossed the goal line
				ballMoveY();
				ballMoveX();
			} else 
				playerScore();	// complete goal
			
		} else if (isCompScore) {
			if (ball.getCenterY() < getHeight()) {	// goal, but still haven't crossed the goal line
				ballMoveY();
				ballMoveX();
			} else
				compScore();	// complete goal
			
		} else {

			if (ball_x_right) {
				if (getWidth() > ball.getCenterX() + BALL_RADIUS + ball_jumps)
					ballMoveX();
				else {
					ball.setCenterX(getWidth()-BALL_RADIUS);
					ball_x_right = false;
					if (useCompHitPrediction)
						xHitPrediction = getBallXhitPrediction();
				}
			} else {
				if (ball.getCenterX() - ball_jumps > 0)
					ballMoveX();
				else {
					ball.setCenterX(ball.getRadius());
					ball_x_right = true;
					if (useCompHitPrediction)
						xHitPrediction = getBallXhitPrediction();
				}
			}

			/* determine Y position according to it's current place */
			if (ball_y_down) {
				if (getHeight() > ball.getCenterY() + BALL_RADIUS + ball_jumps + PLAYER_HEIGHT) {
					ballMoveY();
				} else {
					if ((ball.getCenterX() + BALL_RADIUS >= player.getX()) && (ball.getCenterX() - BALL_RADIUS <= player.getX() + PLAYER_LENGTH)) {
						ball.setCenterY(getHeight()-BALL_RADIUS-PLAYER_HEIGHT);
						ball_x_right = (ball.getCenterX() + BALL_RADIUS > playerCenterX()) ? true : false ;
						ball_y_down = false;
						if (useCompHitPrediction)
							xHitPrediction = getBallXhitPrediction();
					}
					else
						isCompScore = true;
				}
			} else {
				if (ball.getCenterY() - ball.getRadius() - ball_jumps - COMP_HEIGHT > 0) {
					ballMoveY();
				} else {
					if ((ball.getCenterX() + 2*BALL_RADIUS >= comp.getX()) && (ball.getCenterX() - 2*BALL_RADIUS <= comp.getX() + COMP_LENGTH)) {
						ball.setCenterY(COMP_HEIGHT + ball.getRadius());
						ball_x_right = (ball.getCenterX() + BALL_RADIUS > compCenterX()) ? true : false ;
						ball_y_down = true;
						if (useCompHitPrediction)
							xHitPrediction = getBallXhitPrediction();
					}
					else 
						isPlayerScore = true;
				}
			}

		}

	}
	
	private void ballMoveX() {
		if (ball_x_right)
			ball.setCenterX(ball.getCenterX() + ball_jumps);
		else
			ball.setCenterX(ball.getCenterX() - ball_jumps);
	}
	
	private void ballMoveY() {
		if (ball_y_down)
			ball.setCenterY(ball.getCenterY() + ball_jumps);
		else
			ball.setCenterY(ball.getCenterY() - ball_jumps);
	}
	
	private void playerScore() {
		playerScore++;
		isNewRound = true;
		isPlayerScore = false;
		
		pongView.playerScore();
	}
	
	private void compScore() {
		compScore++;
		isNewRound = true;
		isCompScore = false;
		
		pongView.compScore();
	}
	
	public void play() {
		if (gameON == false) {
			timeline.play();
			gameON = true;
			waitForAction = false;
			
			if (gameState != GameState.PLAY) {
				gameState = GameState.PLAY;
				pongView.gameStateChanged();
			}
		}
	}
	
	public void pause() {
		if (gameON) {
			timeline.stop();
			gameON = false;
			waitForAction = false;
			gameState = GameState.PAUSE;
			pongView.gameStateChanged();
		}
	}
	
	public void stop() {
		if (gameState != GameState.STOP) {
				
			if (gameON)
				timeline.stop();
			
			compScore = 0;
			playerScore = 0;
			level = 1;
			
			setLevel(1);
			
			gameON = false;
			waitForAction = false;
			
			newRound();
			
			gameState = GameState.STOP;
			pongView.gameStateChanged();
		}
	}
	
	public void windowsSizeChanged() {
		newRound();
	}

	public int getCompScore() {
		return compScore;
	}

	public int getPlayerScore() {
		return playerScore;
	}

	public int getLevel() {
		return level;
	}

	public GameState getGameState() {
		return gameState;
	}
	
	public void setLevel(int level) {
		this.level = level;
		
		switch (level) {
			case 1:
				useCompHitPrediction = false;
				ball_jumps = BALL_DEFAULT_JUMPS;
				player_keyboard_jumps = PLAYER_DEFAULT_JUMPS;
				comp_jumps = COMP_DEFAULT_JUMPS;
				ball.setFill(BALL_DEFAULT_COLOR);
				break;
			case 2:
				useCompHitPrediction = false;
				ball_jumps = BALL_DEFAULT_JUMPS + 0.5f;
				player_keyboard_jumps = PLAYER_DEFAULT_JUMPS;
				comp_jumps = COMP_DEFAULT_JUMPS;
				ball.setFill(Color.rgb(78,210,48));
				break;
			case 3:
				useCompHitPrediction = false;
				ball_jumps = BALL_DEFAULT_JUMPS + 1;
				player_keyboard_jumps = PLAYER_DEFAULT_JUMPS + 0.5f;
				comp_jumps = COMP_DEFAULT_JUMPS + 0.5f;
				ball.setFill(Color.rgb(18,184,225));
				break;
			case 4:
				useCompHitPrediction = true;
				ball_jumps = BALL_DEFAULT_JUMPS + 1.5f;
				player_keyboard_jumps = PLAYER_DEFAULT_JUMPS + 1.1f;
				comp_jumps = COMP_DEFAULT_JUMPS + 1.1f;
				ball.setFill(Color.rgb(240,29,228));
				break;
			case 5:
				useCompHitPrediction = true;
				ball_jumps = BALL_DEFAULT_JUMPS + 2;
				player_keyboard_jumps = PLAYER_DEFAULT_JUMPS + 1.3f;
				comp_jumps = COMP_DEFAULT_JUMPS + 1.3f;
				ball.setFill(Color.rgb(245,0,58));
				break;
			case 6:
				useCompHitPrediction = true;
				ball_jumps = BALL_DEFAULT_JUMPS + 3;
				player_keyboard_jumps = PLAYER_DEFAULT_JUMPS + 1.5f;
				comp_jumps = COMP_DEFAULT_JUMPS + 1.5f;
				ball.setFill(Color.rgb(0,42,255));
				break;
		}
		
		refreshScore();
	}
	
}

