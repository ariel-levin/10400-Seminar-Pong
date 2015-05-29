package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import view.utils.PopupMessage;
import model.GameData;
import controller.PongController;
import events.PongEvents.EventType;
import events.PongEvents.GameState;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


/**
 * 
 * @author 	Ariel Levin
 * 			<br/><a href="http://about.me/ariel.levin">about.me/ariel.levin</a>
 * 			<br/><a href="mailto:ariel.lvn89@gmail.com">ariel.lvn89@gmail.com</a><br/><br/>
 *
 * */
public class PongViewJavaFX extends Application implements PongView {
	
	public final int WIDTH 	= 270;
	public final int HEIGHT = 420;

	private int				viewNum;
	private Stage 			primaryStage;
	private Scene 			scene;
	private HBox			buttonPane, scorePane;
	private BorderPane		mainPane;
	private PlayGroundPane 	playGroundPane;
	private PongController 	controller;
	
	private Map<EventType, ActionListener> 	viewListenersMap;


	
	public PongViewJavaFX(int viewNum) {
		super();
		this.viewNum = viewNum;
		
		viewListenersMap = new HashMap<EventType, ActionListener>();
		viewListenersMap.put(EventType.MODEL_GAME_STATE, new GameStateEvent());
		viewListenersMap.put(EventType.LEVEL, new LevelEvent());
		
		try {
			Stage pongViewStage = new Stage();
			start(pongViewStage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void start(Stage arg0) throws Exception {
		this.primaryStage = arg0;
		buildScene();
	}
	
	
	private void buildScene() {
		
		primaryStage.setTitle("Pong v2.0 <#" + viewNum + ">");
		
		mainPane = new BorderPane();

		scene = new Scene(mainPane, WIDTH, HEIGHT);	

		VBox topBox = new VBox(10);
		topBox.setAlignment(Pos.CENTER);

		buttonPane = new HBox();
		buttonPane.setAlignment(Pos.CENTER);
		buttonPane.spacingProperty().bind(mainPane.widthProperty().divide(6));
		
		scorePane = new HBox();
		scorePane.setAlignment(Pos.CENTER);
		scorePane.spacingProperty().bind(mainPane.widthProperty().divide(8));
		scorePane.setPadding(new Insets(0,10,10,10));
		
		topBox.getChildren().addAll(createMenu(), buttonPane, scorePane);
		
		playGroundPane = new PlayGroundPane(this);

		mainPane.setTop(topBox);
		mainPane.setCenter(playGroundPane);
		
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent ke) {
				if (ke.getCode() == KeyCode.ESCAPE) {
					primaryStage.close();
				}
			}
		});
		
		primaryStage.setOnCloseRequest((new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent arg0) {
				viewClose();
			}
		}));

		scene.widthProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(
					ObservableValue<? extends Number> observableValue,
					Number oldSceneWidth, Number newSceneWidth) {
				playGroundPane.windowsSizeChanged();
			}
		});

		scene.heightProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(
					ObservableValue<? extends Number> observableValue,
					Number oldSceneHeight, Number newSceneHeight) {
				playGroundPane.windowsSizeChanged();
			}
		});
		
		primaryStage.setScene(scene);
		primaryStage.show();
		
		playGroundPane.placeComponents();
		playGroundPane.requestFocus();
		
		primaryStage.setX(primaryStage.getX() + WIDTH + 50 + viewNum*40);
		primaryStage.setY(primaryStage.getY() + viewNum*40);
	}
	
	private MenuBar createMenu() {
		
		final Menu fileMenu = new Menu("File");

		MenuItem exitMenuItem = new MenuItem("Exit");
		exitMenuItem.setOnAction(new EventHandler<javafx.event.ActionEvent>() {

			@Override
			public void handle(javafx.event.ActionEvent arg0) {
				viewClose();
				primaryStage.close();
			}
		});
		fileMenu.getItems().add(exitMenuItem);
		
		//////////////////////////////////////////////////////
		
		final Menu actionMenu = new Menu("Actions");
		
		MenuItem startMenuItem = new MenuItem("Start");
		startMenuItem.setOnAction(new EventHandler<javafx.event.ActionEvent>() {

			@Override
			public void handle(javafx.event.ActionEvent arg0) {
				playGroundPane.play();
			}
		});
		
		MenuItem pauseMenuItem = new MenuItem("Pause");
		pauseMenuItem.setOnAction(new EventHandler<javafx.event.ActionEvent>() {

			@Override
			public void handle(javafx.event.ActionEvent arg0) {
				playGroundPane.pause();
			}
		});
		
		MenuItem stopMenuItem = new MenuItem("Stop");
		stopMenuItem.setOnAction(new EventHandler<javafx.event.ActionEvent>() {

			@Override
			public void handle(javafx.event.ActionEvent arg0) {
				playGroundPane.stop();
			}
		});
		
		actionMenu.getItems().addAll(startMenuItem, pauseMenuItem, stopMenuItem);

		//////////////////////////////////////////////////////
		
		final Menu helpMenu = new Menu("Help");
		
		MenuItem aboutMenuItem = new MenuItem("About");
		aboutMenuItem.setOnAction(new EventHandler<javafx.event.ActionEvent>() {

			@Override
			public void handle(javafx.event.ActionEvent arg0) {
				String msg = 	"Ariel Levin\n" +
								"ariel.lvn89@gmail.com\n" +
								"http://about.me/ariel.levin";
				
				final Popup popup = PopupMessage.createPopup(msg, primaryStage);

				popup.show(primaryStage);
			}
		});
		helpMenu.getItems().add(aboutMenuItem);
		
		//////////////////////////////////////////////////////
		
		MenuBar menuBar = new MenuBar();
		menuBar.getMenus().addAll(fileMenu, actionMenu, helpMenu);
		
		return menuBar;
	}
	
	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public Scene getScene() {
		return scene;
	}

	public HBox getButtonPane() {
		return buttonPane;
	}

	public HBox getScorePane() {
		return scorePane;
	}
	
	@Override
	public void setController(PongController controller) {
		this.controller = controller;
		if (controller != null) {
			
			for (EventType et : viewListenersMap.keySet())
				controller.addActionListener(viewListenersMap.get(et), et);
			
			controller.processEvent(EventType.VIEW_OPEN, new ActionEvent(this,
					viewNum, EventType.VIEW_OPEN.toString()));
		}
	}

	@Override
	public int getViewNum() {
		return viewNum;
	}
	
	@Override
	public int getCompScore() {
		return playGroundPane.getCompScore();
	}

	@Override
	public int getPlayerScore() {
		return playGroundPane.getPlayerScore();
	}

	@Override
	public int getLevel() {
		return playGroundPane.getLevel();
	}

	@Override
	public GameState getGameState() {
		return playGroundPane.getGameState();
	}

	public void playerScore() {
		controller.processEvent(EventType.PLAYER_SCORE, new ActionEvent(this,
				viewNum, EventType.PLAYER_SCORE.toString()));
	}
	
	public void compScore() {
		controller.processEvent(EventType.COMP_SCORE, new ActionEvent(this,
				viewNum, EventType.COMP_SCORE.toString()));
	}
	
	public void gameStateChanged() {
		controller.processEvent(EventType.VIEW_GAME_STATE, new ActionEvent(this,
				viewNum, EventType.VIEW_GAME_STATE.toString()));
	}
	
	public void viewClose() {
		for (EventType et : viewListenersMap.keySet())
			controller.removeActionListener(viewListenersMap.get(et), et);
		
		controller.processEvent(EventType.VIEW_CLOSE,
				new ActionEvent(PongViewJavaFX.this, viewNum, EventType.VIEW_CLOSE.toString()));
	}
	
	
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	
	
	class GameStateEvent implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			
			GameData game = (GameData)e.getSource();
			
			if (game.getViewNum() == viewNum) {
				
				switch (game.getGameState()) {
				
					case PLAY:
						playGroundPane.play();
						break;
					case PAUSE:
						playGroundPane.pause();
						break;
					case STOP:
						playGroundPane.stop();
						break;
				}
			}
		}
	}
	
	
	class LevelEvent implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {

			GameData game = (GameData)e.getSource();
			
			if (game.getViewNum() == viewNum)
				playGroundPane.setLevel(game.getLevel());
		}
	}
	
}

