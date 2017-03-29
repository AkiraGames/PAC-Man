package de.akiragames.pacman.entity;

import java.awt.image.BufferedImage;
import java.io.File;

import de.akiragames.pacman.Main;
import de.akiragames.pacman.game.Direction;
import de.akiragames.pacman.graphics.Screen;

public class PacMan extends LivingEntity {
	
	private int counter, anim;
	private boolean animUp;
	
	private BufferedImage[] imagesUp;
	private BufferedImage[] imagesRight;
	private BufferedImage[] imagesDown;
	private BufferedImage[] imagesLeft;

	public PacMan(int posX, int posY) {
		super(posX, posY, new File[]{
				new File("res/pacman/pacman_1.png"), new File("res/pacman/pacman_2.png"), new File("res/pacman/pacman_3.png"), 
				new File("res/pacman/pacman_4.png"), new File("res/pacman/pacman_5.png"), new File("res/pacman/pacman_6.png"), 
				new File("res/pacman/pacman_7.png"), new File("res/pacman/pacman_8.png"), new File("res/pacman/pacman_9.png"), 
				new File("res/pacman/pacman_10.png"), new File("res/pacman/pacman_11.png"), new File("res/pacman/pacman_12.png"), 
				new File("res/pacman/pacman_13.png")}, true);
		
		this.counter = 0;
		this.anim = 0;
		this.animUp = true;
		
		this.imagesUp = new BufferedImage[]{this.images[0], this.images[1], this.images[2], this.images[3]};
		this.imagesRight = new BufferedImage[]{this.images[0], this.images[4], this.images[5], this.images[6]};
		this.imagesDown = new BufferedImage[]{this.images[0], this.images[7], this.images[8], this.images[9]};
		this.imagesLeft = new BufferedImage[]{this.images[0], this.images[10], this.images[11], this.images[12]};
		
		this.setSpeed(2);
		this.changeDirection(Direction.RIGHT);
	}

	public void update() {
		if (Main.getKeyboard().up) {
			this.changeDirection(Direction.UP);
			
			this.posY -= this.getSpeed();
		} else if (Main.getKeyboard().down) {
			this.changeDirection(Direction.DOWN);
			
			this.posY += this.getSpeed();
		} else if (Main.getKeyboard().left) {
			this.changeDirection(Direction.LEFT);
			
			this.posX -= this.getSpeed();
		} else if (Main.getKeyboard().right) {
			this.changeDirection(Direction.RIGHT);
			
			this.posX += this.getSpeed();
		}
	}

	public void renderAnimation(Screen screen) {
		int[] pixels = screen.getPixels();
		
		this.counter++;
		
		if (this.counter % 100 == 0) {
			if (this.anim == this.imagesUp.length - 1) {
				this.animUp = false;
			} else if (this.anim == 0) {
				this.animUp = true;
			}
			
			if (this.animUp) {
				anim++;
			} else {
				anim--;
			}
		}
		
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
			if (y >= 0 && y < screen.getHeight() && y < h + yOffset) {
				for (int x = xOffset; x < this.posX + w / 2; x++) {
					if (x >= 0 && x < screen.getWidth() && x < w + xOffset) {
						if (imagePixels[(x - xOffset) + (y - yOffset) * w] != screen.getAlphaColor().getRGB())
							pixels[x + y * screen.getWidth()] = imagePixels[(x - xOffset) + (y - yOffset) * w];
					}
				}
			}
		}
		
		screen.changePixels(pixels);
	}

}
