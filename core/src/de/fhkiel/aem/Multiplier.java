package de.fhkiel.aem;

public class Multiplier extends Item{
    public Multiplier(float xPos, float yPos) {
        super(xPos, yPos, Configuration.item_multipyImg);
    }

    @Override
    public void collide(Bird bird) {

    }
}
