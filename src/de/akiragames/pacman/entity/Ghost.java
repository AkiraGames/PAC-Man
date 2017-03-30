package de.akiragames.pacman.entity;

import de.akiragames.pacman.game.Direction;
import de.akiragames.pacman.game.GameColor;
import de.akiragames.pacman.graphics.Screen;

public class Ghost extends LivingEntity {
	
	private GameColor color;

	public Ghost(int posX, int posY, Screen screen, Direction direction) {
		super(posX, posY, screen, new String[]{"res/ghost/ghost_1.png", "res/ghost/ghost_2.png", "res/ghost/ghost_3.png", "res/ghost/ghost_4.png", "res/ghost/ghost_5.png"}, true);
		
		this.color = GameColor.NORMAL;
		
		this.changeDirection(direction);
	}
	
	/**
	 * Verändert die aktuelle Farbe.
	 */
	public void changeColor(GameColor newColor) {
		this.color = newColor;
	}
	
	/**
	 * Setzt aktuelle Farbe auf Ursprungsfarbe zurück.
	 */
	public void resetColor() {
		this.color = GameColor.NORMAL;
	}
	
	///////////////////////////////////////////////
	
	public GameColor getColor() {
		return this.color;
	}

}
