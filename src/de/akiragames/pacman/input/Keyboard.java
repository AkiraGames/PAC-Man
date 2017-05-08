package de.akiragames.pacman.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import de.akiragames.pacman.Main;
import de.akiragames.pacman.game.Direction;
import de.akiragames.pacman.game.GameState;

public class Keyboard implements KeyListener {
	
	private boolean[] keys = new boolean[65536];
	
	private Main main;
	
	public Keyboard(Main main) {
		this.main = main;
	}
	
	public void update(Direction direction) {		
		if (this.main.getGame().getGameState() == GameState.IN_GAME || this.main.getGame().getGameState() == GameState.POWERUP_ACTIVE)
			this.main.getGame().getMap().getPacMan().changeDirection(this.main.getGame().getMap(), direction);
	}

	public void keyPressed(KeyEvent e) {
		int keyCode = e.getExtendedKeyCode();
		
		if (!keys[keyCode]) {
			if (keyCode == KeyEvent.VK_F1) {
				this.main.getGame().getScreen().saveScreenshot();
			}
		}
		
		if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_W) {
			this.update(Direction.UP);
		} else if (keyCode == KeyEvent.VK_DOWN || keyCode == KeyEvent.VK_S){
			this.update(Direction.DOWN);
		} else if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_A){
			this.update(Direction.LEFT);
		} else if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_D){
			this.update(Direction.RIGHT);
		}
		
		keys[e.getExtendedKeyCode()] = true;
	}

	public void keyReleased(KeyEvent e) {
		if (keys[e.getExtendedKeyCode()]) keys[e.getExtendedKeyCode()] = false;
	}

	public void keyTyped(KeyEvent e) {
	}

}
