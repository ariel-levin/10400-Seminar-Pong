package model;

import java.util.ArrayList;

import controller.PongController;
import javafx.stage.Stage;


/**
 * 
 * @author 	Ariel Levin
 * 			<br/><a href="http://about.me/ariel.levin">about.me/ariel.levin</a>
 * 			<br/><a href="mailto:ariel.lvn89@gmail.com">ariel.lvn89@gmail.com</a><br/><br/>
 *
 * */
public class PongModel {

	private PongModelUI 			modelUI;
	private PongController 			controller;
	private ArrayList<GameData>		gameData;
	
	
	public PongModel(PongController controller) {
		
		this.controller = controller;
		
		modelUI = new PongModelUI(this);
		try {
			Stage gameViewStage = new Stage();
			modelUI.start(gameViewStage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void playPressed(GameData game) {
		
	}
	
	public void pausePressed(GameData game) {
		
	}
	
	public void stopPressed(GameData game) {
		
	}
	
	
}
