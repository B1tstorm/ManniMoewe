package de.fhkiel.aem;

import de.fhkiel.aem.model.HighscoreEntry;
import org.json.JSONArray;
import org.json.JSONObject;
import de.fhkiel.aem.model.Highscore;
import de.fhkiel.aem.model.HighscoreEntry;
import org.json.JSONArray;
import org.json.JSONObject;

public class NetworkHandler {

	/**
	 *
	 * @param highscore
	 * @return
	 */
	private String writeJson(Highscore highscore) {
		JSONArray arrayEasy = new JSONArray();
		JSONArray arrayMedium = new JSONArray();
		JSONArray arrayHard = new JSONArray();
		JSONObject object = new JSONObject();

		for(HighscoreEntry entry : highscore.easy) {
			arrayEasy.put(new JSONObject().put("name", entry.name));
			arrayEasy.put(new JSONObject().put("highscore", entry.highscore));
		}
		object.put("easy", arrayEasy);

		for(HighscoreEntry entry : highscore.medium) {
			arrayMedium.put(new JSONObject().put("name", entry.name));
			arrayMedium.put(new JSONObject().put("highscore", entry.highscore));
		}
		object.put("medium", arrayMedium);

		for(HighscoreEntry entry : highscore.hard) {
			arrayHard.put(new JSONObject().put("name", entry.name));
			arrayHard.put(new JSONObject().put("highscore", entry.highscore));
		}
		object.put("hard", arrayHard);

		return object.toString();
	}

	private Highscore readJson(String jsonString) {
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
	}

	public void sendToServer(Highscore highscore) {
		String jsonObject = writeJson(highscore);
	}

	public Highscore getFromServer() {

		return readJson("");
	}
}
