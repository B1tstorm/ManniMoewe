package de.fhkiel.aem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import de.fhkiel.aem.utility.ButtonFactory;

public class CreditScreen implements Screen {

    private final FlappyBird game;
    private final OrthographicCamera camera;
    private final Stage stage;
    private Table table;
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

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = new BitmapFont(Gdx.files.internal("title-font-export.fnt"));
        labelStyle.fontColor = Color.BLACK;

        headerLabel = new Label("Credits", labelStyle);
        name = new Label("Name", labelStyle);
        role = new Label("Rolle", labelStyle);
        poRole = new Label("Product Owner", labelStyle);
        smRole = new Label("Scrum Master", labelStyle);
        devRole = new Label("Developer", labelStyle);
        devRole1 = new Label("Developer", labelStyle);
        devRole2 = new Label("Developer", labelStyle);
        devRole3 = new Label("Developer", labelStyle);
        desRole = new Label("Designer", labelStyle);
        devdesRole = new Label("Designer / Developer", labelStyle);

        niels = new Label("Niels Lassen", labelStyle);
        oliver = new Label("Kamil Oliver Gorczyca", labelStyle);
        marvin = new Label("Marvin Winterhoff", labelStyle);
        anas = new Label("Anas Arodake", labelStyle);
        joshua = new Label("Joshua Widdermann", labelStyle);
        kai = new Label("Kai Tilman Harmsen", labelStyle);
        jonas = new Label("Jonas Becker", labelStyle);
        paul = new Label("Paul Rieck", labelStyle);

        backButton = ButtonFactory.CreateImageButton(Configuration.back, false,
                () -> {
                    game.setScreen(new OptionsScreen(game));
                    dispose();
                });

        table.add(headerLabel).top().center().colspan(3).height(200);
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
        table.add(new Image(new Texture(Configuration.manny_heyImg))).colspan(3).height(300);
        table.row();
        table.add(backButton).top().center().colspan(3).height(100);

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
