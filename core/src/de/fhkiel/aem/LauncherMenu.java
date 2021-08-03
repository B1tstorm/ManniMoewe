package de.fhkiel.aem;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;

import java.awt.*;

public class LauncherMenu implements Screen {

    Stage stage;
    TextButton optionsButton;
    TextButton.TextButtonStyle textButtonStyle;
    BitmapFont font;
    Skin skin;
    TextureAtlas buttonAtlas;
    SpriteBatch batch;
    OrthographicCamera camera;

    LauncherMenu(){
        create();
    }

    public void create() {
        camera = new OrthographicCamera();
        batch = new SpriteBatch();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        font = new BitmapFont();


        buttonAtlas = new TextureAtlas(Gdx.files.internal("uiskin.atlas"));
        skin = new Skin();
        skin.addRegions(buttonAtlas);

        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font;
        textButtonStyle.fontColor = Color.WHITE;
        textButtonStyle.downFontColor = Color.BLACK;
        textButtonStyle.checkedFontColor = Color.BLUE;
        textButtonStyle.up = skin.getDrawable("textfield");
        textButtonStyle.down = skin.getDrawable("textfield");

        optionsButton = new TextButton("Optionen", textButtonStyle);
        optionsButton.setSize(100, 70);
        optionsButton.setPosition(Gdx.graphics.getWidth() - optionsButton.getWidth() - 50, 20);
        optionsButton.setColor(Color.GREEN);
        optionsButton.addListener(new ChangeListener() {
            @Override
            public void changed (ChangeEvent event, Actor actor) {
                System.out.println("Button Pressed");
            }
        });
        stage.addActor(optionsButton);
    }



    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 0, 0, 1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        stage.draw();
        batch.end();
    }

    @Override
    public void show() {

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
