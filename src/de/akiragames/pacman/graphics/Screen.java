package de.akiragames.pacman.graphics;

import java.awt.Color;

public class Screen {

	private int width, height;
	private int[] pixels;

	public Screen(int width, int height) {
		this.width = width;
		this.height = height;

		this.pixels = new int[width * height];
	}
	
	public void clear() {
		for (int i = 0; i < this.pixels.length; i++) {
			pixels[i] = 0;
		}
	}
	
	public void changePixels(int[] pixels) {
		if (this.pixels.length == pixels.length) {
			this.pixels = pixels;
		} else {
			throw new ArrayIndexOutOfBoundsException();
		}
	}

	/**
	 * Rendert das Labyrinth.
	 */
	public void renderLevel() {
	}
	
	/////////////////////////////////////////

	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public int[] getPixels() {
		return this.pixels;
	}
	
	/**
	 * Gibt den Farbwert zurück, der ausgeschnitten werden soll (wie .png).
	 */
	public Color getAlphaColor() {
		return new Color(0xff00ff);
	}

}
