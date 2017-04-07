package de.akiragames.pacman.entity;

import de.akiragames.pacman.graphics.Screen;

public class PacDot extends Entity {

	public PacDot(int gridX, int gridY, Screen screen) {
		super(gridX, gridY, screen, new String[]{"map/pacdot.png"}, true);
	}
	
	public void render() {
		this.render(0);
	}

}
