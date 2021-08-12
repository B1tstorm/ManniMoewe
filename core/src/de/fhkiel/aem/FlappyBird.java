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
	public Music spaceMusic;
	private int difficulty = 2;
	public boolean musicShouldPlay = true;
	private int defaultPadding = 50;
	public NormalBackground background;
	private boolean charManniActive = true;
	private boolean charSpaceManniActive = false;
	private boolean foundEasteregg = false;

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

		spaceMusic = Gdx.audio.newMusic(Gdx.files.internal("Space_Sound.mp3"));
		spaceMusic.setVolume(0.5f);
		spaceMusic.setLooping(true);

		batch = new SpriteBatch();
		font = new BitmapFont();

		background = new NormalBackground(batch);

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
		spaceMusic.dispose();
	}

	/**
	 * Increases the Speed of the Game.
	 */
	public void increaseGameSpeed(){
		if(speedMultiplier <= 3){
			Barrier.speed = Barrier.speed / speedMultiplier * (speedMultiplier + 0.01f);
			Background.layerOneSpeed = Background.layerOneSpeed / speedMultiplier  * (speedMultiplier + 0.01f);
			Background.layerTwoSpeed = Background.layerTwoSpeed / speedMultiplier  * (speedMultiplier + 0.01f);
			Background.layerThreeSpeed = Background.layerThreeSpeed / speedMultiplier  * (speedMultiplier + 0.01f);
			Background.layerFourSpeed = Background.layerFourSpeed / speedMultiplier  * (speedMultiplier + 0.01f);
			Background.layerFiveSpeed = Background.layerFiveSpeed / speedMultiplier  * (speedMultiplier + 0.01f);
			Item.itemspeed = Barrier.speed;
			speedMultiplier += 0.01f;
		}
	}

	/**
	 * Decreses the Speed of the Game.
	 */
	public void decreaseGameSpeed(){
		Barrier.speed = Barrier.speed / speedMultiplier * (speedMultiplier - .15f);
		Background.layerOneSpeed = Background.layerOneSpeed / speedMultiplier  * (speedMultiplier - 0.15f);
		Background.layerTwoSpeed = Background.layerTwoSpeed / speedMultiplier  * (speedMultiplier  - 0.15f);
		Background.layerThreeSpeed = Background.layerThreeSpeed / speedMultiplier  * (speedMultiplier  - 0.15f);
		Background.layerFourSpeed = Background.layerFourSpeed / speedMultiplier  * (speedMultiplier  - 0.15f);
		Background.layerFiveSpeed = Background.layerFiveSpeed / speedMultiplier  * (speedMultiplier  - 0.15f);
		Item.itemspeed = Item.itemspeed /speedMultiplier * (speedMultiplier  - 0.15f);
		speedMultiplier -= 0.15f;
	}

	/**
	 * Resets the Speed of the Game.
	 */
	public void resetGameSpeed(){
		Barrier.speed = 300;
		Background.layerFiveSpeed = 30;
		Background.layerFourSpeed = 60;
		Background.layerThreeSpeed = 110;
		Background.layerTwoSpeed = 180;
		Background.layerOneSpeed = 300;
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
		spaceMusic.setVolume(volume);
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

	public boolean isCharManniActive() {
		return charManniActive;
	}

	public void setCharManniActive(boolean charManniActive) {
		this.charManniActive = charManniActive;
	}

	public boolean isCharSpaceManniActive() {
		return charSpaceManniActive;
	}

	public void setCharSpaceManniActive(boolean charSpaceManniActive) {
		this.charSpaceManniActive = charSpaceManniActive;
	}

	public boolean isFoundEasteregg() {
		return foundEasteregg;
	}

	public void setFoundEasteregg(boolean foundEasteregg) {
		this.foundEasteregg = foundEasteregg;
	}
}
