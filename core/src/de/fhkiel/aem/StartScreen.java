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
        bird = new Bird(50, 250 );

        Table table = new Table();
        table.setFillParent(true);
        Gdx.input.setInputProcessor(stage);

        startButton = ButtonFactory.CreateImageButton(Configuration.startImg, Configuration.startImgPressed, Configuration.startImg,
                () -> {
                    game.setScreen(new PlayScreen(game));
                    dispose();
        });
        highscoreButton = ButtonFactory.CreateImageButton(Configuration.highscoreImg, Configuration.highscoreImgPressed, Configuration.highscoreImg,
                () -> {
                    game.setScreen(new HighscoreScreen(game));
                    dispose();
        });
        optionsButton = ButtonFactory.CreateImageButton(Configuration.optionImg, Configuration.optionImgPressed, Configuration.optionImgPressed,
                () -> {
            game.setScreen(new OptionsScreen(game));
            dispose();
        });
        muteButton = ButtonFactory.CreateImageButton(Configuration.unmuteImg, Configuration.muteImg, () -> {
            if(game.musicShouldPlay){
                game.musicShouldPlay = false;
                game.kielMusic.setVolume(0f);
                game.oceanSeagullMusic.pause();
                muteButton.setChecked(true);
            }   else {
                game.musicShouldPlay = true;
                game.kielMusic.setVolume(0.5f);
                game.oceanSeagullMusic.play();
                muteButton.setChecked(false);
            }
        });
        exitButton = ButtonFactory.CreateImageButton(Configuration.exitImg, Configuration.exitImgPressed, Configuration.exitImg,
                () -> {
                    Gdx.app.exit();
                    dispose();
        });

        muteButton.setProgrammaticChangeEvents(false);

//        ImageButton.ImageButtonStyle buttonStyle = new ImageButton.ImageButtonStyle();
//        buttonStyle. = buttonStyle.checkedOver;

//        ImageButton imageButton = new ImageButton();


        table.add(exitButton).expand().left().top();

        table.add().expand();

        table.add(muteButton).expand().right().top();

        table.row();
        table.add(startButton).center().fillX();
        table.add(highscoreButton).center().fillX();
        table.add(optionsButton).center().fillX();

        stage.addActor(table);

        game.background = new Background(game.batch);
    }

    @Override
    public void show() {
        if(game.musicShouldPlay) {
            game.oceanSeagullMusic.play();
        }
        if(!game.musicShouldPlay) {
            game.kielMusic.setVolume(0f);
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
        game.background.renderForeground();

        game.batch.draw(bird.getBirdSprite(), bird.getBirdSprite().getX(), bird.getBirdSprite().getY() , bird.getBirdWidth() , bird.getBirdWidth());

        stage.draw();
        game.batch.end();

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
