package de.fhkiel.aem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import de.fhkiel.aem.utility.ButtonFactory;

public class Optionsscreen implements Screen {

    final FlappyBird game;
    OrthographicCamera camera;
    Stage stage;
    private final Table table;
    private Label headerLabel;
    private ImageButton backButton;
    private ImageButton easyButton;
    private ImageButton mediumButton;
    private ImageButton hardButton;
    private ImageButton skinButton1;
    private ImageButton skinButton2;
    private ImageButton skinButton3;

    public Optionsscreen(FlappyBird game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Configuration.ScreenWidth, Configuration.ScreenHeight);

        stage = new Stage();
        table = new Table();
        createButtons();
        table.setFillParent(true);
        Gdx.input.setInputProcessor(stage);

        headerLabel = new Label();

        table.add().expand().colspan(3);
        table.add(headerLabel);
        table.row();
        table.add(easyButton).center().fillX();
        table.add(mediumButton).center().fillX();
        table.add(hardButton).center().fillX();
        table.row();
        table.add(skinButton1).center().fillX();
        table.add(skinButton2).center().fillX();
        table.add(skinButton3).center().fillX();

        table.row();

        table.add(backButton).expand().colspan(3);

        stage.addActor(table);

    }

    private void createButtons(){
        backButton = ButtonFactory.CreateImageButton("start.png",
                () -> {
                    game.setScreen(new StartScreen(game));
                    dispose();
                });

        easyButton = ButtonFactory.CreateImageButton("start.png",
                () -> {
                    game.setScreen(new Playscreen(game));
                    dispose();
                });

        mediumButton = ButtonFactory.CreateImageButton("start.png",
                () -> {
                    game.setScreen(new Playscreen(game));
                    dispose();
                });

        hardButton = ButtonFactory.CreateImageButton("start.png",
                () -> {
                    game.setScreen(new Playscreen(game));
                    dispose();
                });

        skinButton1 = ButtonFactory.CreateImageButton("start.png",
                () -> {
                    game.setScreen(new Playscreen(game));
                    dispose();
                });

        skinButton2 = ButtonFactory.CreateImageButton("start.png",
                () -> {
                    game.setScreen(new Playscreen(game));
                    dispose();
                });

        skinButton3 = ButtonFactory.CreateImageButton("start.png",
                () -> {
                    game.setScreen(new Playscreen(game));
                    dispose();
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
