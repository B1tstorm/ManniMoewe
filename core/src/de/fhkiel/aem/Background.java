package de.fhkiel.aem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

import java.util.Iterator;

/**
 * The Background of the game
 */
public class Background {
	private final SpriteBatch batch;

	private final Array<Texture> skyLayerTextures;
	private final Array<Texture> cityLayerTextures;
	private final Array<Texture> waterLayerTextures;
	private final Array<Texture> foregroundLayerTextures;

	private final Array<Sprite> skyLayerLoop;
	private final Array<Sprite> cityLayerLoop;
	private final Array<Sprite> waterLayerLoop;
	private final Array<Sprite> foregroundLayerLoop;

	private final static float SKYSPEED = 30;
	private final static float CITYSPEED = 75;
	private final static float WATERSPEED = 150;
	private final static float FOREGROUNDSPEED = 300;

	private int skyCounter = 0;
	private int cityCounter = 0;
	private int waterCounter = 0;
	private int foregroundCounter = 0;

	public Background(SpriteBatch batch) {
		this.batch = batch;

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

		skyCounter = initialLoopFill(skyLayerLoop, skyLayerTextures, 0, skyCounter);
		cityCounter = initialLoopFill (cityLayerLoop, cityLayerTextures,  0, cityCounter);
		waterCounter =  initialLoopFill(waterLayerLoop, waterLayerTextures,0, waterCounter);
		foregroundCounter =  initialLoopFill(foregroundLayerLoop, foregroundLayerTextures, 0, foregroundCounter);
	}

	public void renderBackground() {
		renderArray(skyLayerLoop);
		renderArray(cityLayerLoop);
		renderArray(waterLayerLoop);
	}

	public void renderForeground() {
		renderArray(foregroundLayerLoop);
	}

	public void move() {
		moveArrayLeft(skyLayerLoop, SKYSPEED);
		moveArrayLeft(cityLayerLoop, CITYSPEED);
		moveArrayLeft(waterLayerLoop, WATERSPEED);
		moveArrayLeft(foregroundLayerLoop, FOREGROUNDSPEED);

		skyCounter = updateArray(skyLayerLoop, skyLayerTextures, skyCounter);
		cityCounter = updateArray(cityLayerLoop, cityLayerTextures, cityCounter);
		waterCounter = updateArray(waterLayerLoop, waterLayerTextures, waterCounter);
		foregroundCounter = updateArray(foregroundLayerLoop, foregroundLayerTextures, foregroundCounter);
	}

	private int updateArray(Array<Sprite> loop, Array<Texture> textures, int counter) {
		 if (findRightestPixel(loop) < Configuration.ScreenWidth + 100) {
            Sprite sprite = new Sprite(textures.get(counter++ % textures.size));
            sprite.setX(findRightestPixel(loop));
            sprite.setY(0);
            loop.add(sprite);
        }
		for(Iterator<Sprite> iter = new Array.ArrayIterator<>(loop); iter.hasNext(); ) {
			Sprite item = iter.next();
			if(item.getX() + item.getWidth() < -20) {
				iter.remove();
			}
		}
		return counter;
	}

	private int initialLoopFill(Array<Sprite> loop, Array<Texture> textures, int drawHeight, int counter) {
		while(!isFilledWithBackgroundImages(loop)) {
			Sprite sprite = new Sprite(textures.get(counter++ % textures.size));
			sprite.setX(findRightestPixel(loop));
			sprite.setY(drawHeight);
			loop.add(sprite);
		}
		return counter;
	}


	/**
	 * Renders all items of an array.
	 * @param array The array that should be rendered
	 */
	private void renderArray(Array<Sprite> array) {
		for(Sprite item : new Array.ArrayIterator<>(array)) {
			batch.draw(item.getTexture(), item.getX(), item.getY());
		}
	}

	/**
	 * Moves an array in the left direction of the screen.
	 * @param array The array that should be moved.
	 * @param speed The speed it should be moved.
	 */
	private void moveArrayLeft(Array<Sprite> array, float speed) {
		float movement = speed * Gdx.graphics.getDeltaTime();
		for(Sprite item : new Array.ArrayIterator<>(array)) {
			item.setX(item.getX() - movement);
		}
	}

	/**
	 * Checks if the whole Screen is covered by an background image.
	 * @param array The array of background images
	 * @return True if the whole width is covered.
	 */
	private boolean isFilledWithBackgroundImages(Array<Sprite> array) {
		float pixel = 0;

		for (Sprite positionTexture : new Array.ArrayIterator<>(array)) {
			if (positionTexture.getX() < 0) {
				pixel += positionTexture.getWidth() - positionTexture.getX();
			} else {
				pixel += positionTexture.getWidth();
			}
		}
		return pixel > Configuration.ScreenWidth;
	}

	/**
	 * Finds the most right position (with its width) of an image array.
	 * @param array The array that is checked
	 * @return The most right pixel on the screen
	 */
	private float findRightestPixel(Array<Sprite> array) {
		float pixel = 0;

		for (Sprite texture: new Array.ArrayIterator<>(array)) {
			float right = texture.getX() + texture.getWidth();
			if (right > pixel) pixel = right;
		}

		return pixel;
	}

	public void dispose() {

		for(Texture texture: new Array.ArrayIterator<>(cityLayerTextures)) {
			texture.dispose();
		}
		for(Texture texture: new Array.ArrayIterator<>(waterLayerTextures)) {
			texture.dispose();
		}
		for(Texture texture: new Array.ArrayIterator<>(foregroundLayerTextures)) {
			texture.dispose();
		}
		for(Texture texture: new Array.ArrayIterator<>(skyLayerTextures)) {
			texture.dispose();
		}
	}
}
