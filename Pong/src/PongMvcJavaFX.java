import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.PongModel;
import view.PongViewJavaFX;
import view.utils.PopupMessage;
import controller.PongController;


/**
 * Main Program
 * 
 * @author 	Ariel Levin
 * 			<br/><a href="http://about.me/ariel.levin">about.me/ariel.levin</a>
 * 			<br/><a href="mailto:ariel.lvn89@gmail.com">ariel.lvn89@gmail.com</a><br/><br/>
 *
 * */
public class PongMvcJavaFX extends Application {
	
	public final int WIDTH 	= 300;
	public final int HEIGHT = 150;

	private Stage 			primaryStage;
	private Scene 			scene;
	private Button 			btnAddView, btnCreateModel;
	private PongController 	controller;
	private int				viewsCounter = 0;
	
	
	public static void main(String[] args) {
		launch(args);
		
	}

	@Override
	public void start(Stage arg0) throws Exception {
		this.primaryStage = arg0;
		
		controller = new PongController();	// creating controller
		
		buildScene();
	}
	
	private void buildScene() {
		
		primaryStage.setTitle("Main Program");
		
		BorderPane mainPane = new BorderPane();
		
		scene = new Scene(mainPane, WIDTH, HEIGHT);	
		
		HBox centerPane = new HBox();
		centerPane.setAlignment(Pos.CENTER);
		centerPane.setPadding(new Insets(0,20,0,20));
		centerPane.setSpacing(30);

		btnCreateModel = new Button("Create Pong Model");
		btnCreateModel.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				
				// creating model
				controller.setModel(new PongModel());
				btnAddView.setDisable(false);
				btnCreateModel.setDisable(true);
			}
		});
		
		btnAddView = new Button("Add Pong View");
		btnAddView.setDisable(true);
		btnAddView.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				
				// adding view
				controller.addView(new PongViewJavaFX(++viewsCounter));
			}
		});
		
		centerPane.getChildren().addAll(btnCreateModel, btnAddView);

		mainPane.setTop(createMenu());
		mainPane.setCenter(centerPane);
		
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
	
}
