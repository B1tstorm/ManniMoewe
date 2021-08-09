package de.fhkiel.aem;

import com.badlogic.gdx.utils.TimeUtils;

public class Multiplier extends Item{
    public Multiplier(float xPos, float yPos) {
        super(xPos, yPos, Configuration.item_multipyImg);
    }

    @Override
    public void collide(Bird bird) {
        bird.setMultiplier(2);
        bird.setLastMultiplierTime(TimeUtils.nanoTime());
    }
}
