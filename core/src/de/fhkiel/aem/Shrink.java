package de.fhkiel.aem;

import com.badlogic.gdx.utils.TimeUtils;

public class Shrink extends Item{
    public Shrink(float xPos, float yPos) {
        super(xPos, yPos, Configuration.item_miniImg);
    }

    @Override
    public void collide(Bird bird) {
        bird.getHitbox().setRadius(bird.getWidth() / 4);
        bird.setBirdWidth(bird.getWidth() / 2);
        bird.setLastShrinkTime(TimeUtils.nanoTime());
    }
}
