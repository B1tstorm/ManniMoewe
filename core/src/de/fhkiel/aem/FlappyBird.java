package de.fhkiel.aem;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class FlappyBird extends Game {
	public SpriteBatch batch;
	public BitmapFont font;
	public Music meerMoeweMusik;
	public Music kielMusik;
	public boolean musik = true;

	@Override
	public void create () {
		meerMoeweMusik  = Gdx.audio.newMusic(Gdx.files.internal("Meer_Möwe.mp3"));
		meerMoeweMusik.setLooping(true);
		meerMoeweMusik.setVolume(0.5f);


		kielMusik = Gdx.audio.newMusic(Gdx.files.internal("Kiel_Sound.mp3"));
		kielMusik.setVolume(0.5f);
		kielMusik.setLooping(true);

		batch = new SpriteBatch();
		font = new BitmapFont();
		this.setScreen(new StartScreen(this));

	}

	@Override
	public void render () {
		super.render();
	}

	@Override
	public void dispose () {
		batch.dispose();
		meerMoeweMusik.dispose();
		kielMusik.dispose();
	}
}
