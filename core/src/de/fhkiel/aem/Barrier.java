package de.fhkiel.aem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class Barrier {


    private Sprite barrierSprite = new Sprite(new Texture("barrier.png"));
    private float speed = 300;
    private float gap = 350;
    private float distance = 700;


    public void render(SpriteBatch game, int posXLast){
        game.draw(barrierSprite, barrierSprite.getX(), barrierSprite.getY(), barrierSprite.getOriginX(), barrierSprite.getOriginY(),
                barrierSprite.getWidth(), barrierSprite.getHeight(),1,1, barrierSprite.getRotation());
        move(posXLast);
    }

    private void move(int barriers){
        barrierSprite.setX(barrierSprite.getX() - Gdx.graphics.getDeltaTime() * speed);

    }

    public Sprite getBarrierSprite() {
        return barrierSprite;
    }

    public float getDistance() {
        return distance;
    }

    public float getGap() {
        return gap;
    }
}
