package de.akiragames.pacman.entity;

public class Entity {
	
	protected int posX, posY;
	// private Image[] images;
	// private boolean imagesContainAlphaColor;
	
	public Entity(int posX, int posY) {
		this.posX = posX;
		this.posY = posY;
	}
	
	/////////////////////////////////////////////////
	
	public int getPosX() {
		return this.posX;
	}
	
	public int getPosY() {
		return this.posY;
	}

}
