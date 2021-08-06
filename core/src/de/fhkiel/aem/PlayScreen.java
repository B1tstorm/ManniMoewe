package de.fhkiel.aem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.concurrent.ThreadLocalRandom;

/**
 * The screen the game is running on
 */
public class PlayScreen implements Screen {

    private final FlappyBird game;
    private final OrthographicCamera camera;
    private final Stage stage;
    private final Bird bird;
    private final Label highscoreLabel;
    private final Array<Barrier> barriers = new Array<>();

    private final ShapeRenderer shapeRenderer;

    /**
     * Creates a new PlayScreen where the game is running on.
     * @param game The game object
     */
    public PlayScreen(FlappyBird game) {

        shapeRenderer = new ShapeRenderer();
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = new BitmapFont(Gdx.files.internal("title-font-export.fnt"));
        labelStyle.fontColor = Color.GRAY;
        Table table = new Table();
        table.setFillParent(true);

        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Configuration.ScreenWidth, Configuration.ScreenHeight);

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        highscoreLabel = new Label("Highscore: ", labelStyle);
        table.add(highscoreLabel).height(100).center().top().expand();
        stage.addActor(table);

        bird = new Bird(50, 250 );
        bird.getBirdSprite().setTexture(bird.getMannyStraight());

        createBarriers();
    }

    @Override
    public void show() {
        game.oceanSeagullMusic.stop();
        game.kielMusic.play();
    }

    /**
     * Rendering of the all objects on the screen.
     * @param delta time delta
     */
    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.443f, 0.772f, 0.811f, 1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.background.renderBackground();

        for(Barrier barrier : new Array.ArrayIterator<>(barriers)){
            barrier.render(game.batch);
        }

        game.background.renderForeground();
        bird.render(game.batch);
        bird.move();

        stage.draw();

        game.batch.end();

        update();
    }

    /**
     * Calculation and Updates of values.
     */
    private void update() {
        int randomNum = 0;

        highscoreLabel.setText("Highscore: " + (int)bird.getHighscore());

        for(Barrier barrier : new Array.ArrayIterator<>(barriers)) {
            if (Intersector.overlaps(bird.getHitbox(), barrier.getBarrierSprite().getBoundingRectangle())){
                game.kielMusic.stop();
                game.setScreen(new StartScreen(game));
                dispose();
            }
        }

        game.background.move();

        for(Barrier barrier : new Array.ArrayIterator<>(barriers)) {
            if(bird.getBirdSprite().getX() >= barrier.getBarrierSprite().getX() && barrier.getWealth() != 0) {
                bird.setHighscore(bird.getHighscore() + barrier.getWealth());
                barrier.setWealth(0);
            }
            if (barrier.getBarrierSprite().getX() < (0 - barrier.getBarrierSprite().getWidth())) {
                if(barrier.getBarrierSprite().getRotation() != 180) {
                    randomNum = ThreadLocalRandom.current().nextInt(
                            (int) (Gdx.graphics.getHeight() - barrier.getBarrierSprite().getHeight()), Gdx.graphics.getHeight());
                    barrier.getBarrierSprite().setY(randomNum);
                }
                else{
                    barrier.getBarrierSprite().setY(randomNum - barrier.getBarrierSprite().getHeight() - barrier.getGap());
                }
                barrier.getBarrierSprite().setX(barrier.getBarrierSprite().getX() + (barriers.size / 2f) * barrier.getDistance());
            }
        }
    }



    /**
     * Creates the Barriers for the game.
     */
    public void createBarriers(){
        for(int i = 0; i < 10; i++) {
            int randomNum = ThreadLocalRandom.current().nextInt(
                    (Gdx.graphics.getHeight() - new Texture("barrier-down.png").getHeight()),
                    Gdx.graphics.getHeight());
            Barrier b = new Barrier(
                    Gdx.graphics.getWidth() + new Barrier(0,0, "barrier-down.png").getDistance() * i,
                    randomNum,"barrier-up.png");
            barriers.add(b);
            Barrier b2 = new Barrier(Gdx.graphics.getWidth() + (b.getDistance() * i),
                    randomNum - b.getBarrierSprite().getHeight() - b.getGap(), "barrier-up.png");
            b2.getBarrierSprite().setRotation(180f);
            barriers.add(b2);
        }
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
