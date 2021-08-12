package de.fhkiel.aem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import de.fhkiel.aem.utility.ButtonFactory;

/**
 * The OptionsScreen where you can change the difficulty and the skin of the Bird.
 */
public class OptionsScreen implements Screen {

    final FlappyBird game;
    private final OrthographicCamera camera;
    private final Stage stage;
    private Label difficultyLabel, charLabel, soundLabel;
    private ImageButton backButton;
    private ImageButton easyButton;
    private ImageButton mediumButton;
    private ImageButton hardButton;
    private ImageButton skinButton1;
    private ImageButton skinButton2;
    private ImageButton skinButton3;
    private ImageButton creditButton;
    private Skin skin;
    private Slider soundSlider;


    /**
     * Creates an OptionsScreen depending on a game.
     * @param game The game
     */
    public OptionsScreen(FlappyBird game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Configuration.ScreenWidth, Configuration.ScreenHeight);

        stage = new Stage(new FitViewport(Configuration.ScreenWidth, Configuration.ScreenHeight));
        Table table = new Table();
        createButtons();
        table.setFillParent(true);
        Gdx.input.setInputProcessor(stage);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = new BitmapFont(Gdx.files.internal("title-font-export.fnt"));
        labelStyle.fontColor = Color.WHITE;

        charLabel = new Label("Character", labelStyle);
        difficultyLabel = new Label("Difficulty", labelStyle);
        soundLabel = new Label("Sound", labelStyle);

        skin = new Skin(Gdx.files.internal("uiskin.json"));
        soundSlider = new Slider(0, 1, 0.01f, false, skin);
        soundSlider.setValue(0.5f);
        soundSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setMusicVolume(soundSlider.getValue());
            }
        });

        Label headerLabel = new Label("Options", labelStyle);

        table.add(headerLabel).height(200).center().colspan(3);
        table.row();
        table.add(difficultyLabel).center().colspan(3);
        table.row();
        table.add(easyButton).center().fillX();
        table.add(mediumButton).center().fillX();
        table.add(hardButton).center().fillX();
        table.row();
        table.add(charLabel).center().colspan(3);
        table.row();
        table.add(skinButton1).center().fillX();
        table.add(skinButton2).center().fillX();
        table.add(skinButton3).center().fillX();
        table.row();
        table.add(soundLabel).center().colspan(3);
        table.row();
        table.add(soundSlider).center().colspan(3).width(Gdx.graphics.getWidth() / 4);
        table.row();
        table.add(backButton).expand().fillX();
        table.add().expand();
        table.add(creditButton).expand().fillX();

        stage.addActor(table);

        switch(game.getDifficulty()) {
            case 1:
                easyButton.setChecked(true);
                break;
            case 2:
                mediumButton.setChecked(true);
                break;
            case 3:
                hardButton.setChecked(true);
                break;
            default:
                break;
        }
    }

    /**
     * Creates all shown Buttons
     */
    private void createButtons(){
        backButton = ButtonFactory.CreateImageButton(Configuration.back, false,
                () -> {
                    game.setScreen(new StartScreen(game));
                    dispose();
                });

        easyButton = ButtonFactory.CreateImageButton(Configuration.difficulty_easy, true,
                () -> {
                    game.setDifficulty(1);
                    easyButton.setChecked(true);
                    mediumButton.setChecked(false);
                    hardButton.setChecked(false);
                });

        mediumButton = ButtonFactory.CreateImageButton(Configuration.difficulty_medium, true,
                () -> {
                    game.setDifficulty(2);
                    easyButton.setChecked(false);
                    mediumButton.setChecked(true);
                    hardButton.setChecked(false);
                });

        hardButton = ButtonFactory.CreateImageButton(Configuration.difficulty_hard, true,
                () -> {
                    game.setDifficulty(3);
                    easyButton.setChecked(false);
                    mediumButton.setChecked(false);
                    hardButton.setChecked(true);
                });

        skinButton1 = ButtonFactory.CreateImageButton(Configuration.manny_stareImg, Configuration.manny_stareImg,
                () -> {
                });

        skinButton2 = ButtonFactory.CreateImageButton(Configuration.manny_heyImg, Configuration.manny_heyImg,
                () -> {
                });

        skinButton3 = ButtonFactory.CreateImageButton(Configuration.manny_heyImg, Configuration.manny_heyImg,
                () -> {

                });
        creditButton = ButtonFactory.CreateImageButton(Configuration.credit, false,
                () -> {
                    game.setScreen(new CreditScreen(game));
                });

        easyButton.setProgrammaticChangeEvents(false);
        mediumButton.setProgrammaticChangeEvents(false);
        hardButton.setProgrammaticChangeEvents(false);
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.background.renderBackground();
        game.background.renderForeground();
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
