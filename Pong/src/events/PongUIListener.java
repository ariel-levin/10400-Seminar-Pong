package events;


/**
 * 
 * @author 	Ariel Levin
 * 			<br/><a href="http://about.me/ariel.levin">about.me/ariel.levin</a>
 * 			<br/><a href="mailto:ariel.lvn89@gmail.com">ariel.lvn89@gmail.com</a><br/><br/>
 *
 * */
public interface PongUIListener {
	
	public void playerGoal(int viewNum, int score);
	
	public void compGoal(int viewNum, int score);

	public void userPlayPressed(int viewNum);
	
	public void userStopPressed(int viewNum);
	
	public void userPausePressed(int viewNum);
	
	public void gameStarted(int viewNum);
	
	public void viewOpened(int viewNum);
	
	public void viewClosed(int viewNum);
	
}
