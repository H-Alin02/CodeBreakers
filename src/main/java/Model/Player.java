package Model;

import Controller.PlayerInputManager;
import Model.Enemies.Enemy;
import View.Boot;
import View.GameScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

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
    private final int PLAYER_BULLET_DAMAGE = 20;
    private float bulletSpeed = 10;
    private int playerLife = 100;
    private float sprintStat = 100;
    private int bulletCount = 0 ;

    // HitBox
    private int HitBoxX; // Player x + 8
    private int HitBoxY; // Player y + 6
    private final int  HitBoxWidht = 54;
    private final int  HitBoxHeight = 51;
    private Rectangle hitBox;
    private List<Enemy> enemies;
    private List<Bullet> bullets;
    private boolean canRegenerateSprint = true;
    private boolean playerDead = false;
    private boolean playerWon = false;

    private static final SoundPlayer damageSound = new SoundPlayer("sound_effects/player_damaged.mp3");
    private static final SoundPlayer shotSound = new SoundPlayer("sound_effects/shot.mp3");
    private static final SoundPlayer punchSound = new SoundPlayer("sound_effects/missed_punch.wav");
    private static final SoundPlayer bulletHitSound = new SoundPlayer("sound_effects/bullet_hit.mp3");
    private static final SoundPlayer deathSound = new SoundPlayer("sound_effects/player_death_sound.wav");
    //private static final SoundPlayer walkingSound = new SoundPlayer(0.5f, "sound_effects/walking_sound.wav");

    private Player() {
        currentState = PlayerState.STANDING;
        inputManager = new PlayerInputManager(this);
        animationManager = new PlayerAnimationManager();
        mapModel = MapModel.getInstance();
        bullets = new ArrayList<>();

        MusicPlayer.play("tutorial");
    }

    public static void updateSound(float delta)
    {
        damageSound.update(delta);
        shotSound.update(delta);
        punchSound.update(delta);
        bulletHitSound.update(delta);
        deathSound.update(delta);
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
        inputManager.handleInteractInput(mapModel.getInteractables());
        animationManager.update(delta);
        // Check for melee attack and collisions with enemies
        updateAttackTimer(delta);
        updateShootTimer(delta);
        updateSound(delta);

        // Aggiorna i proiettili
        for (Bullet bullet : bullets) {
            bullet.update(delta);
            // Aggiungi la logica di collisione qui, se necessario
        }

        for (Bullet bullet : bullets) {
            if (mapModel.isCollisionWithScaledObjects(bullet.getX(), bullet.getY(), 32, 32)) {
                bullet.setBulletState(BulletState.HIT);
                bulletHitSound.play(0.2f);
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
    }

    public void interactWithNearestObject(Array<Interactable> interactables) {
        Interactable nearestInteractable = findNearestInteractable(interactables);

        if (nearestInteractable != null) {
            nearestInteractable.interact(this);
        }
    }

    private Interactable findNearestInteractable(Array<Interactable> interactables) {
        float minDistance = 150f;
        Interactable nearestInteractable = null;

        for (Interactable interactable : interactables) {
            float distance = new Vector2(getHitBox().x + getHitBox().width/2, getHitBox().y + getHitBox().height/2).dst(
                    interactable.getPosition());

            if (distance < minDistance) {
                minDistance = distance;
                nearestInteractable = interactable;
            }
        }

        return nearestInteractable;
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
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && !isShooting && bulletCount > 0) {
            shotSound.play(0.1f);

            bulletCount--;
            isShooting = true;
            animationManager.resetShoot();

            switch (getDirection()) {
                case 'w':
                    currentState = PlayerState.SHOOT_UP;
                    break;
                case 's':
                    currentState = PlayerState.SHOOT_DOWN;
                    break;
                case 'd':
                    currentState = PlayerState.SHOOT_RIGHT;
                    break;
                case 'a':
                    currentState = PlayerState.SHOOT_LEFT;
                    break;
            }
        }
    }
    public void resetPlayer(){
        Player.INSTANCE = null;
        MusicPlayer.dispose();
    };

    public void takeDamage(int damage){
        this.playerLife -= damage;

        if (playerLife <= 0) {
            // Implement logic for enemy death or removal from the game
            // For example, set the enemy state to a death state and stop animations

            System.out.println("PLAYER IS DEAD - GAME OVER");


            playerDead = true;
            currentState = PlayerState.DEAD;
            resetPlayer();
            deathSound.play(0.2f);


        } else {
            gameScreen.shakeCamera(0.3f, 4);
            System.out.println("PLAYER HIT , OUCH!! , LIFE = " + playerLife);
            damageSound.play(0.2f);
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
        punchSound.play(0.1f);
        currentState = PlayerState.ATTACK_UP;
        isAttacking = true;
        animationManager.resetAttack();
    }

    public void attackDown() {
        punchSound.play(0.1f);
        currentState = PlayerState.ATTACK_DOWN;
        isAttacking = true;
        animationManager.resetAttack();
    }

    public void attackRight() {
        punchSound.play(0.1f);
        currentState = PlayerState.ATTACK_RIGHT;
        isAttacking = true;
        animationManager.resetAttack();
    }

    public void attackLeft() {
        punchSound.play(0.1f);
        currentState = PlayerState.ATTACK_LEFT;
        isAttacking = true;
        animationManager.resetAttack();
    }

    public boolean upColliding() {
        return isCollision(HitBoxX, HitBoxY + getSPEED());
    }

    public boolean downColliding() {
        return isCollision(HitBoxX, HitBoxY - getSPEED());
    }

    public boolean leftColliding() {
        return isCollision(HitBoxX - getSPEED(), HitBoxY);
    }

    public boolean rightColliding() {
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

        //if(!walkingSound.isPlaying())
        //    walkingSound.play(0.2f);

        if (isSprinting) {
            SPEED *= 2;
            animationManager.updateAnimSpeed(0.07f);
            sprintStat -= 0.5f;
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

    public void setSprinting(boolean isSprinting) {
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

    public boolean isPlayerDead() {
        return playerDead;
    }

    public boolean hasPlayerWon() {
        return playerWon;
    }

    public void setPlayerWon(boolean playerWon) {
        this.playerWon = playerWon;
    }

    public void setPlayerLife(int life){
        this.playerLife += life;
        if (playerLife >= 100){
            playerLife = 100;
        }
        System.out.println("PLAYER LIFE "+ playerLife);
    }

    public void setBulletCount(int shoot) {
        this.bulletCount = shoot;
    }
    public int getBulletCount(){
        return this.bulletCount;
    }


    public void setPlayerX(int x) {
        this.playerX = x;
    }

    public void setPlayerY(int y) {
        this.playerY = y;
    }


}

