package de.fhkiel.aem.model;

import com.badlogic.gdx.utils.Array;

import java.util.Comparator;

/**
 * Highscore object.
 */
public class Highscore {
	public Array<HighscoreEntry> easy;
	public Array<HighscoreEntry> medium;
	public Array<HighscoreEntry> hard;

	public void setHighscore(final int difficulty, final HighscoreEntry highscore) {
		switch(difficulty) {
			case 1:
				addHighscore(easy, highscore);
				break;
			case 2:
				addHighscore(medium, highscore);
				break;
			case 3:
				addHighscore(hard, highscore);
				break;
		}
	}

	private void addHighscore(Array<HighscoreEntry> array, HighscoreEntry highscore) {
		array.add(highscore);
		array.sort(Comparator.comparingInt(highscoreEntry -> highscoreEntry.highscore));
		if(array.size > 10) {
			array.removeIndex(10);
		}
	}
}
