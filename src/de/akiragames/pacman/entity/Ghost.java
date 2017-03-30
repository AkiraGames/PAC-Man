package de.akiragames.pacman.entity;

import java.io.File;

import de.akiragames.pacman.game.Direction;
import de.akiragames.pacman.game.GameColor;
import de.akiragames.pacman.graphics.Screen;

public class Ghost extends LivingEntity {
	
	private GameColor color;

	public Ghost(int posX, int posY, Screen screen, Direction direction) {
		super(posX, posY, screen, new File[]{new File("res/ghost/ghost_1.png"), new File("res/ghost/ghost_2.png"), new File("res/ghost/ghost_3.png"), new File("res/ghost/ghost_4.png"), new File("res/ghost/ghost_5.png")}, true);
		
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
