import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
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

	private Stage 		primaryStage;
	private Scene 		scene;
	private BorderPane	mainPane;
	

	private PongController controller;
	
	
	public static void main(String[] args) {
		launch(args);
		
	}

	@Override
	public void start(Stage arg0) throws Exception {
		this.primaryStage = arg0;
		
		controller = new PongController();		// creating controller
		controller.setModel(new PongModel());	// creating model
		
		buildScene();
	}
	
	
	private void buildScene() {
		
		primaryStage.setTitle("Main Program");
		
		mainPane = new BorderPane();
		scene = new Scene(mainPane, WIDTH, HEIGHT);	

		Button btnAddView = new Button("Add Pong View");
		
		btnAddView.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				
				// adding view
				controller.addView(new PongView(controller.getViewsCount() + 1));
			}
		});
		
		mainPane.setCenter(btnAddView);

		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent ke) {
				if (ke.getCode() == KeyCode.ESCAPE) {
//					primaryStage.close();
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

