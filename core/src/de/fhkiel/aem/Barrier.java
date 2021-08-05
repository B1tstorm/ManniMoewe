package de.fhkiel.aem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * The barriers the bird can collide with.
 */
public class Barrier {


    private final Sprite barrierSprite;
    private float speed = 300;
    private float gap = 350;
    private float distance = 700;
    private float wealth = 0.5f;

    public Barrier() {
        barrierSprite = new Sprite(new Texture("barrier.png"));
    }


    /**
     * Renders the Barrier on the given SpriteBatch.
     * @param batch The SpriteBatch its rendered on
     */
    public void render(SpriteBatch batch){
        batch.draw(barrierSprite, barrierSprite.getX(), barrierSprite.getY(), barrierSprite.getOriginX(), barrierSprite.getOriginY(),
                barrierSprite.getWidth(), barrierSprite.getHeight(),1,1, barrierSprite.getRotation());
        move();
    }

    /**
     * Moves the barriers.
     */
    private void move(){
        barrierSprite.setX(barrierSprite.getX() - Gdx.graphics.getDeltaTime() * speed);

    }

    /**
     * Gets the Sprite of the barrier.
     * @return The Sprite
     */
    public Sprite getBarrierSprite() {
        return barrierSprite;
    }

    /**
     * Gets the distance between barriers (X distance).
     * @return The Distance
     */
    public float getDistance() {
        return distance;
    }

    /**
     * Gets the gap between the barriers (Y distance).
     * @return The gap
     */
    public float getGap() {
        return gap;
    }

    /**
     * Gets the wealth of the barrier.
     * @return The wealth
     */
    public float getWealth() {
        return wealth;
    }

    /**
     * Sets the wealth of the barrier.
     * @param wealth New wealth
     */
    public void setWealth(float wealth) {
        this.wealth = wealth;
    }
}
