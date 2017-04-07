package de.akiragames.pacman.entity;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import de.akiragames.pacman.game.Game;
import de.akiragames.pacman.game.WallBlock;
import de.akiragames.pacman.graphics.Screen;
import de.akiragames.pacman.utils.FileUtils;

public class Map {

	private BufferedImage image;
	private Screen screen;
	private Game game;
	private int width, height;
	
	private PacMan pacman;
	
	private WallBlock[] wallBlocks;
	private PowerUp[] powerUps;
	private PacDot[] pacDots;
	
	public Map(Game game, Screen screen) {
		this.image = FileUtils.loadImages(new String[]{"map/labyrinth.png"})[0];
		this.screen = screen;
		this.game = game;
		
		this.width = this.image.getWidth();
		this.height = this.image.getHeight();
		
		this.pacman = this.loadPacMan();
		
		this.wallBlocks = this.loadWalls();
		this.powerUps = this.loadPowerUps();
		this.pacDots = this.loadPacDots();
	}
	
	public void update() {
		for (int i = 0; i < this.powerUps.length; i++) {
			this.powerUps[i].update();
		}
		
		this.pacman.update();
	}
	
	public void render() {
		for (int i = 0; i < this.wallBlocks.length; i++) {
			this.wallBlocks[i].render();
		}
		
		for (int i = 0; i < this.pacDots.length; i++) {
			this.pacDots[i].render();
		}
		
		for (int i = 0; i < this.powerUps.length; i++) {
			this.powerUps[i].render();
		}
		
		this.pacman.renderAnimation();
	}
	
	private PacMan loadPacMan() {
		PacMan pm = null;
		int[] pixels = new int[this.image.getWidth() * this.image.getHeight()];
		
		this.image.getRGB(0, 0, this.image.getWidth(), this.image.getHeight(), pixels, 0, this.image.getWidth());
		
		for (int y = 0; y < this.image.getHeight(); y++) {
			if (y >= 0 && y < this.screen.getHeight()) {
				for (int x = 0 * 32; x < this.image.getWidth(); x++) {
					if (x >= 0 && x < this.screen.getWidth()) {
						if (pixels[x + y * this.image.getWidth()] == Color.RED.getRGB()) {
							pm = new PacMan(x, y, this.screen, this.game);
							break;
						}
					}
				}
				
				if (pm != null) break;
			}
		}
		
		return pm;
	}
	
	private PacDot[] loadPacDots() {
		ArrayList<PacDot> pd = new ArrayList<PacDot>();
		int[] pixels = new int[this.image.getWidth() * this.image.getHeight()];
		
		this.image.getRGB(0, 0, this.image.getWidth(), this.image.getHeight(), pixels, 0, this.image.getWidth());
		
		for (int y = 0; y < this.image.getHeight(); y++) {
			if (y >= 0 && y < this.screen.getHeight()) {
				for (int x = 0 * 32; x < this.image.getWidth(); x++) {
					if (x >= 0 && x < this.screen.getWidth()) {
						if (pixels[x + y * this.image.getWidth()] == Color.BLACK.getRGB())
							pd.add(new PacDot(x, y, this.screen));
					}
				}
			}
		}
		
		return pd.toArray(new PacDot[pd.size()]);
	}
	
	private PowerUp[] loadPowerUps() {
		ArrayList<PowerUp> pu = new ArrayList<PowerUp>();
		int[] pixels = new int[this.image.getWidth() * this.image.getHeight()];
		
		this.image.getRGB(0, 0, this.image.getWidth(), this.image.getHeight(), pixels, 0, this.image.getWidth());
		
		for (int y = 0; y < this.image.getHeight(); y++) {
			if (y >= 0 && y < this.screen.getHeight()) {
				for (int x = 0 * 32; x < this.image.getWidth(); x++) {
					if (x >= 0 && x < this.screen.getWidth()) {
						if (pixels[x + y * this.image.getWidth()] == Color.GREEN.getRGB())
							pu.add(new PowerUp(x, y, this.screen));
					}
				}
			}
		}
		
		return pu.toArray(new PowerUp[pu.size()]);
	}
	
	private WallBlock[] loadWalls() {
		ArrayList<WallBlock> blocks = new ArrayList<WallBlock>();
		int[] pixels = new int[this.image.getWidth() * this.image.getHeight()];
		
		this.image.getRGB(0, 0, this.image.getWidth(), this.image.getHeight(), pixels, 0, this.image.getWidth());
		
		for (int y = 0; y < this.image.getHeight(); y++) {
			if (y >= 0 && y < this.screen.getHeight()) {
				for (int x = 0 * 32; x < this.image.getWidth(); x++) {
					if (x >= 0 && x < this.screen.getWidth()) {
						if (pixels[x + y * this.image.getWidth()] == Color.BLUE.getRGB())
							blocks.add(new WallBlock(x, y, this));
					}
				}
			}
		}
		
		return blocks.toArray(new WallBlock[blocks.size()]);
	}
	
	/////////////////////////////////////////////////
	
	public BufferedImage getImage() {
		return this.image;
	}
	
	public boolean isWallBlock(int gridX, int gridY) {
		boolean isWall = false;
		
		for (int i = 0; i < this.wallBlocks.length; i++) {
			if (this.wallBlocks[i].getPosX() == gridX && this.wallBlocks[i].getPosY() == gridY) {
				isWall = true;
				break;
			}
		}
		
		return isWall;
	}
	
	public PacMan getPacMan() {
		return this.pacman;
	}
	
	public PowerUp[] getPowerUps() {
		return this.powerUps;
	}
	
	public PacDot[] getPacDots() {
		return this.pacDots;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public Game getGame() {
		return this.game;
	}
	
	public Screen getScreen() {
		return this.screen;
	}
	
}
