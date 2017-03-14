package de.akiragames.pacman.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import de.akiragames.pacman.Project;

public class ProjectUtils {
	
	// Methode zum Überprüfen, ob eine neue Version vom Projekt vorhanden ist
	public static void checkUpdates() {
		String result = "UNKNOWN";
		
		try {
			System.out.println("Requesting project data from database...");
			result = ProjectUtils.sendGetRequest(Utils.websiteDomain + "/project_updates.php?gameRef=" + Project.GAME_ID_REF);
			System.out.println("Successfully requested project data from database.");
		} catch (Exception e) {
			System.err.println("Failed to request project data from database (" + e.getMessage() + ")!");
		}
		
		if (!result.equalsIgnoreCase("UNKNOWN")) {
			String[] projectUpdates = result.split(";");
			String[] lastVersion = projectUpdates[0].split(",");
			
			String newVersion = lastVersion[0];
			int timestamp = Integer.valueOf(lastVersion[1]);
			
			System.out.println("Checking if application is up to date...");
			
			if (!newVersion.equalsIgnoreCase(Project.VERSION)) {
				Project.newVersion = newVersion;
				
				System.err.println("A new version of this application has been released on " + Utils.formatTime(timestamp) + ": " + Project.NAME + " " + newVersion);
				System.err.println("Download latest version on " + Utils.websiteDomain + "/" + Project.GAME_ID_REF + "/");
			} else {
				System.out.println("Application is up to date.");
			}
		}
	}
	
	// Methode, um alle bereits existierenden Game-IDs zu erhalten
	public static String getExistingGameIds() {
		String result = "";
		
		try {
			System.out.println("Requesting game data from database...");
			result = ProjectUtils.sendGetRequest(Utils.websiteDomain + "/" + Project.GAME_ID_REF + "/game_ids.php");
			System.out.println("Successfully requested game data from database.");
		} catch (Exception e) {
			System.err.println("Failed to request game data from database (" + e.getMessage() + ")!");
		}
		
		return result;
	}

	// Methode, um eine GET-Anfrage an einen Webserver zu senden
	public static String sendGetRequest(String url) throws Exception {
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		
		con.setRequestMethod("GET");

		System.out.println("Sending 'GET' request to URL: " + url);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		
		in.close();
		con.disconnect();

		return response.toString();
	}

}
