package de.fhkiel.aem;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import de.fhkiel.aem.model.Highscore;

/**
 * The game object which handles Screen general behavior.
 */
public class FlappyBird extends Game {
	public SpriteBatch batch;
	public BitmapFont font;
	public Music oceanSeagullMusic;
	public Music kielMusic;
	private int difficulty = 2;
	public boolean musicShouldPlay = true;
	public Background background;
	private int defaultPadding = 50;

	private static float speedMultiplier = 1.0f;
	private String playerName = "Enter your Name";
	private Highscore highscore;
	private NetworkHandler networkHandler;

	@Override
	public void create () {
		oceanSeagullMusic = Gdx.audio.newMusic(Gdx.files.internal("Meer_Möwe.mp3"));
		oceanSeagullMusic.setLooping(true);
		oceanSeagullMusic.setVolume(0.5f);

		kielMusic = Gdx.audio.newMusic(Gdx.files.internal("Kiel_Sound.mp3"));
		kielMusic.setVolume(0.5f);
		kielMusic.setLooping(true);

		batch = new SpriteBatch();
		font = new BitmapFont();

		background = new Background(batch);

		networkHandler = new NetworkHandler();
		highscore = new Highscore();

		this.setScreen(new StartScreen(this));

	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose () {
		batch.dispose();
		font.dispose();
		oceanSeagullMusic.dispose();
		kielMusic.dispose();
		background.dispose();
	}

	/**
	 * Increases the Speed of the Game.
	 */
	public void increaseGameSpeed(){
		if(speedMultiplier <= 3){
			Barrier.speed = Barrier.speed / speedMultiplier * (speedMultiplier + 0.01f);
			Background.FOREGROUNDSPEED = Background.FOREGROUNDSPEED / speedMultiplier  * (speedMultiplier + 0.01f);
			Background.WATERSPEED = Background.WATERSPEED / speedMultiplier  * (speedMultiplier + 0.01f);
			Background.CITYSPEED = Background.CITYSPEED / speedMultiplier  * (speedMultiplier + 0.01f);
			Background.SKYSPEED = Background.SKYSPEED / speedMultiplier  * (speedMultiplier + 0.01f);
			Item.itemspeed = Barrier.speed;
			speedMultiplier += 0.01f;
		}
	}

	/**
	 * Decreses the Speed of the Game.
	 */
	public void decreaseGameSpeed(){
		Barrier.speed = Barrier.speed / speedMultiplier * (speedMultiplier - .15f);
		Background.FOREGROUNDSPEED = Background.FOREGROUNDSPEED / speedMultiplier  * (speedMultiplier - 0.15f);
		Background.WATERSPEED = Background.WATERSPEED / speedMultiplier  * (speedMultiplier  - 0.15f);
		Background.CITYSPEED = Background.CITYSPEED / speedMultiplier  * (speedMultiplier  - 0.15f);
		Background.SKYSPEED = Background.SKYSPEED / speedMultiplier  * (speedMultiplier  - 0.15f);
		Item.itemspeed = Item.itemspeed /speedMultiplier * (speedMultiplier  - 0.15f);
		speedMultiplier -= 0.15f;
	}

	/**
	 * Resets the Speed of the Game.
	 */
	public void resetGameSpeed(){
		Barrier.speed = 300;
		Background.FOREGROUNDSPEED = 300;
		Background.WATERSPEED = 150;
		Background.CITYSPEED = 75;
		Background.SKYSPEED = 30;
		Item.itemspeed = 300;
		speedMultiplier = 1.0f;
	}

	/**
	 * Sets the music volume.
	 * @param volume volume
	 */
	public void setMusicVolume(float volume){
		oceanSeagullMusic.setVolume(volume);
		kielMusic.setVolume(volume);
	}

	/**
	 * Gets the difficulty of the game.
	 * @return Difficulty level
	 */
	public int getDifficulty(){return difficulty;}

	/**
	 * Sets the difficulty of the game.
	 * @param difficulty Difficulty level
	 */
	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	/**
	 * Gets the SpeedMultiplier
	 * @return SpeedMultiplier
	 */
	public static float getSpeedMultiplier() {
		return speedMultiplier;
	}

	/**
	 * Sets the SpeedMultiplier.
	 * @param speedMultiplier New SpeedMultiplier
	 */
	public static void setSpeedMultiplier(float speedMultiplier) {
		FlappyBird.speedMultiplier = speedMultiplier;
	}

	/**
	 * Gets the playername.
	 * @return playername
	 */
	public String getPlayerName() {
		return playerName;
	}

	/**
	 * Sets the playername.
	 * @param playerName New playername
	 */
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	/**
	 * Gets the NetworkHandler.
	 * @return The NetworkHandler
	 */
	public NetworkHandler getNetworkHandler() {
		return networkHandler;
	}

	/**
	 * Gets the Highscore.
	 * @return The Highscore
	 */
	public Highscore getHighscore() {
		return highscore;
	}

	/**
	 * Sets the Highscore.
	 * @param highscore New Highscore
	 */
	public void setHighscore(Highscore highscore) {
		this.highscore = highscore;
	}

	public int getDefaultPadding() {
		return defaultPadding;
	}
}
