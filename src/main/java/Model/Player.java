package Model;

import Controller.PlayerInputManager;
import Model.Enemies.Enemy;
import Model.Enemies.EnemyManager;
import View.Boot;
import View.GameScreen;
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
    private GameScreen gameScreen;
    private int playerX = 1984;
    private int playerY = 3436;
    private boolean isSprinting = false;
    private char direction = 's';
    private boolean isAttacking = false;
    private boolean isShooting = false;
    private float attackTimer = 0f;
    private static final float ATTACK_DURATION = 0.4f;
    private float shootTimer = 0f;
    private static final float SHOOT_DURATION = 0.21f;
    private final int PLAYER_DAMAGE = 10;
    private final int PLAYER_BULLET_DAMAGE = 10;
    private float bulletSpeed = 10;
    private int playerLife = 100;
    private float sprintStat = 100;

    // HitBox
    private int HitBoxX; // Player x + 8
    private int HitBoxY; // Player y + 6
    private final int  HitBoxWidht = 54;
    private final int  HitBoxHeight = 51;
    private Rectangle hitBox;

    private List<Enemy> enemies;
    private List<Bullet> bullets;
    private boolean canRegenerateSprint = true;

    private Player() {
        currentState = PlayerState.STANDING;
        inputManager = new PlayerInputManager(this);
        animationManager = new PlayerAnimationManager();
        mapModel = MapModel.getInstance();
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
        if(Boot.INSTANCE.getScreen() instanceof GameScreen) gameScreen = (GameScreen) Boot.INSTANCE.getScreen();
        inputManager.handleInput();
        animationManager.update(delta);
        enemyManager.update(delta);
        // Check for melee attack and collisions with enemies
        updateAttackTimer(delta);
        updateShootTimer(delta);

        // Aggiorna i proiettili
        for (Bullet bullet : bullets) {
            bullet.update(delta);
            // Aggiungi la logica di collisione qui, se necessario
        }

        for (Bullet bullet : bullets) {
            if (mapModel.isCollisionWithScaledObjects(bullet.getX(), bullet.getY(), 32, 32)) {
                bullet.setBulletState(BulletState.HIT);
                bullet.deactivate();
            }
        }
        // Rimuovi i proiettili inattivi
        bullets.removeIf(bullet -> !bullet.isActive());

        HitBoxX = playerX + (8 * 3);
        HitBoxY = playerY + (6 * 3);

        if(canRegenerateSprint && sprintStat < 100){
            sprintStat+=0.5;
        }

        //this.isSprinting = isSprinting && sprintStat > 10;

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
                inflictDamageToEnemies();
                isAttacking = false;
                attackTimer = 0f;
                currentState = PlayerState.STANDING;  // Ritorna allo stato di standing dopo l'attacco
                animationManager.resetAttack();
            }
        }
    }

    private void updateShootTimer(float delta) {
        if (isShooting) {
            shootTimer += delta;
            if (shootTimer >= SHOOT_DURATION) {
                Bullet bullet;
                switch (getDirection()){
                    case 'w' :
                        bullet = new Bullet(getPlayerX()+ PLAYER_WIDTH/2 + 20 , getPlayerY()+ 20 + PLAYER_HEIGHT, bulletSpeed, getDirection());
                        bullet.setBulletState(BulletState.SHOOT_UP);
                        bullets.add(bullet);
                        break;
                    case 's' :
                        bullet = new Bullet(getPlayerX() + PLAYER_WIDTH/2 + 20, getPlayerY() , bulletSpeed, getDirection());
                        bullet.setBulletState(BulletState.SHOOT_DOWN);
                        bullets.add(bullet);
                        break;
                    case 'd' :
                        bullet = new Bullet(getPlayerX() + PLAYER_WIDTH, getPlayerY() +PLAYER_HEIGHT/2 + 20, bulletSpeed, getDirection());
                        bullet.setBulletState(BulletState.SHOOT_RIGHT);
                        bullets.add(bullet);
                        break;
                    case 'a' :
                        bullet = new Bullet(getPlayerX() , getPlayerY() + PLAYER_HEIGHT/2 + 20, bulletSpeed, getDirection());
                        bullet.setBulletState(BulletState.SHOOT_LEFT);
                        bullets.add(bullet);
                        break;
                }
                isShooting = false;
                shootTimer = 0f;
                currentState = PlayerState.STANDING;  // Ritorna allo stato di standing dopo l'attacco
                animationManager.resetShoot();

            }
        }
    }

    public void inflictShootDamageToEnemies(Bullet bullet){
        for(Enemy enemy : enemies){
            if(isCollisionWithAttackArea(bullet.getX(),bullet.getY(), 32, 32, enemy)) {
                bullet.deactivate();
                enemy.takeDamage(PLAYER_BULLET_DAMAGE);
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
                attackY -= PLAYER_HEIGHT/2;
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

    public boolean isCollisionWithAttackArea(float x, float y, float width, float height, Enemy enemy) {
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
        boolean enemyCollision = false;
        hitBox = new Rectangle(x , y , HitBoxWidht, HitBoxHeight);
        for(Enemy enemy : enemies){
            Rectangle enemyHitBox = enemy.getHitBox();
            if(enemyHitBox != null && hitBox.overlaps(enemyHitBox)){
                enemyCollision = true;
            }
        }
        //check for collision with map object
        return mapModel.isCollisionWithScaledObjects(x, y, HitBoxWidht, HitBoxHeight) || enemyCollision;

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
    }

    public void takeDamage(int damage){
        this.playerLife -= damage;
        if (playerLife <= 0) {
            // Implement logic for enemy death or removal from the game
            // For example, set the enemy state to a death state and stop animations
            System.out.println("PLAYER IS DEAD - GAME OVER");

        } else {
            gameScreen.shakeCamera(0.3f, 4);
            System.out.println("PLAYER HIT , OUCH!! , LIFE = " + playerLife);
        }
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
        isAttacking = true;
        animationManager.resetAttack();
    }

    public void attackDown() {
        currentState = PlayerState.ATTACK_DOWN;
        isAttacking = true;
        animationManager.resetAttack();
    }

    public void attackRight() {
        currentState = PlayerState.ATTACK_RIGHT;
        isAttacking = true;
        animationManager.resetAttack();
    }

    public void attackLeft() {
        currentState = PlayerState.ATTACK_LEFT;
        isAttacking = true;
        animationManager.resetAttack();
    }

    public Boolean upColliding() {
        return isCollision(HitBoxX, HitBoxY + getSPEED());
    }

    public Boolean downColliding() {
        return isCollision(HitBoxX, HitBoxY - getSPEED());
    }

    public Boolean leftColliding() {
        return isCollision(HitBoxX - getSPEED(), HitBoxY);
    }

    public Boolean rightColliding() {
        return isCollision(HitBoxX + getSPEED(), HitBoxY);
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
            SPEED *= 2;
            animationManager.updateAnimSpeed(0.07f);
            sprintStat -= 1;
            if(sprintStat <= 0){
                this.isSprinting = false;
            }
        } else {
            animationManager.updateAnimSpeed(0.1f);
        }
            this.SPEED = SPEED;
    }
    public List<Enemy> getEnemies() {
        return enemies;
    }

    public char getDirection() {
        return direction;
    }

    public int getPlayerLife(){
        return playerLife;
    }

    public float getPlayerStamina(){
        return this.sprintStat;
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
        this.isSprinting = isSprinting && sprintStat > 0;
        if(!isSprinting) this.canRegenerateSprint = true;
        else this.canRegenerateSprint = false;
    }

    public PlayerAnimationManager getAnimationManager() {
        return animationManager;
    }

    public Rectangle getArea(){
        return new Rectangle(getPlayerX(), getPlayerY(),getPLAYER_WIDTH(),getPLAYER_HEIGHT());
    }

    public Rectangle getHitBox(){
        return new Rectangle(HitBoxX, HitBoxY, HitBoxWidht , HitBoxHeight);
    }

    public List<Bullet> getBullets() {
        return bullets;
    }
}

