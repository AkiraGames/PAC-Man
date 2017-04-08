package de.akiragames.pacman.game;

import java.awt.Color;
import java.util.ArrayList;

public class WallBlock {
	
	private int posX, posY;
	private Map map;
	
	private ArrayList<Direction> surroundingWalls;
	
	private Color color;
	
	public WallBlock(int posX, int posY, Map map) {
		this.posX = posX;
		this.posY = posY;
		
		this.surroundingWalls = new ArrayList<Direction>();
		
		this.map = map;
		this.color = Color.BLUE;
	}
	
	public void setSurroundingWalls(ArrayList<Direction> directions) {
		this.surroundingWalls = directions;
	}
	
	public void render() {
		int[] pixels = this.map.getGame().getScreen().getPixels();
		
		for (int y = this.posY * 32; y < (this.posY + 1) * 32; y++) {
			if (y >= 0 && y < this.map.getGame().getScreen().getHeight()) {
				for (int x = this.posX * 32; x < (this.posX + 1) * 32; x++) {
					if (x >= 0 && x < this.map.getGame().getScreen().getWidth()) {
						pixels[x + y * this.map.getGame().getScreen().getWidth()] = this.color.getRGB();
					}
				}
			}
		}
		
		this.map.getGame().getScreen().changePixels(pixels);
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
	
	public ArrayList<Direction> getSurroundingWalls() {
		return this.surroundingWalls;
	}
	
	public Color getWallPixelColor() {
		return this.color;
	}

}
