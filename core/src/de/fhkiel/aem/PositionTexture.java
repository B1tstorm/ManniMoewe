package de.fhkiel.aem;

import com.badlogic.gdx.graphics.Texture;

/**
 * An Texture with X and Y Positions
 */
public class PositionTexture {

	private float x;
	private float y;
	private final Texture texture;

	/**
	 * Creates an new PositionTexture with X and Y on 0.
	 * @param texture The texture
	 */
	public PositionTexture(final Texture texture) {
		this(texture, 0, 0);
	}

	/**
	 * Creates an the PositionTexture on the given values.
	 * @param texture The texture
	 * @param x The X value
	 * @param y The Y value
	 */
	public PositionTexture(final Texture texture, final float x, final float y) {
		this.texture = texture;
		this.x = x;
		this.y = y;
	}

	/**
	 * Gets the X value.
	 * @return X
	 */
	public float getX() {
		return x;
	}

	/**
	 * Sets X to a new value.
	 * @param x New X value
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * Gets the Y value.
	 * @return Y value
	 */
	public float getY() {
		return y;
	}

	/**
	 * Sets Y to a new value.
	 * @param y New Y value
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * Gets the texture.
	 * @return Texture
	 */
	public Texture getTexture() {
		return texture;
	}

	/**
	 * Gets the width of the texture.
	 * @return The width
	 */
	public float getWidth() {
		return texture.getWidth();
	}

	/**
	 * Gets the height of the texture.
	 * @return The height
	 */
	public float getHeight() {
		return texture.getHeight();
	}

}
