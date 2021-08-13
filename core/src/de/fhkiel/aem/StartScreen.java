package de.fhkiel.aem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import de.fhkiel.aem.utility.ButtonFactory;


/**
 * The StartingScreen which is shown at the start and acts as an main menu.
 */
public class StartScreen implements Screen {

    private final FlappyBird game;
    private final OrthographicCamera camera;
    private final Stage stage;
    private final ImageButton startButton;
    private final ImageButton highscoreButton;
    private final ImageButton optionsButton;
    private final ImageButton helpButton;
    private ImageButton muteButton;
    private ImageButton exitButton;
    private final Bird bird;

    protected Viewport viewport;

    /**
     * Creates an StartScreen depending on a game.
     * @param game The game
     */
    public StartScreen(final FlappyBird game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Configuration.ScreenWidth, Configuration.ScreenHeight);

        stage = new Stage(new FitViewport(Configuration.ScreenWidth, Configuration.ScreenHeight));
        bird = new Bird(50, 250, game.isCharSpaceManniActive());


        Table table = new Table();
        table.setFillParent(true);

        int padding = game.getDefaultPadding();
        table.pad(padding, padding, padding, padding);

        Gdx.input.setInputProcessor(stage);

        startButton = ButtonFactory.CreateImageButton(Configuration.start, false,
                () -> {
                    game.setScreen(new PlayScreen(game));
                    dispose();
        });
        highscoreButton = ButtonFactory.CreateImageButton(Configuration.highscore, false,
                () -> {
                    game.setScreen(new HighscoreScreen(game));
                    dispose();
        });
        optionsButton = ButtonFactory.CreateImageButton(Configuration.option, false,
                () -> {
            game.setScreen(new OptionsScreen(game));
            dispose();
        });
        helpButton = ButtonFactory.CreateImageButton(Configuration.help, false,
                () -> {
                    game.setScreen(new HelpScreen(game));
                    dispose();
                });
        muteButton = ButtonFactory.CreateImageButton(Configuration.unmuteImg, Configuration.muteImg, () -> {
            if(game.musicShouldPlay){
                game.musicShouldPlay = false;
                game.kielMusic.setVolume(0f);
                game.spaceMusic.setVolume(0f);
                game.oceanSeagullMusic.setVolume(0f);
                muteButton.setChecked(true);
            }   else {
                game.musicShouldPlay = true;
                game.kielMusic.setVolume(0.5f);
                game.oceanSeagullMusic.setVolume(0.5f);
                game.spaceMusic.setVolume(0.5f);
                muteButton.setChecked(false);
            }
        });
        exitButton = ButtonFactory.CreateImageButton(Configuration.exit, false,
                () -> {
                    Gdx.app.exit();
                    dispose();
        });

        muteButton.setProgrammaticChangeEvents(false);


        table.add(exitButton).left().top();

        table.add().fillX();

        table.add(helpButton).right().top();
        table.row();
        table.add(muteButton).colspan(3).expand().top().right();

        table.row();
        table.add(startButton).center().fillX();
        table.add(highscoreButton).center().fillX();
        table.add(optionsButton).center().fillX();

        stage.addActor(table);

        game.background = new NormalBackground(game.batch);
    }

    @Override
    public void show() {
        if(game.musicShouldPlay) {
            game.oceanSeagullMusic.play();
            game.spaceMusic.pause();
            game.kielMusic.pause();
        }
        if(!game.musicShouldPlay) {
            game.kielMusic.pause();
            game.spaceMusic.pause();
            muteButton.setChecked(true);
        }

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();

        game.background.renderBackground();

        game.batch.draw(bird.getBirdSprite(), bird.getBirdSprite().getX(), bird.getBirdSprite().getY() , bird.getBirdWidth() , bird.getBirdWidth());

        game.batch.end();

        stage.draw();
        stage.act();
        game.background.move();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
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
