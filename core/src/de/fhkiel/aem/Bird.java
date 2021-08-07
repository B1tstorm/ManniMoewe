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

    private final Texture mannyOuch2 = new Texture(Gdx.files.internal(Configuration.mannyOuch2));
    private final Texture mannyOuch3 = new Texture(Gdx.files.internal(Configuration.mannyOuch3));
    private final Texture mannyOuch4 = new Texture(Gdx.files.internal(Configuration.mannyOuch4));
    private final Texture mannyOuch5 = new Texture(Gdx.files.internal(Configuration.mannyOuch5));
    private final Texture mannyOuch6 = new Texture(Gdx.files.internal(Configuration.mannyOuch6));
    private final Texture mannyOuch7 = new Texture(Gdx.files.internal(Configuration.mannyOuch7));
    private final Texture mannyOuch8 = new Texture(Gdx.files.internal(Configuration.mannyOuch8));

    private final int width = 100;
    private float highscore = 0;

    private int birdWidth = 350;

    private long dieTime;
    private static HashMap <Integer, Texture> animation = new HashMap<>();


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
        initializeAnimation();
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
        birdGetsSmaler();

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
        if (birdSprite.getY() <= 25 ){
            //! zu testen da.... Damit der Vogel stopp, musst du die vorherige Zeile aktivieren und die folgende löschen
            slide();
        }

    }

    //zum beginn des playScreens wird der Vogel allmählich klein
    private void birdGetsSmaler() {
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
        birdSprite.setTexture(mannyUp);
        fallSpeed = -13;
    }

    private void slide(){
        pressTime = TimeUtils.millis();
        birdSprite.setTexture(mannyUp);
        fallSpeed = -7;
    }
    //!hier
    public void birdDies(){
//        if(TimeUtils.millis() >= dieTime + 100 && TimeUtils.millis() < dieTime + 200){
//            System.out.println("die time"+dieTime);
//            birdSprite.setTexture(mannyOuch2);
//            System.out.println(dieStep());
//        }else if (TimeUtils.millis() >= dieTime + 200 && TimeUtils.millis() < dieTime + 300){
//            birdSprite.setTexture(mannyOuch3);
//            System.out.println(TimeUtils.millis());
//            System.out.println(dieStep());
//
//        }else if (TimeUtils.millis() >= dieTime + 300 && TimeUtils.millis() < dieTime + 400){
//            birdSprite.setTexture(mannyOuch4);
//            System.out.println(TimeUtils.millis());
//            System.out.println(dieStep());
//
//        }else if (TimeUtils.millis() >= dieTime + 400 && TimeUtils.millis() < dieTime + 500){
//            birdSprite.setTexture(mannyOuch5);
//            System.out.println(TimeUtils.millis());
//            System.out.println(dieStep());
//
//        }else if (TimeUtils.millis() >= dieTime + 500 && TimeUtils.millis() < dieTime + 600){
//            birdSprite.setTexture(mannyOuch6);
//            System.out.println(TimeUtils.millis());
//            System.out.println(dieStep());
//
//        }else if (TimeUtils.millis() >= dieTime + 600 && TimeUtils.millis() < dieTime + 700){
//            birdSprite.setTexture(mannyOuch7);
//            System.out.println(TimeUtils.millis());
//            System.out.println(dieStep());
//
//        }else if (TimeUtils.millis() >= dieTime + 700 && TimeUtils.millis() < dieTime + 800){
//            birdSprite.setTexture(mannyOuch8);
//            System.out.println(TimeUtils.millis());
//            System.out.println(dieStep());
//
//        }

//        if(TimeUtils.millis() >= dieTime + 700 && TimeUtils.millis() < dieTime + 800){
//            birdSprite.setTexture(mannyOuch8);
//            System.out.println(TimeUtils.millis());
//            System.out.println(dieStep());
//        }
        int dieStep = dieStep();
        if (dieStep >2 && dieStep < 9){
        birdSprite.setTexture(animation.get(dieStep));
        }
    }

    private int dieStep(){
        return (int) ((TimeUtils.millis() -dieTime)/100);
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

    private void initializeAnimation(){
        animation.put(2,new Texture(Gdx.files.internal(Configuration.mannyOuch2)));
        animation.put(3,new Texture(Gdx.files.internal(Configuration.mannyOuch3)));
        animation.put(4,new Texture(Gdx.files.internal(Configuration.mannyOuch4)));
        animation.put(5,new Texture(Gdx.files.internal(Configuration.mannyOuch5)));
        animation.put(6,new Texture(Gdx.files.internal(Configuration.mannyOuch6)));
        animation.put(7,new Texture(Gdx.files.internal(Configuration.mannyOuch7)));
        animation.put(8,new Texture(Gdx.files.internal(Configuration.mannyOuch8)));
    }
}
