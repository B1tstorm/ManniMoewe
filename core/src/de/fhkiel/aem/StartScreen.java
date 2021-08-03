package de.fhkiel.aem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;

public class StartScreen implements Screen {

    final FlappyBird game;
    OrthographicCamera camera;
    Stage stage;
    ImageButton optionsButton;
    ImageButton highscorebutton;

    public StartScreen(final FlappyBird game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Configuration.ScreenWidth, Configuration.ScreenHeight);
        createButtons();

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        highscorebutton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("highscore.png")))));
        highscorebutton.setPosition((1920/2) - 100, 200);
        stage.addActor(highscorebutton);
        highscorebutton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                System.out.println("Button highscorebutton Pressed");
//                game.setScreen(new Playscreen(game));
//                dispose();
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

        if (Gdx.input.isTouched()) {
            // game.setScreen(new GameScreen(game));
        }
    }

    public void createButtons() {

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);;

        optionsButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture("optionen.png"))));
        optionsButton.setPosition(Gdx.graphics.getWidth() - optionsButton.getWidth() - 50, 20);
        optionsButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                System.out.println("Button Pressed");
            }
        });
        stage.addActor(optionsButton);

        highscorebutton = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("highscore.png")))));
        highscorebutton.setPosition((1920/2) - 100, 200);
        highscorebutton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                System.out.println("Button Pressed");
            }
        });
        stage.addActor(highscorebutton);
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
