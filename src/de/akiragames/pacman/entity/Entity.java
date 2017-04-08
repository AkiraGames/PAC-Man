package de.akiragames.pacman.entity;

import java.awt.image.BufferedImage;

import de.akiragames.pacman.game.Game;
import de.akiragames.pacman.utils.FileUtils;

public class Entity {
	
	protected int posX, posY, gridX, gridY;
	protected Game game;
	protected BufferedImage[] images;
	private boolean imagesContainAlphaColor;
	
	public Entity(int gridX, int gridY, Game game, String[] imageFiles, boolean imagesContainAlphaColor) {
		this.gridX = gridX;
		this.gridY = gridY;
		this.posX = gridX * 32 + 16;
		this.posY = gridY * 32 + 16;
		
		this.game = game;
		
		this.images = FileUtils.loadImages(imageFiles);
		this.imagesContainAlphaColor = imagesContainAlphaColor;
	}
	
	public void render(int imageIndex) {
//		if (!Main.entityCollisionChecker.isCollidingWithPacMan(this)) {
			int[] pixels = this.game.getScreen().getPixels();
			
			int w = this.images[imageIndex].getWidth();
			int h = this.images[imageIndex].getHeight();
			int[] imagePixels = new int[w * h];
			
			this.images[imageIndex].getRGB(0, 0, w, h, imagePixels, 0, w);
			
			int xOffset = this.posX - w / 2;
			int yOffset = this.posY - h / 2;
			
			for (int y = yOffset; y < this.posY + h / 2; y++) {
				if (y >= 0 && y < this.game.getScreen().getHeight() && y < h + yOffset) {
					for (int x = xOffset; x < this.posX + w / 2; x++) {
						if (x >= 0 && x < this.game.getScreen().getWidth() && x < w + xOffset) {
							if (imagePixels[(x - xOffset) + (y - yOffset) * w] != this.game.getScreen().getAlphaColor().getRGB())
								pixels[x + y * this.game.getScreen().getWidth()] = imagePixels[(x - xOffset) + (y - yOffset) * w];
						}
					}
				}
			}
			
			this.game.getScreen().changePixels(pixels);
//		}
	}
	
	/////////////////////////////////////////////////
	
	public int getPosX() {
		return this.posX;
	}
	
	public int getPosY() {
		return this.posY;
	}
	
	public int getGridX() {
		return this.gridX;
	}
	
	public int getGridY() {
		return this.gridY;
	}
	
	public Game getGame() {
		return this.game;
	}
	
	public BufferedImage[] getImages() {
		return this.images;
	}
	
	public boolean doImagesContainAlphaColor() {
		return this.imagesContainAlphaColor;
	}

}
