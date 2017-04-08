package de.akiragames.pacman.game;

import java.util.ArrayList;

import de.akiragames.pacman.entity.Entity;
import de.akiragames.pacman.entity.PacDot;
import de.akiragames.pacman.entity.PacMan;

public class CollisionChecker {
	
	private PacMan pacman;
	private Entity[] entities;
	private ArrayList<Entity> collisions; // Kollisionsvariablen für die Entities (boolean)
	
	public CollisionChecker(PacMan pacman, Entity[] entities) {
		this.pacman = pacman;
		this.entities = entities;
		
		this.collisions = new ArrayList<Entity>();
	}
	
	public void check() {
		int xOffset = this.pacman.getImages()[0].getWidth() / 2;
		int yOffset = this.pacman.getImages()[0].getHeight() / 2;
		
		int offset = (xOffset + yOffset) / 2;
		
		for (int i = 0; i < this.entities.length; i++) {
			Entity entity = this.entities[i];
			
			if (this.pacman.getGame().getScreen().getDistance(this.pacman.getPosX(), this.pacman.getPosY(), entity.getPosX(), entity.getPosY()) <= offset - (entity instanceof PacDot ? 4 : 0)) {
				if (!this.collisions.contains(entity)) this.collisions.add(entity);
			} else {
				if (this.collisions.contains(entity)) this.collisions.remove(entity);
			}
		}
	}
	
	public void addEntity(Entity entity) {
		this.addEntities(new Entity[]{entity});
	}
	
	public void addEntities(Entity[] newEntities) {
		Entity[] arr = new Entity[this.entities.length + newEntities.length];
		
		for (int i = 0; i < this.entities.length; i++) {
			arr[i] = this.entities[i];
		}
		
		for (int j = 0; j < newEntities.length; j++) {
			arr[j + this.entities.length] = newEntities[j];
		}
		
		this.entities = arr;
	}
	
	/////////////////////////////////////////////////
	
	public PacMan getPacMan() {
		return this.pacman;
	}
	
	public boolean isCollidingWithPacMan(Entity entity) {
		return this.collisions.contains(entity);
	}
	
	public ArrayList<Entity> getCollidingEntities() {
		return this.collisions;
	}

}
