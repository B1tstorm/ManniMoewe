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
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;


public class SpaceScreen implements Screen {

    private final FlappyBird game;
    private final OrthographicCamera camera;
    private final Stage stage;
    private final Bird bird;
    private GameOverScreen gameOverScreen;
    private final Label highscoreLabel, collectableLabel, speedLabel;
    private final Array<Barrier> barriers = new Array<>();
    private Label pressSpaceLable;
    private final Table table;
    private final Table tablePressSpace;
    private boolean runGame = false;
    private boolean gameOver = false;
    private long lastInvisibleTime;
    private long currentTime;
    private final Array<Item> items;
    private Texture pommespackung_leer, pommespackung_eins, pommespackung_zwei, pommespackung_drei;
    private final ShapeRenderer shapeRenderer;
    private final float highscoreAlt;
    private SpaceBackground spaceBackground;


    /**
     * Creates a new PlayScreen where the game is running on.
     *
     * @param game The game object
     */
    public SpaceScreen(FlappyBird game, float highscoreAlt, Bird bird) {

        game.setFoundEasteregg(true);

        shapeRenderer = new ShapeRenderer();
        gameOver = false;
        runGame = false;

        spaceBackground = new SpaceBackground(game.batch);
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = new BitmapFont(Gdx.files.internal("title-font-export.fnt"));
        labelStyle.fontColor = Color.GRAY;

        table = new Table();
        table.setFillParent(true);
        tablePressSpace = new Table();
        tablePressSpace.setFillParent(true);
        //table.debug();

        items = new Array<>();

        pommespackung_leer = new Texture(Configuration.pommespackung_leerImg);
        pommespackung_eins = new Texture(Configuration.pommespackung_einsImg);
        pommespackung_zwei = new Texture(Configuration.pommespackung_zweiImg);
        pommespackung_drei = new Texture(Configuration.pommespackung_dreiImg);

        this.game = game;
        this.highscoreAlt = highscoreAlt;
        this.bird = bird;

        bird.setHighscore(highscoreAlt);

        if(bird.spaceScreen) {
            if (bird.isHelmetactive()) {
                bird.getBirdSprite().setTexture(bird.getMannyStraightSpace());
            } else {
                bird.getBirdSprite().setTexture(bird.getMannyStraightSpace());
            }
        }
        else {
            if (bird.isHelmetactive()) {
                bird.getBirdSprite().setTexture(bird.getMannyStraightSpaceHelmet());
            } else {
                bird.getBirdSprite().setTexture(bird.getMannyStraight());
            }
        }



        camera = new OrthographicCamera();
        camera.setToOrtho(false, Configuration.ScreenWidth, Configuration.ScreenHeight);

        stage = new Stage(new FitViewport(Configuration.ScreenWidth, Configuration.ScreenHeight));

        Gdx.input.setInputProcessor(stage);

        highscoreLabel = new Label("Highscore: ", labelStyle);
        collectableLabel = new Label("" + bird.getScoreCollectable() + " / 3", labelStyle);
        speedLabel = new Label("Speed: " + game.getSpeedMultiplier() * 60 + "km/h", labelStyle);

        table.add(new Image(pommespackung_leer)).top().left().maxHeight(55).maxWidth(55);
        table.add(collectableLabel).expand().center().top().left().padLeft(20);

        table.add(highscoreLabel).expand().height(100).center().top();
        table.add(speedLabel).expand().height(100).center().top();

        table.add().expand().padRight(75 + collectableLabel.getWidth());
        stage.addActor(table);

        pressSpaceLable = new Label("Press Space to start...", labelStyle);
        tablePressSpace.add(pressSpaceLable).height(750).center().top().expand();
        stage.addActor(tablePressSpace);

        createBarriers();
    }

    @Override
    public void show() {
        bird.spaceScreen = true;
        game.oceanSeagullMusic.pause();
        game.kielMusic.pause();
        game.spaceMusic.play();
        game.resetGameSpeed();
        bird.getBirdSprite().setX(75);
        bird.getBirdSprite().setY(250);
        bird.getHitbox().setPosition(bird.getBirdSprite().getX() + bird.getHitbox().radius,
                bird.getBirdSprite().getY() + bird.getHitbox().radius);
    }

