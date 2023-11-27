package Model;

import Controller.PlayerInputManager;
import Model.Enemies.Enemy;
import Model.Enemies.EnemyManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.List;


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
    private final EnemyManager enemyManager;
    private int playerX = 64;
    private int playerY = 64;
    private boolean isSprinting = false;
    private char direction = 's';
    private boolean isAttacking = false;
    private float attackTimer = 0f;
    private static final float ATTACK_DURATION = 0.4f;

    private List<Enemy> enemies;

    public Player() {
        currentState = PlayerState.STANDING;
        inputManager = new PlayerInputManager(this);
        animationManager = new PlayerAnimationManager();
        mapModel = new MapModel();
        enemyManager = new EnemyManager();
    }

    public static Player getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Player();
        }
        return INSTANCE;
    }

    public void setEnemies(List<Enemy> enemies){
        this.enemies = enemies;
    }

    public void update(float delta) {
        inputManager.handleInput();
        animationManager.update(delta);
        // Check for melee attack and collisions with enemies
        updateAttackTimer(delta);
    }

    public void checkMeleeAttack(){
        if (Gdx.input.isKeyJustPressed(Input.Keys.K)) {
            // Handle melee attacks in the direction the player is facing
            switch (getDirection()) {
                case 'w':
                    attackUp();
                    break;
                case 's':
                    attackDown();
                    break;
                case 'a':
                    attackLeft();
                    break;
                case 'd':
                    attackRight();
                    break;
            }
        }
    }

    private void updateAttackTimer(float delta) {
        if (isAttacking) {
            attackTimer += delta;
            if (attackTimer >= ATTACK_DURATION) {
                isAttacking = false;
                attackTimer = 0f;
                currentState = PlayerState.STANDING;  // Ritorna allo stato di standing dopo l'attacco
                animationManager.resetAttack();
            }
        }
    }


    public boolean isCollision(float x, float y){
        //check for collision with map object
        return mapModel.isCollisionWithScaledObjects(x, y, PLAYER_WIDTH+15, PLAYER_HEIGHT+15);
    }

    public void moveUp() {
        currentState = PlayerState.WALK_UP;
        playerY += SPEED;

        setDirection('w');
    }

    public void moveDown() {
        currentState = PlayerState.WALK_DOWN;
        playerY -= SPEED;

        setDirection('s');
    }

    public void moveLeft() {
        currentState = PlayerState.WALK_LEFT;
        playerX -= SPEED;

        setDirection('a');
    }
    public void moveRight() {
        currentState = PlayerState.WALK_RIGHT;
        playerX += SPEED;

        setDirection('d');
    }

    public void attackUp(){
        currentState = PlayerState.ATTACK_UP;
        System.out.println("ATTACK_UP");
        isAttacking = true;
        animationManager.resetAttack();
    }

    public void attackDown(){
        currentState = PlayerState.ATTACK_DOWN;
        System.out.println("ATTACK_DOWN");
        isAttacking = true;
        animationManager.resetAttack();
    }

    public void attackRight(){
        currentState = PlayerState.ATTACK_RIGHT;
        System.out.println("ATTACK_RIGHT");
        isAttacking = true;
        animationManager.resetAttack();
    }

    public void attackLeft(){
        currentState = PlayerState.ATTACK_LEFT;
        System.out.println("ATTACK_LEFT");
        isAttacking = true;
        animationManager.resetAttack();
    }

    public Boolean upColliding()
    {
        return isCollision(getPlayerX() + (getPLAYER_WIDTH() / 4), getPlayerY() + getSPEED());
    }

    public Boolean downColliding()
    {
        return isCollision(getPlayerX() + (getPLAYER_WIDTH() / 4), getPlayerY() - getSPEED());
    }

    public Boolean leftColliding()
    {
        return isCollision(getPlayerX() + (getPLAYER_WIDTH() / 4) - getSPEED(), getPlayerY());
    }

    public Boolean rightColliding()
    {
        return isCollision(getPlayerX() + (getPLAYER_WIDTH() / 4) + getSPEED(), getPlayerY());
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

        if(isSprinting) {
            SPEED *= 1.5;
            animationManager.updateAnimSpeed(0.07f);
        }
        else
            animationManager.updateAnimSpeed(0.1f);

        this.SPEED = SPEED;
    }

    public char getDirection() {
        return direction;
    }

    public void setDirection(char direction) {
        this.direction = direction;
    }

    public boolean isSprinting() {
        return isSprinting;
    }

    public boolean isAttacking(){
        return isAttacking;
    }

    public void setSprinting(Boolean isSprinting) {
        this.isSprinting = isSprinting;
    }

    public PlayerAnimationManager getAnimationManager() {
        return animationManager;
    }
}

