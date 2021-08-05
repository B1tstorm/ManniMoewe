package de.fhkiel.aem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import de.fhkiel.aem.utility.ButtonFactory;



public class StartScreen implements Screen {

    private final FlappyBird game;
    private final OrthographicCamera camera;
    private final Stage stage;
    private final ImageButton startButton;
    private final ImageButton highscoreButton;
    private final ImageButton optionsButton;
    private ImageButton muteButton;
    private final Table table;
    private final Bird bird;




    public StartScreen(final FlappyBird game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Configuration.ScreenWidth, Configuration.ScreenHeight);

        stage = new Stage();
        bird = new Bird(50, 250 );

        table = new Table();
        table.setFillParent(true);
        Gdx.input.setInputProcessor(stage);

        startButton = ButtonFactory.CreateImageButton("start.png",
                () -> {
                    game.setScreen(new Playscreen(game));
                    dispose();
        });
        highscoreButton = ButtonFactory.CreateImageButton("highscore.png",
                () -> {
                    game.setScreen(new HighscoreScreen(game));
                    dispose();
        });
        optionsButton = ButtonFactory.CreateImageButton("optionen.png",
                () -> {
            game.setScreen(new Optionsscreen(game));
            dispose();
        });
        muteButton = ButtonFactory.CreateImageButton("unmute.png", "mute.png", () -> {
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

        muteButton.setProgrammaticChangeEvents(false);

        table.add().expand().colspan(2);
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
        game.batch.draw(bird.birdSprite, bird.birdSprite.getX(), bird.birdSprite.getY() , bird.getBirdWidth() , bird.getBirdWidth());
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
