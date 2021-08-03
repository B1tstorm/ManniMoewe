package de.fhkiel.aem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;

public class StartScreen implements Screen {

    final FlappyBird game;
    OrthographicCamera camera;
    private final Stage stage;
    private ImageButton startButton;
    private ImageButton highscorebutton;

    public StartScreen(FlappyBird game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Configuration.ScreenWidth, Configuration.ScreenHeight);

        stage = new Stage();
        createButtons();
        stage.addActor(startButton);
    }

    private void createButtons() {
        Drawable startImage = new TextureRegionDrawable(
                new TextureRegion(new Texture("start.png")));
        startButton = new ImageButton(startImage);

        startButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                System.out.println("Start Button Pressed");
            }
        });

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        highscorebutton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("highscore.png")))));
        highscorebutton.setPosition((1920/2) - 100, 200);
        stage.addActor(highscorebutton);
        highscorebutton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                System.out.println("Button Pressed");
            }
        });

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
        game.font.draw(game.batch, "Welcome the best Flappy Bird Game ever.", Configuration.ScreenWidth / 2, 500);
        game.font.draw(game.batch, "PRESS ANY KEY TO START", Configuration.ScreenWidth / 2, 450);
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

    }
}
