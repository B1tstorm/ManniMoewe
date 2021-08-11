package de.fhkiel.aem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * The barriers the bird can collide with.
 */
public class Barrier {


    private final Sprite barrierSprite;
    public static float speed = 300;
    private float gap = 450;
    private float distance = 850;
    private float wealth;
    private Rectangle hitbox;
    private int hitboxKorrektur = 10;

    /**
     * Creates a new Barrier on the given location.
     * @param x X position
     * @param y Y position
     * @param imagePath The image of the barrier
     */
    public Barrier(float x, float y, String imagePath, int difficulty){
        barrierSprite = new Sprite(new Texture(imagePath));
        barrierSprite.setX(x);
        barrierSprite.setY(y);

        distance -= difficulty * 100;
        gap -= difficulty * 50;
        wealth = difficulty / 2.0f;

        hitbox = new Rectangle(barrierSprite.getX() + hitboxKorrektur, barrierSprite.getY(),
                barrierSprite.getWidth() - hitboxKorrektur, barrierSprite.getHeight() - 90);
    }


    /**
     * Renders the Barrier on the given SpriteBatch.
     * @param batch The SpriteBatch its rendered on
     */
    public void render(SpriteBatch batch){
        batch.draw(barrierSprite, barrierSprite.getX(), barrierSprite.getY(), barrierSprite.getOriginX(), barrierSprite.getOriginY(),
                barrierSprite.getWidth(), barrierSprite.getHeight(),1,1, barrierSprite.getRotation());
    }

    /**
     * Moves the barriers.
     */
    public void move(){
        barrierSprite.setX(barrierSprite.getX() - Gdx.graphics.getDeltaTime() * speed);
        hitbox.setPosition(hitbox.getX() - Gdx.graphics.getDeltaTime() * speed, hitbox.getY());
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
     * Gets the hitbox of the barrier.
     * @return The Rectangle hitbox
     */
    public Rectangle getHitbox() {
        return hitbox;}

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
