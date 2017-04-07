package de.akiragames.pacman.entity;

import de.akiragames.pacman.game.Direction;
import de.akiragames.pacman.game.Game;
import de.akiragames.pacman.game.GameColor;
import de.akiragames.pacman.graphics.Screen;

public class Ghost extends LivingEntity {
	
	private int id;
	private GameColor color;

	public Ghost(int gridX, int gridY, Screen screen, Game game, Direction direction, int id) {
		super(gridX, gridY, screen, game, new String[]{"ghost/ghost_1.png", "ghost/ghost_2.png", "ghost/ghost_3.png", "ghost/ghost_4.png", "ghost/ghost_5.png"}, true);
		
		this.color = GameColor.NORMAL;
		this.id = id;
		
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
	
	public void update() {
		this.updateMovement();
	}

	///////////////////////////////////////////////
	
	public GameColor getColor() {
		return this.color;
	}
	
	public int getId() {
		return this.id;
	}

}
