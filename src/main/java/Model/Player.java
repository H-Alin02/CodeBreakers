package Model;

import Controller.PlayerInputManager;
import Model.Enemies.Enemy;
import Model.Enemies.EnemyManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.List;


public class Player {
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
    private int playerX = 832;
    private int playerY = 1408;
    private boolean isSprinting = false;
    private char direction = 's';
    private boolean isAttacking = false;
    private boolean isShooting = false;
    private float attackTimer = 0f;
    private static final float ATTACK_DURATION = 0.4f;
    private float shootTimer = 0f;
    private static final float SHOOT_DURATION = 0.21f;
    private final int PLAYER_DAMAGE = 10;
    private float bulletSpeed = 5;

    private List<Enemy> enemies;
    private List<Bullet> bullets;

    private Player() {
        currentState = PlayerState.STANDING;
        inputManager = new PlayerInputManager(this);
        animationManager = new PlayerAnimationManager();
        mapModel = new MapModel();
        enemyManager = new EnemyManager();
        setEnemies(enemyManager.getEnemies());
        bullets = new ArrayList<>();
    }

    public static Player getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Player();
        }
        return INSTANCE;
    }

    public void setEnemies(List<Enemy> enemies) {
        this.enemies = enemies;
    }

    public void update(float delta) {
        inputManager.handleInput();
        animationManager.update(delta);
        // Check for melee attack and collisions with enemies
        updateAttackTimer(delta);
        updateShootTimer(delta);

        // Aggiorna i proiettili
        for (Bullet bullet : bullets) {
            bullet.update(delta);
            // Aggiungi la logica di collisione qui, se necessario
        }
        // Rimuovi i proiettili inattivi
        bullets.removeIf(bullet -> !bullet.isActive());

    }

    public void checkMeleeAttack() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.K) && !isAttacking) {
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
                inflictDamageToEnemies();
            }
        }
    }

    private void updateShootTimer(float delta) {
        if (isShooting) {
            shootTimer += delta;
            if (shootTimer >= SHOOT_DURATION) {
                isShooting = false;
                shootTimer = 0f;
                currentState = PlayerState.STANDING;  // Ritorna allo stato di standing dopo l'attacco
                animationManager.resetShoot();
            }
        }
    }

    private void inflictDamageToEnemies() {
        // Get the attack direction and calculate the attack area
        float attackX = playerX;
        float attackY = playerY;
        float attackWidth = PLAYER_WIDTH;
        float attackHeight = PLAYER_HEIGHT;

        switch (getDirection()) {
            case 'w':
                attackY += PLAYER_HEIGHT;
                attackHeight *= 2;
                break;
            case 's':
                attackHeight *= 2;
                break;
            case 'a':
                attackX -= PLAYER_WIDTH/2;
                attackWidth *= 2;
                break;
            case 'd':
                attackX += PLAYER_WIDTH;
                attackWidth *= 2;
                break;
        }

        // Iterate through enemies and check for collision with the attack area
        for (Enemy enemy : enemies) {
            if (isCollisionWithAttackArea(attackX, attackY, attackWidth, attackHeight, enemy)) {
                // Inflict damage to the enemy
                enemy.takeDamage(PLAYER_DAMAGE);
            }
        }
    }

    private boolean isCollisionWithAttackArea(float x, float y, float width, float height, Enemy enemy) {
        float enemyX = enemy.getEnemyX();
        float enemyY = enemy.getEnemyY();
        float enemyWidth = enemy.getEnemyWidth();
        float enemyHeight = enemy.getEnemyHeight();

        return x < enemyX + enemyWidth &&
                x + width > enemyX &&
                y < enemyY + enemyHeight &&
                y + height > enemyY;
    }


    public boolean isCollision(float x, float y) {
        //check for collision with map object
        return mapModel.isCollisionWithScaledObjects(x, y, PLAYER_WIDTH + 15, PLAYER_HEIGHT + 15);
    }

    public void shoot() {
        // Aggiungi un nuovo proiettile in base alla direzione corrente del giocatore
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && !isShooting) {
            switch (getDirection()) {
                case 'w':
                    isShooting = true;
                    currentState = PlayerState.SHOOT_UP;
                    animationManager.resetShoot();
                    break;
                case 's':
                    isShooting = true;
                    currentState = PlayerState.SHOOT_DOWN;
                    animationManager.resetShoot();
                    break;
                case 'd':
                    isShooting = true;
                    currentState = PlayerState.SHOOT_RIGHT;
                    animationManager.resetShoot();
                    break;
                case 'a':
                    isShooting = true;
                    currentState = PlayerState.SHOOT_LEFT;
                    animationManager.resetShoot();
                    break;
            }
        }
        System.out.println("Player state : " + currentState);
        Bullet bullet = new Bullet(getPlayerX(), getPlayerY(), bulletSpeed, getDirection());
        bullets.add(bullet);

    }

    public void moveUp() {
        setDirection('w');

        if(upColliding())
            return;

        currentState = PlayerState.WALK_UP;
        playerY += SPEED;
    }

    public void moveDown() {
        setDirection('s');

        if(downColliding())
            return;

        currentState = PlayerState.WALK_DOWN;
        playerY -= SPEED;
    }

    public void moveLeft() {
        setDirection('a');

        if(leftColliding())
            return;

        currentState = PlayerState.WALK_LEFT;
        playerX -= SPEED;
    }

    public void moveRight() {
        setDirection('d');

        if(rightColliding())
            return;

        currentState = PlayerState.WALK_RIGHT;
        playerX += SPEED;
    }

    public void attackUp() {
        currentState = PlayerState.ATTACK_UP;
        //System.out.println("ATTACK_UP");
        isAttacking = true;
        animationManager.resetAttack();
    }

    public void attackDown() {
        currentState = PlayerState.ATTACK_DOWN;
        //System.out.println("ATTACK_DOWN");
        isAttacking = true;
        animationManager.resetAttack();
    }

    public void attackRight() {
        currentState = PlayerState.ATTACK_RIGHT;
        //System.out.println("ATTACK_RIGHT");
        isAttacking = true;
        animationManager.resetAttack();
    }

    public void attackLeft() {
        currentState = PlayerState.ATTACK_LEFT;
        //System.out.println("ATTACK_LEFT");
        isAttacking = true;
        animationManager.resetAttack();
    }

    public Boolean upColliding() {
        return isCollision(getPlayerX() + (getPLAYER_WIDTH() / 4), getPlayerY() + getSPEED());
    }

    public Boolean downColliding() {
        return isCollision(getPlayerX() + (getPLAYER_WIDTH() / 4), getPlayerY() - getSPEED());
    }

    public Boolean leftColliding() {
        return isCollision(getPlayerX() + (getPLAYER_WIDTH() / 4) - getSPEED(), getPlayerY());
    }

    public Boolean rightColliding() {
        return isCollision(getPlayerX() + (getPLAYER_WIDTH() / 4) + getSPEED(), getPlayerY());
    }

    public TextureRegion getCurrentFrame() {
        return animationManager.getKeyFrame(currentState);
    }

    public int getPlayerX() {
        return playerX;
    }

    public int getPlayerY() {
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

        if (isSprinting) {
            SPEED *= 1.5;
            animationManager.updateAnimSpeed(0.07f);
        } else
            animationManager.updateAnimSpeed(0.1f);

        this.SPEED = SPEED;
    }

    public char getDirection() {
        return direction;
    }

    public void setDirection(char direction) {
        this.direction = direction;
    }

    public float getAttackTimer() {
        return attackTimer;
    }


    public boolean isSprinting() {
        return isSprinting;
    }

    public boolean isAttacking() {
        return isAttacking;
    }

    public  boolean isShooting(){
        return isShooting;
    }

    public void setSprinting(Boolean isSprinting) {
        this.isSprinting = isSprinting;
    }

    public PlayerAnimationManager getAnimationManager() {
        return animationManager;
    }
    public Rectangle getArea(){
        return new Rectangle(getPlayerX(), getPlayerY(),getPLAYER_WIDTH(),getPLAYER_HEIGHT());
    }
}

