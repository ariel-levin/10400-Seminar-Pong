import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.PongModel;
import view.PongView;
import controller.PongController;


/**
 * 
 * @author 	Ariel Levin
 * 			<br/><a href="http://about.me/ariel.levin">about.me/ariel.levin</a>
 * 			<br/><a href="mailto:ariel.lvn89@gmail.com">ariel.lvn89@gmail.com</a><br/><br/>
 *
 * */
public class Program extends Application {

	public final int WIDTH 	= 300;
	public final int HEIGHT = 150;

	private Stage 			primaryStage;
	private Scene 			scene;
	private HBox			mainPane;
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
		
		mainPane = new HBox();
		mainPane.setAlignment(Pos.CENTER);
		mainPane.setPadding(new Insets(0,20,0,20));
		mainPane.setSpacing(30);
		
		scene = new Scene(mainPane, WIDTH, HEIGHT);	

		btnCreateModel = new Button("Create Pong Model");
		btnCreateModel.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				
				// adding view
				controller.setModel(new PongModel());		// creating model
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
				controller.addView(new PongView(++viewsCounter));
			}
		});
		
		mainPane.getChildren().addAll(btnCreateModel, btnAddView);

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

}

