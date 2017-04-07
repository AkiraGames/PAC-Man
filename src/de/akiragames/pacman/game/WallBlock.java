package de.akiragames.pacman.game;

import java.awt.Color;

import de.akiragames.pacman.graphics.Screen;

public class WallBlock {
	
	private int posX, posY;
	private Screen screen;
	
	private Color color;
	
	public WallBlock(int posX, int posY, Screen screen) {
		this.posX = posX;
		this.posY = posY;
		
		this.screen = screen;
		this.color = Color.BLUE;
	}
	
	public void render() {
		int[] pixels = this.screen.getPixels();
		
		for (int y = this.posY * 32; y < (this.posY + 1) * 32; y++) {
			if (y >= 0 && y < this.screen.getHeight()) {
				for (int x = this.posX * 32; x < (this.posX + 1) * 32; x++) {
					if (x >= 0 && x < this.screen.getWidth()) {
						pixels[x + y * this.screen.getWidth()] = this.color.getRGB();
					}
				}
			}
		}
		
		this.screen.changePixels(pixels);
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
	
	public Color getWallPixelColor() {
		return this.color;
	}

}
