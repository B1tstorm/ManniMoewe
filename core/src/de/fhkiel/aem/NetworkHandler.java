package de.fhkiel.aem;

import com.badlogic.gdx.utils.Array;
import de.fhkiel.aem.model.HighscoreEntry;
import org.json.JSONArray;
import org.json.JSONObject;
import de.fhkiel.aem.model.Highscore;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

/**
 * Handles the Network Calls to send a JSON to the server and get one form the Server.
 */
public class NetworkHandler {

	private final String serverUrl = "http://62.75.175.207:3000/api/highscore";

	/**
	 * Writes an Highscore object to a JSON String.
	 * @param highscore Highscore
	 * @return JSON String
	 */
	private String writeJson(Highscore highscore) {
		JSONArray arrayEasy = new JSONArray();
		JSONArray arrayMedium = new JSONArray();
		JSONArray arrayHard = new JSONArray();
		JSONObject object = new JSONObject();

		for(HighscoreEntry entry : new Array.ArrayIterator<>(highscore.easy)) {
			JSONObject obj = new JSONObject();
			obj.put("name", entry.name);
			obj.put("highscore", entry.highscore);
			arrayEasy.put(obj);
		}
		object.put("easy", arrayEasy);

		for(HighscoreEntry entry : new Array.ArrayIterator<>(highscore.medium)) {
			JSONObject obj = new JSONObject();
			obj.put("name", entry.name);
			obj.put("highscore", entry.highscore);
			arrayMedium.put(obj);
		}
		object.put("medium", arrayMedium);

		for(HighscoreEntry entry : new Array.ArrayIterator<>(highscore.hard)) {
			JSONObject obj = new JSONObject();
			obj.put("name", entry.name);
			obj.put("highscore", entry.highscore);
			arrayHard.put(obj);
		}
		object.put("hard", arrayHard);
		return object.toString();
	}

	/**
	 * Reads the JSON String and creates a Highscore object.
	 * @param jsonString JSON String
	 * @return The parsed Highscore
	 */
	private Highscore readJson(String jsonString) {
		try {
			Highscore highscore = new Highscore();
			JSONObject jsonObject = new JSONObject(jsonString);
			JSONArray easyArray = jsonObject.getJSONArray("easy");
			JSONArray mediumArray = jsonObject.getJSONArray("medium");
			JSONArray hardArray = jsonObject.getJSONArray("hard");

			for(Object obj: easyArray) {
				JSONObject jObj = (JSONObject) obj;
				highscore.addHighscore(1, new HighscoreEntry(jObj.getString("name"), jObj.getInt("highscore")));
			}

			for(Object obj: mediumArray) {
				JSONObject jObj = (JSONObject) obj;
				highscore.addHighscore(2, new HighscoreEntry(jObj.getString("name"), jObj.getInt("highscore")));
			}

			for(Object obj: hardArray) {
				JSONObject jObj = (JSONObject) obj;
				highscore.addHighscore(3, new HighscoreEntry(jObj.getString("name"), jObj.getInt("highscore")));
			}
			return highscore;
		} catch(Exception e) {
			System.err.println("JSON could not be read!");
			System.err.println(e.getMessage());
			return new Highscore();
		}
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

			byte[] out = writeJson(highscore).getBytes(StandardCharsets.UTF_8);
			int length = out.length;

			http.setFixedLengthStreamingMode(length);
			http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			http.connect();
			try(OutputStream os = http.getOutputStream()) {
				os.write(out);
			}
			System.out.println("Send JSON to Server!");
		} catch (Exception e) {
			System.out.println("Outer Catch!");
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Reads from a reader to create a full String.
	 * @param rd reader
	 * @return Full String
	 * @throws IOException IOException
	 */
	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

	/**
	 * Gets the Highscore from the Server.
	 * @return Received Highscore
	 */
	public Highscore getFromServer() {
		String jsonText = "";
		try {
			InputStream stream = new URL(serverUrl).openStream();

			try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
				jsonText= readAll(reader);
				System.out.println("Got JSON from Server!");
				System.out.println(jsonText);
			} catch (Exception e) {
				System.out.println("Inner Catch!");
				System.out.println(e.getMessage());
			}
		} catch (Exception e) {
			System.out.println("Outer Catch!");
			System.out.println(e.getMessage());
		}
		return readJson(jsonText);
	}
}
