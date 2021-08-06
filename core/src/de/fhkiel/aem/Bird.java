package de.fhkiel.aem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * The Bird of the Game.
 */
public class Bird {
    private final Sprite birdSprite;
    private final Circle hitbox;
    private final Texture mannyStraight = new Texture("manny-straight.png");
    private final Texture mannyUp = new Texture("manny-up.png");
    private final Texture mannyDown = new Texture("manny-down.png");
    private final int width = 100;
    private float highscore = 0;

    //bestimmt die Beschleunigung, in der der Vogel nach unten fällt
    private float fallSpeed = 2;
    private long pressTime;

    /**
     * Creates an new bird object on the given location.
     * @param xPos x position of the bird
     * @param yPos y position of the bird
     */
    public Bird(float xPos, float yPos) {
        birdSprite = new Sprite(mannyStraight);

        birdSprite.setX(xPos);
        birdSprite.setY(yPos);
        hitbox = new Circle(birdSprite.getX(), birdSprite.getY(), width / 2f);
        hitbox.setPosition(birdSprite.getX(), birdSprite.getY());
    }

    /**
     * Renders the bird on the given SpriteBatch.
     * @param batch spriteBatch it rendered on
     */
    public void render(SpriteBatch batch){
        batch.draw(birdSprite, birdSprite.getX(), birdSprite.getY(), birdSprite.getOriginX(), birdSprite.getOriginY(),
                getWidth(), getWidth(),1,1, birdSprite.getRotation());
    }

    /**
     * Moves the Bird.
     */
    public void move(){
        //fallen und in eine Richtung beschleunigen
        birdSprite.setY(birdSprite.getY() - fallSpeed);
        hitbox.setPosition(birdSprite.getX() + width / 2f, birdSprite.getY() + width / 2f);
        fallSpeed +=0.3;

        if ( Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            sprungNachOben();
        }

        //nach xxxx millisec nachdem der button betätigt wurde, fällt der Vogel auf 2 Phasen wieder runter
        if (pressTime != 0 &&( fallSpeed < 0  && TimeUtils.millis() >= (pressTime + 200))){
            birdSprite.setTexture(mannyStraight);
            fallSpeed = 4;
        }
        if (pressTime != 0 && fallSpeed > 0  && TimeUtils.millis() >= (pressTime + 300)){
            birdSprite.setTexture(mannyDown);

            fallSpeed = 6;
            pressTime = 0;
        }
        //stoppe den Vogel am Rand des Bilds
        if (birdSprite.getY() <= 0 ){
            //fallspeed = 0;
            //! zu testen da.... Damit der Vogel stopp, musst du die vorherige Zeile aktivieren und die folgende löschen
            sprungNachOben();
        }

}

    /**
     * Gets the Sprite of the bird.
     * @return The bird Sprite
     */
    public Sprite getBirdSprite(){
            return birdSprite;
    }

    /**
     * Returns the hitbox of the bird.
     * @return Circle that represents the hitbox
     */
    public Circle getHitbox() {
        return hitbox;
    }

    /**
     * Gets the width of the Bird.
     * @return Width of bird
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gets the highscore.
     * @return The highscore
     */
    public float getHighscore(){return highscore;}

    /**
     * Sets the highscore to a new value.
     * @param highscore New highscore
     */
    public void setHighscore(float highscore) {
        this.highscore = highscore;
    }

    /**
     * The bird jumps up.
     */
    private void sprungNachOben(){
        pressTime = TimeUtils.millis();
        birdSprite.setTexture(mannyUp);
        fallSpeed = -13;
    }
}