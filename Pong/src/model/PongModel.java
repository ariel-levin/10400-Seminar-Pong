package model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
	private Map<Integer, GameData>	gameDataMap;
	
	
	public PongModel() {
		
		gameDataMap = new HashMap<Integer, GameData>();
		modelUI 	= new PongModelUI(this);
		
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
			controller.addActionListener(new GameStateEvent(), EventType.VIEW_GAME_STATE);
			controller.addActionListener(new ViewOpenEvent(), EventType.VIEW_OPEN);
			controller.addActionListener(new ViewCloseEvent(), EventType.VIEW_CLOSE);
			controller.addActionListener(new PlayerScoreEvent(), EventType.PLAYER_SCORE);
			controller.addActionListener(new CompScoreEvent(), EventType.COMP_SCORE);
		}
	}

	public void modelUIgameStateChange(GameData game) {
		
		controller.processEvent(EventType.MODEL_GAME_STATE, new ActionEvent(game,
				game.getViewNum(), EventType.MODEL_GAME_STATE.toString()));
		
		gameDataMap.get(game.getViewNum()).setGameState(game.getGameState());
		modelUI.gameStateChange(game);
	}
	
	
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////

	
	class GameStateEvent implements ActionListener {

		@Override
		public void actionPerformed(java.awt.event.ActionEvent e) {
			
			PongView view = (PongView)e.getSource();
			GameData game = gameDataMap.get(view.getViewNum());
			game.setGameState(view.getGameState());
			modelUI.gameStateChange(game);
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
			
			gameDataMap.put(game.getViewNum(), game);
			modelUI.viewAdded(game);
		}
	}
	
	class ViewCloseEvent implements ActionListener {

		@Override
		public void actionPerformed(java.awt.event.ActionEvent e) {
			
			PongView view = (PongView)e.getSource();
			GameData game = gameDataMap.get(view.getViewNum());
			
			gameDataMap.remove(game.getViewNum());
			modelUI.viewClosed(game);
		}
	}
	
	class PlayerScoreEvent implements ActionListener {

		@Override
		public void actionPerformed(java.awt.event.ActionEvent e) {
			
			PongView view = (PongView)e.getSource();
			GameData game = gameDataMap.get(view.getViewNum());
			game.setPlayerScore(view.getPlayerScore());
			modelUI.playerScoreChanged(game);
		}
	}
	
	class CompScoreEvent implements ActionListener {

		@Override
		public void actionPerformed(java.awt.event.ActionEvent e) {
			
			PongView view = (PongView)e.getSource();
			GameData game = gameDataMap.get(view.getViewNum());
			game.setCompScore(view.getCompScore());
			modelUI.compScoreChanged(game);
		}
	}
	
}
