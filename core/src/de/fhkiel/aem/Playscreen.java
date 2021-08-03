package de.fhkiel.aem;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class Playscreen implements Screen {
    final FlappyBird game;
    OrthographicCamera camera;

    public Playscreen(FlappyBird game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Configuration.ScreenWidth, Configuration.ScreenHeight);
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        game.batch.begin();
        game.font.draw(game.batch, "Welcome the best Flappy Bird Game ever.", Configuration.ScreenWidth / 2, 500);
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
