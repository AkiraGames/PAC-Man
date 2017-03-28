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

	public void render() {
		for (int y = 0; y < this.height; y++) {
			if (y < 0 || y >= this.height) break;
			
			for (int x = 0; x < this.width; x++) {
				if (x < 0 || x >= this.width) break;
				
				this.pixels[x + y * this.width] = 0x00ff00; // Test
			}
		}
	}
	
	/**
	 * Draws a rectangle with top-left corner coordinates (xPos / yPos).
	 */
	public void drawRect(int xPos, int yPos, int width, int height, Color fillColor) {
		for (int y = yPos; y < yPos + height; y++) {
			if (y < 0 || y >= this.height) break;
			
			for (int x = xPos; x < xPos + width; x++) {
				if (x < 0 || x >= this.width) break;
				
				this.pixels[x + y * this.width] = fillColor.getRGB();
			}
		}
	}
	
	/**
	 * Draws a circle with center corner coordinates (xCenter / yCenter).
	 */
	public void drawCircle(int xCenter, int yCenter, int radius, Color fillColor, boolean drawBorder, int borderStrength, Color borderColor) {
		for (int y = yCenter - radius; y < yCenter + radius; y++) {
			if (y < 0 || y >= this.height) break;
			
			for (int x = xCenter - radius; x < xCenter + radius; x++) {
				if (x < 0 || x >= this.width) break;
				
				double distance = this.getPixelDistance(xCenter, yCenter, x, y);
				
				if (!drawBorder) {
					if (distance <= radius) {
						this.pixels[x + y * this.width] = fillColor.getRGB();
					}
				} else {
					if (distance <= radius - borderStrength) {
						this.pixels[x + y * this.width] = fillColor.getRGB();
					} else if (distance <= radius && distance > radius - borderStrength) {
						this.pixels[x + y * this.width] = borderColor.getRGB();
					}
				}
			}
		}
	}
	
	/////////////////////////////////////////
	
	public double getPixelDistance(int x1, int y1, int x2, int y2) {
		return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
	}

	public int[] getPixels() {
		return this.pixels;
	}

}
