package de.akiragames.pacman.entity;

import de.akiragames.pacman.game.Direction;
import de.akiragames.pacman.game.Game;
import de.akiragames.pacman.graphics.Screen;

public class LivingEntity extends Entity {
	
	private int speed;
	private boolean isMovingX, isMovingY;
	private Direction direction;
	private Direction formerDirection;
	private int desiredGridX, desiredGridY;
	private Game game;
	
	public LivingEntity(int gridX, int gridY, Screen screen, Game game, String[] imageFiles, boolean imagesContainAlphaColor) {
		super(gridX, gridY, screen, imageFiles, imagesContainAlphaColor);
		
		this.speed = 2;
		this.isMovingX = false;
		this.isMovingY = false;
		
		this.direction = Direction.RIGHT;
		this.formerDirection = null;
		this.game = game;
		
		this.desiredGridX = this.gridX;
		this.desiredGridY = this.gridY;
	}
	
	/**
	 * Ändert Richtung des LivingEntity.
	 */
	public void changeDirection(Direction newDirection) {
		if (!this.isMoving() || newDirection == this.direction.invert()) {
			
			if (newDirection != this.direction) {
				this.formerDirection = this.direction;
				this.direction = newDirection;
			}
			
			switch (newDirection) {
				case UP:
					this.desiredGridY = this.gridY - this.getDistanceToNextWall(this.gridX, gridY, this.direction);
					break;
				case DOWN:
					this.desiredGridY = this.gridY + this.getDistanceToNextWall(this.gridX, gridY, this.direction);
					break;
				case LEFT:
					this.desiredGridX = this.gridX - this.getDistanceToNextWall(this.gridX, gridY, this.direction);
					break;
				case RIGHT:
					this.desiredGridX = this.gridX + this.getDistanceToNextWall(this.gridX, gridY, this.direction);
					break;
				default:
					break;
			}
		}
	}
	
	private int getDistanceToNextWall(int gridX, int gridY, Direction dir) {
		int distance = 0;
		
		if (dir == Direction.UP) {
			for (int i = gridY; i >= 0; i--) {
				if (this.game.getMap().isWallBlock(gridX, i)) {
					distance = gridY - i - 1;
					break;
				}
			}
		}
		
		if (dir == Direction.DOWN) {
			for (int i = gridY; i < this.game.getMap().getHeight(); i++) {
				if (this.game.getMap().isWallBlock(gridX, i)) {
					distance = i - gridY - 1;
					break;
				}
			}
		}
		
		if (dir == Direction.LEFT) {
			for (int i = gridX; i >= 0; i--) {
				if (this.game.getMap().isWallBlock(i, gridY)) {
					distance = gridX - i - 1;
					break;
				}
			}
		} 
		
		if (dir == Direction.RIGHT) {
			for (int i = gridX; i < game.getMap().getWidth(); i++) {
				if (this.game.getMap().isWallBlock(i, gridY)) {
					distance = i - gridX - 1;
					break;
				}
			}
		}
		
		return distance;
	}
	
	protected void updateMovement() {
		this.moveToGridPosition(this.desiredGridX, this.desiredGridY);
	}
	
	/**
	 * Setzt Geschwindigkeitswert für die Kreatur.
	 */
	public void setSpeed(int newSpeed) {
		if (newSpeed < 0) newSpeed = 2;
		
		this.speed = newSpeed;
	}
	
	public void moveToGridPosition(int newGridX, int newGridY) {
		if (this.posX != this.desiredGridX * 32 + 16) {
			this.posX += (newGridX - this.gridX < 0 ? -1 : 1) * this.speed;
			this.isMovingX = true;
		} else {
			this.gridX = newGridX;
			this.isMovingX = false;
		}
		
		if (this.posY != this.desiredGridY * 32 + 16) {
			this.posY += (newGridY - this.gridY < 0 ? -1 : 1) * this.speed;
			this.isMovingY = true;
		} else {
			this.gridY = newGridY;
			this.isMovingY = false;
		}
	}
	
	////////////////////////////////////////////
	
	public Direction getDirection() {
		return this.direction;
	}
	
	public Direction getFormerDirection() {
		return this.formerDirection;
	}
	
	public Game getGame() {
		return this.game;
	}
	
	public int getSpeed() {
		return this.speed;
	}
	
	public boolean isMoving() {
		return this.isMovingX || this.isMovingY;
	}

}
