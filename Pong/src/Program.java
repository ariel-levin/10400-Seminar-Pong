import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;


/**
 * 
 * @author 	Ariel Levin
 * 			<br/><a href="http://about.me/ariel.levin">about.me/ariel.levin</a>
 * 			<br/><a href="mailto:ariel.lvn89@gmail.com">ariel.lvn89@gmail.com</a><br/><br/>
 *
 * */
public class Program extends Application {
	
	public final int WIDTH = 250;
	public final int HEIGHT = 380;

	private Stage 	primaryStage;
	private Scene 	scene;
	private FlowPane 	buttonPane, scorePane;
	private GridPane	topPane;
	private BorderPane	mainPane;
	private PlayGroundPane playGroundPane;
	
	
	public static void main(String[] args) {
		launch(args);
	}
	
	
	@Override
	public void start(Stage arg0) throws Exception {
		this.primaryStage = arg0;
		buildScene();
	}
	
	
	private void buildScene() {
		
		primaryStage.setTitle("Pong v2.0");	// title
		
//		mainPane = new FlowPane(Orientation.VERTICAL);
//		mainPane.setColumnHalignment(HPos.CENTER);
//		mainPane.setAlignment(Pos.CENTER);
//		mainPane.setVgap(30);
		mainPane = new BorderPane();
		
		scene = new Scene(mainPane, WIDTH, HEIGHT);	
		
		topPane = new GridPane();
		topPane.setVgap(15);
		
		buttonPane = new FlowPane(Orientation.HORIZONTAL);
		buttonPane.setColumnHalignment(HPos.CENTER);
		buttonPane.setAlignment(Pos.TOP_CENTER);
		buttonPane.setHgap(30);
		GridPane.setConstraints(buttonPane, 1, 2);
		
		scorePane = new FlowPane(Orientation.HORIZONTAL);
		scorePane.setColumnHalignment(HPos.CENTER);
		scorePane.setAlignment(Pos.TOP_CENTER);
		scorePane.setHgap(30);
		GridPane.setConstraints(scorePane, 1, 3);
		
		topPane.getChildren().addAll(buttonPane, scorePane);
		
		playGroundPane = new PlayGroundPane(buttonPane, scorePane, this);
		//topPanel = new JPanel();
		//PlayGroundPane = new PlayGroundPane();

		mainPane.setTop(topPane);
		mainPane.setCenter(playGroundPane);
		//getContentPane().add(topPanel, BorderLayout.NORTH);
		//getContentPane().add(PlayGroundPane, BorderLayout.CENTER);
		
		createMenu();
		//setJMenuBar(new Menu());
		//PlayGroundPane.setFocusable(true);

//		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//		Point middle = new Point(screenSize.width / 2, screenSize.height / 2);
//		Point newLocation = new Point(middle.x - (getWidth() / 2) - WIDTH,
//				middle.y - (getHeight() / 2) - HEIGHT / 2);
//		setLocation(newLocation);
		
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
	        public void handle(KeyEvent ke) {
	            if (ke.getCode() == KeyCode.ESCAPE) {
	                System.out.println("Key Pressed: " + ke.getCode());
	                primaryStage.close();
	            }
	        }
	    });
		
		primaryStage.setScene(scene);
		primaryStage.show();
		
		playGroundPane.placeComponents();
		playGroundPane.requestFocus();
		
		//setLocationRelativeTo(null);
		//setAlwaysOnTop(false);
		//setDefaultCloseOperation(EXIT_ON_CLOSE);
		//setResizable(false);
		//setSize(WIDTH, HEIGHT);
		//setVisible(true);
		
	}
	
	private void createMenu() {
		final Menu menu1 = new Menu("File");
		final Menu menu2 = new Menu("Options");
		final Menu menu3 = new Menu("Help");

		MenuBar menuBar = new MenuBar();
		menuBar.getMenus().addAll(menu1, menu2, menu3);
		
//		JMenu actionMenu = new JMenu("Actions");
//		
//		JMenuItem startMenuItem = new JMenuItem("Start");
//		startMenuItem.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//
//				PlayGroundPane.start();
//			}
//		});
//		actionMenu.add(startMenuItem);
//		
//		JMenuItem pauseMenuItem = new JMenuItem("Pause");
//		pauseMenuItem.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//
//				PlayGroundPane.pause();
//			}
//		});
//		actionMenu.add(pauseMenuItem);
//		
//		JMenuItem stopMenuItem = new JMenuItem("Stop");
//		stopMenuItem.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//
//				PlayGroundPane.stop();
//			}
//		});
//		actionMenu.add(stopMenuItem);
//		
//		JMenuItem exitMenuItem = new JMenuItem("Exit");
//		exitMenuItem.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//
//				System.exit(0);
//			}
//		});
//		actionMenu.add(exitMenuItem);
//		
//		this.add(actionMenu);
//
//		
//		/////////////////////////////////////////////////////////////////////////////////////
//
//		JMenu helpMenu = new JMenu("Help");
//		
//		JMenuItem aboutItem = new JMenuItem("About");
//		aboutItem.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				String msg = 	"Ariel Levin\n" +
//								"ariel.lvn89@gmail.com\n" +
//								"http://about.me/ariel.levin";
//				
//				JOptionPane.showMessageDialog(null,msg,"About Pong",JOptionPane.INFORMATION_MESSAGE);
//			}
//		});
//		helpMenu.add(aboutItem);
//		
//		this.add(helpMenu);
	}

}

	