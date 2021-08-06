package de.fhkiel.aem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import de.fhkiel.aem.utility.ButtonFactory;

import java.util.concurrent.ThreadLocalRandom;

/**
 * The screen the game is running on
 */
public class PlayScreen implements Screen {

    private final FlappyBird game;
    private final OrthographicCamera camera;
    private final Stage stage;
    private final Bird bird;
    private GameOverScreen gameOverScreen;
    private final Label highscoreLabel;
    private final Array<Barrier> barriers = new Array<>();
    private Label pressSpaceLable;
    private final Table tablePressSpace;
    private boolean runGame = false;
    private boolean gameOver = false;
    private ImageButton ausweichButton, platzhalterButton;
    private long lastInvisibleTime;
    private long currtime;

    private final ShapeRenderer shapeRenderer;

    /**
     * Creates a new PlayScreen where the game is running on.
     * @param game The game object
     */
    public PlayScreen(FlappyBird game) {
        shapeRenderer = new ShapeRenderer();
        gameOver = false;
        runGame = false;
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = new BitmapFont(Gdx.files.internal("title-font-export.fnt"));
        labelStyle.fontColor = Color.GRAY;
        Table table = new Table();
        table.setFillParent(true);
        tablePressSpace = new Table();
        tablePressSpace.setFillParent(true);

        this.game = game;

        bird = new Bird(50, 250 );
        bird.getBirdSprite().setTexture(bird.getMannyStraight());

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Configuration.ScreenWidth, Configuration.ScreenHeight);

        stage = new Stage(new FitViewport(Configuration.ScreenWidth, Configuration.ScreenHeight));

        Gdx.input.setInputProcessor(stage);
        highscoreLabel = new Label("Highscore: ", labelStyle);
        table.add(ausweichButton = ButtonFactory.CreateImageButton(Configuration.backImg,
                () -> {
                        lastInvisibleTime = TimeUtils.nanoTime();
                        bird.getHitbox().setRadius(0f);
        })).expand().left().top();
        table.add(highscoreLabel).expand().height(100).center().top();
        table.add(platzhalterButton = ButtonFactory.CreateImageButton(Configuration.transparentImg,
                () -> {})).expand();



        stage.addActor(table);

        pressSpaceLable = new Label("Press Space to start...", labelStyle);
        tablePressSpace.add(pressSpaceLable).height(750).center().top().expand();
        stage.addActor(tablePressSpace);

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

        ScreenUtils.clear(0f, 0f, 0f, 1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.background.renderBackground();
        game.background.renderForeground();

        for(Barrier barrier : new Array.ArrayIterator<>(barriers)) {
            barrier.render(game.batch);
        }

        stage.draw();
        bird.render(game.batch);

        //beim Drücken der Leertaste soll die Zeile"press space to ......" verschwenden und das spiel wird in Bewegung gesetzt
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            runGame = true;
            tablePressSpace.clear();
            currtime = TimeUtils.nanoTime();
        }
        if (runGame && !gameOver) {
            bird.move();
            for (Barrier barrier : new Array.ArrayIterator<>(barriers)) {
                barrier.move();
            }
        }
        game.batch.end();

        //Debug Hitbox
        /*shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.CYAN);
        for(Barrier barrier : barriers){
            shapeRenderer.rect(barrier.getHitbox().x, barrier.getHitbox().y,
                    barrier.getHitbox().width, barrier.getHitbox().height);
        }
        shapeRenderer.circle(bird.hitbox.x, bird.hitbox.y, bird.hitbox.radius);
        shapeRenderer.end();*/

        if (gameOver) {
            game.resetGameSpeed();
            gameOverScreen.render(delta);
        } else {
            update();
        }
    }

    /**
     * Calculation and Updates of values.
     */
    private void update() {
        int randomNum = 0;

        if(TimeUtils.nanoTime() - lastInvisibleTime > 1500000000) {
            bird.getHitbox().setRadius(50);
        }
        if(runGame && TimeUtils.nanoTime() - currtime > 500000000){
            game.increaseGameSpeed();
            currtime = TimeUtils.nanoTime();
        }

        highscoreLabel.setText("Highscore: " + (int) bird.getHighscore());

        for (Barrier barrier : new Array.ArrayIterator<>(barriers)) {
            if (Intersector.overlaps(bird.getHitbox(), barrier.getBarrierSprite().getBoundingRectangle())) {
                game.kielMusic.stop();
                gameOver = true;
                runGame = false;
                gameOverScreen = new GameOverScreen(game);
            }
            if(bird.getHitbox().y > Configuration.ScreenHeight && barrier.getBarrierSprite().getX() <= bird.getBirdSprite().getX()) {
                game.kielMusic.stop();
                gameOver = true;
                runGame = false;
                gameOverScreen = new GameOverScreen(game);
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
                            (int) (Gdx.graphics.getHeight() - barrier.getBarrierSprite().getHeight()) +200, Gdx.graphics.getHeight());
                    barrier.getBarrierSprite().setY(randomNum);
                } else {
                    barrier.getBarrierSprite().setY(randomNum - barrier.getBarrierSprite().getHeight() - barrier.getGap());
                }
                barrier.getBarrierSprite().setX(barrier.getBarrierSprite().getX() + (barriers.size / 2f) * barrier.getDistance());
                barrier.setWealth(game.getDifficulty() / 2.0f);
            }
        }
    }



    /**
     * Creates the Barriers for the game.
     */
    public void createBarriers() {
        for(int i = 0; i < 10; i++) {
            int randomNum = ThreadLocalRandom.current().nextInt(
                    Gdx.graphics.getHeight() - new Texture(Configuration.barrierdownImg).getHeight() +200,Gdx.graphics.getHeight());

            Barrier b = new Barrier(
                    Gdx.graphics.getWidth() + new Barrier(0, 0, Configuration.barrierdownImg, game.getDifficulty()).getDistance() * i,
                    randomNum, Configuration.barrierupImg, game.getDifficulty());
            barriers.add(b);
            Barrier b2 = new Barrier(Gdx.graphics.getWidth() + (b.getDistance() * i),
                    randomNum - b.getBarrierSprite().getHeight() - b.getGap(), Configuration.barrierupImg, game.getDifficulty());
            b2.getBarrierSprite().setRotation(180f);
            barriers.add(b2);
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
