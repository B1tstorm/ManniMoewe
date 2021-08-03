package de.fhkiel.aem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import org.w3c.dom.Text;


public class HighscoreScreen implements Screen {

    final FlappyBird game;
    OrthographicCamera camera;
    Stage stage;
    Label highscore, platz, name, score;
    Label.LabelStyle labelStyle;
    Table highscoreTabelle;

    public HighscoreScreen(final FlappyBird game){
        this.game = game;
        stage = new Stage();
        highscoreTabelle = new Table();
        highscoreTabelle.setFillParent(true);

        labelStyle = new Label.LabelStyle();
        labelStyle.font = new BitmapFont(Gdx.files.internal("title-font-export.fnt"));
        labelStyle.fontColor = Color.WHITE;

        highscore = new Label("Highscores: ",labelStyle);
        name = new Label("Name", labelStyle);
        platz = new Label("Platz", labelStyle);
        score = new Label("Score",labelStyle);

        Gdx.input.setInputProcessor(stage);

        highscoreTabelle.add(highscore).height(100).colspan(3).center();

        highscoreTabelle.row();
        highscoreTabelle.add(platz).expand();
        highscoreTabelle.add(name).expand();
        highscoreTabelle.add(score).expand();

        for(int i = 0; i<10; i++) {
            highscoreTabelle.row();
            highscoreTabelle.add(new Label((i+1) + ".", labelStyle)).expand();
            highscoreTabelle.add().expand();
            highscoreTabelle.add().expand();
        }

        highscoreTabelle.row();

        stage.addActor(highscoreTabelle);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Configuration.ScreenWidth, Configuration.ScreenHeight);


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
