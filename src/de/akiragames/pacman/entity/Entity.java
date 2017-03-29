package de.akiragames.pacman.entity;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import de.akiragames.pacman.graphics.Screen;

public class Entity {
	
	private int counter, time;
	private boolean animUp;
	
	protected int posX, posY;
	private BufferedImage[] images;
	private boolean imagesContainAlphaColor;
	
	public Entity(int posX, int posY, File[] imageFiles, boolean imagesContainAlphaColor) {
		this.counter = 0;
		this.time = 0;
		this.animUp = true;
		
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
		
		for (int y = 0; y < screen.getHeight(); y++) {
			if (y < 0 || y >= screen.getHeight() || y >= h) break;
			
			for (int x = 0; x < screen.getWidth(); x++) {
				if (x < 0 || x >= screen.getWidth() || x >= w) break;
				
				if (imagePixels[x + y * w] != screen.getAlphaColor().getRGB())
					pixels[x + y * screen.getWidth()] = imagePixels[x + y * w];
			}
		}
		
		screen.changePixels(pixels);
	}
	
	public void renderAnimation(Screen screen) {
		int[] pixels = screen.getPixels();
		
		this.counter++;
		
		if (this.counter % 250 == 0) {
			if (this.time == this.images.length - 1) {
				this.animUp = false;
			} else if (this.time == 0) {
				this.animUp = true;
			}
			
			if (this.animUp) {
				time++;
			} else {
				time--;
			}
		}
		
		int w = this.images[this.time].getWidth();
		int h = this.images[this.time].getHeight();
		int[] imagePixels = new int[w * h];
		
		this.images[this.time].getRGB(0, 0, w, h, imagePixels, 0, w);
		
		for (int y = 0; y < screen.getHeight(); y++) {
			if (y < 0 || y >= screen.getHeight() || y >= h) break;
			
			for (int x = 0; x < screen.getWidth(); x++) {
				if (x < 0 || x >= screen.getWidth() || x >= w) break;
				
				if (imagePixels[x + y * w] != screen.getAlphaColor().getRGB())
					pixels[x + y * screen.getWidth()] = imagePixels[x + y * w];
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
