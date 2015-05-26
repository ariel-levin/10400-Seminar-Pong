package model;

import events.PongEvents;


public class GameData implements PongEvents {

	private int			viewNum, playerScore, compScore, level;
	private GameState	gameState;
	
	
	public GameData(int viewNum, int playerScore, int compScore, int level,
			GameState gameState) {

		this.viewNum = viewNum;
		this.playerScore = playerScore;
		this.compScore = compScore;
		this.level = level;
		this.gameState = gameState;
	}


	public int getViewNum() {
		return viewNum;
	}


	public void setViewNum(int viewNum) {
		this.viewNum = viewNum;
	}


	public int getPlayerScore() {
		return playerScore;
	}


	public void setPlayerScore(int playerScore) {
		this.playerScore = playerScore;
	}


	public int getCompScore() {
		return compScore;
	}


	public void setCompScore(int compScore) {
		this.compScore = compScore;
	}


	public int getLevel() {
		return level;
	}


	public void setLevel(int level) {
		this.level = level;
	}


	public GameState getGameState() {
		return gameState;
	}


	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}


	@Override
	public String toString() {
		return "View #" + viewNum;
	}
	
}

