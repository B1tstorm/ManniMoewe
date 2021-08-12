package de.fhkiel.aem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import de.fhkiel.aem.utility.ButtonFactory;

public class CreditScreen implements Screen {

    private final FlappyBird game;
    private final OrthographicCamera camera;
    private final Stage stage;
    private Table table, buttonTable;
    private Label headerLabel, name, role, poRole, smRole, devRole, desRole, devdesRole, devRole1,devRole2, devRole3;
    private Label niels, oliver, marvin, anas, joshua, kai, jonas, paul;
    private ImageButton backButton;

    public CreditScreen(FlappyBird game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Configuration.ScreenWidth, Configuration.ScreenHeight);

        table = new Table();
        table.setFillParent(true);

        stage = new Stage(new FitViewport(Configuration.ScreenWidth, Configuration.ScreenHeight));

        Gdx.input.setInputProcessor(stage);
        
        headerLabel = new Label("Credits", game.labelStyle);
        name = new Label("Name", game.labelStyle);
        role = new Label("Rolle", game.labelStyle);
        poRole = new Label("Product Owner", game.labelStyle);
        smRole = new Label("Scrum Master", game.labelStyle);
        devRole = new Label("Developer", game.labelStyle);
        devRole1 = new Label("Developer", game.labelStyle);
        devRole2 = new Label("Developer", game.labelStyle);
        devRole3 = new Label("Developer", game.labelStyle);
        desRole = new Label("Designer", game.labelStyle);
        devdesRole = new Label("Designer / Developer", game.labelStyle);

        niels = new Label("Niels Lassen", game.labelStyle);
        oliver = new Label("Kamil Oliver Gorczyca", game.labelStyle);
        marvin = new Label("Marvin Winterhoff", game.labelStyle);
        anas = new Label("Anas Arodake", game.labelStyle);
        joshua = new Label("Joshua Widdermann", game.labelStyle);
        kai = new Label("Kai Tilman Harmsen", game.labelStyle);
        jonas = new Label("Jonas Becker", game.labelStyle);
        paul = new Label("Paul Rieck", game.labelStyle);

        buttonTable = new Table();
        buttonTable.setFillParent(true);

        backButton = ButtonFactory.CreateImageButton(Configuration.back, false,
                () -> {
                    game.setScreen(new OptionsScreen(game));
                    dispose();
                });

        int padding = game.getDefaultPadding();
        table.pad(padding, padding, padding, padding);
        table.background(new TextureRegionDrawable(new Texture(Configuration.table_backgroundImg)));

        table.add(headerLabel).top().center().colspan(2).height(100);
        table.row();
        table.add(name).expand();
        table.add(role).expand();
        table.row();
        table.add(niels).expand();
        table.add(poRole).expand();
        table.row();
        table.add(oliver).expand();
        table.add(smRole).expand();
        table.row();
        table.add(anas).expand();
        table.add(devRole).expand();
        table.row();
        table.add(marvin).expand();
        table.add(devRole1).expand();
        table.row();
        table.add(joshua).expand();
        table.add(devRole2).expand();
        table.row();
        table.add(paul).expand();
        table.add(devRole3).expand();
        table.row();
        table.add(jonas).expand();
        table.add(devdesRole).expand();
        table.row();
        table.add(kai).expand();
        table.add(desRole).expand();
        table.row();
        table.add(new Image(new Texture(Configuration.manny_heyImg))).colspan(2).height(300);
        table.row();
        buttonTable.padLeft(padding);
        buttonTable.add().expand().colspan(3);
        buttonTable.row();
        buttonTable.add().fillX().minWidth((Gdx.graphics.getWidth() / 3) - (padding * 12));
        buttonTable.add().fillX().minWidth((Gdx.graphics.getWidth() / 3) - (padding * 12));
        buttonTable.add(ButtonFactory.CreateImageButton(Configuration.back, false,
                () -> {
                    game.setScreen(new OptionsScreen(game));
                    dispose();
                })).bottom().center();

        table.add(buttonTable).colspan(3).fillX();

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
