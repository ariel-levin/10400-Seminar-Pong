package controller;

import java.awt.event.ActionEvent;
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
	private Map<Integer, PongView>						views;
	private Map<EventType, ArrayList<ActionListener>> 	listenersMap;
	
	
	public PongController() {
		
		views 			= new HashMap<Integer, PongView>();
		listenersMap 	= new HashMap<EventType, ArrayList<ActionListener>>();
	}
	
	
	public void setModel(PongModel model) {
		this.model = model;
		model.setController(this);
	}

	public void addView(PongView pongView) {
		views.put(pongView.getViewNum(), pongView);
		try {
			Stage pongViewStage = new Stage();
			views.get(pongView.getViewNum()).start(pongViewStage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		pongView.setController(this);
	}
	
	public int getViewsCount() {
		return views.size();
	}
	
	
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	
	
	public synchronized void addActionListener(ActionListener l, EventType et) {
		ArrayList<ActionListener> al;
		al = listenersMap.get(et);
		if (al == null)
			al = new ArrayList<ActionListener>();
		al.add(l);
		listenersMap.put(et, al);
	}

	public synchronized void removeActionListener(ActionListener l, EventType et) {
		ArrayList<ActionListener> al;
		al = listenersMap.get(et);
		if (al != null && al.contains(l))
			al.remove(l);
		listenersMap.put(et, al);
	}

	public void processEvent(EventType et, ActionEvent e) {
		ArrayList<ActionListener> al;
		synchronized (this) {
			al = listenersMap.get(et);
			if (al == null)
				return;
		}
		
		for (int i = 0; i < al.size(); i++) {
			ActionListener listener = (ActionListener) al.get(i);
			listener.actionPerformed(e);
		}
	}

	
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////
	

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
