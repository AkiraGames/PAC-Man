package de.akiragames.pacman;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import de.akiragames.pacman.graphics.Screen;
import de.akiragames.pacman.utils.ProjectUtils;

public class Main extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;

	// Projekt-Parameter
	public static final String NAME = "Project PAC-Man";
	public static final String GAME_ID_REF = "PAC";
	public static final String VERSION = "inDev";

	// Fenster-Parameter
	public static final int WIDTH = 750;
	public static final int HEIGHT = WIDTH / 4 * 3;

	// Nur für Update-Check
	public static String newVersion = VERSION;

	// Threads und Fenster
	private Thread thread;
	private JFrame frame;
	private boolean running = false;
	
	private Screen screen;

	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) this.image.getRaster().getDataBuffer()).getData();
	
	//////////////////////////////////////////////////////////////////////////

	public Main() {
		Dimension size = new Dimension(WIDTH, HEIGHT);
		setPreferredSize(size);

		this.screen = new Screen(WIDTH, HEIGHT);
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

		this.thread = new Thread(this, NAME);
		this.thread.start();
	}

	// Thread stoppen
	public synchronized void stop() {
		this.running = false;

		try {
			this.thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("Thread has been terminated.");
	}

	// Programm wird ausgeführt
	// Maximal 60 Updates pro Sekunde, unbegrenzte FPS
	public void run() {
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double desiredUPS = 60.0;
		final double nano = 1000000000.0 / desiredUPS;	
		double delta = 0;
		int frames = 0;
		int updates = 0;
		
		while (this.running) {
			long now = System.nanoTime();
			
			delta += (now - lastTime) / nano;
			lastTime = now;
			
			while (delta >= 1) {
				update();
				updates++;
				delta--;
			}
			
			this.render();
			frames++;
			
			// UPS-/FPS-Counter
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				
				this.frame.setTitle(NAME + " " + VERSION + " [ " + updates + " ups | " + frames + " fps ]");
				
				updates = 0;
				frames = 0;
			}
		}
		
		this.stop();
	}

	public void update() {
	}

	public void render() {
		BufferStrategy bs = getBufferStrategy();

		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		this.screen.clear();
		this.screen.render();
		this.screen.drawRect(10, 10, 300, 200, Color.RED); // Test
		this.screen.drawCircle(400, 300, 200, Color.YELLOW, true, 10, Color.BLACK); // Test
		
		for (int i = 0; i < this.pixels.length; i++) {
			this.pixels[i] = screen.getPixels()[i];
		}

		// BufferedImage wird auf Screen gezeichnet
		Graphics g = bs.getDrawGraphics();
		g.drawImage(this.image, 0, 0, getWidth(), getHeight(), null);
		
		g.dispose();
		bs.show();
	}

	public static boolean isOutdated() {
		return !VERSION.equalsIgnoreCase(newVersion);
	}

}