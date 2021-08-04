package de.fhkiel.aem;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Circle;

/**
 * The Bird of the Game.
 */
public class Bird extends PositionTexture {
    Sprite birdSprite;
    Circle hitbox;

    public Bird(float xPos, float yPos) {
        super(new Texture("flappy1_up.png"), xPos, yPos);
        birdSprite = new Sprite(getTexture());
        hitbox = new Circle(birdSprite.getX(), birdSprite.getY(), birdSprite.getWidth()/2);
    }

    public Circle getHitbox() {
        return hitbox;
    }
}
