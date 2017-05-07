package de.akiragames.pacman.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import de.akiragames.pacman.Main;
import de.akiragames.pacman.utils.Utils;

public class Screen {

	private Main main;
	
	private int width, height;
	private int[] pixels;

	public Screen(Main main, int width, int height) {
		this.main = main;
		
		this.width = width;
		this.height = height;

		this.pixels = new int[width * height];
	}
	
	public void clearFull() {
		for (int i = 0; i < this.pixels.length; i++) {
			pixels[i] = 0;
		}
	}
	
	private void clearArea(int startX, int startY, int width, int height) {
		for (int y = startY; y < startY + height; y++) {
			if (y >= 0 && y < this.height && y < startY + height) {
				for (int x = startX; x < startX + width; x++) {
					if (x >= 0 && x < this.width && x < startX + width) {
						this.pixels[x + y * this.width] = 0;
					}
				}
			}
		}
	}
	
	public void clearKeepWalls() {
		for (int y = 1; y < this.main.getGame().getMap().getHeight() - 1; y++) {
			if (y >= 0 && y < this.height) {
				for (int x = 1; x < this.main.getGame().getMap().getWidth() - 1; x++) {
					if (x >= 0 && x < this.width) {
						if (!this.main.getGame().getMap().isWallBlock(x, y)) {
							this.clearArea((x + 0) * 32, (y + 0) * 32, 32, 32);
						}
					}
				}
			}
		}
		
		// Score, Zeit und Leben werden "gecleart"
		this.clearArea(0, this.main.getGame().getMap().getHeight() * 32, this.width, 40);
	}
	
	public void changePixels(int[] pixels) {
		if (this.pixels.length == pixels.length) {
			this.pixels = pixels;
		} else {
			throw new ArrayIndexOutOfBoundsException();
		}
	}
	
	/**
	 * Rendert einen Text.
	 */
	public void renderText(String text, int posX, int posY, int fontSize, Color fontColor) {
		BufferedImage image = new BufferedImage(Main.WIDTH, fontSize - fontSize / 5, BufferedImage.TYPE_INT_RGB);
		Graphics graphics = image.getGraphics();
		
		int w = image.getWidth();
		int h = image.getHeight();
		int[] imagePixels = new int[w * h];
		
		graphics.setColor(this.getAlphaColor());
		graphics.fillRect(0, 0, w, h);
		
		graphics.setColor(fontColor);
		graphics.setFont(new Font("Arial", Font.BOLD, fontSize));
		
		graphics.drawString(text.toUpperCase(), 0, fontSize - fontSize / 4);
		graphics.dispose();
			
		image.getRGB(0, 0, w, h, imagePixels, 0, w);
		
		for (int y = posY; y < h + posY; y++) {
			if (y >= 0 && y < this.height && y < h + posY) {
				for (int x = posX; x < w; x++) {
					if (x >= 0 && x < this.width && x < w + posX) {
						if (imagePixels[(x - posX) + (y - posY) * w] != this.getAlphaColor().getRGB())
							this.pixels[x + y * this.width] = imagePixels[(x - posX) + (y - posY) * w];
					}
				}
			}
		}
	}
	
	/**
	 * Speichert einen Screenshot.
	 */
	public void saveScreenshot() {
		int unixTime = Utils.unixTime();
		
		File outputFile = new File("screenshots/" + unixTime + ".png");
		outputFile.getParentFile().mkdirs();
		
		try {
			ImageIO.write(Main.image, "png", outputFile);
			
			System.out.println("Screenshot was saved to 'screenshots/" + unixTime + ".png'.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/////////////////////////////////////////
	
	public Main getMain() {
		return this.main;
	}

	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public int[] getPixels() {
		return this.pixels;
	}
	
	public Color getPixelColor(int x, int y) {
		return new Color(this.pixels[x + y * this.width]);
	}
	
	/**
	 * Gibt den Farbwert zurück, der ausgeschnitten werden soll (wie .png).
	 */
	public Color getAlphaColor() {
		return new Color(0xff00ff);
	}
	
	/**
	 * Gibt die Entfernung zwischen zwei Punkten zurück.
	 */
	public int getDistance(int x1, int y1, int x2, int y2) {
		return (int) Math.round(Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2)));
	}

}
