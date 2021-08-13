package de.fhkiel.aem;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
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

//	private final Json json;
	private final String serverUrl = "http://62.75.175.207:3000/api/highscore";

	public NetworkHandler() {
//		json = new Json();
//		json.setOutputType(JsonWriter.OutputType.json);
	}

	/**
	 * Sends a Highscore to the Server API.
	 * @param highscore Highscore to send
	 */
	public void sendToServer(Highscore highscore) {
//		try {
//			try(FileWriter writer = new FileWriter("highscore.json")){
//				writer.write(json.toJson(highscore));
//			}
//			System.out.println(json.toJson(highscore));
//
//		} catch (Exception e) {
//			System.out.println("Outer Catch!");
//			System.out.println(e.getMessage());
//		}
	}

	/**
	 * Gets the Highscore from the Server.
	 * @return Received Highscore
	 */
	public Highscore getFromServer() {
//		String jsonText = "";
//		Highscore highscore;
//		try {
//			FileReader fReader = new FileReader("highscore.json");
//			try(BufferedReader reader = new BufferedReader(fReader)) {
//				System.out.println(jsonText);
//				highscore = json.fromJson(Highscore.class, reader);
//			}
//		} catch (Exception e) {
//			System.out.println("Outer Catch!");
//			System.out.println(e.getMessage());
//			highscore = new Highscore();
//		}
		return new Highscore();
	}
}
