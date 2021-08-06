package de.fhkiel.aem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

import java.util.Iterator;

/**
 * The Background of the game
 */
public class Background {
	private final SpriteBatch batch;

	private final Array<Sprite> skyLayerSprites;
	private final Array<Sprite> cityLayerSprites;
	private final Array<Sprite> waterLayerSprites;
	private final Array<Sprite> foregroundLayerSprites;

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

		skyLayerSprites = new Array<>();
		skyLayerSprites.add(new Sprite());

		cityLayerSprites = new Array<>();
		cityLayerSprites.add(new Sprite());
		cityLayerSprites.add(new Sprite());
		cityLayerSprites.add(new Sprite());

		waterLayerSprites = new Array<>();
		waterLayerSprites.add(new Sprite());
		waterLayerSprites.add(new Sprite());
		waterLayerSprites.add(new Sprite());

		foregroundLayerSprites = new Array<>();
		foregroundLayerSprites.add(new Sprite());
		foregroundLayerSprites.add(new Sprite());
		foregroundLayerSprites.add(new Sprite());

		skyLayerLoop = new Array<>();
		cityLayerLoop = new Array<>();
		waterLayerLoop = new Array<>();
		foregroundLayerLoop = new Array<>();


		skyCounter = initialLoopFill(skyLayerSprites, skyLayerLoop, 0, skyCounter);
		cityCounter = initialLoopFill(cityLayerSprites, cityLayerSprites, waterLayerSprites.get(0).getRegionHeight(), cityCounter);
		waterCounter =  initialLoopFill(waterLayerSprites, waterLayerLoop, 0, waterCounter);
		foregroundCounter =  initialLoopFill(foregroundLayerSprites, foregroundLayerLoop, 0, foregroundCounter);
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
		moveArrayLeft(skyLayerLoop, FOREGROUNDSPEED);

		skyCounter = updateArray(skyLayerLoop, skyLayerSprites, skyCounter);
		cityCounter = updateArray(cityLayerLoop, cityLayerSprites, cityCounter);
		waterCounter = updateArray(waterLayerLoop, waterLayerSprites, waterCounter);
		foregroundCounter = updateArray(foregroundLayerLoop, foregroundLayerSprites, foregroundCounter);
	}

	private int updateArray(Array<Sprite> loop, Array<Sprite> sprites, int counter) {
		 if (findRightestPixel(loop) < Configuration.ScreenWidth + 100) {
            Sprite sprite = new Sprite(sprites.get(counter++ % sprites.size));
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

	private int initialLoopFill(Array<Sprite> sprites, Array<Sprite> loop, int drawHeight, int counter) {
		while(!isFilledWithBackgroundImages(loop)) {
			Sprite sprite = new Sprite(sprites.get(counter++ % sprites.size));
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
}
