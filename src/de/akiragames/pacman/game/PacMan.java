package de.akiragames.pacman.game;

public class PacMan {
	
	private int posX, posY, lives, ghostsEaten, powerUpsEaten;
	
	//private int radius = 50;
	
	public PacMan(int posX, int posY) {
		this.lives = 3;
		this.ghostsEaten = 0;
		this.powerUpsEaten = 0;
	}
	
	public int getPosX() {
		return this.posX;
	}
	
	public int getPosY() {
		return this.posY;
	}
	
	public int getLives() {
		return this.lives;
	}
	
	public int getGhostsEaten() {
		return this.ghostsEaten;
	}
	
	public int getPowerUpsEaten() {
		return this.powerUpsEaten;
	}

}
