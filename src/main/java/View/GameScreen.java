package View;

import Controller.MenuMediator;
import Controller.PlayerInputManager;
import Model.*;
import Model.Enemies.Enemy;
import Model.Enemies.EnemyManager;
import Model.Enemies.MetalRobot.MetalRobot;
import Model.Object.ObjectManager;
import View.Hud.Hud;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import org.lwjgl.opengl.GL20;

/**
 * La classe `GameScreen` rappresenta la schermata di gioco principale.
 * Gestisce l'aggiornamento della logica di gioco, il rendering degli elementi di gioco e la gestione degli input.
 * @author Alin Marian Habasescu
 * @author Francesco Gambone
 * @author Manda Hery Ny Aina
 * @author Gabriele Zimmerhofer
 *
 */

public class GameScreen extends ScreenAdapter {
    private final OrthographicCamera camera;
    private final SpriteBatch batch;
    private final World world;
    private final Box2DDebugRenderer box2DDebugRenderer;
    private Player player;
    private final PlayerInputManager playerInputManager;
    private MapModel mapModel;
    private EnemyManager enemyManager;
    private ShapeRenderer shapeRenderer;
    private ObjectManager objects;
    private Hud hud;
    private FitViewport playerViewport;
    private float shakeDuration = 0f;
    private float shakeIntensity = 5f;

    private boolean isPaused = false;

    private MenuMediator menuMediator;

    /**
     * Costruttore della classe `GameScreen`.
     *
     * @param camera La camera ortografica utilizzata per la visualizzazione.
     */
    public GameScreen(OrthographicCamera camera) {
        this.batch = new SpriteBatch();
        this.camera = camera;
        this.camera.position.set(new Vector3(Boot.INSTANCE.getScreenWidth() / 2, Boot.INSTANCE.getScreenHeight() / 2, 0));
        this.playerViewport = new FitViewport(Boot.INSTANCE.getScreenWidth() / 2, Boot.INSTANCE.getScreenHeight() / 2, camera);
        this.world = new World(new Vector2(0, 0), false);
        this.box2DDebugRenderer = new Box2DDebugRenderer();
        this.player = Player.getInstance();
        this.playerInputManager = new PlayerInputManager(player);
        this.mapModel = MapModel.getInstance();
        this.enemyManager = new EnemyManager();
        this.enemyManager.initializeEnemies();
        this.player.setEnemies(enemyManager.getEnemies());
        this.shapeRenderer = new ShapeRenderer();
        this.objects = new ObjectManager();
        this.objects.initializeObject();
        this.menuMediator = new MenuMediator();
        this.hud = new Hud(batch, objects, menuMediator);

        this.enemyManager.addObjectManager(objects);
        this.mapModel.addObjectManager(objects);

    }

    @Override
    public void show() {
        mapModel.getNpcManager().addObserversToNPC(this.hud);
    }

    /**
     * Metodo per l'avanzamento del gioco e aggiornamento della logica di gioco.
     *
     * @param delta Tempo trascorso dall'ultimo frame in secondi.
     */
    public void update(float delta) {
        world.step(1 / 60f, 6, 2);
        batch.setProjectionMatrix(camera.combined);
        mapModel.update(delta);
        player.update(delta);
        playerInputManager.update(delta);
        enemyManager.update(delta);
        objects.update(delta);

        Door.updateSound(delta);
    }

    /**
     * Renderizza gli elementi del gioco principali e gestisce il flusso della logica di gioco.
     * Tramite menuMediator controlla ed esaudisce le richieste di ripresa del gioco, passaggio a schermata di opzioni o ritorno al menu principale.
     *
     * @param delta Il tempo trascorso tra i frame.
     */
    @Override
    public void render(float delta) {


        if (!isPaused) {
            update(delta);
        }


        camera.position.set(player.getPlayerX() + player.getPLAYER_WIDTH() / 2, player.getPlayerY() + player.getPLAYER_HEIGHT() / 2, 0);
        if (shakeDuration > 0) {
            float shakeX = (MathUtils.random() - 0.5f) * 2 * shakeIntensity + player.getPlayerX() + player.getPLAYER_WIDTH() / 2;
            float shakeY = (MathUtils.random() - 0.5f) * 2 * shakeIntensity + player.getPlayerY() + player.getPLAYER_HEIGHT() / 2;

            camera.position.set(shakeX, shakeY, 0);
            shakeDuration -= delta;
        }

        // Applica lo shake della camera se necessario
        camera.update();
        //clear the screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        // Disegna la mappa
        mapModel.render(batch, camera);
        // Disegna gli oggetti di gioco
        objects.draw(batch);
        //Disegna i nemici
        for (Enemy enemy : enemyManager.getEnemies()) {
            batch.draw(enemy.getCurrentFrame(), enemy.getEnemyX(), enemy.getEnemyY(), enemy.getEnemyWidth(), enemy.getEnemyHeight());
        }
        //Disegna il giocatore
        batch.draw(player.getCurrentFrame(), player.getPlayerX(), player.getPlayerY(), player.getPLAYER_WIDTH(), player.getPLAYER_HEIGHT());

        // Disegna proiettili attivi
        for (Bullet bullet : player.getBullets()) {
            batch.draw(bullet.getCurrentFrame(), bullet.getX(), bullet.getY(), 32, 32);
        }


        batch.end();
        hud.update(player, delta);//sets playerLife on hud equal to player's playerLife
        batch.setProjectionMatrix(hud.getStage().getCamera().combined); //set the spriteBatch to draw what our stageViewport sees
        hud.getStage().act(delta); //act the Hud
        hud.getStage().draw(); //draw the Hud

        //DEBUG
        //renderDebug();
        //renderPlayerCollisionDebug();
        //renderEnemyDebug();

        //Gestione della Pausa

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {


            isPaused = !isPaused;

            hud.setMenuVisibility();


        }

        if (isPaused) {

            //se continua nel menu è stato premuto
            if (menuMediator.isChangeGameState()) {
                menuMediator.changeGameStatus();
                hud.setMenuVisibility();
                isPaused = false;


            }
            //se opzioni nel menu è stato premuto
            if (menuMediator.isChangeToOptionsScreen()) {
                menuMediator.changeToOptionsScreen();

                Boot.INSTANCE.setScreen(new OptionScreen(this.camera, true));
            } else {
                hud.setInputProcessorOn();
            }


            //se exit nel menu è stato premuto
            if (menuMediator.isChangeToMainMenuScreen()) {
                menuMediator.changeToMenuScreen();
                hud.setMenuVisibility();
                returnToMainMenuScreen();


            }
        }
        //fine gestione pausa

        //se il giocatore ha finito il gioco vincendo o morendo
        if (player.hasPlayerWon()) {
            endGame(true);
        } else if (player.isPlayerDead()) {
            endGame(false);
        }


    }

