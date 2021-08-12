package de.fhkiel.aem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import de.fhkiel.aem.model.HighscoreEntry;
import de.fhkiel.aem.utility.ButtonFactory;

public class GameOverScreen implements Screen {

    private final FlappyBird game;
    private final OrthographicCamera camera;
    private final Stage stage;
    private final TextField nameTextField;
    private ImageButton backButton;
    private ImageButton restartButton;
    private ImageButton highscoreButton;
    private int score;
    private long createTime;


    public GameOverScreen(FlappyBird game, float score) {
        this.game = game;
        this.score = (int)score;
        createTime = TimeUtils.nanoTime();

        Texture highscoreInputImg = new Texture(Gdx.files.internal(Configuration.highscoreInputImg));
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Configuration.ScreenWidth, Configuration.ScreenHeight);

        stage = new Stage(new FitViewport(Configuration.ScreenWidth, Configuration.ScreenHeight));
        Table table = new Table();
        createButtons();
        table.setFillParent(true);
        Gdx.input.setInputProcessor(stage);
        
        Label overLabel = new Label("Game Over!", game.labelStyle);

        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.font = game.font;
        textFieldStyle.fontColor= Color.valueOf("FFFFFF");

        Pixmap cursorColor = new Pixmap(3,
                (int) overLabel.getHeight(),
                Pixmap.Format.RGB888);
        cursorColor.setColor(Color.BLACK);
        cursorColor.fill();

        textFieldStyle.cursor = new Image(new Texture(cursorColor)).getDrawable();

        //das Holzbrett als Hintergrund für den Eingabefeld setzten
        textFieldStyle.background = new SpriteDrawable(new Sprite(highscoreInputImg));
        nameTextField = new TextField(game.getPlayerName(), textFieldStyle);
        nameTextField.setAlignment(Align.center);
        nameTextField.setMaxLength(30);

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
    private void createButtons() {
        backButton = ButtonFactory.CreateImageButton(Configuration.back, false,
                () -> {
                    game.setHighscore(game.getNetworkHandler().getFromServer());
                    game.setPlayerName(nameTextField.getText());
                    game.getHighscore().addHighscore(game.getDifficulty() ,new HighscoreEntry(game.getPlayerName(), score));
                    game.getNetworkHandler().sendToServer(game.getHighscore());
                    game.setScreen(new StartScreen(game));
                    dispose();
                });

        restartButton = ButtonFactory.CreateImageButton(Configuration.start, false,
                () -> {
                    game.setHighscore(game.getNetworkHandler().getFromServer());
                    game.setPlayerName(nameTextField.getText());
                    game.getHighscore().addHighscore(game.getDifficulty() ,new HighscoreEntry(game.getPlayerName(), score));
                    game.getNetworkHandler().sendToServer(game.getHighscore());
                    game.setScreen(new PlayScreen(game));
                    dispose();
                });

        highscoreButton = ButtonFactory.CreateImageButton(Configuration.highscore, false,
                () -> {
                    game.setHighscore(game.getNetworkHandler().getFromServer());
                    game.setPlayerName(nameTextField.getText());
                    game.getHighscore().addHighscore(game.getDifficulty() ,new HighscoreEntry(game.getPlayerName(), score));
                    game.getNetworkHandler().sendToServer(game.getHighscore());
                    game.setScreen(new HighscoreScreen(game));
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

        setFocus();

        game.batch.begin();
        game.batch.end();

        stage.draw();
        stage.act();
    }

    private void setFocus() {
        if (TimeUtils.nanoTime() - createTime > 750000000L && stage.getKeyboardFocus() != nameTextField) {
            stage.setKeyboardFocus(nameTextField);
            nameTextField.selectAll();
        }
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
