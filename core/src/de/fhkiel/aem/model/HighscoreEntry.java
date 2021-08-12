package de.fhkiel.aem.model;

/**
 * HighscoreEntry with name and highscore.
 */
public class HighscoreEntry {
	public String name;
	public int highscore;

	// Empty Constructor for JSON serialization
	public HighscoreEntry() { }

	public HighscoreEntry(final String name, final int highscore) {
		this.highscore = highscore;
		this.name = name;
	}
}
