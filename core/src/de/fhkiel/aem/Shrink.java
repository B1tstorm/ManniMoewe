package de.fhkiel.aem;

import com.badlogic.gdx.utils.TimeUtils;

public class Shrink extends Item{
    public Shrink(float xPos, float yPos) {
        super(xPos, yPos, Configuration.item_miniImg);
    }

    @Override
    public void collide(Bird bird, FlappyBird game) {
        bird.getHitbox().setRadius(bird.getWidth() / 4);
        bird.setBirdWidth((int) (bird.getWidth() * 0.6));
        bird.setLastShrinkTime(TimeUtils.nanoTime());
    }
}
