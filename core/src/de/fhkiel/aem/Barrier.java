package de.fhkiel.aem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;


public class Barrier {

    private Texture barrierTex = new Texture("barrier.png");
    private Sprite barrierSprite = new Sprite(barrierTex);
    private float posX, posY;
    private float speed = 500;
    private float gap = 250;
    private float distance = 500;


    public void render(FlappyBird game, int posXLast){
        game.batch.draw(barrierSprite, posX, posY, barrierSprite.getOriginX(), barrierSprite.getOriginY(),
                barrierSprite.getWidth(), barrierSprite.getHeight(),1,1, barrierSprite.getRotation());
        move(posXLast);
    }

    private void move(int barriers){
        posX -= Gdx.graphics.getDeltaTime() * speed;
        if(posX < (0 - barrierTex.getWidth())){
            posX += barriers * distance;
        }

    }

    public Texture getBarrierTex() {
        return barrierTex;
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

    public float getPosX() {
        return posX;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }
}
