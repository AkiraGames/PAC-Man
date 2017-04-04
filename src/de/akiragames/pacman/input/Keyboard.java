package de.akiragames.pacman.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import de.akiragames.pacman.Main;

public class Keyboard implements KeyListener {
	
	private boolean[] keys = new boolean[65536];
	public boolean up, down, left, right;
	
	private Main main;
	
	public Keyboard(Main main) {
		this.main = main;
	}
	
	public void update() {
		up = keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_W];
		down = keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S];
		left = keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_A];
		right = keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D];
	}

	public void keyPressed(KeyEvent e) {
		keys[e.getExtendedKeyCode()] = true;
	}

	public void keyReleased(KeyEvent e) {
		keys[e.getExtendedKeyCode()] = false;
		
		if (e.getExtendedKeyCode() == KeyEvent.VK_F1) {
			this.main.getScreen().saveScreenshot();
		}
	}

	public void keyTyped(KeyEvent e) {
	}

}
