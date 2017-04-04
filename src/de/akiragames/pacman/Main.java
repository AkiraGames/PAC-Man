package de.akiragames.pacman;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import de.akiragames.pacman.entity.Ghost;
import de.akiragames.pacman.entity.LivingEntity;
import de.akiragames.pacman.entity.PacDot;
import de.akiragames.pacman.entity.PacMan;
import de.akiragames.pacman.entity.PowerUp;
import de.akiragames.pacman.game.Direction;
import de.akiragames.pacman.game.Wall;
import de.akiragames.pacman.game.WallCollisionChecker;
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
	public static final int WIDTH = 600;
	public static final int HEIGHT = WIDTH / 4 * 3;

	// Nur für Update-Check
	public static String newVersion = VERSION;

	// Threads und Fenster
	private Thread thread;
	private JFrame frame;
	private boolean running = false;
	
	// Input-Manager
	private static Keyboard keyboard;
	
	private Screen screen;
	
	public static WallCollisionChecker wallCollisionChecker;
	
	private Wall[] testWalls;
	private PacMan testPacMan;
	private Ghost testGhost1;
	private Ghost testGhost2;
	private Ghost testGhost3;
	private Ghost testGhost4;
	private Ghost testGhost5;
	private PowerUp testPowerUp1;
	private PowerUp testPowerUp2;
	private PowerUp testPowerUp3;
	private PowerUp testPowerUp4;
	private PacDot[] testPacDots = new PacDot[5];

	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) this.image.getRaster().getDataBuffer()).getData();
	
	//////////////////////////////////////////////////////////////////////////

	public Main() {
		Dimension size = new Dimension(WIDTH, HEIGHT);
		setPreferredSize(size);

		this.screen = new Screen(WIDTH, HEIGHT);
		this.frame = new JFrame();
		keyboard = new Keyboard();
		
		this.testWalls = new Wall[]{new Wall(60, 120, 350, 152, this.screen), new Wall(60, 220, 350, 252, this.screen)};
		this.testPacMan = new PacMan(100, 100, this.screen);
		this.testGhost1 = new Ghost(200, 400, this.screen, Direction.UP, 1);
		this.testGhost2 = new Ghost(400, 50, this.screen, Direction.DOWN, 2);
		this.testGhost3 = new Ghost(500, 200, this.screen, Direction.LEFT, 3);
		this.testGhost4 = new Ghost(50, 300, this.screen, Direction.RIGHT, 4);
		this.testGhost5 = new Ghost(200, 150, this.screen, Direction.RIGHT, 5);
		this.testPowerUp1 = new PowerUp(200, 150, this.screen);
		this.testPowerUp2 = new PowerUp(400, 150, this.screen);
		this.testPowerUp3 = new PowerUp(200, 250, this.screen);
		this.testPowerUp4 = new PowerUp(400, 250, this.screen);
		
		Main.wallCollisionChecker = new WallCollisionChecker(new LivingEntity[]{this.testPacMan, this.testGhost1, this.testGhost2, this.testGhost3, this.testGhost4, this.testGhost5}, this.testWalls);
	
		for (int i = 0; i < this.testPacDots.length; i++) {
			this.testPacDots[i] = new PacDot(250 + i * 25, 200, this.screen);
		}
		
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
		Main.keyboard.update();
		Main.wallCollisionChecker.check();
		
		this.testPacMan.update();
		this.testGhost1.move();
		this.testGhost2.move();
		this.testGhost3.move();
		this.testGhost4.move();
		this.testGhost5.move();
	}

	public void render() {
		BufferStrategy bs = getBufferStrategy();

		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		this.screen.clear();
		
		for (int i = 0; i < this.testWalls.length; i++) {
			this.testWalls[i].render();
		}
		
		for (int i = 0; i < this.testPacDots.length; i++) {
			this.testPacDots[i].render();
		}
		
		this.testPowerUp1.render();
		this.testPowerUp2.render();
		this.testPowerUp3.render();
		this.testPowerUp4.render();
		this.testGhost1.render(0);
		this.testGhost2.render(1);
		this.testGhost3.render(2);
		this.testGhost4.render(3);
		this.testGhost5.render(4);
		this.testPacMan.renderAnimation();
		
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
	
	public static Keyboard getKeyboard() {
		return keyboard;
	}

}