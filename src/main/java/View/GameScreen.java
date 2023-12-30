package View;

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

    public GameScreen(OrthographicCamera camera) {
        this.batch = new SpriteBatch();

        this.camera = camera;
        this.camera.position.set(new Vector3(Boot.INSTANCE.getScreenWidth()/2,Boot.INSTANCE.getScreenHeight()/2,0 ));
        this.playerViewport = new FitViewport(Boot.INSTANCE.getScreenWidth()/2, Boot.INSTANCE.getScreenHeight()/2, camera);
        this.world = new World(new Vector2(0,0), false);
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
        this.hud = new Hud(batch, objects);
    }

    @Override
    public void show(){

    }
    public void update(float delta){
        world.step(1/60f, 6, 2);
        batch.setProjectionMatrix(camera.combined);
        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            Gdx.app.exit();
        }
        mapModel.update(delta);
        player.update(delta);
        playerInputManager.update(delta);
        enemyManager.update(delta);
        objects.update(delta);

    }

    @Override
    public void render(float delta){
        update(delta);

        camera.position.set(player.getPlayerX() + player.getPLAYER_WIDTH() / 2 , player.getPlayerY() + player.getPLAYER_HEIGHT() / 2 , 0);
        if (shakeDuration > 0) {
            float shakeX = (MathUtils.random() - 0.5f) * 2* shakeIntensity + player.getPlayerX() + player.getPLAYER_WIDTH() / 2;
            float shakeY = (MathUtils.random() - 0.5f) * 2 * shakeIntensity + player.getPlayerY() + player.getPLAYER_HEIGHT() / 2;

            camera.position.set(shakeX, shakeY, 0);
            shakeDuration -= delta;
        }

        // Applica lo shake della camera se necessario
        camera.update();
        //clear the screen
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        // Disegna la mappa
        mapModel.render(batch,camera);
        // Disegna gli oggetti di gioco
        objects.draw(batch);
        //Disegna i nemici
        for (Enemy enemy : enemyManager.getEnemies()) {
            batch.draw(enemy.getCurrentFrame(), enemy.getEnemyX(), enemy.getEnemyY(), enemy.getEnemyWidth(), enemy.getEnemyHeight());
        }
        //Disegna il giocatore
        batch.draw(player.getCurrentFrame(),player.getPlayerX(), player.getPlayerY(), player.getPLAYER_WIDTH(), player.getPLAYER_HEIGHT());

        // Disegna proiettili attivi
        for(Bullet bullet : player.getBullets()){
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
    }

    public void shakeCamera(float duration , float intensity){
        shakeDuration = duration;
        shakeIntensity = intensity;
    }

    private void renderPlayerCollisionDebug() {
        // Set the ShapeRenderer's projection matrix to the camera's combined matrix
        shapeRenderer.setProjectionMatrix(camera.combined);

        // Begin drawing shapes
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        // Set the color for the debug rendering (choose a color you prefer)
        shapeRenderer.setColor(Color.MAGENTA);

        // Draw a rectangle around the player's collision box
        Rectangle rectangle = player.getHitBox();
        shapeRenderer.rect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);

        // End drawing shapes
        shapeRenderer.end();

    }


    private void renderDebug() {
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        // Iterate through collision objects and draw rectangles
        for (MapObject object : mapModel.getScaledCollisionObjects()) {
            if (object instanceof RectangleMapObject) {
                Rectangle rect = ((RectangleMapObject) object).getRectangle();
                shapeRenderer.rect(rect.x, rect.y, rect.width, rect.height);
            }
            // Add additional checks for other object types if needed
        }

        for(Interactable door : mapModel.getInteractables()){
            if(door instanceof Door){
                Rectangle rect = ((Door) door).getBoundingBox();
                shapeRenderer.rect(rect.x, rect.y, rect.width, rect.height);
            }
        }
        shapeRenderer.end();
    }

    private void renderEnemyDebug() {
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        for(Enemy enemy : player.getEnemies()){
            Rectangle rect = enemy.getHitBox();
            shapeRenderer.rect(rect.x, rect.y, rect.width, rect.height);
            if(enemy instanceof MetalRobot){
                shapeRenderer.circle(enemy.getEnemyX() + enemy.getEnemyWidth() / 2, enemy.getEnemyY() + enemy.getEnemyHeight() / 2, ((MetalRobot)enemy).getChasingArea());
                shapeRenderer.circle(enemy.getEnemyX() + enemy.getEnemyWidth() / 2, enemy.getEnemyY() + enemy.getEnemyHeight() / 2, 90);
                // Draw the line of sight
                if (((MetalRobot)enemy).isChasing()) {
                    shapeRenderer.line(
                            rect.x + rect.width/2,
                            rect.y + rect.height/2,
                            player.getHitBox().x + player.getHitBox().width/2,
                            player.getHitBox().y + player.getHitBox().height/2
                    );
                }
            }
        }
        shapeRenderer.end();
    }

    @Override
    public void dispose(){
        shapeRenderer.dispose();
        hud.dispose();
        batch.dispose();
    }

    public Hud getHud() {
        return hud;
    }
}
