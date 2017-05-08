package de.akiragames.pacman.game;

import java.awt.Color;

import de.akiragames.pacman.Main;
import de.akiragames.pacman.graphics.Screen;
import de.akiragames.pacman.utils.ProjectUtils;
import de.akiragames.pacman.utils.Utils;

public class Game {
	
	private String gameId;
	private int gameScore, gameStart, gameEnd;
	private int lives, ghostsEaten, powerUpsEaten;
	
	// Temporäre Variable
	private int temp;
	
	private Screen screen;
	private GameState gameState;
	
	private Map map;
	
	public Game(Screen screen) {
		this.gameId = Utils.generateGameId();
		this.gameScore = 0;
		this.gameStart = Utils.unixTime();
		
		this.lives = 3;
		this.ghostsEaten = 0;
		this.powerUpsEaten = 0;
		
		this.temp = 5;
		
		this.gameState = GameState.START_GAME;
		this.screen = screen;
		
		this.map = new Map(this);
	}
	
	// Methode, wenn PacMan stirbt
	public void die() {
		this.temp = Utils.unixTime();
		
		if (this.lives > 0) {
			this.gameState = GameState.DEATH;
			this.map.deathReset();
			
			this.lives--;
		} else {
			this.gameOver();
		}
	}
	
	// Spiel vorbei: gewonnen
	public void victory() {
		this.gameState = GameState.VICTORY;
		
		this.temp = Utils.unixTime();
		
		this.gameEnd = Utils.unixTime();
	}
	
	// Spiel vorbei: verloren
	public void gameOver() {
		this.gameState = GameState.GAME_OVER;
		
		this.gameEnd = Utils.unixTime();
	}
	
	// Score um "points" erhöhen
	public void scoreUp(int points) {
		this.gameScore += points;
	}
	
	// Methode zum Speichern des Scores
	public void saveToDatabase(String playerName) {
		int duration = this.gameEnd - this.gameStart;
		
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
		
		if (this.gameState == GameState.START_GAME) {
			if (this.temp > 0 ) {
				this.temp = 5 - (Utils.unixTime() - this.gameStart);
			} else {
				this.gameStart = Utils.unixTime();
				this.gameState = GameState.IN_GAME;
			}
		} else if (this.gameState == GameState.DEATH) {
			if (Utils.unixTime() - this.temp == 3)
				this.gameState = GameState.IN_GAME;
		} else if (this.gameState == GameState.GAME_OVER) {
			if (Utils.unixTime() - this.temp == 3)
				this.gameState = GameState.ENTER_NAME;
		} else if (this.gameState == GameState.VICTORY) {
			if (Utils.unixTime() - this.temp == 3)
				this.gameState = GameState.ENTER_NAME;
		} else if (this.gameState == GameState.POWERUP_ACTIVE) {
			if (Utils.unixTime() - this.temp == 20)
				this.gameState = GameState.IN_GAME;
		}
	}
	
	public void render() {
		if (this.gameState == GameState.ENTER_NAME) {
			this.screen.clearFull();
			
			// Namen eingeben und Spiel wird in Datenbank gespeichert.
		} else {
			this.map.render();
			
			if (this.gameState == GameState.START_GAME) {
				this.renderStartText();
			} else if (this.gameState == GameState.DEATH) {
				this.renderDeathText();
			} else if (this.gameState == GameState.GAME_OVER) {
				this.renderGameOver();
			} else if (this.gameState == GameState.VICTORY) {
				this.renderVictoryText();
			} else {
				this.renderInformation();
			}
		}
	}
	
	private void renderDeathText() {
		this.screen.renderText("Score: " + this.gameScore, 10, 360, 30, Color.WHITE);
		this.screen.renderText("Lives: " + this.lives, 500, 360, 30, Color.WHITE);
		this.screen.renderText("You Died!", 240, 360, 30, Color.RED);
	}
	
	private void renderGameOver() {
		this.screen.renderText("Score: " + this.gameScore, 10, 360, 30, Color.WHITE);
		this.screen.renderText("Lives: " + this.lives, 500, 360, 30, Color.WHITE);
		this.screen.renderText("Game Over!", 225, 360, 30, Color.RED);
	}
	
	private void renderVictoryText() {
		this.screen.renderText("Score: " + this.gameScore, 10, 360, 30, Color.WHITE);
		this.screen.renderText("Lives: " + this.lives, 500, 360, 30, Color.WHITE);
		this.screen.renderText("Victory!", 240, 360, 30, Color.RED);
	}
	
	private void renderStartText() {		
		this.screen.renderText("Ready? | Start in " + this.temp + "...", 155, 360, 30, Color.RED);
	}
	
	private void renderInformation() {
		this.screen.renderText("Score: " + this.gameScore, 10, 360, 30, Color.WHITE);
		this.screen.renderText("Lives: " + this.lives, 500, 360, 30, Color.WHITE);
		this.screen.renderText("Time: " + (Utils.unixTime() - this.gameStart), 260, 360, 30, Color.WHITE);
	}
	
	public void activatePowerUp() {
		this.powerUpsEaten++;
		
		this.temp = Utils.unixTime();
		this.gameState = GameState.POWERUP_ACTIVE;
	}
	
	public void setGameState(GameState state) {
		this.gameState = state;
	}
	
	public void eatGhost() {
		this.ghostsEaten++;
		this.scoreUp(50);
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
