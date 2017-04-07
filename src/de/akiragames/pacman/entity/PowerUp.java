package de.akiragames.pacman.entity;

import de.akiragames.pacman.graphics.Screen;

public class PowerUp extends Entity {
	
	private int counter, anim;

	public PowerUp(int gridX, int gridY, Screen screen) {
		super(gridX, gridY, screen, new String[]{"map/powerup.png"}, true);
	}
	
	public void render() {
//		if (!Main.entityCollisionChecker.isCollidingWithPacMan(this)) {
			int[] pixels = this.screen.getPixels();
			
			int w = this.images[0].getWidth();
			int h = this.images[0].getHeight();
			int[] imagePixels = new int[w * h];
			
			this.images[0].getRGB(0, 0, w, h, imagePixels, 0, w);
			
			int xOffset = this.posX - w / 2;
			int yOffset = this.posY - h / 2;
			
			if (this.anim % 4 < 2) {
				for (int y = yOffset; y < this.posY + h / 2; y++) {
					if (y >= 0 && y < this.screen.getHeight() && y < h + yOffset) {
						for (int x = xOffset; x < this.posX + w / 2; x++) {
							if (x >= 0 && x < this.screen.getWidth() && x < w + xOffset) {
								if (imagePixels[(x - xOffset) + (y - yOffset) * w] != this.screen.getAlphaColor().getRGB())
									pixels[x + y * this.screen.getWidth()] = imagePixels[(x - xOffset) + (y - yOffset) * w];
							}
						}
					}
				}
				
				this.screen.changePixels(pixels);
			}
//		}
	}
	
	public void update() {
		this.counter++;
		
		if (this.counter % 6 == 0) this.anim++;
	}

}
