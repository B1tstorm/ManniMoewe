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
    private final Texture mannyStraight = new Texture(Gdx.files.internal(Configuration.manny_straightImg));
    private final Texture mannyUp = new Texture(Gdx.files.internal(Configuration.manny_upImg));
    private final Texture mannyDown = new Texture(Gdx.files.internal(Configuration.manny_downImg));
    private final Texture mannyStraightHelm = new Texture(Configuration.manny_straight_helmImg);
    private final Texture mannyDownHelm = new Texture(Configuration.manny_down_HelmImg);
    private final Texture mannyUpHelm = new Texture(Configuration.manny_up_helmImg);
    private final int width = 100;
    private float highscore = 0;
    private int  scoreCollectable = 0;
    private boolean invincible = false;
    private boolean helmetactive = false;
    private int multiplier = 1;
    private int birdWidth = 350;
    private long lastMultiplierTime;


    public Texture getMannyStraight() {
        return mannyStraight;
    }

    //bestimmt die Beschleunigung, in der der Vogel nach unten fällt
    private float fallSpeed = 2;
    private long pressTime;

    /**
     * Creates an new bird object on the given location.
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
    }

    /**
     * Renders the bird on the given SpriteBatch.
     * @param batch spriteBatch it rendered on
     */
    public void render(SpriteBatch batch){
        batch.draw(birdSprite, birdSprite.getX(), birdSprite.getY(), birdSprite.getOriginX(), birdSprite.getOriginY(),
                getBirdWidth(), getBirdWidth(),1,1, birdSprite.getRotation());
    }

    /**
     * Moves the Bird.
     */
    public void move(){
        birdSprite.setY(birdSprite.getY() - fallSpeed);
        hitbox.setPosition(birdSprite.getX() + width / 2f, birdSprite.getY() + width / 2f);
        fallSpeed +=0.3;

        if ( Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            sprungNachOben();
        }

        //nach xxxx millisec nachdem der button betätigt wurde, fällt der Vogel auf 2 Phasen wieder runter
        if (pressTime != 0 &&( fallSpeed < 0  && TimeUtils.millis() >= (pressTime + 200))){
            if(helmetactive){
                birdSprite.setTexture(mannyStraightHelm);
            } else {
                birdSprite.setTexture(mannyStraight);
            }
            fallSpeed = 4;
        }
        if (pressTime != 0 && fallSpeed > 0  && TimeUtils.millis() >= (pressTime + 300)){
            if(helmetactive){
                birdSprite.setTexture(mannyDownHelm);
            } else {
                birdSprite.setTexture(mannyDown);
            }
            fallSpeed = 6;
            pressTime = 0;
        }
        //stoppe den Vogel am Rand des Bilds
        if (birdSprite.getY() <= 25 ){
            //! zu testen da.... Damit der Vogel stopp, musst du die vorherige Zeile aktivieren und die folgende löschen
            slide();
        }

    }

    //zum beginn des playScreens wird der Vogel allmählich klein
    public void birdGetSmaller() {
        if(birdWidth > 100){
            this.setBirdWidth(this.getBirdWidth()-5);
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


    public int getBirdWidth() {
        return birdWidth;
    }

    public void setBirdWidth(int birdWidth) {
        this.birdWidth = birdWidth;
    }

    /**
     * Gets the highscore.
     * @return The highscore
     */
    public float getHighscore(){
        return highscore;
    }

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
        if(helmetactive){
            birdSprite.setTexture(mannyUpHelm);
        } else {
            birdSprite.setTexture(mannyUp);
        }
        fallSpeed = -13;
    }

    private void slide(){
        pressTime = TimeUtils.millis();
        if(helmetactive){
            birdSprite.setTexture(mannyUpHelm);
        }
        else {
            birdSprite.setTexture(mannyUp);
        }
        fallSpeed = -7;
    }

    public void setFallSpeed(float fallSpeed) {
        this.fallSpeed = fallSpeed;
    }

    public int getScoreCollectable() {return scoreCollectable;}

    public void setScoreCollectable(int scoreCollectable) {
        this.scoreCollectable = scoreCollectable;
    }

    public Texture getMannyUp() {
        return mannyUp;
    }

    public Texture getMannyDown() {
        return mannyDown;
    }

    public boolean isInvincible() {
        return invincible;
    }

    public void setInvincible(boolean invincible) {
        this.invincible = invincible;
    }

    public float getFallSpeed() {
        return fallSpeed;
    }

    public long getPressTime() {
        return pressTime;
    }

    public void setPressTime(long pressTime) {
        this.pressTime = pressTime;
    }

    public Texture getMannyStraightHelm() {
        return mannyStraightHelm;
    }

    public Texture getMannyDownHelm() {
        return mannyDownHelm;
    }

    public Texture getMannyUpHelm() {
        return mannyUpHelm;
    }

    public boolean isHelmetactive() {
        return helmetactive;
    }

    public void setHelmetactive(boolean helmetactive) {
        this.helmetactive = helmetactive;
    }

    public int getMultiplier() {
        return multiplier;
    }

    public void setMultiplier(int multiplier) {
        this.multiplier = multiplier;
    }

    public long getLastMultiplierTime() {
        return lastMultiplierTime;
    }

    public void setLastMultiplierTime(long lastMultiplierTime) {
        this.lastMultiplierTime = lastMultiplierTime;
    }
}
