package de.fhkiel.aem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import java.util.concurrent.ThreadLocalRandom;

import java.util.Iterator;

/**
 * The screen the game is running on
 */
public class Playscreen implements Screen {

    private final FlappyBird game;
    private final OrthographicCamera camera;
    private final Stage stage;
    private final Texture backgroundTexture;
    private final Array<PositionTexture> backgroundLoop;
    private final Bird bird;
    private Array<Barrier> barriers = new Array<>();
    private final Music kielMusik;

    ShapeRenderer shapeRenderer;

    /**
     * Creates a new PlayScreen where the game is running on.
     * @param game Gameobject
     */
    public Playscreen(FlappyBird game) {

        shapeRenderer = new ShapeRenderer();

        this.game = game;

        kielMusik = Gdx.audio.newMusic(Gdx.files.internal("Kiel_Sound.mp3"));
        kielMusik.setVolume(0.5f);
        kielMusik.setLooping(true);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Configuration.ScreenWidth, Configuration.ScreenHeight);

        stage = new Stage();
        Gdx.input.setInputProcessor(stage);

        backgroundTexture = new Texture(Gdx.files.internal("background.png"));
        backgroundLoop = new Array<>();

        while(!isFilledWithBackgroundImages(backgroundLoop)) {
            backgroundLoop.add(new PositionTexture(backgroundTexture, findRightestPixel(backgroundLoop), 0));
        }

        bird = new Bird(50, 500 );
        createBarriers();
    }

    /**
     * Checks if the whole Screen is covered by an background image.
     * @param array The array of background images
     * @return True if the whole width is covered.
     */
    private boolean isFilledWithBackgroundImages(Array<PositionTexture> array) {
        float pixel = 0;

        for (PositionTexture positionTexture : new Array.ArrayIterator<>(array)) {
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
     * @param array The array that is checked
     * @return The most right pixel on the screen
     */
    private float findRightestPixel(Array<PositionTexture> array) {
        float pixel = 0;

        for (PositionTexture texture: new Array.ArrayIterator<>(array)) {
            float right = texture.getX() + texture.getWidth();
            if (right > pixel) pixel = right;
        }

        return pixel;
    }

    @Override
    public void show() {
        game.meerMoeweMusik.stop();
        kielMusik.play();
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
        renderArray(backgroundLoop);

        stage.draw();

        game.batch.draw(bird.bird, bird.xPos, bird.yPos , 100 , 100 );
        bird.move();

        for(Barrier barrier : barriers){
            barrier.render(game.batch, barriers.size / 2);
        }

        game.batch.draw(bird.birdSprite, bird.birdSprite.getX(), bird.birdSprite.getY() , bird.getWidth() , bird.getWidth());
        bird.move();

        game.batch.end();

        //Debug Hitbox
        /*shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.CYAN);
        shapeRenderer.circle(bird.hitbox.x, bird.hitbox.y, bird.hitbox.radius);
        shapeRenderer.end();*/

        update();
    }

    /**
     * Renders all items of an array.
     * @param array The array that should be rendered
     */
    private void renderArray(Array<PositionTexture> array) {
        for(PositionTexture item : new Array.ArrayIterator<>(array)) {
            game.batch.draw(item.getTexture(), item.getX(), item.getY());
        }
    }

    /**
     * Calculation and Updates of values.
     */
    private void update() {
        int randomNum = 0;

        for(Barrier barrier : barriers) {
            if (Intersector.overlaps(bird.getHitbox(), barrier.getBarrierSprite().getBoundingRectangle()))
                game.setScreen(new StartScreen(game));
        }
        moveArrayLeft(backgroundLoop, 60f);
        for(Iterator<PositionTexture> iter = backgroundLoop.iterator(); iter.hasNext(); ) {
            PositionTexture item = iter.next();
            if(item.getX() + item.getWidth() < -20) {
                iter.remove();
            }
        }
        if (findRightestPixel(backgroundLoop) < Configuration.ScreenWidth + 100) {
            backgroundLoop.add(new PositionTexture(backgroundTexture, findRightestPixel(backgroundLoop), 0));
        }
        for(Barrier barrier : barriers) {
            if (barrier.getBarrierSprite().getX() < (0 - barrier.getBarrierSprite().getWidth())) {
                if(barrier.getBarrierSprite().getRotation() != 180) {
                    randomNum = ThreadLocalRandom.current().nextInt(
                            (int) (Gdx.graphics.getHeight() - barrier.getBarrierSprite().getHeight()), Gdx.graphics.getHeight());
                    barrier.getBarrierSprite().setY(randomNum);
                }
                else{
                    barrier.getBarrierSprite().setY(randomNum - barrier.getBarrierSprite().getHeight() - barrier.getGap());
                }
                barrier.getBarrierSprite().setX(barrier.getBarrierSprite().getX() + (barriers.size / 2) * barrier.getDistance());
            }
        }
    }

    /**
     * Moves an array in the left direction of the screen.
     * @param array The array that should be moved.
     * @param speed The speed it should be moved.
     */
    private void moveArrayLeft(Array<PositionTexture> array, float speed) {
        float movement = speed * Gdx.graphics.getDeltaTime();
        for(PositionTexture item : new Array.ArrayIterator<>(array)) {
            item.setX(item.getX() - movement);
        }
    }

    public void createBarriers(){
        for(int i = 0; i < 10; i++) {
            Barrier b = new Barrier();
            int randomNum = ThreadLocalRandom.current().nextInt(
                    (int) (Gdx.graphics.getHeight() - b.getBarrierSprite().getHeight()), Gdx.graphics.getHeight());
            b.getBarrierSprite().setX(Gdx.graphics.getWidth() + (b.getDistance() * i));
            b.getBarrierSprite().setY(randomNum);
            barriers.add(b);
            Barrier b2 = new Barrier();
            b2.getBarrierSprite().setRotation(180f);
            b2.getBarrierSprite().setX(Gdx.graphics.getWidth() + (b.getDistance() * i));
            b2.getBarrierSprite().setY(randomNum - b2.getBarrierSprite().getHeight() - b2.getGap());
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
        backgroundTexture.dispose();
        for(PositionTexture texture: backgroundLoop) {
            texture.dispose();
        }
        kielMusik.dispose();
    }
}
