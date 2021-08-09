package de.fhkiel.aem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import de.fhkiel.aem.utility.ButtonFactory;

public class GameOverScreen implements Screen {

    private final FlappyBird game;
    private final OrthographicCamera camera;
    private final Stage stage;
    private final TextField nameTextField;
    private ImageButton backButton;
    private ImageButton restartButton;
    private ImageButton highscoreButton;
    private final ShapeRenderer shapeRenderer;


    public GameOverScreen(FlappyBird game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Configuration.ScreenWidth, Configuration.ScreenHeight);

        shapeRenderer = new ShapeRenderer();
        stage = new Stage(new FitViewport(Configuration.ScreenWidth, Configuration.ScreenHeight));
        Table table = new Table();
        createButtons();
        table.setFillParent(true);
        Gdx.input.setInputProcessor(stage);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = new BitmapFont(Gdx.files.internal("title-font-export.fnt"));
        labelStyle.fontColor = Color.WHITE;

        Label overLabel = new Label("Game Over!", labelStyle);


        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.font = new BitmapFont(Gdx.files.internal("title-font-export.fnt"));
        textFieldStyle.fontColor = Color.GRAY;

        Pixmap cursorColor = new Pixmap( 3,
                (int) overLabel.getHeight(),
                Pixmap.Format.RGB888);
        cursorColor.setColor(Color.BLACK);
        cursorColor.fill();

        textFieldStyle.cursor = new Image(new Texture(cursorColor)).getDrawable();

        nameTextField = new TextField(game.getPlayerName(), textFieldStyle);

        table.add(overLabel).height(200).top().center().colspan(3).expand();
        table.row();
        table.add(nameTextField).center().colspan(3).maxSize(Gdx.graphics.getWidth() / 2f).fillX();
        table.row();
        table.add(highscoreButton).center().bottom().expand();
        table.add(restartButton).center().bottom().expand();
        table.add(backButton).center().bottom().expand();

        stage.addActor(table);

    }

    /**
     * Create all used Buttons
     */
    private void createButtons(){
        backButton = ButtonFactory.CreateImageButton(Configuration.backImg,
                () -> {
                    game.setScreen(new StartScreen(game));
                    game.setPlayerName(nameTextField.getText());
                    dispose();
                });

        restartButton = ButtonFactory.CreateImageButton(Configuration.startImg,
                () -> {
                    game.setScreen(new PlayScreen(game));
                    game.setPlayerName(nameTextField.getText());
                    dispose();
                });

        highscoreButton = ButtonFactory.CreateImageButton(Configuration.highscoreImg,
                () -> {
                    game.setScreen(new HighscoreScreen(game));
                    game.setPlayerName(nameTextField.getText());
                    dispose();
                });
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        if(stage.getKeyboardFocus() != nameTextField) {
            stage.setKeyboardFocus(nameTextField);
            nameTextField.selectAll();
        }

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(80, 80, 50, 0.01f);
        shapeRenderer.rect(nameTextField.getX(), nameTextField.getY(),
                nameTextField.getWidth(), nameTextField.getHeight());
        shapeRenderer.end();

        game.batch.begin();
        stage.draw();
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
