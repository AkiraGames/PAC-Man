package de.akiragames.pacman.creature;

public class Creature {
	
	private int posX, posY;
	
	public Creature(int posX, int posY) {
		this.posX = posX;
		this.posY = posY;
	}
	
	public int getPosX() {
		return this.posX;
	}
	
	public int getPosY() {
		return this.posY;
	}

}
