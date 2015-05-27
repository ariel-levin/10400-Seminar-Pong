package view;

import controller.PongController;
import events.PongEvents.GameState;


/**
 * 
 * @author 	Ariel Levin
 * 			<br/><a href="http://about.me/ariel.levin">about.me/ariel.levin</a>
 * 			<br/><a href="mailto:ariel.lvn89@gmail.com">ariel.lvn89@gmail.com</a><br/><br/>
 *
 * */
public interface PongView {
	
	public void setController(PongController controller);

	public int getViewNum();
	
	public int getCompScore();

	public int getPlayerScore();

	public int getLevel();
	
	public GameState getGameState();
	
}
