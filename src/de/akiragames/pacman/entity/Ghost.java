package de.akiragames.pacman.entity;

import java.awt.Color;

import de.akiragames.pacman.game.Direction;

public class Ghost extends LivingEntity {
	
	private Color mainColor, currentColor;

	public Ghost(int posX, int posY, Color color, Direction direction) {
		super(posX, posY);
		
		this.mainColor = color;
		this.currentColor = color;
		
		this.changeDirection(direction);
	}
	
	/**
	 * Verändert die aktuelle Farbe.
	 */
	public void changeColor(Color newColor) {
		this.currentColor = newColor;
	}
	
	/**
	 * Setzt aktuelle Farbe auf Ursprungsfarbe zurück.
	 */
	public void resetColor() {
		this.currentColor = this.mainColor;
	}
	
	///////////////////////////////////////////////
	
	public Color getMainColor() {
		return this.mainColor;
	}
	
	public Color getCurrentColor() {
		return this.currentColor;
	}

}
