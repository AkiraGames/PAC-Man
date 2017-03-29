package de.akiragames.pacman.entity;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import de.akiragames.pacman.graphics.Screen;

public class Entity {
	
	protected int posX, posY;
	protected BufferedImage[] images;
	private boolean imagesContainAlphaColor;
	
	public Entity(int posX, int posY, File[] imageFiles, boolean imagesContainAlphaColor) {
		this.posX = posX;
		this.posY = posY;
		
		this.images = this.loadImages(imageFiles);
		this.imagesContainAlphaColor = imagesContainAlphaColor;
	}
	
	private BufferedImage[] loadImages(File[] imageFiles) {
		BufferedImage[] images = new BufferedImage[imageFiles.length];
		
		try {
			for (int i = 0; i < imageFiles.length; i++) {
				images[i] = ImageIO.read(imageFiles[i]);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return images;
	}
	
	public void render(Screen screen, int imageIndex) {
		int[] pixels = screen.getPixels();
		
		int w = this.images[imageIndex].getWidth();
		int h = this.images[imageIndex].getHeight();
		int[] imagePixels = new int[w * h];
		
		this.images[imageIndex].getRGB(0, 0, w, h, imagePixels, 0, w);
		
		int xOffset = this.posX - w / 2;
		int yOffset = this.posY - h / 2;
		
		for (int y = yOffset; y < screen.getHeight(); y++) {
			if (y >= 0 && y < screen.getHeight() && y < h + yOffset) {
				for (int x = xOffset; x < screen.getWidth(); x++) {
					if (x >= 0 && x < screen.getWidth() && x < w + xOffset) {
						if (imagePixels[(x - xOffset) + (y - yOffset) * w] != screen.getAlphaColor().getRGB())
							pixels[x + y * screen.getWidth()] = imagePixels[(x - xOffset) + (y - yOffset) * w];
					}
				}
			}
		}
		
		screen.changePixels(pixels);
	}
	
	/////////////////////////////////////////////////
	
	public int getPosX() {
		return this.posX;
	}
	
	public int getPosY() {
		return this.posY;
	}
	
	public BufferedImage[] getImages() {
		return this.images;
	}
	
	public boolean doImagesContainAlphaColor() {
		return this.imagesContainAlphaColor;
	}

}
