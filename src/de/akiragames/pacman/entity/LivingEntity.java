package de.akiragames.pacman.entity;

import de.akiragames.pacman.game.Direction;
import de.akiragames.pacman.graphics.Screen;

public class LivingEntity extends Entity {
	
	private int speed;
	private boolean isMoving;
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
	
	public void move() {
		int xOffset = this.images[0].getWidth() / 2;
		int yOffset = this.images[0].getHeight() / 2;
		
		if (posY <= yOffset) {
			this.direction = Direction.DOWN;
			this.posY = yOffset;
		} else if (posY >= this.screen.getHeight() - yOffset) {
			this.direction = Direction.UP;
			this.posY = this.screen.getHeight() - yOffset;
		}
		
		if (posX <= xOffset) {
			this.direction = Direction.RIGHT;
			this.posX = xOffset;
		} else if (posX >= this.screen.getWidth() - xOffset) {
			this.direction = Direction.LEFT;
			this.posX = this.screen.getWidth() - xOffset;
		}
		
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
