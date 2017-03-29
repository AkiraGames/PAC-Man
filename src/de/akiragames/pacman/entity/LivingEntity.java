package de.akiragames.pacman.entity;

import java.io.File;

import de.akiragames.pacman.game.Direction;

public class LivingEntity extends Entity {
	
	private int speed;
	private boolean isMoving;
	private Direction direction;
	
	public LivingEntity(int posX, int posY, File[] imageFiles, boolean imagesContainAlphaColor) {
		super(posX, posY, imageFiles, imagesContainAlphaColor);
		
		this.speed = 1;
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
		if (newSpeed < 0) newSpeed = 1;
		
		this.speed = newSpeed;
	}
	
	public void move() {
		switch(this.direction) {
			case UP:
				this.posY -= this.speed;
				break;
			case DOWN:
				this.posY += this.speed;
				break;
			case LEFT:
				this.posX -= this.speed;
				break;
			case RIGHT:
				this.posX += this.speed;
				break;
			default:
				break;
		}
	}
	
	////////////////////////////////////////////
	
	public Direction getDirection() {
		return this.direction;
	}
	
	public double getSpeed() {
		return this.speed;
	}
	
	public boolean isMoving() {
		return this.isMoving;
	}

}
