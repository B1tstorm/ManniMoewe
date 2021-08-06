package de.fhkiel.aem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import jdk.javadoc.internal.tool.Start;

import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;

/**
 * The screen the game is running on
 */
public class PlayScreen implements Screen {

    private final FlappyBird game;
    private final OrthographicCamera camera;
    private final Stage stage;
    private final Texture backgroundTexture;
    private final Array<Sprite> backgroundLoop;
    private final Bird bird;
    private GameOverScreen gameOverScreen;
    private final Label highscoreLabel;
    private final Array<Barrier> barriers = new Array<>();
    private Label pressSpaceLable;
    private final Table tablePressSpace;
    private boolean runGame = false;
    private boolean gameOver = false;

    ShapeRenderer shapeRenderer;

    /**
     * Creates a new PlayScreen where the game is running on.
     *
     * @param game The game object
     */
    public PlayScreen(FlappyBird game) {
        shapeRenderer = new ShapeRenderer();
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = new BitmapFont(Gdx.files.internal("title-font-export.fnt"));
        labelStyle.fontColor = Color.GRAY;
        Table table = new Table();
        table.setFillParent(true);
        tablePressSpace = new Table();
        tablePressSpace.setFillParent(true);

        this.game = game;
        bird = new Bird(50, 500);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Configuration.ScreenWidth, Configuration.ScreenHeight);

        stage = new Stage(new FitViewport(Configuration.ScreenWidth, Configuration.ScreenHeight));

        Gdx.input.setInputProcessor(stage);
        highscoreLabel = new Label("Highscore: ", labelStyle);
        table.add(highscoreLabel).height(100).center().top().expand();
        stage.addActor(table);

        pressSpaceLable = new Label("Press Space to start...", labelStyle);
        tablePressSpace.add(pressSpaceLable).height(750).center().top().expand();
        stage.addActor(tablePressSpace);

        backgroundTexture = new Texture(Gdx.files.internal(Configuration.backgroundImg));
        backgroundLoop = new Array<>();

        while (!isFilledWithBackgroundImages(backgroundLoop)) {
            Sprite sprite = new Sprite(backgroundTexture);
            sprite.setX(findRightestPixel(backgroundLoop));
            sprite.setY(0);
            backgroundLoop.add(sprite);
        }