    /**
     * Rendering of the all objects on the screen.
     *
     * @param delta time delta
     */
    @Override
    public void render(float delta) {

        ScreenUtils.clear(0f, 0f, 0f, 1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        spaceBackground.renderBackground();

        for (Barrier barrier : new Array.ArrayIterator<>(barriers)) {
            barrier.render(game.batch);
        }

        stage.draw();

        bird.render(game.batch);

        bird.birdGetSmaller();

        for (Item item : items) {
            item.render(game.batch);
        }

        game.batch.end();

        //Debug Hitbox
        /*shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.CYAN);
        for(Barrier barrier : barriers){
            shapeRenderer.setColor(Color.CYAN);
            shapeRenderer.rect(barrier.getHitbox().x, barrier.getHitbox().y,
                    barrier.getHitbox().width, barrier.getHitbox().height);
        }
        shapeRenderer.circle(bird.getHitbox().x, bird.getHitbox().y, bird.getHitbox().radius);
        shapeRenderer.end();*/

        if (gameOver) {
            game.resetGameSpeed();
            gameOverScreen.render(delta);
            bird.birdDies();
        } else {
            update(delta);
        }
    }

    /**
     * Calculation and Updates of values.
     */
    private void update(float delta) {
        int randomNum = 0;

        if (bird.getScoreCollectable() < 3) {
            bird.setHelmetactive(false);
        }

        if (TimeUtils.nanoTime() - lastInvisibleTime > 1500000000) {
            bird.setInvincible(false);
        }
        if (TimeUtils.nanoTime() - bird.getLastMultiplierTime() > 10000000000L) {
            bird.setMultiplier(1);
        }

        if(bird.getBirdWidth() < 101) {
            if (TimeUtils.nanoTime() - bird.getLastShrinkTime() > 8000000000L && TimeUtils.nanoTime() - bird.getLastShrinkTime() < 8500000000L) {
                bird.setBirdWidth(80);
            }
            else if(TimeUtils.nanoTime() - bird.getLastShrinkTime() >8500000000L && TimeUtils.nanoTime() - bird.getLastShrinkTime() < 9000000000L) {
                bird.setBirdWidth(50);
            }
            else if(TimeUtils.nanoTime() - bird.getLastShrinkTime() > 9000000000L && TimeUtils.nanoTime() - bird.getLastShrinkTime() < 9500000000L){
                bird.setBirdWidth(80);
            }
            else if(TimeUtils.nanoTime() - bird.getLastShrinkTime() > 9500000000L && TimeUtils.nanoTime() - bird.getLastShrinkTime() < 9800000000L) {
                bird.setBirdWidth(50);
            }
        }

        if (TimeUtils.nanoTime() - bird.getLastShrinkTime() > 10000000000L) {
            bird.getHitbox().setRadius(50);
            if (bird.getBirdWidth() < 100) {
                bird.getHitbox().setPosition(bird.getBirdSprite().getX() + bird.getHitbox().radius,
                        bird.getBirdSprite().getY() + bird.getHitbox().radius);
                bird.setBirdWidth((int) (bird.getHitbox().radius * 2));
            }
        }
        if (runGame && TimeUtils.nanoTime() - currentTime > 500000000) {
            game.increaseGameSpeed();
            currentTime = TimeUtils.nanoTime();
        }

        highscoreLabel.setText("Highscore: " + (int) bird.getHighscore() /*+ " " + Background.FOREGROUNDSPEED*/);
        collectableLabel.setText(bird.getScoreCollectable() + " / 3");

        float speed = game.getSpeedMultiplier() * 60;
        double roundOff = Math.round(speed * 100.0) / 100.0;

        speedLabel.setText("Speed: " + roundOff + "km/h");

        if (bird.getScoreCollectable() == 1) {
            table.getCells().get(0).setActor(new Image(pommespackung_eins));
        } else if (bird.getScoreCollectable() == 2) {
            table.getCells().get(0).setActor(new Image(pommespackung_zwei));
        } else if (bird.getScoreCollectable() == 3) {
            table.getCells().get(0).setActor(new Image(pommespackung_drei));
        } else {
            table.getCells().get(0).setActor(new Image(pommespackung_leer));
        }

        for (Barrier barrier : new Array.ArrayIterator<>(barriers)) {
            if (!bird.isInvincible()) {
                if (Intersector.overlaps(bird.getHitbox(), barrier.getBarrierSprite().getBoundingRectangle())) {
                    if (bird.getScoreCollectable() == 3) {
                        lastInvisibleTime = TimeUtils.nanoTime();
                        bird.setInvincible(true);
                        game.helm_cracked.play();
                        bird.setScoreCollectable(0);
                    } else {
                        game.spaceMusic.stop();
                        gameOver = true;
                        runGame = false;
                        game.die_Sound.play();
                        gameOverScreen = new GameOverScreen(game, bird.getHighscore());
                        bird.setDieTime(TimeUtils.millis());
                    }
                }
                if (bird.getHitbox().y > Configuration.ScreenHeight && barrier.getBarrierSprite().getX() <= bird.getBirdSprite().getX()) {
                    game.spaceMusic.stop();
                    gameOver = true;
                    runGame = false;
                    gameOverScreen = new GameOverScreen(game, bird.getHighscore());
                    bird.setDieTime(TimeUtils.millis());
                }
            }
        }
        for (Iterator<Item> iter = items.iterator(); iter.hasNext(); ) {
            Item item = iter.next();
            if (Intersector.overlaps(bird.getHitbox(), item.getHitbox())) {
                item.collide(bird, game);
                iter.remove();
            }
            if (item.getItemSprite().getX() < -item.getItemSprite().getWidth()) {
                iter.remove();
            }
        }
        //beim Drücken der Leertaste soll die Zeile"press space to ......" verschwenden und das spiel wird in Bewegung gesetzt
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.justTouched()) {
            runGame = true;
            tablePressSpace.clear();
            currentTime = TimeUtils.nanoTime();
        }
        if (runGame && !gameOver) {
            bird.move(delta);
            for (Item item : items) {
                item.move();
            }
            for (Barrier barrier : new Array.ArrayIterator<>(barriers)) {
                barrier.move(delta);
            }
        }

        spaceBackground.move();

        for (Barrier barrier : new Array.ArrayIterator<>(barriers)) {
            if (bird.getBirdSprite().getX() >= barrier.getBarrierSprite().getX() && barrier.getWealth() != 0) {
                bird.setHighscore(bird.getHighscore() + (barrier.getWealth() * bird.getMultiplier()));
                barrier.setWealth(0);
            }
            if (barrier.getBarrierSprite().getX() < (0 - barrier.getBarrierSprite().getWidth())) {
                barrier.getBarrierSprite().setX(barrier.getBarrierSprite().getX() + (barriers.size / 2f) * barrier.getDistance());
                barrier.getHitbox().setX(barrier.getHitbox().getX() + (barriers.size / 2f) * barrier.getDistance());
                if (!barrier.isDown()) {
                    randomNum = MathUtils.random(200, 1025);
                    barrier.getBarrierSprite().setY(randomNum);
                    barrier.getHitbox().setY(barrier.getBarrierSprite().getY());
                    createItems(barrier.getBarrierSprite().getX() + (barrier.getDistance() / 2));
                } else {
                    barrier.getBarrierSprite().setY(randomNum - barrier.getBarrierSprite().getHeight() - barrier.getGap());
                    barrier.getHitbox().setY(barrier.getBarrierSprite().getY());
                }

                barrier.setWealth(game.getDifficulty() / 2.0f);
            }
        }
    }

