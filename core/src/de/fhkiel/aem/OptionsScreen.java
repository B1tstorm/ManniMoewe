package de.fhkiel.aem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import de.fhkiel.aem.utility.ButtonFactory;

/**
 * The OptionsScreen where you can change the difficulty and the skin of the Bird.
 */
public class OptionsScreen implements Screen {

    final FlappyBird game;
    private final OrthographicCamera camera;
    private final Stage stage;
    private ImageButton backButton;
    private ImageButton easyButton;
    private ImageButton mediumButton;
    private ImageButton hardButton;
    private ImageButton skinButton1;
    private ImageButton skinButton2;
    private ImageButton skinButton3;


    /**
     * Creates an OptionsScreen depending on a game.
     * @param game The game
     */
    public OptionsScreen(FlappyBird game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Configuration.ScreenWidth, Configuration.ScreenHeight);

        stage = new Stage();
        Table table = new Table();
        createButtons();
        table.setFillParent(true);
        Gdx.input.setInputProcessor(stage);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = new BitmapFont(Gdx.files.internal("title-font-export.fnt"));
        labelStyle.fontColor = Color.WHITE;

        Label headerLabel = new Label("Options", labelStyle);

        table.add(headerLabel).height(200).center().colspan(3);
        table.row();
        table.add(easyButton).center().fillX();
        table.add(mediumButton).center().fillX();
        table.add(hardButton).center().fillX();
        table.row();
        table.add(skinButton1).height(180).center().fillX();
        table.add(skinButton2).center().fillX();
        table.add(skinButton3).center().fillX();
        table.row();
        table.add(backButton).expand().colspan(3);

        stage.addActor(table);

    }

    private void createButtons(){
        backButton = ButtonFactory.CreateImageButton("back.png",
                () -> {
                    game.setScreen(new StartScreen(game));
                    dispose();
                });

        easyButton = ButtonFactory.CreateImageButton("difficulty-easy.png",
                () -> {
                });

        mediumButton = ButtonFactory.CreateImageButton("difficulty-medium.png",
                () -> {

                });

        hardButton = ButtonFactory.CreateImageButton("difficulty-hard.png",
                () -> {

                });

        skinButton1 = ButtonFactory.CreateImageButton("flappy1_up.png",
                () -> {
                });

        skinButton2 = ButtonFactory.CreateImageButton("flappy1_mid.png",
                () -> {

                });

        skinButton3 = ButtonFactory.CreateImageButton("flappy1_down.png",
                () -> {

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
