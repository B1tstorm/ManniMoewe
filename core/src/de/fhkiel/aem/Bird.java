package de.fhkiel.aem;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * The Bird of the Game.
 */
public class Bird extends PositionTexture {
     Sprite birdSprite;

    public Bird(float xPos, float yPos) {
        super(new Texture("flappy1_up.png"), xPos, yPos);
        birdSprite = new Sprite(getTexture());
    }

}
