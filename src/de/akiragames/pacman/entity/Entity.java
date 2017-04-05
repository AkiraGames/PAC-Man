package de.akiragames.pacman.entity;

import java.awt.image.BufferedImage;

import de.akiragames.pacman.Main;
import de.akiragames.pacman.graphics.Screen;
import de.akiragames.pacman.utils.FileUtils;

public class Entity {
	
	protected int posX, posY;
	protected Screen screen;
	protected BufferedImage[] images;
	private boolean imagesContainAlphaColor;
	
	public Entity(int posX, int posY, Screen screen, String[] imageFiles, boolean imagesContainAlphaColor) {
		this.posX = posX;
		this.posY = posY;
		this.screen = screen;
		
		this.images = FileUtils.loadImages(imageFiles);
		this.imagesContainAlphaColor = imagesContainAlphaColor;
	}
	
	public void render(int imageIndex) {
		if (!Main.entityCollisionChecker.isCollidingWithPacMan(this)) {
			int[] pixels = this.screen.getPixels();
			
			int w = this.images[imageIndex].getWidth();
			int h = this.images[imageIndex].getHeight();
			int[] imagePixels = new int[w * h];
			
			this.images[imageIndex].getRGB(0, 0, w, h, imagePixels, 0, w);
			
			int xOffset = this.posX - w / 2;
			int yOffset = this.posY - h / 2;
			
			for (int y = yOffset; y < this.posY + h / 2; y++) {
				if (y >= 0 && y < this.screen.getHeight() && y < h + yOffset) {
					for (int x = xOffset; x < this.posX + w / 2; x++) {
						if (x >= 0 && x < this.screen.getWidth() && x < w + xOffset) {
							if (imagePixels[(x - xOffset) + (y - yOffset) * w] != this.screen.getAlphaColor().getRGB())
								pixels[x + y * this.screen.getWidth()] = imagePixels[(x - xOffset) + (y - yOffset) * w];
						}
					}
				}
			}
			
			this.screen.changePixels(pixels);
		}
	}
	
	/////////////////////////////////////////////////
	
	public int getPosX() {
		return this.posX;
	}
	
	public int getPosY() {
		return this.posY;
	}
	
	public Screen getScreen() {
		return this.screen;
	}
	
	public BufferedImage[] getImages() {
		return this.images;
	}
	
	public boolean doImagesContainAlphaColor() {
		return this.imagesContainAlphaColor;
	}

}
