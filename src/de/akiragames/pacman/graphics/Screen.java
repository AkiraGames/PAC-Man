package de.akiragames.pacman.graphics;

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
				
				this.pixels[x + y * this.width] = 0x00ff00;
			}
		}
	}

	public int[] getPixels() {
		return this.pixels;
	}

}
