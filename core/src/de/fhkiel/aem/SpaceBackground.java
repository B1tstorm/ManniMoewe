package de.fhkiel.aem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

/**
 * SpaceBackground with 5 Layers.
 */
public class SpaceBackground extends Background {
	private final Array<Texture> layerFiveTextures;
	private final Array<Texture> layerFourTextures;
	private final Array<Texture> layerThreeTextures;
	private final Array<Texture> layerTwoTextures;
	private final Array<Texture> layerOneTextures;

	private final Array<Sprite> layerFiveLoop;
	private final Array<Sprite> layerFourLoop;
	private final Array<Sprite> layerThreeLoop;
	private final Array<Sprite> layerTwoLoop;
	private final Array<Sprite> layerOneLoop;

	private int layerFiveCounter = 0;
	private int layerFourCounter = 0;
	private int layerThreeCounter = 0;
	private int layerTwoCounter = 0;
	private int layerOneCounter = 0;

	/**
	 * Creates a new SpaceBackground
	 * @param batch SpriteBatch the background is rendered on.
	 */
	public SpaceBackground(SpriteBatch batch) {
		super(batch);

		layerFiveTextures = new Array<>();
		layerFiveTextures.add(new Texture(Gdx.files.internal(Configuration.spaceLayerFive1Img)));
		layerFiveTextures.add(new Texture(Gdx.files.internal(Configuration.spaceLayerFive2Img)));
		layerFiveTextures.add(new Texture(Gdx.files.internal(Configuration.spaceLayerFive3Img)));
		layerFiveTextures.add(new Texture(Gdx.files.internal(Configuration.spaceLayerFive4Img)));
		layerFiveTextures.add(new Texture(Gdx.files.internal(Configuration.spaceLayerFive5Img)));

		layerFourTextures = new Array<>();
		layerFourTextures.add(new Texture(Gdx.files.internal(Configuration.spaceLayerFour1Img)));
		layerFourTextures.add(new Texture(Gdx.files.internal(Configuration.spaceLayerFour2Img)));
		layerFourTextures.add(new Texture(Gdx.files.internal(Configuration.spaceLayerFour3Img)));
		layerFourTextures.add(new Texture(Gdx.files.internal(Configuration.spaceLayerFour4Img)));
		layerFourTextures.add(new Texture(Gdx.files.internal(Configuration.spaceLayerFour5Img)));

		layerThreeTextures = new Array<>();
		layerThreeTextures.add(new Texture(Gdx.files.internal(Configuration.spaceLayerThree1Img)));
		layerThreeTextures.add(new Texture(Gdx.files.internal(Configuration.spaceLayerThree2Img)));
		layerThreeTextures.add(new Texture(Gdx.files.internal(Configuration.spaceLayerThree3Img)));
		layerThreeTextures.add(new Texture(Gdx.files.internal(Configuration.spaceLayerThree4Img)));
		layerThreeTextures.add(new Texture(Gdx.files.internal(Configuration.spaceLayerThree5Img)));

		layerTwoTextures = new Array<>();
		layerTwoTextures.add(new Texture(Gdx.files.internal(Configuration.spaceLayerTwo1Img)));
		layerTwoTextures.add(new Texture(Gdx.files.internal(Configuration.spaceLayerTwo2Img)));
		layerTwoTextures.add(new Texture(Gdx.files.internal(Configuration.spaceLayerTwo3Img)));
		layerTwoTextures.add(new Texture(Gdx.files.internal(Configuration.spaceLayerTwo4Img)));
		layerTwoTextures.add(new Texture(Gdx.files.internal(Configuration.spaceLayerTwo5Img)));

		layerOneTextures = new Array<>();
		layerOneTextures.add(new Texture(Gdx.files.internal(Configuration.spaceLayerOne1Img)));
		layerOneTextures.add(new Texture(Gdx.files.internal(Configuration.spaceLayerOne2Img)));
		layerOneTextures.add(new Texture(Gdx.files.internal(Configuration.spaceLayerOne3Img)));
		layerOneTextures.add(new Texture(Gdx.files.internal(Configuration.spaceLayerOne4Img)));
		layerOneTextures.add(new Texture(Gdx.files.internal(Configuration.spaceLayerOne5Img)));

		layerFiveLoop = new Array<>();
		layerFourLoop = new Array<>();
		layerThreeLoop = new Array<>();
		layerTwoLoop = new Array<>();
		layerOneLoop = new Array<>();

		layerFiveCounter = initialLoopFill(layerFiveLoop, layerFiveTextures, layerFiveCounter);
		layerFourCounter = initialLoopFill(layerFourLoop, layerFourTextures, layerFourCounter);
		layerThreeCounter = initialLoopFill(layerThreeLoop, layerThreeTextures, layerThreeCounter);
		layerTwoCounter = initialLoopFill(layerTwoLoop, layerTwoTextures, layerTwoCounter);
		layerOneCounter = initialLoopFill(layerOneLoop, layerOneTextures, layerOneCounter);
	}

	@Override
	public void renderBackground() {
		renderArray(layerFiveLoop);
		renderArray(layerFourLoop);
		renderArray(layerThreeLoop);
		renderArray(layerTwoLoop);
		renderArray(layerOneLoop);
	}

	@Override
	public void move() {
		moveArrayLeft(layerFiveLoop, layerFiveSpeed);
		moveArrayLeft(layerFourLoop, layerFourSpeed);
		moveArrayLeft(layerThreeLoop, layerThreeSpeed);
		moveArrayLeft(layerTwoLoop, layerTwoSpeed);
		moveArrayLeft(layerOneLoop, layerOneSpeed);

		layerFiveCounter = updateArray(layerFiveLoop, layerFiveTextures, layerFiveCounter);
		layerFourCounter = updateArray(layerFourLoop, layerFourTextures, layerFourCounter);
		layerThreeCounter = updateArray(layerThreeLoop, layerThreeTextures, layerThreeCounter);
		layerTwoCounter = updateArray(layerTwoLoop, layerTwoTextures, layerTwoCounter);
		layerOneCounter = updateArray(layerOneLoop, layerOneTextures, layerOneCounter);
	}

	@Override
	public void dispose() {
		disposeTextureArray(layerFiveTextures);
		disposeTextureArray(layerFourTextures);
		disposeTextureArray(layerThreeTextures);
		disposeTextureArray(layerTwoTextures);
		disposeTextureArray(layerOneTextures);
	}

}
