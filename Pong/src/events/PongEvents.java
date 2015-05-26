package events;


/**
 * 
 * @author 	Ariel Levin
 * 			<br/><a href="http://about.me/ariel.levin">about.me/ariel.levin</a>
 * 			<br/><a href="mailto:ariel.lvn89@gmail.com">ariel.lvn89@gmail.com</a><br/><br/>
 *
 * */
public interface PongEvents {

	enum EventType {
		PLAYER_GOAL, COMP_GOAL, LEVEL, GAME_STATUS, GAME_START
	}
	
	enum GameState {
		PLAY, PAUSE, STOP
	}

}
