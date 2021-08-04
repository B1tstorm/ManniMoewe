package de.fhkiel.aem;

import com.badlogic.gdx.graphics.Texture;

/**
 * The Bird of the Game.
 */
public class Bird extends PositionTexture {

    public Bird(float xPos, float yPos) {
        super(new Texture("flappy1_up.png"), xPos, yPos);
    }

}
