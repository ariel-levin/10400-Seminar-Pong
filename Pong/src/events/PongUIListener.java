package events;


/**
 * 
 * @author 	Ariel Levin
 * 			<br/><a href="http://about.me/ariel.levin">about.me/ariel.levin</a>
 * 			<br/><a href="mailto:ariel.lvn89@gmail.com">ariel.lvn89@gmail.com</a><br/><br/>
 *
 * */
public interface PongUIListener {
	
	public void playerGoal(int score);
	
	public void compGoal(int score);

	public void userPlayPressed();
	
	public void userStopPressed();
	
	public void userPausePressed();
	
	public void gameStarted();
	
}
