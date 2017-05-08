package de.akiragames.pacman.entity;

import de.akiragames.pacman.game.Game;

public class PowerUp extends Entity {
	
	private int counter, anim;
	private boolean pacManCollision;

	public PowerUp(int gridX, int gridY, Game game) {
		super(gridX, gridY, game, new String[]{"map/powerup.png"}, true);
		
		this.pacManCollision = false;
	}
	
	public void render() {
		if (!this.pacManCollision) {
			int[] pixels = this.game.getScreen().getPixels();
			
			int w = this.images[0].getWidth();
			int h = this.images[0].getHeight();
			int[] imagePixels = new int[w * h];
			
			this.images[0].getRGB(0, 0, w, h, imagePixels, 0, w);
			
			int xOffset = this.posX - w / 2;
			int yOffset = this.posY - h / 2;
			
			if (this.anim % 4 < 2) {
				for (int y = yOffset; y < this.posY + h / 2; y++) {
					if (y >= 0 && y < this.game.getScreen().getHeight() && y < h + yOffset) {
						for (int x = xOffset; x < this.posX + w / 2; x++) {
							if (x >= 0 && x < this.game.getScreen().getWidth() && x < w + xOffset) {
								if (imagePixels[(x - xOffset) + (y - yOffset) * w] != this.game.getScreen().getAlphaColor().getRGB())
									pixels[x + y * this.game.getScreen().getWidth()] = imagePixels[(x - xOffset) + (y - yOffset) * w];
							}
						}
					}
				}
				
				this.game.getScreen().changePixels(pixels);
			}
		}
	}
	
	public void update() {
		if (this.game.getMap().getCollisionCheckers()[0].isCollidingWithPacMan(this) && !this.pacManCollision) {
			this.pacManCollision = true;
			
			this.game.activatePowerUp();
			this.game.scoreUp(20);
		} else {
			this.counter++;
			
			if (this.counter % 6 == 0) this.anim++;
		}
	}
	
	/////////////////////////////////////////////////
		
	public boolean isCollidingPacMan() {
		return this.pacManCollision;
	}

}
