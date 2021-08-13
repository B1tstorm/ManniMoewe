package de.fhkiel.aem;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import de.fhkiel.aem.model.Highscore;
import de.fhkiel.aem.model.HighscoreEntry;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

/**
 * Handles the Network Calls to send a JSON to the server and get one form the Server.
 */
public class NetworkHandler {

	private final Json json;
	private final String serverUrl = "http://62.75.175.207:3000/api/highscore";

	public NetworkHandler() {
		json = new Json();
		json.setOutputType(JsonWriter.OutputType.json);
	}

	/**
	 * Sends a Highscore to the Server API.
	 * @param highscore Highscore to send
	 */
	public void sendToServer(Highscore highscore) {
		try {
			URL url = new URL(serverUrl);
			URLConnection con = url.openConnection();
			HttpURLConnection http = (HttpURLConnection)con;
			http.setRequestMethod("POST");
			http.setDoOutput(true);

			byte[] out = json.toJson(highscore).getBytes(StandardCharsets.UTF_8);
			int length = out.length;

			http.setFixedLengthStreamingMode(length);
			http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			http.connect();
			try(OutputStream os = http.getOutputStream()) {
				os.write(out);
			}
			System.out.println(json.toJson(highscore));
			System.out.println("Send JSON to Server!");
		} catch (Exception e) {
			System.out.println("Outer Catch!");
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Gets the Highscore from the Server.
	 * @return Received Highscore
	 */
	public Highscore getFromServer() {
		String jsonText = "";
		Highscore highscore;
		try {
			InputStream stream = new URL(serverUrl).openStream();
			try(BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))) {
				System.out.println("Got JSON from Server!");
				System.out.println(jsonText);
				highscore = json.fromJson(Highscore.class, reader);
			}
		} catch (Exception e) {
			System.out.println("Outer Catch!");
			System.out.println(e.getMessage());
			highscore = new Highscore();
		}
		return highscore;
	}
}
