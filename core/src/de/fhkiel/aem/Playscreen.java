package de.fhkiel.aem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;

public class Playscreen implements Screen {

    final FlappyBird game;
    private final Bird bird;
    private final OrthographicCamera camera;
    private final Stage stage;
    private final Music kielMusik;

    public Playscreen(FlappyBird game) {
        this.game = game;

        kielMusik = Gdx.audio.newMusic(Gdx.files.internal("Kiel_Sound.mp3"));
        kielMusik.setVolume(0.5f);
        kielMusik.setLooping(true);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Configuration.ScreenWidth, Configuration.ScreenHeight);

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        bird = new Bird(50, 700 );
    }


    @Override
    public void show() {
        kielMusik.play();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.5f, 1);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(bird.bird, bird.xPos, bird.yPos , 200 , 150);

        game.font.draw(game.batch, "Welcome the Play Screen .", 500, 500);
        stage.draw();
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
        kielMusik.dispose();
    }
}
