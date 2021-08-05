package de.fhkiel.aem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;


public class Barrier {


    private Sprite barrierSprite;
    private float speed = 300;
    private float gap = 350;
    private float distance = 700;
    private float wealth = 0.5f;
    private Rectangle hitbox;
    private int hitboxKorrektur;

    public Barrier(float x, float y, String imagePath){
        barrierSprite = new Sprite(new Texture(imagePath));
        barrierSprite.setX(x);
        barrierSprite.setY(y);

        hitbox = new Rectangle(barrierSprite.getX() + hitboxKorrektur, barrierSprite.getY(),
                barrierSprite.getWidth() - hitboxKorrektur, barrierSprite.getHeight());
    }

    public void render(SpriteBatch game, int posXLast){
        game.draw(barrierSprite, barrierSprite.getX(), barrierSprite.getY(), barrierSprite.getOriginX(), barrierSprite.getOriginY(),
                barrierSprite.getWidth(), barrierSprite.getHeight(),1,1, barrierSprite.getRotation());
        move(posXLast);
    }

    private void move(int barriers){
        barrierSprite.setX(barrierSprite.getX() - Gdx.graphics.getDeltaTime() * speed);
        hitbox.setPosition(hitbox.getX() - Gdx.graphics.getDeltaTime() * speed, hitbox.getY());
    }

    public Sprite getBarrierSprite() {
        return barrierSprite;
    }

    public float getDistance() {
        return distance;
    }

    public float getGap() { return gap;}

    public Rectangle getHitbox(){ return hitbox;}

    public float getWealth() {
        return wealth;
    }

    public void setWealth(float wealth) {
        this.wealth = wealth;
    }
}
