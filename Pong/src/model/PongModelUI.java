package model;

import java.util.Comparator;

import javafx.application.Application;
import javafx.application.Platform;
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
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
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
	public final int HEIGHT = 450;

	private PongModel					model;
	private Stage 						primaryStage;
	private Scene 						scene;
	private BorderPane					mainPane;
	private TableView<GameData>			table;
	private ObservableList<GameData> 	tableData = FXCollections.observableArrayList();
	private ComboBox<GameData> 			cbViews;
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
		
		mainPane.setPadding(new Insets(10,10,10,10));

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
		mainPane.setCenter(table);
	}
	
	private void createTopControlPanel() {
		
		HBox topPanel = new HBox();
		topPanel.setAlignment(Pos.CENTER);
		topPanel.setPadding(new Insets(10,20,20,20));
		topPanel.spacingProperty().bind(mainPane.widthProperty().divide(8));
		
		cbViews = new ComboBox<GameData>();
		
		Button btnPlay 	= new Button("Play");
		Button btnPause = new Button("Pause");
		Button btnStop 	= new Button("Stop");
		
		btnPlay.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				model.playPressed(cbViews.getValue());
			}
		});
		
		btnPause.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				model.pausePressed(cbViews.getValue());
			}
		});
		
		btnStop.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				model.stopPressed(cbViews.getValue());
			}
		});
		
		topPanel.getChildren().addAll(cbViews, btnPlay, btnPause, btnStop);
		mainPane.setTop(topPanel);
	}
	
	private void createConsole() {

		HBox bottomPanel = new HBox();
		bottomPanel.setAlignment(Pos.CENTER);
		bottomPanel.setPadding(new Insets(10,0,0,0));
		
		console = new TextArea();
		console.setEditable(false);
		console.setPrefSize(mainPane.getWidth(), 120);
		console.prefWidthProperty().bind(mainPane.widthProperty());
		
		bottomPanel.getChildren().add(console);
		mainPane.setBottom(bottomPanel);
	}

	private int getViewIndexInTable(GameData game) {
		for (int i = 0; i < tableData.size(); i++ ) {
			if (tableData.get(i).getViewNum() == game.getViewNum())
				return i;
		}
		return -1;
	}
	
	private int getViewIndexInComboBox(GameData game) {
		for (int i = 0 ; i < cbViews.getItems().size(); i++ ) {
			if (cbViews.getItems().get(i).getViewNum() == game.getViewNum())
				return i;
		}
		return -1;
	}
	
	public void viewAdded(GameData game) {
		tableData.add(game);
		cbViews.getItems().add(game);
		cbViews.setItems(new SortedList<GameData>(cbViews.getItems(), new Comparator<GameData>() {

			@Override
			public int compare(GameData o1, GameData o2) {
				return Integer.compare(o1.getViewNum(), o2.getViewNum());
			}
		}));
		
		console.setText(console.getText() + "\nView #" + game.getViewNum() + " was added");
	}
	
	public void viewClosed(GameData game) {
		int index = getViewIndexInTable(game);
		if (index != -1)
			tableData.remove(index);
		
		index = getViewIndexInComboBox(game);
		if (index != -1)
			cbViews.getItems().remove(index);
		
		console.setText(console.getText() + "\nView #" + game.getViewNum() + " was closed");
	}
	
	public void playerScoreChanged(GameData game) {
		int index = getViewIndexInTable(game);
		if (index != -1)
			tableData.get(index).setPlayerScore(game.getPlayerScore());
		
		console.setText(console.getText() + "\nView #" + game.getViewNum() + " >> Player Scored!");
	}
	
	public void compScoreChanged(GameData game) {
		int index = getViewIndexInTable(game);
		if (index != -1)
			tableData.get(index).setCompScore(game.getCompScore());
		
		console.setText(console.getText() + "\nView #" + game.getViewNum() + " >> Computer Scored!");
	}
	
	public void levelChanged(GameData game) {
		int index = getViewIndexInTable(game);
		if (index != -1)
			tableData.get(index).setLevel(game.getLevel());
		
		console.setText(console.getText() + "\nView #" + game.getViewNum() + " >> Level Up! (Level " + game.getLevel() + ")");
	}
	
	public void gameStateChange(GameData game, GameState state) {
		int index = getViewIndexInTable(game);
		if (index != -1)
			tableData.get(index).setGameState(game.getGameState());
		
		console.setText(console.getText() + "\nView #" + game.getViewNum() + " >> Game State changed to: " + game.getGameState());
	}
	
}

