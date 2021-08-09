package de.fhkiel.aem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;

public abstract class Item {
    private final Sprite itemSprite;
    private final Circle hitbox;
    private final int radius = 25;
    public static float itemspeed = 300;

    public Item(float xPos, float yPos, String itemTexture) {
        itemSprite = new Sprite(new Texture(Gdx.files.internal(itemTexture)));

        itemSprite.setX(xPos);
        itemSprite.setY(yPos);

        hitbox = new Circle(itemSprite.getX(), itemSprite.getY(), radius);
        hitbox.setPosition(itemSprite.getX(), itemSprite.getY());
    }


    public void render(SpriteBatch batch){
        batch.draw(itemSprite, itemSprite.getX(), itemSprite.getY(), itemSprite.getOriginX(), itemSprite.getOriginY(),
                getRadius()*2, getRadius()*2,1,1, itemSprite.getRotation());
    }

    public void move() {
        hitbox.setPosition(itemSprite.getX() + radius, itemSprite.getY() + radius);
        float movement = itemspeed * Gdx.graphics.getDeltaTime();
        itemSprite.setX(itemSprite.getX() - movement);
    }

    public void collide(Bird bird){

    }

    public Sprite getItemSprite() {
        return itemSprite;
    }

    public Circle getHitbox() {
        return hitbox;
    }

    public int getRadius() {
        return radius;
    }


}
