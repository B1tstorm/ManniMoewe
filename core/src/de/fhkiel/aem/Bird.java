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
    Sprite birdSprite;
    Circle hitbox;
    private final Texture mannyStraight = new Texture("manny-straight.png");
    private final Texture mannyUp = new Texture("manny-up.png");
    private final Texture mannyDown = new Texture("manny-down.png");
    private final int width = 350;
    private float highscore = 0;
    private final Texture mannyStare = new Texture("manny-stare.png");



    private int birdWidth = 350;


    public Texture getMannyStraight() {
        return mannyStraight;
    }

    //bestimmt die Beschleunigung, in der der Vogel nach iunten fällt
    float fallspeed = 2;
    long pressTime;

    public Bird(float xPos, float yPos) {
        birdSprite = new Sprite(mannyStare);

        birdSprite.setX(xPos);
        birdSprite.setY(yPos);
        hitbox = new Circle(birdSprite.getX(), birdSprite.getY(), 50);
        hitbox.setPosition(birdSprite.getX(), birdSprite.getY());
    }

    public void render(SpriteBatch game, int posXLast){
        game.draw(birdSprite, birdSprite.getX(), birdSprite.getY(), birdSprite.getOriginX(), birdSprite.getOriginY(),
                birdSprite.getWidth(), birdSprite.getHeight(),1,1, birdSprite.getRotation());
    }

    public void move(){
        birdGetsSmaler();

        //fallen und in eine Richtung beschleunigen
        birdSprite.setY(birdSprite.getY() - fallspeed);

        hitbox.setPosition(birdSprite.getX() + birdWidth / 2, birdSprite.getY() + birdWidth / 2);
        fallspeed +=0.3;

        if ( Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            sprungNachOben();
        }

        //nach xxxx millisec nachdem der button betätigt wurde, fällt der Vogel auf 2 Phasen wieder runter
        if (pressTime != 0 &&( fallspeed < 0  && TimeUtils.millis() >= (pressTime + 200))){
            birdSprite.setTexture(mannyStraight);
            fallspeed = 4;
        }
        if (pressTime != 0 && fallspeed > 0  && TimeUtils.millis() >= (pressTime + 300)){
            birdSprite.setTexture(mannyDown);

            fallspeed = 6;
            pressTime = 0;
        }
//stoppe den Vogel am Rand des Bilds
        if (birdSprite.getY() <= 0 ){
            //fallspeed = 0;
            //! zu testen da.... Damit der Vogel stopp, musst du die vorherige Zeile aktivieren und die folgende löschen
            sprungNachOben();
        }

}
    //zum beginn des playScreens wird der Vogel allmählich klein
    private void birdGetsSmaler() {
        if(birdWidth > 100){
            this.setBirdWidth(this.getBirdWidth()-5);
        }
    }

    public Circle getHitbox() {
        return hitbox;
    }



    public int getBirdWidth() {
        return birdWidth;
    }

    public void setBirdWidth(int birdWidth) {
        this.birdWidth = birdWidth;
    }

    public float getHighscore(){return highscore;}

    public void setHighscore(float highscore) {
        this.highscore = highscore;
    }

    /**
 * der Vogel fliegt nach oben mit der beschleunigung -8
 */
    private void sprungNachOben(){
        pressTime = TimeUtils.millis();
        birdSprite.setTexture(mannyUp);
        fallspeed = -13;
    }


}