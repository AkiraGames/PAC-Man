package de.akiragames.pacman;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import de.akiragames.pacman.utils.ProjectUtils;

public class Project extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;
	
	// Projekt-Parameter
	public static final String NAME = "Project PAC-Man";
	public static final String GAME_ID_REF = "PAC";
	public static final String VERSION = "inDev";
	
	// Fenster-Parameter
	public static final int WIDTH = 250;
	public static final int HEIGHT = WIDTH / 4 * 3;
	public static final int SCALE = 3;
	
	public static boolean isOutdated = false;
	
	private Thread thread;
	private JFrame frame;
	private boolean running = false;
	
	//////////////////////////////////////////////////////////////////////////
	
	public Project() {
		Dimension size = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
		setPreferredSize(size);
		
		this.frame = new JFrame();
	}
	
	public static void main(String[] args) {
		ProjectUtils.checkUpdates();
		
		Project project = new Project();
		
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
	}

}