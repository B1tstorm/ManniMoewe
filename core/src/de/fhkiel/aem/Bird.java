package de.fhkiel.aem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.HashMap;

/**
 * The Bird of the Game.
 */
public class Bird {
    private final Sprite birdSprite;
    private final Circle hitbox;
    private final Texture mannyStraight = new Texture(Gdx.files.internal(Configuration.manny_straightImg));
    private final Texture mannyUp = new Texture(Gdx.files.internal(Configuration.manny_upImg));
    private final Texture mannyDown = new Texture(Gdx.files.internal(Configuration.manny_downImg));

    private final int width = 100;
    private float highscore = 0;

    private int birdWidth = 350;

    private long dieTime;
    private static HashMap<Integer, Texture> animationMap = new HashMap<>();


    public Texture getMannyStraight() {
        return mannyStraight;
    }

    //bestimmt die Beschleunigung, in der der Vogel nach unten fällt
    private float fallSpeed = 2;
    private long pressTime;

    /**
     * Creates an new bird object on the given location.
     *
     * @param xPos x position of the bird
     * @param yPos y position of the bird
     */
    public Bird(float xPos, float yPos) {
        Texture mannyStare = new Texture(Gdx.files.internal(Configuration.manny_stareImg));
        birdSprite = new Sprite(mannyStare);

        birdSprite.setX(xPos);
        birdSprite.setY(yPos);
        hitbox = new Circle(birdSprite.getX(), birdSprite.getY(), width / 2f);
        hitbox.setPosition(birdSprite.getX(), birdSprite.getY());
        initializeAnimation();
    }

    /**
     * Renders the bird on the given SpriteBatch.
     *
     * @param batch spriteBatch it rendered on
     */
    public void render(SpriteBatch batch) {
        batch.draw(birdSprite, birdSprite.getX(), birdSprite.getY(), birdSprite.getOriginX(), birdSprite.getOriginY(),
                getBirdWidth(), getBirdWidth(), 1, 1, birdSprite.getRotation());
    }

    /**
     * Moves the Bird.
     */
    public void move() {
        birdGetsSmaler();

        birdSprite.setY(birdSprite.getY() - fallSpeed);
        hitbox.setPosition(birdSprite.getX() + width / 2f, birdSprite.getY() + width / 2f);
        fallSpeed += 0.3;

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            sprungNachOben();
        }

        //nach xxxx millisec nachdem der button betätigt wurde, fällt der Vogel auf 2 Phasen wieder runter
        if (pressTime != 0 && (fallSpeed < 0 && TimeUtils.millis() >= (pressTime + 200))) {
            birdSprite.setTexture(mannyStraight);
            fallSpeed = 4;
        }
        if (pressTime != 0 && fallSpeed > 0 && TimeUtils.millis() >= (pressTime + 300)) {
            birdSprite.setTexture(mannyDown);

            fallSpeed = 6;
            pressTime = 0;
        }
        //stoppe den Vogel am Rand des Bilds
        if (birdSprite.getY() <= 25) {
            slide();
        }

    }

    //zum beginn des playScreens wird der Vogel allmählich klein
    private void birdGetsSmaler() {
        if (birdWidth > 100) {
            this.setBirdWidth(this.getBirdWidth() - 5);
        }
    }

    /**
     * Gets the Sprite of the bird.
     *
     * @return The bird Sprite
     */
    public Sprite getBirdSprite() {
        return birdSprite;
    }

    /**
     * Returns the hitbox of the bird.
     *
     * @return Circle that represents the hitbox
     */
    public Circle getHitbox() {
        return hitbox;
    }

    /**
     * Gets the width of the Bird.
     *
     * @return Width of bird
     */
    public int getWidth() {
        return width;
    }


    public int getBirdWidth() {
        return birdWidth;
    }

    public void setBirdWidth(int birdWidth) {
        this.birdWidth = birdWidth;
    }

    /**
     * Gets the highscore.
     *
     * @return The highscore
     */
    public float getHighscore() {
        return highscore;
    }

    /**
     * Sets the highscore to a new value.
     *
     * @param highscore New highscore
     */
    public void setHighscore(float highscore) {
        this.highscore = highscore;
    }

    /**
     * The bird jumps up.
     */
    private void sprungNachOben() {
        pressTime = TimeUtils.millis();
        birdSprite.setTexture(mannyUp);
        fallSpeed = -13;
    }

    private void slide() {
        pressTime = TimeUtils.millis();
        birdSprite.setTexture(mannyUp);
        fallSpeed = -6;
    }

    /**
     * the Bird dies and the Animation changes
     */
    public void birdDies() {
        fallSpeed = 2;
        birdSprite.setY(birdSprite.getY() - fallSpeed);
        int dieStep = dieStep();
        if (dieStep < 8) {
            birdSprite.setTexture(animationMap.get(dieStep));
        } else {
            fallSpeed = 5;
            birdSprite.setY(birdSprite.getY() - fallSpeed);
        }
        ;
    }

    /**
     * return an integer based on the time of the collision
     * the return number presents the dying step
     */
    private int dieStep() {
        return (int) ((TimeUtils.millis() - dieTime) / 100);
    }

    public void setFallSpeed(float fallSpeed) {
        this.fallSpeed = fallSpeed;
    }

    public long getDieTime() {
        return dieTime;
    }

    public void setDieTime(long dieTime) {
        this.dieTime = dieTime;
    }

    /**
     * fill the Hashmap with the animation assetss
     */
    private void initializeAnimation() {
        animationMap.put(0, new Texture(Gdx.files.internal(Configuration.mannyOuch2)));
        animationMap.put(1, new Texture(Gdx.files.internal(Configuration.mannyOuch3)));
        animationMap.put(2, new Texture(Gdx.files.internal(Configuration.mannyOuch4)));
        animationMap.put(3, new Texture(Gdx.files.internal(Configuration.mannyOuch5)));
        animationMap.put(4, new Texture(Gdx.files.internal(Configuration.mannyOuch6)));
        animationMap.put(5, new Texture(Gdx.files.internal(Configuration.mannyOuch7)));
        animationMap.put(6, new Texture(Gdx.files.internal(Configuration.mannyOuch8)));
        animationMap.put(7, new Texture(Gdx.files.internal(Configuration.mannyOuch9)));
    }
}
