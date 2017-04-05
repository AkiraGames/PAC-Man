package de.akiragames.pacman.game;

import java.awt.Color;

import de.akiragames.pacman.graphics.Screen;

public class Wall {
	
	private int posX1, posY1, posX2, posY2;
	private Screen screen;
	
	private Color color;
	
	public Wall(int posX1, int posY1, int posX2, int posY2, Screen screen) {
		this.posX1 = posX1;
		this.posY1 = posY1;
		this.posX2 = posX2;
		this.posY2 = posY2;
		
		this.screen = screen;
		this.color = Color.BLUE;
	}
	
	public Wall(int posX1, int posY1, int posX2, int posY2, Color color, Screen screen) {
		this.posX1 = posX1;
		this.posY1 = posY1;
		this.posX2 = posX2;
		this.posY2 = posY2;
		
		this.screen = screen;
		this.color = color;
	}
	
	public void render() {
		int[] pixels = this.screen.getPixels();
		
		for (int y = this.posY1; y < this.posY2; y++) {
			if (y >= 0 && y < this.screen.getHeight()) {
				for (int x = this.posX1; x < this.posX2; x++) {
					if (x >= 0 && x < this.screen.getWidth()) {
						pixels[x + y * this.screen.getWidth()] = this.color.getRGB();
					}
				}
			}
		}
		
		this.screen.changePixels(pixels);
	}
	
	/////////////////////////////////////////////////
	
	public int getPosX1() {
		return this.posX1;
	}
	
	public int getPosY1() {
		return this.posY1;
	}
	
	public int getPosX2() {
		return this.posX2;
	}
	
	public int getPosY2() {
		return this.posY2;
	}
	
	public Screen getScreen() {
		return this.screen;
	}
	
	public Color getWallColor() {
		return this.color;
	}

}
