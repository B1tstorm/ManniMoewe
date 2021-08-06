package de.fhkiel.aem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import de.fhkiel.aem.utility.ButtonFactory;
import jdk.nashorn.internal.runtime.regexp.joni.Config;

import java.util.concurrent.locks.Condition;


public class StartScreen implements Screen {

    private final FlappyBird game;
    private final OrthographicCamera camera;
    private final Stage stage;
    private final ImageButton startButton;
    private final ImageButton highscoreButton;
    private final ImageButton optionsButton;
    private ImageButton muteButton;
    private ImageButton exitButton;
    private final Table table;



    public StartScreen(final FlappyBird game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Configuration.ScreenWidth, Configuration.ScreenHeight);

        stage = new Stage();

        table = new Table();
        table.setFillParent(true);
        Gdx.input.setInputProcessor(stage);

        startButton = ButtonFactory.CreateImageButton(Configuration.startImg,
                () -> {
                    game.setScreen(new Playscreen(game));
                    dispose();
        });
        highscoreButton = ButtonFactory.CreateImageButton(Configuration.highscoreImg,
                () -> {
                    game.setScreen(new HighscoreScreen(game));
                    dispose();
        });
        optionsButton = ButtonFactory.CreateImageButton(Configuration.optionImg,
                () -> {
            game.setScreen(new Optionsscreen(game));
            dispose();
        });
        muteButton = ButtonFactory.CreateImageButton(Configuration.unmuteImg, Configuration.muteImg, () -> {
            if(game.musik){
                game.musik = false;
                game.kielMusik.setVolume(0f);
                game.meerMoeweMusik.pause();
                muteButton.setChecked(true);

            }   else {
                game.musik = true;
                game.kielMusik.setVolume(0.5f);
                game.meerMoeweMusik.play();
                muteButton.setChecked(false);
            }
        });
        exitButton = ButtonFactory.CreateImageButton(Configuration.exitImg,
                () -> {
                    Gdx.app.exit();
        });

        muteButton.setProgrammaticChangeEvents(false);

        table.add(exitButton).expand().left().top();

        table.add().expand();

        table.add(muteButton).expand().right().top();

        table.row();
        table.add(startButton).center().fillX();
        table.add(highscoreButton).center().fillX();
        table.add(optionsButton).center().fillX();

        stage.addActor(table);
    }

    @Override
    public void show() {
        if(game.musik) {
            game.meerMoeweMusik.play();
        }
        if(!game.musik) {
            game.kielMusik.setVolume(0f);
            muteButton.setChecked(true);
        }

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.5f, 1);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
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
