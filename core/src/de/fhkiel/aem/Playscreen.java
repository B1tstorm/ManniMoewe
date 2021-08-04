package de.fhkiel.aem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Playscreen implements Screen {

    final FlappyBird game;
    private final Bird bird;
    private Array<Barrier> barriers = new Array<>();
    OrthographicCamera camera;
    Stage stage;

    public Playscreen(FlappyBird game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Configuration.ScreenWidth, Configuration.ScreenHeight);

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        bird = new Bird(50, 700 );
        createBarriers();

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.5f, 1);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        for(Barrier barrier : barriers){
            barrier.render(game.batch, barriers.size / 2);
        }
        game.batch.draw(bird.bird, bird.xPos, bird.yPos , 200 , 150);
        game.font.draw(game.batch, "Welcome the Play Screen .", 500, 500);
        stage.draw();
        game.batch.end();
    }

    public void createBarriers(){
        for(int i = 0; i < 10; i++) {
            Barrier b = new Barrier();
            int randomNum = ThreadLocalRandom.current().nextInt(
                    Gdx.graphics.getHeight() - b.getBarrierTex().getHeight(), Gdx.graphics.getHeight());
            b.setPosX(Gdx.graphics.getWidth() + (b.getDistance() * i));
            b.setPosY(randomNum);
            barriers.add(b);
            Barrier b2 = new Barrier();
            b2.getBarrierSprite().setRotation(180f);
            b2.setPosX(Gdx.graphics.getWidth() + (b.getDistance() * i));
            b2.setPosY(randomNum - b2.getBarrierTex().getHeight() - b2.getGap());
            barriers.add(b2);
        }
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
