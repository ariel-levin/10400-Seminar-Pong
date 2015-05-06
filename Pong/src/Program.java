import java.util.LinkedList;
import java.util.List;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import view.GameView;


public class Program extends Application {

	public final int WIDTH = 300;
	public final int HEIGHT = 150;

	private Stage 		primaryStage;
	private Scene 		scene;
	private BorderPane	mainPane;
	
	private List<GameView> views;
	
	
	
	public static void main(String[] args) {
		launch(args);
		
	}

	@Override
	public void start(Stage arg0) throws Exception {
		this.primaryStage = arg0;
		buildScene();
	}
	
	
	private void buildScene() {
		
		primaryStage.setTitle("Main Program");
		
		views = new LinkedList<GameView>();
		
		mainPane = new BorderPane();
		scene = new Scene(mainPane, WIDTH, HEIGHT);	

		Button btnAddView = new Button("Add Game View");
		
		btnAddView.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				GameView gameView = new GameView(views.size() + 1);
				try {
					Stage gameViewStage = new Stage();
					gameView.start(gameViewStage);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				views.add(gameView);
			}
		});
		
		mainPane.setCenter(btnAddView);

		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent ke) {
				if (ke.getCode() == KeyCode.ESCAPE) {
					primaryStage.close();
				}
			}
		});
		
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}

}

