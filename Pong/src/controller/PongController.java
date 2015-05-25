package controller;

import java.util.ArrayList;

import events.PongEvents;
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
public class PongController implements PongEvents {

	private PongModel			model;
	private ArrayList<PongView> views;
	
	
	public PongController() {
		
		views = new ArrayList<PongView>();
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
	public void playerGoal(int score) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void compGoal(int score) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void playerLevel(int level) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void playing() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void stopped() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void paused() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void gameStarted() {
		// TODO Auto-generated method stub
		
	}
	
}
