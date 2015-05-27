package model;

import java.util.Comparator;

import view.utils.PopupMessage;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import events.PongEvents;


/**
 * 
 * @author 	Ariel Levin
 * 			<br/><a href="http://about.me/ariel.levin">about.me/ariel.levin</a>
 * 			<br/><a href="mailto:ariel.lvn89@gmail.com">ariel.lvn89@gmail.com</a><br/><br/>
 *
 * */
public class PongModelUI extends Application implements PongEvents {

	public final int WIDTH 	= 500;
	public final int HEIGHT = 470;

	private PongModel					model;
	private Stage 						primaryStage;
	private Scene 						scene;
	private BorderPane					mainPane;
	private TableView<GameData>			table;
	private ObservableList<GameData> 	tableData = FXCollections.observableArrayList();
	private ObservableList<GameData> 	cbItems = FXCollections.observableArrayList();
	private ComboBox<GameData> 			cbGames;
	private TextArea					console;
	
	
	public PongModelUI(PongModel model) {
		super();
		this.model = model;
	}

	@Override
	public void start(Stage arg0) throws Exception {
		this.primaryStage = arg0;
		buildScene();
	}
	

	private void buildScene() {
		
		primaryStage.setTitle("Pong Model");
		
		mainPane = new BorderPane();
		scene = new Scene(mainPane, WIDTH, HEIGHT);	

		createTable();
		createTopControlPanel();
		createConsole();

		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent ke) {
				if (ke.getCode() == KeyCode.ESCAPE) {
					Platform.exit();
				}
			}
		});
		
		primaryStage.setOnCloseRequest((new EventHandler<WindowEvent>() {

			@Override
			public void handle(WindowEvent arg0) {
				Platform.exit();
			}
		}));
		
		primaryStage.setScene(scene);
		primaryStage.show();
		
		primaryStage.setX(primaryStage.getX() - WIDTH);
	}
	
	@SuppressWarnings("unchecked")
	private void createTable() {
		
		table = new TableView<GameData>();
		
		TableColumn<GameData, Integer> viewNumCol = new TableColumn<>("View Number");
		viewNumCol.setCellValueFactory(new PropertyValueFactory<GameData, Integer>("viewNum"));
		viewNumCol.setCellFactory(new Callback<TableColumn<GameData, Integer>, TableCell<GameData, Integer>>() {
			
			@Override
			public TableCell<GameData, Integer> call(TableColumn<GameData, Integer> p) {
				
				TableCell<GameData, Integer> tc = new TableCell<GameData, Integer>() {
					@Override
					public void updateItem(Integer item, boolean empty) {
						super.updateItem(item, empty);
						if (!empty) {
							int num = 0;
							if(tableData != null)
								num = tableData.get(this.getIndex()).getViewNum();
							setText(num + "");
						}
						else
							setText(null);
					}
				};
				tc.setAlignment(Pos.CENTER);
				return tc;
			}
		});
		viewNumCol.setMinWidth(100);
		
		TableColumn<GameData, Integer> playerScoreCol = new TableColumn<>("Player Score");
		playerScoreCol.setCellValueFactory(new PropertyValueFactory<GameData, Integer>("playerScore"));
		playerScoreCol.setCellFactory(new Callback<TableColumn<GameData, Integer>, TableCell<GameData, Integer>>() {
			
			@Override
			public TableCell<GameData, Integer> call(TableColumn<GameData, Integer> p) {
				
				TableCell<GameData, Integer> tc = new TableCell<GameData, Integer>() {
					@Override
					public void updateItem(Integer item, boolean empty) {
						super.updateItem(item, empty);
						if (!empty) {
							int score = 0;
							if(tableData != null)
								score = tableData.get(this.getIndex()).getPlayerScore();
							setText(score + "");
						}
						else
							setText(null);
					}
				};
				tc.setAlignment(Pos.CENTER);
				return tc;
			}
		});
		playerScoreCol.setMinWidth(90);
		
		TableColumn<GameData, Integer> compScoreCol = new TableColumn<>("Computer Score");
		compScoreCol.setCellValueFactory(new PropertyValueFactory<GameData, Integer>("compScore"));
		compScoreCol.setCellFactory(new Callback<TableColumn<GameData, Integer>, TableCell<GameData, Integer>>() {
			
			@Override
			public TableCell<GameData, Integer> call(TableColumn<GameData, Integer> p) {
				
				TableCell<GameData, Integer> tc = new TableCell<GameData, Integer>() {
					@Override
					public void updateItem(Integer item, boolean empty) {
						super.updateItem(item, empty);
						if (!empty) {
							int score = 0;
							if(tableData != null)
								score = tableData.get(this.getIndex()).getCompScore();
							setText(score + "");
						}
						else
							setText(null);
					}
				};
				tc.setAlignment(Pos.CENTER);
				return tc;
			}
		});
		compScoreCol.setMinWidth(110);
		
		TableColumn<GameData, Integer> levelCol = new TableColumn<>("Level");
		levelCol.setCellValueFactory(new PropertyValueFactory<GameData, Integer>("level"));
		levelCol.setCellFactory(new Callback<TableColumn<GameData, Integer>, TableCell<GameData, Integer>>() {
			
			@Override
			public TableCell<GameData, Integer> call(TableColumn<GameData, Integer> p) {
				
				TableCell<GameData, Integer> tc = new TableCell<GameData, Integer>() {
					@Override
					public void updateItem(Integer item, boolean empty) {
						super.updateItem(item, empty);
						if (!empty) {
							int level = 0;
							if(tableData != null)
								level = tableData.get(this.getIndex()).getLevel();
							setText(level + "");
						}
						else
							setText(null);
					}
				};
				tc.setAlignment(Pos.CENTER);
				return tc;
			}
		});
		levelCol.setMinWidth(60);
		
		TableColumn<GameData, GameState> stateCol = new TableColumn<>("Game State");
		stateCol.setCellValueFactory(new PropertyValueFactory<GameData, GameState>("gameState"));
		stateCol.setCellFactory(new Callback<TableColumn<GameData, GameState>, TableCell<GameData, GameState>>() {
			
			@Override
			public TableCell<GameData, GameState> call(TableColumn<GameData, GameState> p) {
				
				TableCell<GameData, GameState> tc = new TableCell<GameData, GameState>() {
					@Override
					public void updateItem(GameState item, boolean empty) {
						super.updateItem(item, empty);
						if (!empty) {
							GameState state = GameState.STOP;
							if(tableData != null)
								state = tableData.get(this.getIndex()).getGameState();
							setText(state + "");
						}
						else
							setText(null);
					}
				};
				tc.setAlignment(Pos.CENTER);
				return tc;
			}
		});
		stateCol.setMinWidth(80);
		
		table.getColumns().addAll(viewNumCol, playerScoreCol, compScoreCol, levelCol, stateCol);
		table.setItems(tableData);
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		BorderPane centerPane = new BorderPane();
		centerPane.setPadding(new Insets(0,10,10,10));
		centerPane.setCenter(table);
		mainPane.setCenter(centerPane);
	}
	
	private void createTopControlPanel() {
		
		VBox topBox = new VBox();
		topBox.setAlignment(Pos.CENTER);
		
		HBox topBtnBox = new HBox();
		topBtnBox.setAlignment(Pos.CENTER);
		topBtnBox.setPadding(new Insets(20,20,20,20));
		topBtnBox.spacingProperty().bind(mainPane.widthProperty().divide(8));
		
		cbGames = new ComboBox<GameData>();
		
		Button btnPlay 	= new Button("Play");
		Button btnPause = new Button("Pause");
		Button btnStop 	= new Button("Stop");
		
		btnPlay.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				GameData game = cbGames.getValue();
				game.setGameState(GameState.PLAY);
				model.modelUIgameStateChange(game);
			}
		});
		
		btnPause.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				GameData game = cbGames.getValue();
				game.setGameState(GameState.PAUSE);
				model.modelUIgameStateChange(game);
			}
		});
		
		btnStop.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				GameData game = cbGames.getValue();
				game.setGameState(GameState.STOP);
				model.modelUIgameStateChange(game);
			}
		});
		
		topBtnBox.getChildren().addAll(cbGames, btnPlay, btnPause, btnStop);
		topBox.getChildren().addAll(createMenu(), topBtnBox);
		mainPane.setTop(topBox);
	}
	
	private void createConsole() {

		HBox bottomPanel = new HBox();
		bottomPanel.setAlignment(Pos.CENTER);
		bottomPanel.setPadding(new Insets(10,0,0,0));
		
		console = new TextArea();
		console.setEditable(false);
		console.setPrefSize(mainPane.getWidth(), 120);
		console.prefWidthProperty().bind(mainPane.widthProperty());
		console.textProperty().addListener(new ChangeListener<Object>() {
			public void changed(ObservableValue<?> ov, Object oldValue, Object newValue) {
				console.setScrollTop(Double.MIN_VALUE);
			}
		});
		
		bottomPanel.getChildren().add(console);
		bottomPanel.setPadding(new Insets(0,10,10,10));
		mainPane.setBottom(bottomPanel);
	}

	private MenuBar createMenu() {
		
		final Menu fileMenu = new Menu("File");

		MenuItem exitMenuItem = new MenuItem("Exit");
		exitMenuItem.setOnAction(new EventHandler<javafx.event.ActionEvent>() {

			@Override
			public void handle(javafx.event.ActionEvent arg0) {
				Platform.exit();
			}
		});
		fileMenu.getItems().add(exitMenuItem);

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
		menuBar.getMenus().addAll(fileMenu, helpMenu);
		
		return menuBar;
	}
		
	private int getViewIndexInTable(GameData game) {
		for (int i = 0; i < tableData.size(); i++ ) {
			if (tableData.get(i).getViewNum() == game.getViewNum())
				return i;
		}
		return -1;
	}
	
	private int getViewIndexInComboBox(GameData game) {
		for (int i = 0 ; i < cbGames.getItems().size(); i++ ) {
			if (cbGames.getItems().get(i).getViewNum() == game.getViewNum())
				return i;
		}
		return -1;
	}
	
	public void viewAdded(GameData game) {
		tableData.add(game);
		cbItems.add(game);
		cbGames.setItems(new SortedList<GameData>(cbItems, new Comparator<GameData>() {

			@Override
			public int compare(GameData o1, GameData o2) {
				return Integer.compare(o1.getViewNum(), o2.getViewNum());
			}
		}));
		
		console.appendText("\nView #" + game.getViewNum() + " >> was added");
	}
	
	public void viewClosed(GameData game) {
		int index = getViewIndexInTable(game);
		if (index != -1)
			tableData.remove(index);
		
		index = getViewIndexInComboBox(game);
		if (index != -1)
			cbItems.remove(index);
		
		console.appendText("\nView #" + game.getViewNum() + " >> was closed");
	}
	
	public void playerScoreChanged(GameData game) {
		int index = getViewIndexInTable(game);
		if (index != -1)
			tableData.set(index, game);
		
		console.appendText("\nView #" + game.getViewNum()
				+ " >> Player Scored! (Score = " + game.getPlayerScore() + ")");
	}
	
	public void compScoreChanged(GameData game) {
		int index = getViewIndexInTable(game);
		if (index != -1)
			tableData.set(index, game);
		
		console.appendText("\nView #" + game.getViewNum()
				+ " >> Computer Scored! (Score = " + game.getCompScore() + ")");
	}
	
	public void levelChanged(GameData game) {
		int index = getViewIndexInTable(game);
		if (index != -1)
			tableData.set(index, game);
		
		console.appendText("\nView #" + game.getViewNum()
				+ " >> Level Up! (Level " + game.getLevel() + ")");
	}
	
	public void gameStateChange(GameData game) {
		int index = getViewIndexInTable(game);
		if (index != -1)
			tableData.set(index, game);
		
		console.appendText("\nView #" + game.getViewNum()
				+ " >> Game State changed to: " + game.getGameState());
	}
	
}

