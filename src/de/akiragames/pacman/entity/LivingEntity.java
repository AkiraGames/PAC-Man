package de.akiragames.pacman.entity;

public class LivingEntity extends Entity {
	
	private int direction; // Bewegungsrichtung: 0 = oben, 90 = rechts, 180 = unten, 270 = links
	private double speed;
	private boolean isMoving;
	
	public LivingEntity(int posX, int posY) {
		super(posX, posY);
		
		this.speed = 1.0;
		this.isMoving = false;
	}
	
	/**
	 * Setze Geschwindigkeitswert für die Kreatur.
	 */
	public void setSpeed(double newSpeed) {
		if (newSpeed < 0.0) newSpeed = 1.0;
		
		this.speed = newSpeed;
	}
	
	////////////////////////////////////////////
	
	public int getDirection() {
		return this.direction;
	}
	
	public double getSpeed() {
		return this.speed;
	}
	
	public boolean isMoving() {
		return this.isMoving;
	}

}
