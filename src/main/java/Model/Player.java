package Model;

import Controller.PlayerInputManager;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class Player{
    private static Player INSTANCE;
    private final int PLAYER_WIDTH = 32;
    private final int PLAYER_HEIGHT = 32;
    private final int SCALE = 3;
    private int SPEED = 5;
    public PlayerState currentState;
    private final PlayerInputManager inputManager;
    private final PlayerAnimationManager animationManager;
    private final MapModel mapModel;
    private int playerX = 64;
    private int playerY = 64;
    private boolean isSprinting = false;

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
        return mapModel.isCollisionWithScaledObjects(x, y, PLAYER_WIDTH+15, PLAYER_HEIGHT+15);
    }

    public void moveUp() {
        currentState = PlayerState.WALK_UP;
        playerY += SPEED;
    }

    public void moveDown() {
        currentState = PlayerState.WALK_DOWN;
        playerY -= SPEED;
    }

    public void moveLeft() {
        currentState = PlayerState.WALK_LEFT;
        playerX -= SPEED;
    }
    public void moveRight() {
        currentState = PlayerState.WALK_RIGHT;
        playerX += SPEED;
    }

    public void attackUp(){
        currentState = PlayerState.ATTACK_UP;
        System.out.println("ATTACK_UP");
        animationManager.resetAttack();
    }

    public void attackDown(){
        currentState = PlayerState.ATTACK_DOWN;
        System.out.println("ATTACK_DOWN");
        animationManager.resetAttack();
    }

    public void attackRight(){
        currentState = PlayerState.ATTACK_RIGHT;
        System.out.println("ATTACK_RIGHT");
        animationManager.resetAttack();
    }

    public void attackLeft(){
        currentState = PlayerState.ATTACK_LEFT;
        System.out.println("ATTACK_LEFT");
        animationManager.resetAttack();
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
    public void setSPEED(int SPEED) {
        this.SPEED = SPEED;
    }

    public void setIsSprinting ( boolean isSprinting){
        this.isSprinting = isSprinting;
        if(this.isSprinting) {
            setSPEED(8);
            animationManager.updateAnimSpeed(0.07f);

        }
        else {
            setSPEED(4);
            animationManager.updateAnimSpeed(0.1f);
        }
    }
    public PlayerAnimationManager getAnimationManager() {
        return animationManager;
    }
}

