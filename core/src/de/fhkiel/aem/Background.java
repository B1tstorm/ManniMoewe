package de.fhkiel.aem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

import java.util.Iterator;

public abstract class Background {
	protected final SpriteBatch batch;

	public static float layerFiveSpeed = 30;
	public static float layerFourSpeed = 60;
	public static float layerThreeSpeed = 110;
	public static float layerTwoSpeed = 180;
	public static float layerOneSpeed = 300;

	protected Background(SpriteBatch batch) {
		this.batch = batch;
	}

	/**
	 * Renders the background.
	 */
	public abstract void renderBackground();

	/**
	 * Moves the background.
	 */
	public abstract void move();

	/**
	 * Disposes the Background textures.
	 */
	public abstract void dispose();

	protected void disposeTextureArray(Array<Texture> array) {
		for (Texture disposable: array) {
			disposable.dispose();
		}
	}

	/**
	 * Updates the array loop. Removes images that are
	 * @param loop The background image loop
	 * @param textures The Texture array
	 * @param counter The current Sprite counter
	 * @return The new counter value
	 */
	protected int updateArray(Array<Sprite> loop, Array<Texture> textures, int counter) {
		if (findRightestPixel(loop) < Configuration.ScreenWidth + 100) {
			Sprite sprite = new Sprite(textures.get(counter++ % textures.size));
			sprite.setX(findRightestPixel(loop));
			sprite.setY(0);
			loop.add(sprite);
		}
		for(Iterator<Sprite> iter = loop.iterator(); iter.hasNext(); ) {
			Sprite item = iter.next();
			if(item.getX() + item.getWidth() < -20) {
				iter.remove();
			}
		}
		return counter;
	}

	/**
	 * Updates the array loop. Removes images that are
	 * @param loop The background image loop
	 * @param textures The Texture array
	 * @param counter The current Sprite counter
	 * @return The new counter value
	 */
	protected int initialLoopFill(Array<Sprite> loop, Array<Texture> textures, int counter) {
		while(!isFilledWithBackgroundImages(loop)) {
			Sprite sprite = new Sprite(textures.get(counter++ % textures.size));
			sprite.setX(findRightestPixel(loop));
			sprite.setY(0);
			loop.add(sprite);
		}
		return counter;
	}


	/**
	 * Renders all items of an array.
	 * @param array The array that should be rendered
	 */
	protected void renderArray(Array<Sprite> array) {
		for(Sprite item : new Array.ArrayIterator<>(array)) {
			batch.draw(item.getTexture(), item.getX(), item.getY());
		}
	}

	/**
	 * Moves an array in the left direction of the screen.
	 * @param array The array that should be moved.
	 * @param speed The speed it should be moved.
	 */
	protected void moveArrayLeft(Array<Sprite> array, float speed) {
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
	protected boolean isFilledWithBackgroundImages(Array<Sprite> array) {
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
	protected float findRightestPixel(Array<Sprite> array) {
		float pixel = 0;

		for (Sprite texture: new Array.ArrayIterator<>(array)) {
			float right = texture.getX() + texture.getWidth();
			if (right > pixel) pixel = right;
		}

		return pixel;
	}
}
