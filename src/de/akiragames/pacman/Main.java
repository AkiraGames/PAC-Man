package de.akiragames.pacman;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import de.akiragames.pacman.utils.ProjectUtils;
import de.akiragames.pacman.utils.Utils;

public class Main extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;
	
	// Projekt-Parameter
	public static final String NAME = "Project PAC-Man";
	public static final String GAME_ID_REF = "PAC";
	public static final String VERSION = "inDev";
	
	// Fenster-Parameter
	public static final int WIDTH = 250;
	public static final int HEIGHT = WIDTH / 4 * 3;
	public static final int SCALE = 3;
	
	// Nur für Update-Check
	public static String newVersion = VERSION;
	
	private Thread thread;
	private JFrame frame;
	private Graphics graphics;
	private boolean running = false;
	
	//////////////////////////////////////////////////////////////////////////
	
	public Main() {
		Dimension size = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
		setPreferredSize(size);
		
		this.frame = new JFrame();
	}
	
	public static void main(String[] args) {
		ProjectUtils.checkUpdates();
		
		Main project = new Main();
		
		project.frame.setResizable(false);
		project.frame.setTitle(NAME + " " + VERSION);
		project.frame.add(project);
		project.frame.pack();
		project.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		project.frame.setLocationRelativeTo(null);
		project.frame.setVisible(true);
		project.frame.requestFocusInWindow();
		
		project.start();
	}
	
	// Thread starten
	public synchronized void start() {
		this.running = true;
		
		thread = new Thread(this, NAME);
		thread.start();
	}
	
	// Thread stoppen
	public synchronized void stop() {
		this.running = false;
		
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		while (this.running) {
			this.update();
			this.render();
		}
	}
	
	public void update() {
	}
	
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		this.graphics = bs.getDrawGraphics();
		
		// Hintergrund wird schwarz gezeichnet
		this.graphics.setColor(Color.BLACK);
		this.graphics.fillRect(0, 0, getWidth(), getHeight());
		
		// Nur zu Testzwecken
		if (isOutdated()) {
			this.graphics.setColor(Color.RED);
			this.graphics.drawString("PAC-Man ist nicht mehr auf dem neusten Stand!", 100, 100);
			this.graphics.drawString("Aktuelle Version (" + newVersion + ") herunterladen: " + Utils.websiteDomain + "/" + Main.GAME_ID_REF + "/", 100, 120);
		} else {
			this.graphics.setColor(Color.GREEN);
			this.graphics.drawString("PAC-Man ist auf dem neusten Stand.", 100, 100);
		}
		
		this.graphics.dispose();
		bs.show();
	}
	
	public static boolean isOutdated() {
		return !VERSION.equalsIgnoreCase(newVersion);
	}

}