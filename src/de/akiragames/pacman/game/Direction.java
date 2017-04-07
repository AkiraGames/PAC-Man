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
	
	public Direction invert() {
		Direction dir = null;
		
		switch (this.rotation) {
			case 0:
				dir = DOWN;
				break;
			case 90:
				dir = LEFT;
				break;
			case 180:
				dir = UP;
				break;
			case 270:
				dir = RIGHT;
				break;
		}
		
		return dir;
	}

}
