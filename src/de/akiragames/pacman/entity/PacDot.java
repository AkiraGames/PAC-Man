package de.akiragames.pacman.entity;

import de.akiragames.pacman.game.Game;

public class PacDot extends Entity {
	
	private boolean pacManCollision;

	public PacDot(int gridX, int gridY, Game game) {
		super(gridX, gridY, game, new String[]{"map/pacdot.png"}, true);
		
		this.pacManCollision = false;
	}
	
	public void update() {
		if (this.game.getMap().getCollisionChecker().isCollidingWithPacMan(this) && !this.pacManCollision) {
			this.pacManCollision = true;
			
			this.game.scoreUp(10);
		}
	}
	
	public void render() {
		if (!this.pacManCollision) this.render(0);
	}
	
	/////////////////////////////////////////////////
	
	public boolean isCollidingPacMan() {
		return this.pacManCollision;
	}

}
