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
    private final Table table;

    public StartScreen(FlappyBird game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Configuration.ScreenWidth, Configuration.ScreenHeight);

        stage = new Stage();
        table = new Table();
        table.setFillParent(true);
        Gdx.input.setInputProcessor(stage);

        startButton = ButtonFactory.CreateImageButton("start.png", () -> System.out.println("Play Button pressed"));
        highscoreButton = ButtonFactory.CreateImageButton("highscore.png", () -> System.out.println("Highscore  Button pressed"));
        optionsButton = ButtonFactory.CreateImageButton("optionen.png", () -> System.out.println("Options  Button pressed"));

        table.add().expand().colspan(3);
        table.row();
        table.add(startButton).center().fillX();
        table.add(highscoreButton).center().fillX();
        table.add(optionsButton).center().fillX();

        stage.addActor(table);
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
