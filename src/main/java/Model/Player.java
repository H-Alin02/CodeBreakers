package Model;

import Controller.PlayerInputManager;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class Player{
    private static Player INSTANCE;
    private final int PLAYER_WIDTH = 32;
    private final int PLAYER_HEIGHT = 32;
    private final int SCALE = 3;
    private final int SPEED = 5;
    public PlayerState currentState;
    private final PlayerInputManager inputManager;
    private final PlayerAnimationManager animationManager;
    private final MapModel mapModel;
    private int playerX = 500;
    private int playerY = 500;


    public Player() {
        currentState = PlayerState.STANDING;
        inputManager = new PlayerInputManager(this);
        animationManager = new PlayerAnimationManager();
        mapModel = new MapModel();

    }

    public static Player getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Player();
        }
        return INSTANCE;
    }

    public void update(float delta) {
        inputManager.handleInput();
        animationManager.update(delta);
    }

    public boolean isCollision(float x, float y){
        //check for collision with map object
        return mapModel.isCollisionWithScaledObjects(x, y, PLAYER_WIDTH+15, PLAYER_HEIGHT*2);
    }

    public void moveUp() {
        System.out.println("MOVE UP");
        currentState = PlayerState.WALK_UP;
        playerY += SPEED;
    }

    public void moveDown() {
        System.out.println("MOVE DOWN");
        currentState = PlayerState.WALK_DOWN;
        playerY -= SPEED;
    }

    public void moveLeft() {
        System.out.println("MOVE LEFT");
        currentState = PlayerState.WALK_LEFT;
        playerX -= SPEED;
    }
    public void moveRight() {
        System.out.println("MOVE RIGHT");
        currentState = PlayerState.WALK_RIGHT;
        playerX += SPEED;
    }

    public TextureRegion getCurrentFrame() {
        return animationManager.getKeyFrame(currentState);
    }

    public int getPlayerX() {
        return playerX;
    }

    public int getPlayerY(){
        return playerY;
    }
    public int getPLAYER_HEIGHT() {
        return PLAYER_HEIGHT * SCALE;
    }

    public int getPLAYER_WIDTH() {
        return PLAYER_WIDTH * SCALE;
    }

    public int getSPEED() {
        return SPEED;
    }
}

