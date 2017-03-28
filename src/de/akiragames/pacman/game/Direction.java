package de.akiragames.pacman.game;

public enum Direction {
	
	UP(0), RIGHT(90), DOWN(180), LEFT(270);
	
	private int rotation;
	
	Direction(int rotation) {
		this.rotation = rotation;
	}
	
	public int getRotation() {
		return this.rotation;
	}

}
