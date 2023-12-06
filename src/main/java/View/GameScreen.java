package View;

import Controller.PlayerInputManager;
import Model.Bullet;
import Model.Enemies.Enemy;
import Model.Enemies.EnemyManager;
import Model.MapModel;
import Model.Object.ObjectManager;
import Model.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
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

    public GameScreen(OrthographicCamera camera) {
        this.batch = new SpriteBatch();
        this.hud = new Hud(batch);
        this.camera = camera;
        this.camera.position.set(new Vector3(Boot.INSTANCE.getScreenWidth()/2,Boot.INSTANCE.getScreenHeight()/2,0 ));
        this.playerViewport = new FitViewport(Boot.INSTANCE.getScreenWidth()/2, Boot.INSTANCE.getScreenHeight()/2, camera);
        this.world = new World(new Vector2(0,0), false);
        this.box2DDebugRenderer = new Box2DDebugRenderer();
        this.player = Player.getInstance();
        this.playerInputManager = new PlayerInputManager(player);
        this.mapModel = new MapModel();
        this.enemyManager = new EnemyManager();
        this.enemyManager.initializeEnemies();
        this.player.setEnemies(enemyManager.getEnemies());
        this.shapeRenderer = new ShapeRenderer();
        this.objects = new ObjectManager();
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
        player.update(delta);
        playerInputManager.update(delta);
        enemyManager.update(delta);
        objects.update(delta);
    }

    @Override
    public void render(float delta){
        update(delta);
        camera.position.set(player.getPlayerX() + player.getPLAYER_WIDTH() / 2 , player.getPlayerY() + player.getPLAYER_HEIGHT() / 2 , 0);
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
        batch.setProjectionMatrix(hud.getStage().getCamera().combined); //set the spriteBatch to draw what our stageViewport sees
        hud.getStage().act(delta); //act the Hud
        hud.getStage().draw(); //draw the Hud
        //DEBUG
        //renderDebug();
        //renderPlayerCollisionDebug();
    }

    private void renderPlayerCollisionDebug() {
        // Set the ShapeRenderer's projection matrix to the camera's combined matrix
        shapeRenderer.setProjectionMatrix(camera.combined);

        // Begin drawing shapes
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        // Set the color for the debug rendering (choose a color you prefer)
        shapeRenderer.setColor(Color.MAGENTA);

        // Draw a rectangle around the player's collision box
        shapeRenderer.rect(player.getPlayerX() + (player.getPLAYER_WIDTH()/4) , player.getPlayerY() , player.getPLAYER_WIDTH()/2+15, player.getPLAYER_HEIGHT()/2+15);

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
        shapeRenderer.end();
    }

    @Override
    public void dispose(){

        shapeRenderer.dispose();
        hud.dispose();
        batch.dispose();
    }
}