    /**
     * Creates the Barriers for the game.
     */
    public void createBarriers() {
        for (int i = 0; i < 10; i++) {
            //bestimmt den pixel an dem ein Turm spawnen darf
            int randomNum = MathUtils.random(200,1025);

            Barrier b = new Barrier(
                    Gdx.graphics.getWidth() + new Barrier(0, 0, Configuration.spaceBarrierDownImg,
                            game.getDifficulty()).getDistance() * i,
                    randomNum, Configuration.spaceBarrierDownImg, game.getDifficulty());
            b.getHitbox().setPosition(b.getHitbox().x - 5 ,b.getHitbox().y);
            barriers.add(b);
            Barrier b2 = new Barrier(Gdx.graphics.getWidth() + (b.getDistance() * i),
                    randomNum - b.getBarrierSprite().getHeight() - b.getGap(), Configuration.spaceBarrierUpImg, game.getDifficulty());
            //b2.getBarrierSprite().setRotation(180f);
            b2.setDown(true);
            b2.getHitbox().setX(b.getHitbox().x);
            barriers.add(b2);
            createItems(b.getBarrierSprite().getX() + b.getDistance() / 2);
        }
    }

    private void createItems(float xPos) {
        int randomNum1 = MathUtils.random(
                200, Gdx.graphics.getHeight() - 200);
        int randomNum2 = MathUtils.random(0, 100);

        if (randomNum2 < 10) {
            items.add(new Fries(xPos, randomNum1));
        } else if (randomNum2 < 15) {
            items.add(new Multiplier(xPos, randomNum1));
        } else if (randomNum2 < 20) {
            items.add(new Shrink(xPos, randomNum1));
        } else if (randomNum2 < 23) {
            items.add(new Slow(xPos, randomNum1));
        } else if (randomNum2 < 28) {
            items.add(new ReduceScore(xPos, randomNum1));
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
        bird.spaceScreen = false;
    }
}
