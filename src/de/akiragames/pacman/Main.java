package de.akiragames.pacman;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.HashMap;

import javax.swing.JFrame;

import de.akiragames.pacman.game.Game;
import de.akiragames.pacman.graphics.Screen;
import de.akiragames.pacman.input.Keyboard;
import de.akiragames.pacman.utils.ProjectUtils;

public class Main extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;

	// Projekt-Parameter
	public static final String NAME = "Project PAC-Man";
	public static final String GAME_ID_REF = "PAC";
	public static final String VERSION = "inDev";

	// Fenster-Parameter
	public static final int WIDTH = 20 * 32;
	public static final int HEIGHT = 11 * 32 + 40;
	public static final int SCALE = 1;

	// Nur für Update-Check
	public static String newVersion = VERSION;

	// Threads und Fenster
	private Thread thread;
	private JFrame frame;
	private boolean running = false;
	
	private HashMap<Integer, Integer> fpsList;
	
	// Input-Manager
	private static Keyboard keyboard;
	
	private Screen screen;
	private Game game;

	public static BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) Main.image.getRaster().getDataBuffer()).getData();
	
	//////////////////////////////////////////////////////////////////////////

	public Main() {
		Dimension size = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
		setPreferredSize(size);

		this.screen = new Screen(WIDTH, HEIGHT);
		this.frame = new JFrame();
		
		this.game = new Game(this.screen);
		this.fpsList = new HashMap<Integer, Integer>();
		
		Main.keyboard = new Keyboard(this);
		
		addKeyListener(Main.keyboard);
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
				
				this.fpsList.put(this.fpsList.size(), frames);
				
				updates = 0;
				frames = 0;
			}
		}
		
		this.stop();
	}

	public void update() {		
		this.game.update();
	}

	public void render() {
		BufferStrategy bs = getBufferStrategy();

		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		this.screen.clear();
		this.game.render();
		
		for (int i = 0; i < this.pixels.length; i++) {
			this.pixels[i] = this.screen.getPixels()[i];
		}

		// BufferedImage wird auf Screen gezeichnet
		Graphics g = bs.getDrawGraphics();
		g.drawImage(Main.image, 0, 0, getWidth(), getHeight(), null);
		
		g.dispose();
		bs.show();
	}

	public static boolean isOutdated() {
		return !VERSION.equalsIgnoreCase(newVersion);
	}
	
	public static Keyboard getKeyboard() {
		return keyboard;
	}
	
	public Screen getScreen() {
		return this.screen;
	}
	
	public Game getGame() {
		return this.game;
	}
	
	public double getAverageFPS() {
		double sum = 0.0D;
		
		for (int i = 0; i < this.fpsList.size(); i++) {
			sum += this.fpsList.get(i);
		}
		
		return (double) sum / this.fpsList.size();
	}

}