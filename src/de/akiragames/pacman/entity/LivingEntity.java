package de.akiragames.pacman.entity;

import de.akiragames.pacman.game.Direction;
import de.akiragames.pacman.graphics.Screen;

public class LivingEntity extends Entity {
	
	private int speed;
	protected boolean isMoving;
	private Direction direction;
	
	public LivingEntity(int posX, int posY, Screen screen, String[] imageFiles, boolean imagesContainAlphaColor) {
		super(posX, posY, screen, imageFiles, imagesContainAlphaColor);
		
		this.speed = 2;
		this.isMoving = false;
		this.direction = Direction.UP;
	}
	
	/**
	 * Ändert Richtung des LivingEntity.
	 */
	public void changeDirection(Direction newDirection) {
		if (this.direction != newDirection) this.direction = newDirection;
	}
	
	/**
	 * Setzt Geschwindigkeitswert für die Kreatur.
	 */
	public void setSpeed(int newSpeed) {
		if (newSpeed < 0) newSpeed = 2;
		
		this.speed = newSpeed;
	}
	
	////////////////////////////////////////////
	
	public Direction getDirection() {
		return this.direction;
	}
	
	public int getSpeed() {
		return this.speed;
	}
	
	public boolean isMoving() {
		return this.isMoving;
	}

}
