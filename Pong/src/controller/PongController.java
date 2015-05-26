package controller;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import events.PongEvents;
import events.PongListener;
import events.PongUIListener;
import javafx.stage.Stage;
import model.PongModel;
import view.PongView;


/**
 * 
 * @author 	Ariel Levin
 * 			<br/><a href="http://about.me/ariel.levin">about.me/ariel.levin</a>
 * 			<br/><a href="mailto:ariel.lvn89@gmail.com">ariel.lvn89@gmail.com</a><br/><br/>
 *
 * */
public class PongController implements PongEvents, PongListener, PongUIListener {

	private PongModel									model;
	private ArrayList<PongView> 						views;
	private Map<EventType, ArrayList<ActionListener>> 	listenersMap;
	
	
	public PongController() {
		
		views 			= new ArrayList<PongView>();
		listenersMap 	= new HashMap<EventType, ArrayList<ActionListener>>();
	}
	
	
	public void setModel(PongModel model) {
		this.model = model;
	}

	public void addView(PongView pongView) {
		views.add(pongView);
		try {
			Stage pongViewStage = new Stage();
			views.get(views.size() - 1).start(pongViewStage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int getViewsCount() {
		return views.size();
	}


	@Override
	public void playerGoal(int viewNum, int score) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void compGoal(int viewNum, int score) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void userPlayPressed(int viewNum) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void userStopPressed(int viewNum) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void userPausePressed(int viewNum) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void gameStarted(int viewNum) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void viewOpened(int viewNum) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void viewClosed(int viewNum) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void changeLevel(int viewNum, int level) {
		// TODO Auto-generated method stub
		
	}
	
}
