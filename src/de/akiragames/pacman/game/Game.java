package de.akiragames.pacman.game;

import java.awt.Color;

import de.akiragames.pacman.Main;
import de.akiragames.pacman.entity.Map;
import de.akiragames.pacman.graphics.Screen;
import de.akiragames.pacman.utils.ProjectUtils;
import de.akiragames.pacman.utils.Utils;

public class Game {
	
	private String gameId;
	private int gameScore, gameStart;
	private int lives, ghostsEaten, powerUpsEaten;
	
	private Screen screen;
	private GameState gameState;
	
	private Map map;
	
	// private Ghost[] ghosts;
	// private PowerUp[] powerUps;
	
	public Game(Screen screen) {
		this.gameId = Utils.generateGameId();
		this.gameScore = 0;
		this.gameStart = Utils.unixTime();
		
		this.lives = 3;
		this.ghostsEaten = 0;
		this.powerUpsEaten = 0;
		
		this.gameState = GameState.LOADING_SCREEN;
		this.screen = screen;
		
		this.map = new Map(this, this.screen);
	}
	
	// Methode, wenn PacMan stirbt
	public void die() {
		if (this.lives > 1) {
			this.lives--;
		} else {
			this.gameOver();
		}
	}
	
	private void gameOver() {
		
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
	
	public void update() {
		this.map.update();
	}
	
	public void render() {
		this.map.render();
		this.renderInformation();
	}
	
	private void renderInformation() {
		this.screen.renderText("Score: " + this.gameScore, 10, 360, 30, Color.WHITE);
		this.screen.renderText("Lives: " + this.lives, 500, 360, 30, Color.WHITE);
		this.screen.renderText("Time: " + (Utils.unixTime() - this.gameStart), 260, 360, 30, Color.WHITE);
	}
	
	///////////////////////////////////////////////
	
	public int getScore() {
		return this.gameScore;
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
	
	public GameState getGameState() {
		return this.gameState;
	}
	
	public Screen getScreen() {
		return this.screen;
	}
	
	public Map getMap() {
		return this.map;
	}

}
