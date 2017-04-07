package de.akiragames.pacman.entity;

import java.util.ArrayList;

import de.akiragames.pacman.game.Direction;
import de.akiragames.pacman.game.Game;
import de.akiragames.pacman.graphics.Screen;

public class LivingEntity extends Entity {
	
	private int speed;
	private boolean isMovingX, isMovingY;
	private Direction direction;
	private int desiredGridX, desiredGridY;
	private Game game;
	
	public LivingEntity(int gridX, int gridY, Screen screen, Game game, String[] imageFiles, boolean imagesContainAlphaColor) {
		super(gridX, gridY, screen, imageFiles, imagesContainAlphaColor);
		
		this.speed = 2;
		this.isMovingX = false;
		this.isMovingY = false;
		
		this.direction = Direction.RIGHT;
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
				this.direction = newDirection;
			}
			
			switch (newDirection) {
				case DOWN:
					this.desiredGridY = this.gridY + this.getShortestDistance(this.gridX, this.gridY);
					break;
				case UP:
					this.desiredGridY = this.gridY - this.getShortestDistance(this.gridX, this.gridY);
					break;
				case RIGHT:
					this.desiredGridX = this.gridX + this.getShortestDistance(this.gridX, this.gridY);
					break;
				case LEFT:
					this.desiredGridX = this.gridX - this.getShortestDistance(this.gridX, this.gridY);
					break;
				default:
					break;
			}
		}
	}
	
	private Direction[] getFreeDirections(int gridX, int gridY) {
		ArrayList<Direction> dirs = new ArrayList<Direction>();
		
		if (!this.game.getMap().isWallBlock(gridX, gridY - 1)) {
			dirs.add(Direction.UP);
		}
		
		if (!this.game.getMap().isWallBlock(gridX, gridY + 1)) {
			dirs.add(Direction.DOWN);
		}
		
		if (!this.game.getMap().isWallBlock(gridX - 1, gridY)) {
			dirs.add(Direction.LEFT);
		}
		
		if (!this.game.getMap().isWallBlock(gridX + 1, gridY)) {
			dirs.add(Direction.RIGHT);
		}
		
		return dirs.toArray(new Direction[dirs.size()]);
	}
	
	private int getShortestDistance(int gridX, int gridY) {
		int intersecDist = this.getDistanceToNextIntersection(gridX, gridY);
		int wallDist = this.getDistanceToNextWall(gridX, gridY);
		
//		System.out.println("intersec: " + intersecDist + " | wall: " + wallDist);
		
		if (intersecDist == 0) {
			return wallDist;
		} else if (wallDist < intersecDist) {
			return wallDist;
		} else {
			return intersecDist;
		}
	}
	
	private int getDistanceToNextIntersection(int gridX, int gridY) {
		int distance = 0;
		
		switch (this.direction) {
			case DOWN:
				for (int i = gridY + 1; i < this.game.getMap().getHeight(); i++) {
					if (this.getFreeDirections(gridX, i).length > 2) {
						distance = i - gridY;
						break;
					}
				}
				
				break;
			case UP:
				for (int i = gridY - 1; i >= 0; i--) {
					if (this.getFreeDirections(gridX, i).length > 2) {
						distance = gridY - i;
						break;
					}
				}
				
				break;
			
			case RIGHT:
				for (int i = gridX + 1; i < this.game.getMap().getWidth(); i++) {
					if (this.getFreeDirections(i, gridY).length > 2) {
						distance = i - gridX;
						break;
					}
				}
			
				break;
			case LEFT:
				for (int i = gridX - 1; i >= 0; i--) {
					if (this.getFreeDirections(i, gridY).length > 2) {
						distance = gridX - i;
						break;
					}
				}
				
				break;
		}
		
		return distance;
	}
	
	private int getDistanceToNextWall(int gridX, int gridY) {
		int distance = 0;
		
		switch (this.direction) {
			case DOWN:
				for (int i = gridY; i < this.game.getMap().getHeight(); i++) {
					if (this.game.getMap().isWallBlock(gridX, i)) {
						distance = i - gridY - 1;
						break;
					}
				}
				
				break;
			case UP:
				for (int i = gridY; i >= 0; i--) {
					if (this.game.getMap().isWallBlock(gridX, i)) {
						distance = gridY - i - 1;
						break;
					}
				}
				
				break;
			
			case RIGHT:
				for (int i = gridX; i < this.game.getMap().getWidth(); i++) {
					if (this.game.getMap().isWallBlock(i, gridY)) {
						distance = i - gridX - 1;
						break;
					}
				}
			
				break;
			case LEFT:
				for (int i = gridX; i >= 0; i--) {
					if (this.game.getMap().isWallBlock(i, gridY)) {
						distance = gridX - i - 1;
						break;
					}
				}
				
				break;
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
		if (this.posX < newGridX * 32 + 16) {
			this.posX +=  this.speed;
			this.isMovingX = true;
		} else if (this.posX > newGridX * 32 + 16) {
			this.posX -= this.speed;
			this.isMovingX = true;
		} else {
			this.gridX = newGridX;
			this.isMovingX = false;
		}
		
		if (this.posX % 32 == 16) {
			this.gridX = (this.posX - 16) / 32;
		}
		
		if (this.posY < newGridY * 32 + 16) {
			this.posY += this.speed;
			this.isMovingY = true;
		} else if (this.posY > newGridY * 32 + 16) {
			this.posY -= this.speed;
			this.isMovingY = true;
		} else {
			this.gridY = newGridY;
			this.isMovingY = false;
		}
		
		if (this.posY % 32 == 16) {
			this.gridY = (this.posY - 16) / 32;
		}
	}
	
	////////////////////////////////////////////
	
	public Direction getDirection() {
		return this.direction;
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
