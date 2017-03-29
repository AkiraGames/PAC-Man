package de.akiragames.pacman.entity;

import java.io.File;

import de.akiragames.pacman.game.Direction;

public class LivingEntity extends Entity {
	
	private double speed;
	private boolean isMoving;
	private Direction direction;
	
	public LivingEntity(int posX, int posY, File[] imageFiles, boolean imagesContainAlphaColor) {
		super(posX, posY, imageFiles, imagesContainAlphaColor);
		
		this.speed = 1.0;
		this.isMoving = false;
		this.direction = Direction.UP;
	}
	
	/**
	 * Ändert Richtung des LivingEntity.
	 */
	public void changeDirection(Direction newDirection) {
		this.direction = newDirection;
	}
	
	/**
	 * Setzt Geschwindigkeitswert für die Kreatur.
	 */
	public void setSpeed(double newSpeed) {
		if (newSpeed < 0.0) newSpeed = 1.0;
		
		this.speed = newSpeed;
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
