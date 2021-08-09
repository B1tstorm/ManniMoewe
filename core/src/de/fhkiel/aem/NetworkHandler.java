package de.fhkiel.aem;

import de.fhkiel.aem.model.HighscoreEntry;
import org.json.JSONArray;
import org.json.JSONObject;
import de.fhkiel.aem.model.Highscore;

public class NetworkHandler {

	private void writeJson() {

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

	}

	public Highscore getFromServer() {

		return readJson("");
	}
}
