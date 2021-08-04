package de.fhkiel.aem;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;

/**
 * The Bird of the Game.
 */
public class Bird {
    Sprite birdSprite;
    Circle hitbox;

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

    public Circle getHitbox() {
        return hitbox;
    }
}
