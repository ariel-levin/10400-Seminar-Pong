package model;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import view.PongView;
import controller.PongController;
import events.PongEvents.EventType;
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
	private Map<Integer, GameData>	gameData;
	
	
	public PongModel() {
		
		gameData = new HashMap<Integer, GameData>();
		modelUI = new PongModelUI(this);
		try {
			Stage gameViewStage = new Stage();
			modelUI.start(gameViewStage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void setController(PongController controller) {
		this.controller = controller;
		if (controller != null) {
			controller.addActionListener(new PlayEvent(), EventType.VIEW_PLAY);
			controller.addActionListener(new PauseEvent(), EventType.VIEW_PAUSE);
			controller.addActionListener(new StopEvent(), EventType.VIEW_STOP);
			controller.addActionListener(new ViewOpenEvent(), EventType.VIEW_OPEN);
			controller.addActionListener(new ViewCloseEvent(), EventType.VIEW_CLOSE);
			controller.addActionListener(new PlayerScoreEvent(), EventType.PLAYER_GOAL);
			controller.addActionListener(new CompScoreEvent(), EventType.COMP_GOAL);
		}
	}

	public Map<Integer, GameData> getGameData() {
		return gameData;
	}

	public void modelUIplayPressed(GameData game) {
		
	}
	
	public void modelUIpausePressed(GameData game) {
		
	}
	
	public void modelUIstopPressed(GameData game) {
		
	}
	
	
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////

	
	class PlayEvent implements ActionListener {

		@Override
		public void actionPerformed(java.awt.event.ActionEvent e) {
			
			// TODO Auto-generated method stub
		}
	}
	
	class PauseEvent implements ActionListener {

		@Override
		public void actionPerformed(java.awt.event.ActionEvent e) {
			
			// TODO Auto-generated method stub
		}
	}
	
	class StopEvent implements ActionListener {

		@Override
		public void actionPerformed(java.awt.event.ActionEvent e) {
			
			// TODO Auto-generated method stub
		}
	}
	
	class ViewOpenEvent implements ActionListener {

		@Override
		public void actionPerformed(java.awt.event.ActionEvent e) {
			
			PongView view = (PongView)e.getSource();
			GameData game = new GameData(	view.getViewNum(), 
											view.getPlayerScore(), 
											view.getCompScore(), 
											view.getLevel(), 
											view.getGameState()	);
			
			gameData.put(game.getViewNum(), game);
			modelUI.viewAdded(game);
		}
	}
	
	class ViewCloseEvent implements ActionListener {

		@Override
		public void actionPerformed(java.awt.event.ActionEvent e) {
			
			PongView view = (PongView)e.getSource();
			GameData game = gameData.get(view.getViewNum());
			
			gameData.remove(game.getViewNum());
			modelUI.viewClosed(game);
		}
	}
	
	class PlayerScoreEvent implements ActionListener {

		@Override
		public void actionPerformed(java.awt.event.ActionEvent e) {
			
			// TODO Auto-generated method stub
		}
	}
	
	class CompScoreEvent implements ActionListener {

		@Override
		public void actionPerformed(java.awt.event.ActionEvent e) {
			
			// TODO Auto-generated method stub
		}
	}
	
}
