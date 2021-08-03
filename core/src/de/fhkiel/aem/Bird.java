package de.fhkiel.aem;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;

import java.awt.*;

public class Bird {

    public float xPos;
    public float yPos;
    public Texture bird;

    public Bird(float xPos, float yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
        bird = new Texture("flappy1_up.png");

    }


}
