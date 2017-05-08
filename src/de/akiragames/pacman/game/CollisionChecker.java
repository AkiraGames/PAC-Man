package de.akiragames.pacman.game;

import java.util.ArrayList;

import de.akiragames.pacman.entity.Entity;
import de.akiragames.pacman.entity.LivingEntity;
import de.akiragames.pacman.entity.PacDot;

public class CollisionChecker {
	
	private LivingEntity living;
	private Entity[] entities;
	private ArrayList<Entity> collisions; // Kollisionsvariablen für die Entities (boolean)
	
	public CollisionChecker(LivingEntity living, Entity[] entities) {
		this.living = living;
		this.entities = entities;
		
		this.collisions = new ArrayList<Entity>();
	}
	
	public void check() {
		int xOffset = this.living.getImages()[0].getWidth() / 2;
		int yOffset = this.living.getImages()[0].getHeight() / 2;
		
		int offset = (xOffset + yOffset) / 2;
		
		for (int i = 0; i < this.entities.length; i++) {
			Entity entity = this.entities[i];
			
			if (this.living.getGame().getScreen().getDistance(this.living.getPosX(), this.living.getPosY(), entity.getPosX(), entity.getPosY()) <= offset - (entity instanceof PacDot ? 4 : 0)) {
				if (!this.collisions.contains(entity)) this.collisions.add(entity);
			} else {
				if (this.collisions.contains(entity)) this.collisions.remove(entity);
			}
		}
	}
	
	public void addEntity(Entity entity) {
		this.addEntities(new Entity[]{entity});
	}
	
	public void removeEntity(Entity entity) {
		ArrayList<Entity> arr = new ArrayList<Entity>();
		
		for (int i = 0; i < this.entities.length; i++) {
			if (this.entities[i] != entity) arr.add(entity);
		}
		
		this.entities = arr.toArray(new Entity[arr.size()]);
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
	
	public LivingEntity getLivingEntity() {
		return this.living;
	}
	
	public boolean isCollidingWithPacMan(Entity entity) {
		return this.collisions.contains(entity);
	}
	
	public ArrayList<Entity> getCollidingEntities() {
		return this.collisions;
	}

}
