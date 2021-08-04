package de.fhkiel.aem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;

import java.awt.*;

/**
 * The Bird of the Game.
 */
public class Bird {
    Sprite birdSprite;
    Circle hitbox;

    //bestimmt die Beschleunigung, in der der Vogel nach iunten fällt
    float fallspeed = 2;
    long pressTime;

    public Bird(float xPos, float yPos) {
        birdSprite = new Sprite(new Texture("manny-straight.png"));

        birdSprite.setX(xPos);
        birdSprite.setY(yPos);
        birdSprite.setOrigin(birdSprite.getX() + birdSprite.getTexture().getWidth() / 2,
                birdSprite.getY() + birdSprite.getTexture().getHeight() / 2);
        hitbox = new Circle(birdSprite.getX(), birdSprite.getY(), birdSprite.getWidth()/3);
        hitbox.setPosition(birdSprite.getOriginX(), birdSprite.getOriginY());
    }

    public void render(SpriteBatch game, int posXLast){
        game.draw(birdSprite, birdSprite.getX(), birdSprite.getY(), birdSprite.getOriginX(), birdSprite.getOriginY(),
                birdSprite.getWidth(), birdSprite.getHeight(),1,1, birdSprite.getRotation());
    }

    public void move(){
        //fallen und in eine Richtung beschleunigen
        yPos = yPos - fallspeed;
        fallspeed +=0.3;

        if ( Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            sprungNachOben();
        }

        //nach xxxx millisec nachdem der button betätigt wurde, fällt der Vogel auf 2 Phasen wieder runter
        if (pressTime != 0 &&( fallspeed < 0  && TimeUtils.millis() >= (pressTime + 200))){
            bird = new Texture("manny-straight.png");
            fallspeed = 4;
        }
        if (pressTime != 0 && fallspeed > 0  && TimeUtils.millis() >= (pressTime + 300)){
            bird = new Texture("manny-down.png");

            fallspeed = 6;
            pressTime = 0;
        }
//stoppe den Vogel am Rand des Bilds
        if (yPos <= 0 ){
            //fallspeed = 0;
            //! zu testen da.... Damit der Vogel stopp, musst du die vorherige Zeile aktivieren und die folgende löschen
            sprungNachOben();
        }

}

    public Circle getHitbox() {
        return hitbox;
    }

    }
/**
 * der Vogel fliegt nach oben mit der beschleunigung -8
 */
    private void sprungNachOben(){
        pressTime = TimeUtils.millis();
        bird = new Texture("manny-up.png");
        fallspeed = -13;
    }
}