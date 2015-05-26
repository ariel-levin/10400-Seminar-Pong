package events;


/**
 * 
 * @author 	Ariel Levin
 * 			<br/><a href="http://about.me/ariel.levin">about.me/ariel.levin</a>
 * 			<br/><a href="mailto:ariel.lvn89@gmail.com">ariel.lvn89@gmail.com</a><br/><br/>
 *
 * */
public interface PongEvents {

	public enum EventType {
		VIEW_OPEN, VIEW_CLOSE, PLAYER_GOAL, COMP_GOAL, LEVEL, 
		MODEL_PLAY, MODEL_PAUSE, MODEL_STOP, VIEW_PLAY, VIEW_PAUSE, VIEW_STOP;
	}

	enum GameState {
		PLAY, PAUSE, STOP
	}

//	public enum EventType {
//		
//		VIEW_OPEN, VIEW_CLOSE, PLAYER_GOAL, COMP_GOAL, LEVEL;
//		
//		public enum GameState { PLAY, PAUSE, STOP }
//		
//	}

}
