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
	
	public String playerName;
	
	// Temporäre Variable
	private int temp;
	
	private Screen screen;
	private GameState gameState;
	
	private Map map;
	
	public Game(Screen screen) {
		this.gameId = Utils.generateGameId();
		this.gameScore = 0;
		
		this.lives = 3;
		this.ghostsEaten = 0;
		this.powerUpsEaten = 0;
		
		this.gameState = GameState.CHECK_UPDATES;
		this.screen = screen;
		
		this.playerName = "PACBOT";
		
		this.map = new Map(this);
	}
	
	// Methode, um Spiel zu starten
	public void start() {
		this.gameStart = Utils.unixTime();
		this.gameState = GameState.START_GAME;
		
		this.temp = 5;
		
		this.screen.clearFull();
		this.map.renderWalls();
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
		
		this.gameState = GameState.SAVED_TO_DB;
	}
	
	public void update() {		
		if (this.gameState != GameState.CHECK_UPDATES) this.map.update();
		
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
		} else if (this.gameState == GameState.GAME_OVER || this.gameState == GameState.VICTORY) {
			if (Utils.unixTime() - this.temp == 3)
				this.gameState = GameState.ENTER_NAME;
		} else if (this.gameState == GameState.POWERUP_ACTIVE) {
			if (Utils.unixTime() - this.temp == 7)
				this.gameState = GameState.IN_GAME;
		}
	}
	
	public void render() {
		if (this.gameState == GameState.CHECK_UPDATES) {
			this.screen.clearFull();
			this.renderCheckUpdatesScreen();
		} else if (this.gameState == GameState.ENTER_NAME) {
			this.screen.clearFull();
			this.renderEndScreen();
		} else if (this.gameState == GameState.SAVED_TO_DB) {
			this.screen.clearFull();
			this.renderRestartScreen();
		} else {
			this.screen.clearKeepWalls();
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
	
	private void renderCheckUpdatesScreen() {
		if (Main.newVersion.equalsIgnoreCase(Main.VERSION)) {
			this.screen.renderText("Game is up to date.", 100, 100, 30, Color.GREEN);
			this.screen.renderText("Press Enter to continue.", 100, 170, 30, Color.CYAN);
		} else {
			this.screen.renderText("A new game version", 100, 100, 30, Color.RED);
			this.screen.renderText("(" + Main.newVersion + ") is available!", 100, 140, 30, Color.RED);
			this.screen.renderText("Go on 'akiragames.cynfos.de/PAC/' to download.", 100, 180, 15, Color.RED);
			this.screen.renderText("Press Enter to continue.", 100, 250, 30, Color.CYAN);
		}
	}
	
	private void renderRestartScreen() {
		this.screen.renderText("Game has been saved if", 100, 100, 30, Color.CYAN);
		this.screen.renderText("connected to the internet.", 100, 140, 30, Color.CYAN);
		this.screen.renderText("Close window to quit game.", 100, 210, 30, Color.CYAN);
	}
	
	private void renderEndScreen() {
		this.screen.renderText("Please Enter Your Name:", 100, 100, 30, Color.CYAN);
		this.screen.renderText(">> ", 100, 150, 30, Color.CYAN);
		this.screen.renderText(this.playerName, 150, 150, 30, Color.YELLOW);
		this.screen.renderText("Press Enter to save your", 100, 220, 30, Color.CYAN);
		this.screen.renderText("game.", 100, 260, 30, Color.CYAN);
		this.screen.renderText("ID: " + this.gameId, 340, 260, 30, Color.WHITE);
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
		this.screen.renderText("Time: " + (Utils.unixTime() - this.gameStart), 260, 360, 30, this.gameState == GameState.IN_GAME ? Color.WHITE : Color.CYAN);
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
