package de.akiragames.pacman.entity;

import de.akiragames.pacman.game.Direction;

public class PacMan extends LivingEntity {

	public PacMan(int posX, int posY) {
		super(posX, posY);
		
		this.changeDirection(Direction.RIGHT);
	}

}
