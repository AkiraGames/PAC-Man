package de.akiragames.pacman.game;

import java.awt.Color;

import de.akiragames.pacman.entity.Map;

public class WallBlock {
	
	private int posX, posY;
	private Map map;
	
	private Color color;
	
	public WallBlock(int posX, int posY, Map map) {
		this.posX = posX;
		this.posY = posY;
		
		this.map = map;
		this.color = Color.BLUE;
	}
	
	public void render() {
		int[] pixels = this.map.getScreen().getPixels();
		
		for (int y = this.posY * 32; y < (this.posY + 1) * 32; y++) {
			if (y >= 0 && y < this.map.getScreen().getHeight()) {
				for (int x = this.posX * 32; x < (this.posX + 1) * 32; x++) {
					if (x >= 0 && x < this.map.getScreen().getWidth()) {
						pixels[x + y * this.map.getScreen().getWidth()] = this.color.getRGB();
					}
				}
			}
		}
		
		this.map.getScreen().changePixels(pixels);
	}
	
	/////////////////////////////////////////////////
	
	public int getPosX() {
		return this.posX;
	}
	
	public int getPosY() {
		return this.posY;
	}
	
	public Map getMap() {
		return this.map;
	}
	
	public Color getWallPixelColor() {
		return this.color;
	}

}
