package de.fhkiel.aem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.ScreenUtils;
import de.fhkiel.aem.utility.ButtonFactory;

public class GameOverScreen implements Screen {

    final FlappyBird game;
    private final OrthographicCamera camera;
    private final Stage stage;
    private final Table table;
    private Label overLabel;
    private Label.LabelStyle labelStyle;
    private TextField nameTextField;
    private TextField.TextFieldStyle textFieldStyle;
    private ImageButton backButton;
    private ImageButton restartButton;
    private ImageButton highscoreButton;


    public GameOverScreen(FlappyBird game, float score) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Configuration.ScreenWidth, Configuration.ScreenHeight);

        stage = new Stage();
        table = new Table();
        createButtons();
        table.setFillParent(true);
        Gdx.input.setInputProcessor(stage);

        labelStyle = new Label.LabelStyle();
        labelStyle.font = new BitmapFont(Gdx.files.internal("title-font-export.fnt"));
        labelStyle.fontColor = Color.WHITE;

        textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.font = new BitmapFont(Gdx.files.internal("title-font-export.fnt"));
        textFieldStyle.fontColor = Color.GRAY;

        overLabel = new Label("Game Over!", labelStyle);
        nameTextField = new TextField("Enter Name",textFieldStyle);


        table.add(overLabel).height(200).top().center().colspan(3).expand();
        table.row();
        table.add(nameTextField).center().colspan(3).maxSize(Gdx.graphics.getWidth() / 2).fillX();
        table.row();
        table.add(highscoreButton).center().bottom().expand();
        table.add(restartButton).center().bottom().expand();
        table.add(backButton).center().bottom().expand();

        stage.addActor(table);

    }

    private void createButtons(){
        backButton = ButtonFactory.CreateImageButton("back.png",
                () -> {
                    game.setScreen(new StartScreen(game));
                    dispose();
                });

        restartButton = ButtonFactory.CreateImageButton("start.png",
                () -> {
                    game.setScreen(new Playscreen(game));
                });

        highscoreButton = ButtonFactory.CreateImageButton("highscore.png",
                () -> {
                    game.setScreen(new HighscoreScreen(game));
                });
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        if(nameTextField.isTouchFocusListener())
        {
            nameTextField.selectAll();
        }
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
