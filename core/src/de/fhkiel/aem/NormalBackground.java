package de.fhkiel.aem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

/**
 * The Background of the game.
 */
public class NormalBackground extends Background {
	private final Array<Texture> skyLayerTextures;
	private final Array<Texture> cityLayerTextures;
	private final Array<Texture> waterLayerTextures;
	private final Array<Texture> foregroundLayerTextures;

	private final Array<Sprite> skyLayerLoop;
	private final Array<Sprite> cityLayerLoop;
	private final Array<Sprite> waterLayerLoop;
	private final Array<Sprite> foregroundLayerLoop;

	private int skyCounter = 0;
	private int cityCounter = 0;
	private int waterCounter = 0;
	private int foregroundCounter = 0;

	/**
	 * Creates a new Background.
	 * @param batch SpriteBatch the background is rendered on
	 */
	public NormalBackground(SpriteBatch batch) {
		super(batch);

		skyLayerTextures = new Array<>();
		skyLayerTextures.add(new Texture(Gdx.files.internal(Configuration.sky1Img)));
		skyLayerTextures.add(new Texture(Gdx.files.internal(Configuration.sky2Img)));
		skyLayerTextures.add(new Texture(Gdx.files.internal(Configuration.sky3Img)));

		cityLayerTextures = new Array<>();
		cityLayerTextures.add(new Texture(Gdx.files.internal(Configuration.city1Img)));
		cityLayerTextures.add(new Texture(Gdx.files.internal(Configuration.city2Img)));
		cityLayerTextures.add(new Texture(Gdx.files.internal(Configuration.city3Img)));

		waterLayerTextures = new Array<>();
		waterLayerTextures.add(new Texture(Gdx.files.internal(Configuration.water1Img)));
		waterLayerTextures.add(new Texture(Gdx.files.internal(Configuration.water2Img)));
		waterLayerTextures.add(new Texture(Gdx.files.internal(Configuration.water3Img)));

		foregroundLayerTextures = new Array<>();
		foregroundLayerTextures.add(new Texture(Gdx.files.internal(Configuration.kiel1Img)));
		foregroundLayerTextures.add(new Texture(Gdx.files.internal(Configuration.kiel2Img)));
		foregroundLayerTextures.add(new Texture(Gdx.files.internal(Configuration.kiel3Img)));

		skyLayerLoop = new Array<>();
		cityLayerLoop = new Array<>();
		waterLayerLoop = new Array<>();
		foregroundLayerLoop = new Array<>();

		skyCounter = initialLoopFill(skyLayerLoop, skyLayerTextures, skyCounter);
		cityCounter = initialLoopFill (cityLayerLoop, cityLayerTextures, cityCounter);
		waterCounter =  initialLoopFill(waterLayerLoop, waterLayerTextures, waterCounter);
		foregroundCounter =  initialLoopFill(foregroundLayerLoop, foregroundLayerTextures, foregroundCounter);
	}

	@Override
	public void renderBackground() {
		renderArray(skyLayerLoop);
		renderArray(cityLayerLoop);
		renderArray(waterLayerLoop);
		renderArray(foregroundLayerLoop);
	}

	@Override
	public void move() {
		moveArrayLeft(skyLayerLoop, layerFourSpeed);
		moveArrayLeft(cityLayerLoop, layerThreeSpeed);
		moveArrayLeft(waterLayerLoop, layerTwoSpeed);
		moveArrayLeft(foregroundLayerLoop, layerOneSpeed);

		skyCounter = updateArray(skyLayerLoop, skyLayerTextures, skyCounter);
		cityCounter = updateArray(cityLayerLoop, cityLayerTextures, cityCounter);
		waterCounter = updateArray(waterLayerLoop, waterLayerTextures, waterCounter);
		foregroundCounter = updateArray(foregroundLayerLoop, foregroundLayerTextures, foregroundCounter);
	}


	/**
	 * Disposes the Background textures.
	 */
	@Override
	public void dispose() {
		disposeTextureArray(cityLayerTextures);
		disposeTextureArray(waterLayerTextures);
		disposeTextureArray(foregroundLayerTextures);
		disposeTextureArray(skyLayerTextures);
	}
}
