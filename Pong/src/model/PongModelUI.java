package model;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


/**
 * 
 * @author 	Ariel Levin
 * 			<br/><a href="http://about.me/ariel.levin">about.me/ariel.levin</a>
 * 			<br/><a href="mailto:ariel.lvn89@gmail.com">ariel.lvn89@gmail.com</a><br/><br/>
 *
 * */
public class PongModelUI extends Application {

	public final int WIDTH = 300;
	public final int HEIGHT = 150;

	private Stage 		primaryStage;
	private Scene 		scene;
	private BorderPane	mainPane;


	@Override
	public void start(Stage arg0) throws Exception {
		this.primaryStage = arg0;
		buildScene();
	}
	
	
	private void buildScene() {
		
		primaryStage.setTitle("Pong Model");
		
		mainPane = new BorderPane();
		scene = new Scene(mainPane, WIDTH, HEIGHT);	

		Button btnAddView = new Button("This is Pong Model UI");
		
//		btnAddView.setOnAction(new EventHandler<ActionEvent>() {
//
//			@Override
//			public void handle(ActionEvent arg0) {
//
//				PongView gameView = new PongView(views.size() + 1);
//				try {
//					Stage gameViewStage = new Stage();
//					gameView.start(gameViewStage);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//				
//				views.add(gameView);
//			}
//		});
		
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
		
		primaryStage.setX(primaryStage.getX() - WIDTH - 150);
	}

}

