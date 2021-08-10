package de.fhkiel.aem;

public class ReduceScore extends Item{

    public ReduceScore(float xPos, float yPos) {super(xPos, yPos, Configuration.reduceScoreImg);}

    @Override
    public void collide(Bird bird, FlappyBird game) {
        bird.setHighscore(bird.getHighscore() - 15);
    }
}
