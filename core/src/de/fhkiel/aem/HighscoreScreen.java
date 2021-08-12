package de.fhkiel.aem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import de.fhkiel.aem.model.HighscoreEntry;
import de.fhkiel.aem.utility.ButtonFactory;

/**
 * The HighscoreScreen where the top 10 highscores are displayed.
 */
public class HighscoreScreen implements Screen {

    final FlappyBird game;
    private final OrthographicCamera camera;
    private final Stage stage;
    private ImageButton easyButton;
    private ImageButton mediumButton;
    private ImageButton hardButton;
    private int shownDifficulty;
    private Table table;


    /**
     * Creates an HighscoreScreen depending on a game.
     * @param game The game
     */
    public HighscoreScreen(final FlappyBird game) {

        this.game = game;
        game.setHighscore(game.getNetworkHandler().getFromServer());

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Configuration.ScreenWidth, Configuration.ScreenHeight);

        stage = new Stage(new FitViewport(Configuration.ScreenWidth, Configuration.ScreenHeight));

        shownDifficulty = game.getDifficulty();

        easyButton = ButtonFactory.CreateImageButton(Configuration.difficulty_easy, true,
                () -> {
                    shownDifficulty = 1;
                    updateTable();
                    easyButton.setChecked(true);
                    mediumButton.setChecked(false);
                    hardButton.setChecked(false);
        });
        mediumButton = ButtonFactory.CreateImageButton(Configuration.difficulty_medium, true,
                () -> {
                    shownDifficulty = 2;
                    updateTable();
                    easyButton.setChecked(false);
                    mediumButton.setChecked(true);
                    hardButton.setChecked(false);
                });
        hardButton = ButtonFactory.CreateImageButton(Configuration.difficulty_hard, true,
                () -> {
                    shownDifficulty = 3;
                    updateTable();
                    easyButton.setChecked(false);
                    mediumButton.setChecked(false);
                    hardButton.setChecked(true);
        });


        easyButton.setProgrammaticChangeEvents(false);
        mediumButton.setProgrammaticChangeEvents(false);
        hardButton.setProgrammaticChangeEvents(false);

        table = new Table();
        table.setFillParent(true);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = new BitmapFont(Gdx.files.internal("title-font-export.fnt"));
        labelStyle.fontColor = Color.WHITE;

        Label highscore = new Label("Highscores: ", labelStyle);
        Label name = new Label("Name", labelStyle);
        Label place = new Label("Platz", labelStyle);
        Label score = new Label("Score", labelStyle);

        Gdx.input.setInputProcessor(stage);

        int padding = game.getDefaultPadding();
        table.pad(padding, padding, padding, padding);

        table.add(highscore).height(100).colspan(3).center();

        table.row();
        table.add(easyButton).fillX();
        table.add(mediumButton).fillX();
        table.add(hardButton).fillX();
        table.row();
        table.add(place).expand();
        table.add(name).expand();
        table.add(score).expand();

        for(int i = 0; i<10; i++) {
            table.row();
            table.add(new Label((i+1) + ".", labelStyle)).expand();
            table.add(new Label("", labelStyle)).expand();
            table.add(new Label("", labelStyle)).expand();
        }

        table.row();
        table.add();
        table.add();
        table.add(ButtonFactory.CreateImageButton(Configuration.back, false,
                () -> {
                    game.setScreen(new StartScreen(game));
                    dispose();
        })).center();

        updateTable();
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

    private void updateTable() {
        switch(shownDifficulty) {
            case 1:
                updateLabel(game.getHighscore().easy);
                break;
            case 2:
                updateLabel(game.getHighscore().medium);
                break;
            case 3:
                updateLabel(game.getHighscore().hard);
                break;
        }
    }

    private void updateLabel(Array<HighscoreEntry> array) {
         Array<Cell> cells = table.getCells();
         for(int i = 8, j = 0; i <= 35; i +=3, j++) {
             ((Label)cells.get(i).getActor()).setText(array.size > j ? array.get(j).name : "-");
             ((Label)cells.get(i + 1).getActor()).setText(array.size > j ? array.get(j).highscore : 0);
         }
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
        game.batch.end();

        stage.draw();
        stage.act();
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
