package de.fhkiel.aem;

public class Fries extends Item{
    public Fries(float xPos, float yPos) {
        super(xPos, yPos, Configuration.pommesImg);
    }

    @Override
    public void collide(Bird bird, FlappyBird game){
        if(bird.getScoreCollectable() < 3) {
            bird.setScoreCollectable(bird.getScoreCollectable() + 1);
        }
        if(bird.getScoreCollectable() >= 3){
            bird.setHelmetactive(true);
        }
    }
}
