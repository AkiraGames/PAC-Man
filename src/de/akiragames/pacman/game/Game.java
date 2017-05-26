package de.akiragames.pacman.game;

import java.awt.Color;

import de.akiragames.pacman.Main;
import de.akiragames.pacman.entity.PacMan;
import de.akiragames.pacman.graphics.Screen;
import de.akiragames.pacman.utils.FileUtils;
import de.akiragames.pacman.utils.ProjectUtils;
import de.akiragames.pacman.utils.Utils;

public class Game {
	
	private String gameId;
	private int gameScore, gameLevel, gameStart, gameEnd;
	private int lives, ghostsEaten, powerUpsEaten;
	
	private PacMan dummy;
	
	public String playerName;
	
	// Temporäre Variablen
	private int temp, anim, counter;
	
	private int textSize = 25;
	
	private Main main;
	private GameState gameState;
	
	private Map map;
	
	public Game(Main main) {
		this.gameId = Utils.generateGameId();
		this.gameScore = 0;
		this.gameLevel = 1;
		
		this.lives = 3;
		this.ghostsEaten = 0;
		this.powerUpsEaten = 0;
		
		this.anim = 0;
		this.counter = 0;
		
		this.gameState = GameState.CHECK_UPDATES;
		this.main = main;
		
		this.playerName = "PACBOT";
		
		this.dummy = new PacMan(-2, 6, this, true);
		this.map = new Map(this);
	}
	