    /**
     * Agita la telecamera per una durata specifica con una certa intensità.
     *
     * @param duration  La durata dell'agitazione in secondi.
     * @param intensity L'intensità dell'agitazione.
     */
    public void shakeCamera(float duration, float intensity) {
        shakeDuration = duration;
        shakeIntensity = intensity;
    }

    /**
     * Metodo per il rendering del debug della collisione del giocatore.
     * Disegna un rettangolo intorno alla collision box del giocatore utilizzando `ShapeRenderer`.
     */
    private void renderPlayerCollisionDebug() {
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.MAGENTA);

        Rectangle rectangle = player.getHitBox();
        shapeRenderer.rect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);

        shapeRenderer.end();
    }

    /**
     * Metodo per il rendering del debug generale.
     * Disegna i rettangoli delle collisioni degli oggetti sulla mappa e delle porte interattive.
     */
    private void renderDebug() {
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        // Disegna i rettangoli delle collisioni degli oggetti sulla mappa
        for (MapObject object : mapModel.getScaledCollisionObjects()) {
            if (object instanceof RectangleMapObject) {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();
                shapeRenderer.rect(rect.x, rect.y, rect.width, rect.height);
            }
        }

        // Disegna i rettangoli delle porte interattive
        for (Interactable door : mapModel.getInteractables()) {
            if (door instanceof Door) {
                Rectangle rect = ((Door) door).getBoundingBox();
                shapeRenderer.rect(rect.x, rect.y, rect.width, rect.height);
            }
        }

        shapeRenderer.end();
    }

    /**
     * Metodo per il rendering del debug degli nemici.
     * Disegna i rettangoli delle collisioni degli nemici, i loro campi di visione e linee di vista.
     */
    private void renderEnemyDebug() {
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        // Disegna i rettangoli delle collisioni degli nemici
        for (Enemy enemy : player.getEnemies()) {
            Rectangle rect = enemy.getHitBox();
            shapeRenderer.rect(rect.x, rect.y, rect.width, rect.height);

            // Se l'nemico è un MetalRobot, disegna il suo campo di visione e la linea di vista
            if (enemy instanceof MetalRobot) {
                shapeRenderer.circle(enemy.getEnemyX() + enemy.getEnemyWidth() / 2, enemy.getEnemyY() + enemy.getEnemyHeight() / 2, ((MetalRobot) enemy).getChasingArea());
                shapeRenderer.circle(enemy.getEnemyX() + enemy.getEnemyWidth() / 2, enemy.getEnemyY() + enemy.getEnemyHeight() / 2, 90);

                if (((MetalRobot) enemy).isChasing()) {
                    shapeRenderer.line(
                            rect.x + rect.width / 2,
                            rect.y + rect.height / 2,
                            player.getHitBox().x + player.getHitBox().width / 2,
                            player.getHitBox().y + player.getHitBox().height / 2
                    );
                }
            }
        }

        shapeRenderer.end();
    }
    /**
     * Termina il gioco, visualizzando la schermata di Game Over.
     *
     * @param gameEnding true se il giocatore ha vinto, altrimenti false.
     */
    public void endGame(boolean gameEnding) {
        player.resetPlayer();
        mapModel.resetMapModel();
        Boot.INSTANCE.setScreen(new GameOverScreen(GameScreen.this.camera, gameEnding));
        dispose();
    }

    /**
     * Ritorna alla schermata del Menu Principale.
     */
    private void returnToMainMenuScreen() {
        player.resetPlayer();
        mapModel.resetMapModel();
        Boot.INSTANCE.setScreen(new MainMenuScreen(GameScreen.this.camera));
        dispose();
    }

    /**
     * Rilascia le risorse allocate dalla classe. Chiamato quando la schermata viene chiusa o si passa al menu principale.
     */
    @Override
    public void dispose() {
        shapeRenderer.dispose();
        hud.dispose();
        batch.dispose();
    }

    /**
     * Restituisce l'oggetto `hud`
     *
     * @return L'oggetto `hud`.
     */
    public Hud getHud() {
        return hud;
    }
}

