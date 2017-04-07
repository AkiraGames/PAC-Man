package de.akiragames.pacman.entity;

import java.awt.image.BufferedImage;

import de.akiragames.pacman.game.Direction;
import de.akiragames.pacman.game.Game;
import de.akiragames.pacman.graphics.Screen;

public class PacMan extends LivingEntity {
	
	private int counter, anim;
	private boolean animUp;
	
	private BufferedImage[] imagesUp;
	private BufferedImage[] imagesRight;
	private BufferedImage[] imagesDown;
	private BufferedImage[] imagesLeft;

	public PacMan(int gridX, int gridY, Screen screen, Game game) {
		super(gridX, gridY, screen, game, new String[]{
				"pacman/pacman_1.png", "pacman/pacman_2.png", "pacman/pacman_3.png", 
				"pacman/pacman_4.png", "pacman/pacman_5.png", "pacman/pacman_6.png", 
				"pacman/pacman_7.png", "pacman/pacman_8.png", "pacman/pacman_9.png", 
				"pacman/pacman_10.png", "pacman/pacman_11.png", "pacman/pacman_12.png", 
				"pacman/pacman_13.png"}, true);
		
		this.counter = 0;
		this.anim = 2;
		this.animUp = false;
		
		this.imagesUp = new BufferedImage[]{this.images[0], this.images[1], this.images[2], this.images[3]};
		this.imagesRight = new BufferedImage[]{this.images[0], this.images[4], this.images[5], this.images[6]};
		this.imagesDown = new BufferedImage[]{this.images[0], this.images[7], this.images[8], this.images[9]};
		this.imagesLeft = new BufferedImage[]{this.images[0], this.images[10], this.images[11], this.images[12]};
	}

	public void update() {
		this.updateCounter(4);
		this.updateMovement();
	}

	public void renderAnimation() {
		if (!this.isMoving()) {
			this.anim = 2;
			this.counter = 0;
			this.animUp = false;
			
			switch (this.getDirection()) {
				case DOWN:
					this.render(8);
					break;
				case LEFT:
					this.render(11);
					break;
				case RIGHT:
					this.render(5);
					break;
				case UP:
					this.render(2);
					break;
				default:
					this.render(0);
					break;
			}
			
		} else {
			int[] pixels = this.screen.getPixels();
			
			int w = this.imagesUp[this.anim].getWidth();
			int h = this.imagesUp[this.anim].getHeight();
			int[] imagePixels = new int[w * h];
			
			if (this.getDirection() == Direction.UP) {
				this.imagesUp[this.anim].getRGB(0, 0, w, h, imagePixels, 0, w);
			} else if (this.getDirection() == Direction.RIGHT) {
				this.imagesRight[this.anim].getRGB(0, 0, w, h, imagePixels, 0, w);
			} else if (this.getDirection() == Direction.DOWN) {
				this.imagesDown[this.anim].getRGB(0, 0, w, h, imagePixels, 0, w);
			} else {
				this.imagesLeft[this.anim].getRGB(0, 0, w, h, imagePixels, 0, w);
			}
			
			int xOffset = this.posX - w / 2;
			int yOffset = this.posY - h / 2;
			
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
	}
	
	private void updateCounter(int interval) {
		if (this.isMoving()) {
			this.counter++;
		
			if (this.counter % interval == 0) {
				if (this.anim == this.imagesUp.length - 1) {
					this.animUp = false;
				} else if (this.anim == 0) {
					this.animUp = true;
				}
				
				if (this.animUp) {
					this.anim++;
				} else {
					this.anim--;
				}
			}
		}
	}

}
