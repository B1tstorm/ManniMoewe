package de.fhkiel.aem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
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
public class HelpScreen implements Screen {

    final FlappyBird game;
    private final OrthographicCamera camera;
    private final Stage stage;
    private int shownDifficulty;
    private Table table;


    /**
     * Creates an HighscoreScreen depending on a game.
     * @param game The game
     */
    public HelpScreen(final FlappyBird game) {

        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Configuration.ScreenWidth, Configuration.ScreenHeight);

        stage = new Stage(new FitViewport(Configuration.ScreenWidth, Configuration.ScreenHeight));


        table = new Table();
        table.setFillParent(true);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = new BitmapFont(Gdx.files.internal("title-font-export.fnt"));
        labelStyle.fontColor = Color.DARK_GRAY;

        Label helpLabel = new Label("Help", labelStyle);
        Label itemLabel = new Label("Item", labelStyle);
        Label descriptionLabel = new Label("Description", labelStyle);

        Gdx.input.setInputProcessor(stage);


        table.add(helpLabel).height(100).colspan(2).center();
        table.row();
        table.add(itemLabel).expand();
        table.add(descriptionLabel).expand().left();

        table.row();
        table.add(new Image(new Texture(Configuration.manny_straightImg))).maxWidth(100);
        table.add(new Label("Manni: Die geilste Moewe der Welt.", labelStyle)).fillX();

        table.row();
        table.add(new Image(new Texture(Configuration.barrierdownImg))).maxWidth(100);
        table.add(new Label("Hindernis: Bei Beruehrung stribt Manni.", labelStyle)).fillX();

        table.row();
        table.add(new Image(new Texture(Configuration.pommesImg))).maxWidth(100).maxHeight(100);
        table.add(new Label("Item-Pommes: Wenn du drei Pommes gesammelt hast kannst du von einem\n" +
                "Hindernis getroffen werden ohne zu sterben.", labelStyle)).fillX();

        table.row();
        table.add(new Image(new Texture(Configuration.item_miniImg))).maxWidth(100);
        table.add(new Label("Item-Lupe: Verkleinert Manni fuer eine kurze Zeit.", labelStyle)).fillX();

        table.row();
        table.add(new Image(new Texture(Configuration.item_multipyImg))).maxWidth(100);
        table.add(new Label("Item-Multiplikator: Die erhaltenen Punkte werden für eine " +
                "kurze Zeit verdoppelt", labelStyle)).fillX();

        table.row();
        table.add(new Image(new Texture(Configuration.item_slowmoImg))).maxWidth(100);
        table.add(new Label("Item-Schnecke: Manni wird verlangsamt.", labelStyle)).fillX();

        table.row();
        table.add(ButtonFactory.CreateImageButton(Configuration.backImg, Configuration.backImgPressed, Configuration.backImg,
                () -> {
                    game.setScreen(new StartScreen(game));
                    dispose();
        })).colspan(2).center();

        stage.addActor(table);
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
