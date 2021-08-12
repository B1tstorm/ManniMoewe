package de.fhkiel.aem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import de.fhkiel.aem.utility.ButtonFactory;

/**
 * The HighscoreScreen where the top 10 highscores are displayed.
 */
public class HelpScreen implements Screen {

    final FlappyBird game;
    private final OrthographicCamera camera;
    private final Stage stage;
    private int shownDifficulty;
    private Table table, buttonTable;


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

        buttonTable = new Table();
        buttonTable.setFillParent(true);

        Label helpLabel = new Label("Help", game.labelStyleHeadline);
        Label itemLabel = new Label("Item", game.labelStyle);
        Label descriptionLabel = new Label("Description", game.labelStyle);

        Gdx.input.setInputProcessor(stage);

        int padding = game.getDefaultPadding();
        table.pad(padding, padding, padding, padding);

        table.background(new TextureRegionDrawable(new Texture(Configuration.table_backgroundImg)));

        table.add(helpLabel).height(100).colspan(4).center();
        table.row();
        table.add().width(20);
        table.add(itemLabel).minWidth(300);
        table.add(descriptionLabel).expand().left().colspan(2);

        table.row();
        table.add().width(20);
        table.add(new Image(new Texture(Configuration.manny_straightImg))).maxWidth(100).left().padRight(20);
        table.add(new Label("Manni: Die geilste Moewe der Welt.", game.labelStyle)).left().colspan(2);

        table.row();
        table.add().width(20);
        table.add(new Image(new Texture(Configuration.barrierhelpImg))).maxWidth(100).left().padRight(20);
        table.add(new Label("Hindernis: Bei Beruehrung stribt Manni.", game.labelStyle)).left().colspan(2);

        table.row();
        table.add().width(20);
        table.add(new Image(new Texture(Configuration.pommesImg))).maxWidth(100).maxHeight(100).left().padRight(20);
        table.add(new Label("Item-Pommes: Wenn du drei Pommes gesammelt hast kannst du von einem\n" +
                "Hindernis getroffen werden ohne zu sterben.", game.labelStyle)).left().colspan(2);

        table.row();
        table.add().width(20);
        table.add(new Image(new Texture(Configuration.item_miniImg))).maxWidth(100).left().padRight(20);
        table.add(new Label("Item-Lupe: Verkleinert Manni fuer eine kurze Zeit.", game.labelStyle)).left().colspan(2);

        table.row();
        table.add().width(20);
        table.add(new Image(new Texture(Configuration.item_multipyImg))).maxWidth(100).left().padRight(20);
        table.add(new Label("Item-Multiplikator: Die erhaltenen Punkte werden fuer eine\n" +
                "kurze Zeit verdoppelt", game.labelStyle)).left().colspan(2);

        table.row();
        table.add().width(20);
        table.add(new Image(new Texture(Configuration.item_slowmoImg))).maxWidth(100).left().padRight(20);
        table.add(new Label("Item-Schnecke: Manni wird verlangsamt.", game.labelStyle)).left().colspan(2);

        table.row();
        table.add().width(20);
        table.add(new Image(new Texture(Configuration.reduceScoreImg))).maxWidth(100).left().padRight(20);
        table.add(new Label("Item-Minus: Manni verliert einen Teil seiner Punkte.", game.labelStyle)).left().colspan(2);

        table.row();

        buttonTable.padLeft(padding);
        buttonTable.add().expand().colspan(3);
        buttonTable.row();
        buttonTable.add().fillX().minWidth((Gdx.graphics.getWidth() / 3) - (padding * 12));
        buttonTable.add().fillX().minWidth((Gdx.graphics.getWidth() / 3) - (padding * 12));
        buttonTable.add(ButtonFactory.CreateImageButton(Configuration.back, false,
                () -> {
                    game.setScreen(new StartScreen(game));
                    dispose();
                })).bottom().center();

        table.add(buttonTable).colspan(4).fillX();

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
