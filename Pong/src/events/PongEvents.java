package events;


/**
 * 
 * @author 	Ariel Levin
 * 			<br/><a href="http://about.me/ariel.levin">about.me/ariel.levin</a>
 * 			<br/><a href="mailto:ariel.lvn89@gmail.com">ariel.lvn89@gmail.com</a><br/><br/>
 *
 * */
public interface PongEvents {
	
	static enum eventType { PLAYER_GOAL, COMP_GOAL, LEVEL, PLAY, STOP, PAUSE, GAME_START }
	

	public void playerGoal(int score);
	
	public void compGoal(int score);
	
	public void playerLevel(int level);
	
	public void playing();
	
	public void stopped();
	
	public void paused();
	
	public void gameStarted();
	
}