        createBarriers();
    }

    /**
     * Checks if the whole Screen is covered by an background image.
     *
     * @param array The array of background images
     * @return True if the whole width is covered.
     */
    private boolean isFilledWithBackgroundImages(Array<Sprite> array) {
        float pixel = 0;

        for (Sprite positionTexture : new Array.ArrayIterator<>(array)) {
            if (positionTexture.getX() < 0) {
                pixel += positionTexture.getWidth() - positionTexture.getX();
            } else {
                pixel += positionTexture.getWidth();
            }
        }
        return pixel > Configuration.ScreenWidth;
    }

    /**
     * Finds the most right position (with its width) of an image array.
     *
     * @param array The array that is checked
     * @return The most right pixel on the screen
     */
    private float findRightestPixel(Array<Sprite> array) {
        float pixel = 0;

        for (Sprite texture : new Array.ArrayIterator<>(array)) {
            float right = texture.getX() + texture.getWidth();
            if (right > pixel) pixel = right;
        }

        return pixel;
    }

    @Override
    public void show() {
        game.oceanSeagullMusic.stop();
        game.kielMusic.play();
    }

    /**
     * Rendering of the all objects on the screen.
     *
     * @param delta time delta
     */
    @Override
    public void render(float delta) {

        ScreenUtils.clear(0.443f, 0.772f, 0.811f, 1);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        renderArray(backgroundLoop);


        for (Barrier barrier : new Array.ArrayIterator<>(barriers)) {
            barrier.render(game.batch);
        }

        stage.draw();
        game.batch.draw(bird.getBirdSprite(), bird.getBirdSprite().getX(), bird.getBirdSprite().getY(), bird.getWidth(), bird.getWidth());
        bird.render(game.batch);
        //beim Drücken der Leertaste soll die Zeile"press space to ......" verschwenden und das spiel wird in Bewegung gesetzt
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            runGame = true;
            tablePressSpace.clear();
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
            gameOverScreen.render(delta);
        } else {
            update();
        }
    }

    /**
     * Renders all items of an array.
     *
     * @param array The array that should be rendered
     */
    private void renderArray(Array<Sprite> array) {
        for (Sprite item : new Array.ArrayIterator<>(array)) {
            game.batch.draw(item.getTexture(), item.getX(), item.getY());
        }
    }

    /**
     * Calculation and Updates of values.
     */
    private void update() {
        int randomNum = 0;

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
                game.setScreen(new StartScreen(game));
                dispose();
            }
        }
        moveArrayLeft(backgroundLoop, 60f);
        for (Iterator<Sprite> iter = new Array.ArrayIterator<>(backgroundLoop); iter.hasNext(); ) {
            Sprite item = iter.next();
            if (item.getX() + item.getWidth() < -20) {
                iter.remove();
            }
        }
        if (findRightestPixel(backgroundLoop) < Configuration.ScreenWidth + 100) {
            Sprite sprite = new Sprite(backgroundTexture);
            sprite.setX(findRightestPixel(backgroundLoop));
            sprite.setY(0);
            backgroundLoop.add(sprite);
        }
        for (Barrier barrier : new Array.ArrayIterator<>(barriers)) {
            if (bird.getBirdSprite().getX() >= barrier.getBarrierSprite().getX() && barrier.getWealth() != 0) {
                bird.setHighscore(bird.getHighscore() + barrier.getWealth());
                barrier.setWealth(0);
            }
            if (barrier.getBarrierSprite().getX() < (0 - barrier.getBarrierSprite().getWidth())) {
                if (barrier.getBarrierSprite().getRotation() != 180) {
                    randomNum = ThreadLocalRandom.current().nextInt(
                            (int) (Gdx.graphics.getHeight() -200 - barrier.getBarrierSprite().getHeight()), Gdx.graphics.getHeight() +200);
                    barrier.getBarrierSprite().setY(randomNum);
                } else {
                    barrier.getBarrierSprite().setY(randomNum - barrier.getBarrierSprite().getHeight() - barrier.getGap());
                }
                barrier.getBarrierSprite().setX(barrier.getBarrierSprite().getX() + (barriers.size / 2f) * barrier.getDistance());
            }
        }
    }

    /**
     * Moves an array in the left direction of the screen.
     *
     * @param array The array that should be moved.
     * @param speed The speed it should be moved.
     */
    private void moveArrayLeft(Array<Sprite> array, float speed) {
        float movement = speed * Gdx.graphics.getDeltaTime();
        for (Sprite item : new Array.ArrayIterator<>(array)) {
            item.setX(item.getX() - movement);
        }
    }

    /**
     * Creates the Barriers for the game.
     */
    public void createBarriers() {
        for (int i = 0; i < 10; i++) {
            int randomNum = ThreadLocalRandom.current().nextInt(
                    (int) (Gdx.graphics.getHeight() -200 - new Texture(Configuration.barrierdownImg).getHeight()) +200,
                    Gdx.graphics.getHeight());
//            randomNum = 200;
//            if(randomNum <200){
//                i--;
//                continue;
//            }

            Barrier b = new Barrier(
                    Gdx.graphics.getWidth() + new Barrier(0, 0, Configuration.barrierdownImg).getDistance() * i,
                    randomNum, Configuration.barrierupImg);
            barriers.add(b);
            Barrier b2 = new Barrier(Gdx.graphics.getWidth() + (b.getDistance() * i),
                    randomNum - b.getBarrierSprite().getHeight() - b.getGap(), Configuration.barrierupImg);
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
        backgroundTexture.dispose();
    }
}
