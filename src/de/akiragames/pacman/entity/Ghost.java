package de.akiragames.pacman.entity;

import java.awt.Color;

public class Ghost extends LivingEntity {
	
	private Color mainColor, currentColor;

	public Ghost(int posX, int posY, Color color) {
		super(posX, posY);
		
		this.mainColor = color;
		this.currentColor = color;
	}
	
	public void changeColor(Color newColor) {
		this.currentColor = newColor;
	}
	
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
