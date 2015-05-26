package model;

import events.PongEvents;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;


/**
 * 
 * @author 	Ariel Levin
 * 			<br/><a href="http://about.me/ariel.levin">about.me/ariel.levin</a>
 * 			<br/><a href="mailto:ariel.lvn89@gmail.com">ariel.lvn89@gmail.com</a><br/><br/>
 *
 * */
public class PongModelUI extends Application implements PongEvents {

	public final int WIDTH 	= 485;
	public final int HEIGHT = 300;

	private Stage 						primaryStage;
	private Scene 						scene;
	private BorderPane					mainPane;
	private TableView<GameData>			table;
	private ObservableList<GameData> 	tableData = FXCollections.observableArrayList();


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
		
		tableData.add(new GameData(1,5,0,2,GameState.PLAY));
		
	}
	
	private void createConsole() {
		tableData.get(0).setLevel(5);
	}

}