	// Methode, um Spiel zu starten
	public void start() {
		this.gameStart = Utils.unixTime();
		this.gameState = GameState.START_GAME;
		
		this.temp = 5;
		
		this.main.getScreen().clearFull();
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
	
	// Level bestanden
	public void passedLevel() {
		this.gameState = GameState.LEVEL_PASSED;
		this.temp = Utils.unixTime();
		
		this.scoreUp(75); // 75 Punkte für Levelaufstieg
		
		if (this.lives > 0) this.lives--;
		
		this.map = new Map(this);
	}
	
	// Nächsten Level betreten
	public void nextLevel() {
		this.gameState = GameState.IN_GAME;
		this.gameLevel++;
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
		
		String urlString = Utils.websiteDomain + "/" + Main.GAME_ID_REF + "/addscore.php?gameId=" + this.gameId + "&gameVersion=" + Main.VERSION + "&playerName=" + playerName + "&gameScore=" + this.gameScore + "&reachedLevel=" + this.gameLevel + "&gameStart=" + this.gameStart + "&gameDuration=" + duration + "&ghostsEaten=" + this.getGhostsEaten();
		
		try {
			System.out.println("Sending game data to database...");
			ProjectUtils.sendGetRequest(urlString);
			System.out.println("Successfully sent game data to database.");
			System.out.println("Game stats have been saved with unique Id '" + this.gameId + "'.");
		} catch (Exception e) {
			System.err.println("Failed to send game data to database (" + e.getMessage() + ")!");
			System.err.println("Game stats could not be saved!");
		}
		
		this.temp = Utils.unixTime();
		this.gameState = GameState.SAVED_TO_DB;
	}
	
	public void update() {		
		if (this.gameState != GameState.CHECK_UPDATES && this.gameState != GameState.LEVEL_PASSED) this.map.update();
		
		if (this.gameState == GameState.CHECK_UPDATES) {
			this.counter++;
			
			if (this.counter % 8 == 0) this.anim++;
			
			this.updateDummy();
		} else if (this.gameState == GameState.START_GAME) {
			if (this.temp > 0) {
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
		} else if (this.gameState == GameState.LEVEL_PASSED) {
			if (Utils.unixTime() - this.temp == 3)
				this.nextLevel();
		} else if (this.gameState == GameState.ENTER_NAME) {
			this.counter++;
			
			if (this.counter % 8 == 0) this.anim++;
		} else if (this.gameState == GameState.POWERUP_ACTIVE) {
			if (Utils.unixTime() - this.temp == 7)
				this.gameState = GameState.IN_GAME;
		} else if (this.gameState == GameState.SAVED_TO_DB) {
			if (Utils.unixTime() - this.temp == 5)
				this.main.restart();
		}
	}
	
	public void render() {
		if (this.gameState == GameState.CHECK_UPDATES) {
			this.main.getScreen().clearFull();
			this.renderCheckUpdatesScreen();
		} else if (this.gameState == GameState.ENTER_NAME) {
			this.main.getScreen().clearFull();
			this.renderEndScreen();
		} else if (this.gameState == GameState.SAVED_TO_DB) {
			this.main.getScreen().clearFull();
			this.renderRestartScreen();
		} else {
			this.main.getScreen().clearKeepWalls();
			this.map.render();
			
			if (this.gameState == GameState.START_GAME) {
				this.renderStartText();
			} else if (this.gameState == GameState.DEATH) {
				this.renderDeathText();
			} else if (this.gameState == GameState.GAME_OVER) {
				this.renderGameOver();
			} else if (this.gameState == GameState.LEVEL_PASSED) {
				this.renderNextLevelText();
			} else {
				this.renderInformation();
			}
		}
	}
	
	private void updateDummy() {
		this.dummy.update();
		
		if (this.dummy.getDirection() == Direction.RIGHT) {
			this.dummy.moveToGridPosition(21, 6);
			
			if (this.dummy.getGridX() == 21) this.dummy.setDirection(Direction.LEFT);
		} else {
			this.dummy.moveToGridPosition(-2, 6);
			
			if (this.dummy.getGridX() == -2) this.dummy.setDirection(Direction.RIGHT);
		}
	}
	
	private void renderCheckUpdatesScreen() {
		this.main.getScreen().renderCenteredText("AkiraGames presents", 30, 20, Color.WHITE);
		this.main.getScreen().renderCenteredImage(FileUtils.loadImages(new String[]{"text/title.png"})[0], 60, 1.0);
		this.dummy.renderAnimation();
		
		if (Main.newVersion.equalsIgnoreCase(Main.VERSION)) {
			this.main.getScreen().renderCenteredText("Game is up to date.", 250, 20, Color.GREEN);
			if (this.anim % 5 < 3) this.main.getScreen().renderText(">> Press Enter to continue <<", 150, 290, 20, Color.CYAN);
		} else {
			this.main.getScreen().renderCenteredText("A new game version (" + Main.newVersion + ") is available!", 250, 20, Color.RED);
			this.main.getScreen().renderCenteredText("Go on 'akiragames.cynfos.de/PAC/' to download.", 280, 15, Color.RED);
			if (this.anim % 5 < 3) this.main.getScreen().renderCenteredText(">> Press Enter to continue <<", 320, 20, Color.CYAN);
		}
	}
	
	private void renderRestartScreen() {
		this.main.getScreen().renderCenteredText("Game has been saved if", 70, 30, Color.CYAN);
		this.main.getScreen().renderCenteredText("connected to the internet.", 110, 30, Color.CYAN);
		this.main.getScreen().renderCenteredText("Game-ID: " + this.gameId, 180, 30, Color.WHITE);
		this.main.getScreen().renderCenteredText("Restart in " + (5 - (Utils.unixTime() - this.temp)) + "...", 250, 30, Color.RED);
	}
	
	private void renderEndScreen() {
		int nameLength = this.main.getScreen().getStringPixelLength(this.playerName, 30);
		
		this.main.getScreen().renderCenteredText("Please Enter Your Name:", 100, 30, Color.CYAN);
		this.main.getScreen().renderText(">> ", 120, 150, 30, Color.CYAN);
		this.main.getScreen().renderText(this.playerName, 165, 150, 30, Color.YELLOW);
		if (this.anim % 4 < 2) this.main.getScreen().renderText("|", 170 + nameLength, 145, 40, Color.CYAN);
		this.main.getScreen().renderCenteredText("Press Enter to save your", 220, 30, Color.CYAN);
		this.main.getScreen().renderCenteredText("game.     (Game-ID: " + this.gameId + ")", 260, 30, Color.CYAN);
	}
	
	private void renderDeathText() {
		this.renderCenterText();
		this.main.getScreen().renderCenteredText("You Died!", 357, this.textSize + 5, Color.RED);
	}
	
	private void renderGameOver() {
		this.renderCenterText();
		this.main.getScreen().renderCenteredText("Game Over!", 357, this.textSize + 5, Color.RED);
	}
	
	private void renderNextLevelText() {
		this.main.getScreen().renderCenteredText("You passed Level " + this.gameLevel + "!", 357, this.textSize + 5, Color.RED);
	}
	
	private void renderStartText() {		
		this.main.getScreen().renderCenteredText("Ready? | Start in " + this.temp + "...", 357, this.textSize + 5, Color.RED);
	}
	
	private void renderInformation() {
		this.renderCenterText();
		this.main.getScreen().renderText("Level: " + this.gameLevel, 195, 360, this.textSize, Color.WHITE);
		this.main.getScreen().renderText("Time: " + (Utils.unixTime() - this.gameStart), 350, 360, this.textSize, this.gameState == GameState.IN_GAME ? Color.WHITE : Color.CYAN);
	}
	
	private void renderCenterText() {
		this.main.getScreen().renderText("Score: " + this.gameScore, 20, 360, this.textSize, Color.WHITE);
		this.main.getScreen().renderText("Lives: " + this.lives, 525, 360, this.textSize, Color.WHITE);
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
	
	public int getLevel() {
		return this.gameLevel;
	}
	
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
		return this.main.getScreen();
	}
	
	public Main getMain() {
		return this.main;
	}
	
	public Map getMap() {
		return this.map;
	}

}
