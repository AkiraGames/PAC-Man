package de.akiragames.pacman.game;

import java.awt.Color;

import de.akiragames.pacman.Main;
import de.akiragames.pacman.entity.PacMan;
import de.akiragames.pacman.graphics.Screen;
import de.akiragames.pacman.utils.ProjectUtils;
import de.akiragames.pacman.utils.Utils;

public class Game {
	
	private String gameId;
	private int gameScore, gameStart;
	private int lives, ghostsEaten, powerUpsEaten;
	
	private Screen screen;
	private GameState gameState;
	private PacMan pacman;
	
	private Wall[] walls;
	
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
		
		this.walls = this.initMap();
		
		this.pacman = new PacMan(screen.getWidth() / 2, 280, screen);
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
		this.pacman.update();
	}
	
	public void render() {
		this.renderMap();
		this.renderInformation();
		
		this.pacman.renderAnimation();
	}
	
	private void renderInformation() {
		this.screen.renderText("Score: " + this.gameScore, 8, 317, 30, Color.WHITE);
		this.screen.renderText("Lives: " + this.lives, 400, 317, 30, Color.WHITE);
		this.screen.renderText("Time: " + (Utils.unixTime() - this.gameStart), 200, 317, 30, Color.WHITE);
	}
	
	private void renderMap() {
		for (int i = 0; i < this.walls.length; i++) {
			this.walls[i].render();
		}
	}
	
	private Wall[] initMap() {
		int wallStrength = 12;
		int entityDiameter = 32;
		
		int sum = wallStrength + entityDiameter;
		
		int offset = 5 * sum + 2 * entityDiameter - 8;
		
		return new Wall[]{
				new Wall(0, 0, this.screen.getWidth(), wallStrength, this.screen), // TOP
				new Wall(0, 6 * sum + entityDiameter, this.screen.getWidth(), 7 * sum, this.screen), // BOTTOM
				new Wall(0, wallStrength, wallStrength, 6 * sum + entityDiameter, this.screen), // LEFT
				new Wall(this.screen.getWidth() - wallStrength, wallStrength, this.screen.getWidth(), 6 * sum + entityDiameter, this.screen), // RIGHT
				
				new Wall(sum, sum, sum + wallStrength, 3 * sum, this.screen),
				new Wall(sum + wallStrength, sum, 2 * sum, sum + wallStrength, this.screen),
				new Wall(sum, 3 * sum + entityDiameter, sum + wallStrength, 6 * sum, this.screen),
				new Wall(sum + wallStrength, 6 * sum - wallStrength, 2 * sum, 6 * sum, this.screen),
				
				new Wall(2 * sum + entityDiameter, wallStrength, 3 * sum, sum + wallStrength, this.screen),
				new Wall(2 * sum + entityDiameter, 6 * sum - wallStrength, 3 * sum, 6 * sum + entityDiameter, this.screen),
				
				new Wall(2 * sum, 2 * sum, 3 * sum, 5 * sum, this.screen),
				new Wall(2 * sum + wallStrength, 2 * sum + wallStrength, 2 * sum + wallStrength + 20, 4 * sum + entityDiameter, Color.BLACK, this.screen),
				
				new Wall(3 * sum + entityDiameter, sum, 8 * sum + wallStrength, sum + wallStrength, this.screen),
				new Wall(3 * sum + entityDiameter, 5 * sum + entityDiameter, 8 * sum + wallStrength, 6 * sum, this.screen),
				
				new Wall(offset + 3 * sum + 2 * entityDiameter, sum, offset + 4 * sum + entityDiameter, 3 * sum, this.screen),
				new Wall(offset + 3 * sum + entityDiameter, sum, offset + 3 * sum + 2 * entityDiameter, sum + wallStrength, this.screen),
				new Wall(offset + 3 * sum + 2 * entityDiameter, 3 * sum + entityDiameter, offset + 4 * sum + entityDiameter, 6 * sum, this.screen),
				new Wall(offset + 3 * sum + entityDiameter, 6 * sum - wallStrength, offset + 3 * sum + 2 * entityDiameter, 6 * sum, this.screen),
				
				new Wall(offset + 2 * sum + entityDiameter, wallStrength, offset + 3 * sum, sum + wallStrength, this.screen),
				new Wall(offset + 2 * sum + entityDiameter, 6 * sum - wallStrength, offset + 3 * sum, 6 * sum + entityDiameter, this.screen),
				
				new Wall(offset + 2 * sum + entityDiameter, 2 * sum, offset + 3 * sum + entityDiameter, 5 * sum, this.screen),
				new Wall(offset + 3 * sum, 2 * sum + wallStrength, offset + 3 * sum + 20, 4 * sum + entityDiameter, Color.BLACK, this.screen),
				
				
				new Wall(3 * sum + entityDiameter, 2 * sum, 4 * sum, 3 * sum + entityDiameter, this.screen),
				new Wall(3 * sum + entityDiameter, 3 * sum + 2 * entityDiameter, 8 * sum + wallStrength, 5 * sum, this.screen),
				new Wall(4 * sum, 2 * sum, 5 * sum + 2 * wallStrength, 2 * sum + wallStrength, this.screen),
				new Wall(6 * sum + wallStrength, 2 * sum, 8 * sum + wallStrength, 2 * sum + wallStrength, this.screen),
				new Wall(8 * sum, 3 * sum, 8 * sum + wallStrength, 3 * sum + 2 * entityDiameter, this.screen),
				new Wall(5 * sum - wallStrength, 3 * sum, 5 * sum - wallStrength + entityDiameter + 4, 3 * sum + 2 * entityDiameter, this.screen),
				new Wall(6 * sum + wallStrength, 2 * sum, 7 * sum + wallStrength, 3 * sum + entityDiameter, this.screen)
		};
	}
	
	///////////////////////////////////////////////
	
	public int getScore() {
		return this.gameScore;
	}
	
	public PacMan getPacMan() {
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
	
	public GameState getGameState() {
		return this.gameState;
	}
	
	public Screen getScreen() {
		return this.screen;
	}
	
	public Wall[] getWalls() {
		return this.walls;
	}

}
