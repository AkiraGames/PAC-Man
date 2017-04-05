package de.akiragames.pacman.utils;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import de.akiragames.pacman.Main;

public class FileUtils {
	
	public static String imagePath = "/res/";
	
	public static BufferedImage[] loadImages(String[] imageFiles) {
		BufferedImage[] images = new BufferedImage[imageFiles.length];
		
		try {
			for (int i = 0; i < imageFiles.length; i++) {
				images[i] = ImageIO.read(Main.class.getResource(FileUtils.imagePath + imageFiles[i]));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return images;
	}

}
