package de.akiragames.pacman.entity;

import java.util.Random;

import de.akiragames.pacman.Main;
import de.akiragames.pacman.game.Direction;
import de.akiragames.pacman.game.GameColor;
import de.akiragames.pacman.graphics.Screen;

public class Ghost extends LivingEntity {
	
	private int id;
	private GameColor color;

	public Ghost(int posX, int posY, Screen screen, Direction direction, int id) {
		super(posX, posY, screen, new String[]{"res/ghost/ghost_1.png", "res/ghost/ghost_2.png", "res/ghost/ghost_3.png", "res/ghost/ghost_4.png", "res/ghost/ghost_5.png"}, true);
		
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
	
	private void updateMovement() {
		int xOffset = this.images[0].getWidth() / 2;
		int yOffset = this.images[0].getHeight() / 2;
		
		if (this.getDirection() == Direction.UP) {
			if (this.posY > yOffset && !Main.wallCollisionChecker.getDirections()[this.id][0]) {
				this.posY -= this.getSpeed();
			} else {
				this.setRandomDirection();
			}
		} else if (this.getDirection() == Direction.DOWN) {
			if (this.posY < this.screen.getHeight() - yOffset && !Main.wallCollisionChecker.getDirections()[this.id][1]) {
				this.posY += this.getSpeed();
			} else {
				this.setRandomDirection();
			}
		} else if (this.getDirection() == Direction.LEFT) {
			if (this.posX > xOffset && !Main.wallCollisionChecker.getDirections()[this.id][2]) {
				this.posX -= this.getSpeed();
				this.isMoving = true;
			} else {
				this.setRandomDirection();
			}
		} else {
			if (this.posX < this.screen.getWidth() - xOffset && !Main.wallCollisionChecker.getDirections()[this.id][3]) {
				this.posX += this.getSpeed();
				this.isMoving = true;
			} else {
				this.setRandomDirection();
			}
		}
	}
	
	private void setRandomDirection() {
		boolean rand = new Random().nextBoolean();
		int dir = 0;
		
		if (this.getDirection() == Direction.UP || this.getDirection() == Direction.DOWN) {
			if (rand) {
				this.changeDirection(Direction.LEFT);
				
				dir = 2;
			} else {
				this.changeDirection(Direction.RIGHT);
				
				dir = 3;
			}
		} else {
			if (rand) {
				this.changeDirection(Direction.UP);
				
				dir = 0;
			} else {
				this.changeDirection(Direction.DOWN);
				
				dir = 1;
			}
		}
		
		if (Main.wallCollisionChecker.getDirections()[this.id][dir]) {
			this.setRandomDirection();
		}
	}

	///////////////////////////////////////////////
	
	public GameColor getColor() {
		return this.color;
	}
	
	public int getId() {
		return this.id;
	}

}
