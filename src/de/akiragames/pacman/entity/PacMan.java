package de.akiragames.pacman.entity;

import java.io.File;

import de.akiragames.pacman.game.Direction;

public class PacMan extends LivingEntity {

	public PacMan(int posX, int posY) {
		super(posX, posY, new File[]{new File("res/pacman_1.png"), new File("res/pacman_2.png"), new File("res/pacman_3.png"), new File("res/pacman_4.png")}, true);
		
		this.changeDirection(Direction.RIGHT);
	}

}
