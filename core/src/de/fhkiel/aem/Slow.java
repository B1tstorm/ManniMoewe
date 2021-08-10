package de.fhkiel.aem;

public class Slow extends Item{
    public Slow(float xPos, float yPos) {
        super(xPos, yPos, Configuration.item_slowmoImg);
    }

    @Override
    public void collide(Bird bird, FlappyBird game) {
        game.decreaseGameSpeed();
    }
}
