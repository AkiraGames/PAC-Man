package de.akiragames.pacman.game;

import de.akiragames.pacman.Main;
import de.akiragames.pacman.utils.ProjectUtils;
import de.akiragames.pacman.utils.Utils;

public class Game {
	
	private String gameId;
	private int gameScore;
	private int gameStart;
	
	private PacMan pacman;
	
	public Game(PacMan pacman) {
		this.gameId = Utils.generateGameId();
		this.gameScore = 0;
		this.gameStart = Utils.unixTime();
		
		this.pacman = pacman;
	}
	
	// Score um "points" erhöhen
	public void scoreUp(int points) {
		this.gameScore += points;
	}
	
	// Methode zum Speichern des Scores
	public void saveToDatabase(String playerName) {
		int duration = Utils.unixTime() - this.gameStart;
		
		String urlString = Utils.websiteDomain + "/" + Main.GAME_ID_REF + "/addscore.php?gameId=" + this.gameId + "&gameVersion=" + Main.VERSION + "&playerName=" + playerName + "&gameScore=" + this.gameScore + "&gameStart=" + this.gameStart + "&gameDuration=" + duration + "&livesLeft=" + this.pacman.getLives() + "&ghostsEaten=" + this.pacman.getGhostsEaten();
		
		try {
			System.out.println("Sending game data to database...");
			ProjectUtils.sendGetRequest(urlString);
			System.out.println("Successfully sent game data to database.");
			System.out.println("Game stats have been saved with unique Id '" + this.gameId + "'.");
		} catch (Exception e) {
			System.err.println("Failed to send game data to database (" + e.getMessage() + ")!");
			System.err.println("Game stats could not be saved!");
		}
	}
	
	public int getScore() {
		return this.gameScore;
	}
	
	public PacMan getPacMan() {
		return this.pacman;
	}

}
