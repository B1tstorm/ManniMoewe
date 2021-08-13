package de.fhkiel.aem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
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
    private ImageButton skinButton1 = new ImageButton(new ImageButton.ImageButtonStyle());
    private ImageButton skinButton2 = new ImageButton(new ImageButton.ImageButtonStyle());
    private ImageButton creditButton;
    private Slider.SliderStyle sliderStyle;
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

        charLabel = new Label("Character", game.labelStyle);
        difficultyLabel = new Label("Difficulty", game.labelStyle);
        soundLabel = new Label("Sound", game.labelStyle);

        sliderStyle = new Slider.SliderStyle();
        sliderStyle.background = new TextureRegionDrawable(new Texture(Configuration.slider_backgroundImg));
        sliderStyle.knob = new TextureRegionDrawable(new Texture(Configuration.slider_knobImg));
        soundSlider = new Slider(0, 1, 0.01f, false, sliderStyle);
        soundSlider.setValue(0.5f);
        soundSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setMusicVolume(soundSlider.getValue());
            }
        });

        Label headerLabel = new Label("Options", game.labelStyleHeadline);

        int padding = game.getDefaultPadding();
        table.pad(padding, padding, padding, padding);

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
        table.add().center().fillX();
        table.add(skinButton2).center().fillX();
        table.row();
        table.add(soundLabel).center().colspan(3);
        table.row();
        table.add(soundSlider).center().colspan(3).width(Gdx.graphics.getWidth() / 4).fillY();
        table.row();
        table.add(creditButton).expand().fillX().bottom();
        table.add().expand();
        table.add(backButton).expand().fillX().bottom();

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

        skinButton1 = ButtonFactory.CreateImageButton(Configuration.manniBtn, true,
                () -> {
                    game.setCharManniActive(true);
                    game.setCharSpaceManniActive(false);
                    skinButton2.setChecked(false);
                    skinButton1.setChecked(true);
                });

        if(game.isFoundEasteregg()) {
            skinButton2 = ButtonFactory.CreateImageButton(Configuration.manniSpaceBtn, true,
                    () -> {
                        if (game.isFoundEasteregg()) {
                            game.setCharSpaceManniActive(true);
                            game.setCharManniActive(false);
                            skinButton1.setChecked(false);
                            skinButton2.setChecked(true);
                        }
                    });
        }
        else{
            skinButton2 = ButtonFactory.CreateImageButton(Configuration.manniSpaceLocked, Configuration.manniSpaceLocked,
                    () -> {
                    });
        }

        creditButton = ButtonFactory.CreateImageButton(Configuration.credit, false,
                () -> {
                    game.setScreen(new CreditScreen(game));
                });

        skinButton1.setChecked(game.isCharManniActive());
        skinButton2.setChecked(game.isCharSpaceManniActive());

        easyButton.setProgrammaticChangeEvents(false);
        mediumButton.setProgrammaticChangeEvents(false);
        hardButton.setProgrammaticChangeEvents(false);
        skinButton1.setProgrammaticChangeEvents(false);
        skinButton2.setProgrammaticChangeEvents(false);
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
