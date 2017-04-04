package de.akiragames.pacman.game;

import de.akiragames.pacman.entity.LivingEntity;

public class WallCollisionChecker {
	
	/**
	 * 1. Ebene: Entities, 2. Ebene: Richtungen
	 */
	private boolean[][] directions; // [0] = UP, [1] = DOWN, [2] = LEFT, [3] = RIGHT
	
	private LivingEntity[] entities;
	private Wall[] walls;
	
	public WallCollisionChecker(LivingEntity entities[], Wall[] walls) {
		this.entities = entities;
		this.walls = walls;
		
		this.directions = new boolean[this.entities.length][4];
		
		for (int i = 0; i < this.entities.length; i++) {
			this.directions[i] = new boolean[]{false, false, false, false};
		}
	}
	
	public void check() {
		for (int j = 0; j < entities.length; j++) {
			int xOffset = this.entities[j].getImages()[0].getWidth() / 2;
			int yOffset = this.entities[j].getImages()[0].getHeight() / 2;
			
			for (int i = 0; i < this.walls.length; i++) {
				if (i == 0) {
					this.directions[j][0] = false;
					this.directions[j][1] = false;
					this.directions[j][2] = false;
					this.directions[j][3] = false;
				}
				
				if (this.entities[j].getPosY() - yOffset == this.walls[i].getPosY2() && this.entities[j].getPosX() + xOffset > this.walls[i].getPosX1() && this.entities[j].getPosX() - xOffset < this.walls[i].getPosX2()) {
					this.directions[j][0] = true;
				}
				
				if (this.entities[j].getPosY() + yOffset == this.walls[i].getPosY1() && this.entities[j].getPosX() + xOffset > this.walls[i].getPosX1() && this.entities[j].getPosX() - xOffset < this.walls[i].getPosX2()) {
					this.directions[j][1] = true;
				}
				
				if (this.entities[j].getPosX() - xOffset == this.walls[i].getPosX2() && this.entities[j].getPosY() + yOffset > this.walls[i].getPosY1() && this.entities[j].getPosY() - yOffset < this.walls[i].getPosY2()) {
					this.directions[j][2] = true;
				}
				
				if (this.entities[j].getPosX() + xOffset == this.walls[i].getPosX1() && this.entities[j].getPosY() + yOffset > this.walls[i].getPosY1() && this.entities[j].getPosY() - yOffset < this.walls[i].getPosY2()) {
					this.directions[j][3] = true;
				}
			}
		}
	}
	
	/////////////////////////////////////////////////
	
	public boolean[][] getDirections() {
		return this.directions;
	}
	
	public LivingEntity[] getEntities() {
		return this.entities;
	}
	
	public Wall[] getWalls() {
		return this.walls;
	}

}
