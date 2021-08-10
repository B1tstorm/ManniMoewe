package de.fhkiel.aem.model;

/**
 * HighscoreEntry with name and highscore.
 */
public class HighscoreEntry {
	public final String name;
	public final int highscore;

	public HighscoreEntry(final String name, final int highscore) {
		this.highscore = highscore;
		this.name = name;
	}
}
