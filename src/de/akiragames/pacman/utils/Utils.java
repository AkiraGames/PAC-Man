package de.akiragames.pacman.utils;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Random;

import de.akiragames.pacman.Project;

public class Utils {
	
	public static final String websiteDomain = "http://akiragames.cynfos.de";
	
	// Methode zum Erstellen der UNIX-Timestamp
	public static int unixTime() {
		return (int) (System.currentTimeMillis() / 1000L);
	}
	
	public static String formatTime(int unixTime) {
		Timestamp stamp = new Timestamp((long) (unixTime * 1000L));
		Date date = new Date(stamp.getTime());

		return date.toString();
	}
	
	// Methode zum Generieren einer zufälligen Game-ID
	public static String generateGameId() {
		String result = ProjectUtils.getExistingGameIds();
		String[] gameIds = result.split(",");
		
		int length = 5;

		String randomId = Project.GAME_ID_REF + Utils.randomKey(length);
		
		while (Arrays.asList(gameIds).contains(randomId)) {
			randomId = Project.GAME_ID_REF + Utils.randomKey(length);
		}
		
		return randomId;
	}
	
	// Methode, um zufälligen Schlüssel zu generieren
	public static String randomKey(int length) {
		String valid = "ABCDEFGHIJKLMNOPQRSTUVWXYZ12345678901234567890";
		
		StringBuilder res = new StringBuilder();
		Random random = new Random();
		
		while (0 < length--) {
			res.append(valid.charAt(random.nextInt(valid.length())));
		}
		
		return res.toString();
	}

}
