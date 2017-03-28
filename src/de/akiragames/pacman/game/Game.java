package de.akiragames.pacman.game;

import de.akiragames.pacman.Main;
import de.akiragames.pacman.entity.LivingEntity;
import de.akiragames.pacman.utils.ProjectUtils;
import de.akiragames.pacman.utils.Utils;

public class Game {
	
	private String gameId;
	private int gameScore, gameStart;
	private int lives, ghostsEaten, powerUpsEaten;
	
	private LivingEntity pacman;
	
	public Game(LivingEntity pacman) {
		this.gameId = Utils.generateGameId();
		this.gameScore = 0;
		this.gameStart = Utils.unixTime();
		
		this.lives = 3;
		this.ghostsEaten = 0;
		this.powerUpsEaten = 0;
		
		this.pacman = pacman;
	}
	
	// Score um "points" erhöhen
	public void scoreUp(int points) {
		this.gameScore += points;
	}
	
	// Methode zum Speichern des Scores
	public void saveToDatabase(String playerName) {
		int duration = Utils.unixTime() - this.gameStart;
		
		String urlString = Utils.websiteDomain + "/" + Main.GAME_ID_REF + "/addscore.php?gameId=" + this.gameId + "&gameVersion=" + Main.VERSION + "&playerName=" + playerName + "&gameScore=" + this.gameScore + "&gameStart=" + this.gameStart + "&gameDuration=" + duration + "&livesLeft=" + this.getLives() + "&ghostsEaten=" + this.getGhostsEaten();
		
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
	
	public LivingEntity getPacMan() {
		return this.pacman;
	}
	
	public int getLives() {
		return this.lives;
	}
	
	public int getGhostsEaten() {
		return this.ghostsEaten;
	}
	
	public int getPowerUpsEaten() {
		return this.powerUpsEaten;
	}

}
