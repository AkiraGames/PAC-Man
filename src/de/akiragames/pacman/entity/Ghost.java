package de.akiragames.pacman.entity;

import java.util.ArrayList;
import java.util.Random;

import de.akiragames.pacman.game.Direction;
import de.akiragames.pacman.game.Game;
import de.akiragames.pacman.game.GameState;

public class Ghost extends LivingEntity {
	
	private int id;
	
	private boolean isEaten;
	
	private boolean pacManCollision;

	public Ghost(int gridX, int gridY, Game game, int id) {
		super(gridX, gridY, game, new String[]{"ghost/ghost_1.png", "ghost/ghost_2.png", "ghost/ghost_3.png", "ghost/ghost_4.png", "ghost/ghost_5.png"}, true);

		this.id = id;
		this.pacManCollision = false;
		
		this.isEaten = false;
	}
	
	public void update() {
		this.updateMovement();
		
		if (!this.isMoving() && (this.game.getGameState() == GameState.IN_GAME || this.game.getGameState() == GameState.POWERUP_ACTIVE))
			this.refreshDirection();
		
		if (this.game.getMap().getCollisionCheckers()[0].isCollidingWithPacMan(this) && !this.pacManCollision) {
			this.pacManCollision = true;
		} else {
			this.pacManCollision = false;
		}
	}
	
	private void refreshDirection() {
		Direction[] freeDirections = this.getFreeDirections(this.game.getMap());
		ArrayList<Direction> dirs = new ArrayList<Direction>();
		
		for (int i = 0; i < freeDirections.length; i++) {
			if (freeDirections[i] != this.getDirection().invert())
				dirs.add(freeDirections[i]);
		}
		
		
		this.changeDirection(this.game.getMap(), dirs.get(new Random().nextInt(dirs.size())));
	}
	
	public void vanish() {
		this.isEaten = true;
	}

	///////////////////////////////////////////////
	
	public int getId() {
		return this.id;
	}
	
	public boolean isCollidingPacMan() {
		return this.pacManCollision;
	}
	
	public boolean isEaten() {
		return this.isEaten;
	}

}
