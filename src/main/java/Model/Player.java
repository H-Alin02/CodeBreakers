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

/**
 * La classe rappresenta l'entità del giocatore nel gioco.
 */
public class Player {
    private static Player INSTANCE;
    private final int PLAYER_WIDTH = 32;
    private final int PLAYER_HEIGHT = 32;
    private final int SCALE = 3;
    private int SPEED = 5;
    public PlayerState currentState;
    private final PlayerInputManager inputManager;
    private PlayerAnimationManager animationManager ;
    private MapModel mapModel;
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
    private List<Enemy> enemies = new ArrayList<>();
    private List<Bullet> bullets;
    private boolean canRegenerateSprint = true;
    private boolean playerDead = false;
    private boolean playerWon = false;

    private SoundPlayer damageSound;
    private SoundPlayer shotSound;
    private SoundPlayer punchSound ;
    private SoundPlayer bulletHitSound;
    private SoundPlayer deathSound ;

    private boolean isTestRunning;


    /**
     * Costruttore privato per implementare il pattern Singleton.
     */
    private Player() {
        currentState = PlayerState.STANDING;
        inputManager = new PlayerInputManager(this);
        bullets = new ArrayList<>();
        this.isTestRunning = Gdx.files == null;

        if(!isTestRunning) {
            animationManager = new PlayerAnimationManager();
            mapModel = MapModel.getInstance();
            this.damageSound = new SoundPlayer("sound_effects/player_damaged.mp3");
            this.shotSound = new SoundPlayer("sound_effects/shot.mp3");
            this.punchSound = new SoundPlayer("sound_effects/missed_punch.wav");
            this.bulletHitSound = new SoundPlayer("sound_effects/bullet_hit.mp3");
            this.deathSound = new SoundPlayer("sound_effects/player_death_sound.wav");
            MusicPlayer.play("tutorial");
        }
    }

    /**
     * Aggiorna lo stato dei suoni del giocatore.
     *
     * @param delta Il tempo trascorso dall'ultimo aggiornamento.
     */
    public void updateSound(float delta)
    {
        if(!isTestRunning) {
            damageSound.update(delta);
            shotSound.update(delta);
            punchSound.update(delta);
            bulletHitSound.update(delta);
            deathSound.update(delta);
        }
    }

    /**
     * Restituisce l'istanza singleton del giocatore.
     *
     * @return L'istanza singleton del giocatore.
     */
    public static Player getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Player();
        }
        return INSTANCE;
    }

    /**
     * Imposta la lista degli nemici nel gioco.
     *
     * @param enemies Lista degli nemici nel gioco.
     */
    public void setEnemies(List<Enemy> enemies) {
        this.enemies = enemies;
    }

    /**
     * Aggiorna lo stato del giocatore.
     *
     * @param delta Il tempo trascorso dall'ultimo aggiornamento.
     */
    public void update(float delta) {
        if(Boot.INSTANCE.getScreen() instanceof GameScreen) gameScreen = (GameScreen) Boot.INSTANCE.getScreen();
        inputManager.handleInput();
        if(!isTestRunning)
            inputManager.handleInteractInput(mapModel.getInteractables());

        if(!isTestRunning){
            animationManager.update(delta);
        }


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
                if(!isTestRunning)
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

    /**
     * Restituisce l'oggetto di interazione più vicino al giocatore.
     *
     * @param interactables Array di oggetti interattivi nel gioco.
     * @return L'oggetto interattivo più vicino al giocatore.
     */
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

    /**
     * Interagisce con l'oggetto più vicino tra quelli specificati nella lista.
     *
     * @param interactables Lista di oggetti interattivi nel gioco.
     */
    public void interactWithNearestObject(Array<Interactable> interactables) {
        Interactable nearestInteractable = findNearestInteractable(interactables);

        if (nearestInteractable != null) {
            nearestInteractable.interact(this);
        }
    }

    /**
     * Controlla l'attacco ravvicinato del giocatore.
     * Gestisce gli attacchi corpo a corpo nella direzione in cui il giocatore è rivolto.
     */
    public void checkMeleeAttack() {
        if ((Gdx.input.isKeyJustPressed(Input.Keys.K) || Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) && !isAttacking) {
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

    /**
     * Aggiorna il timer dell'attacco corpo a corpo.
     *
     * @param delta Il tempo trascorso dall'ultimo aggiornamento.
     */
    private void updateAttackTimer(float delta) {
        if (isAttacking) {
            attackTimer += delta;
            if (attackTimer >= ATTACK_DURATION) {
                inflictDamageToEnemies();
                isAttacking = false;
                attackTimer = 0f;
                currentState = PlayerState.STANDING;  // Ritorna allo stato di standing dopo l'attacco
                if(!isTestRunning)
                    animationManager.resetAttack();
            }
        }
    }

    /**
     * Aggiorna il timer dello sparo.
     *
     * @param delta Il tempo trascorso dall'ultimo aggiornamento.
     */
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
                if(!isTestRunning)
                    animationManager.resetShoot();
            }
        }
    }

    /**
     * Infligge danni agli nemici colpiti dai proiettili.
     *
     * @param bullet Il proiettile che ha colpito un nemico.
     */
    public void inflictShootDamageToEnemies(Bullet bullet){
        for(Enemy enemy : enemies){
            if(isCollisionWithAttackArea(bullet.getX(),bullet.getY(), 32, 32, enemy)) {
                bullet.deactivate();
                enemy.takeDamage(PLAYER_BULLET_DAMAGE);
            }
        }
    }

    /**
     * Infligge danni agli nemici colpiti dall'attacco corpo a corpo.
     */
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

    /**
     * Verifica la collisione con l'area di attacco rispetto a un nemico.
     *
     * @param x      La coordinata x dell'area di attacco.
     * @param y      La coordinata y dell'area di attacco.
     * @param width  La larghezza dell'area di attacco.
     * @param height L'altezza dell'area di attacco.
     * @param enemy  Il nemico con cui verificare la collisione.
     * @return True se c'è una collisione, altrimenti false.
     */
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


    /**
     * Verifica se c'è una collisione del giocatore con oggetti nel gioco o con nemici.
     *
     * @param x La coordinata x del giocatore.
     * @param y La coordinata y del giocatore.
     * @return True se c'è una collisione, altrimenti false.
     */
    public boolean isCollision(float x, float y) {
        hitBox = new Rectangle(x , y , HitBoxWidht, HitBoxHeight);
        for(Enemy enemy : enemies){
            Rectangle enemyHitBox = enemy.getHitBox();
            if(enemyHitBox != null && hitBox.overlaps(enemyHitBox)){
                return true;
            }
        }
        //check for collision with map object
        if(!isTestRunning)
            return mapModel.isCollisionWithScaledObjects(x, y, HitBoxWidht, HitBoxHeight) ;

        return false;
    }

    /**
     * Gestisce l'azione di sparare del giocatore.
     */
    public void shoot() {
        // Aggiungi un nuovo proiettile in base alla direzione corrente del giocatore
        if ((Gdx.input.isKeyJustPressed(Input.Keys.SPACE) || Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT))&& !isShooting && bulletCount > 0) {

            bulletCount--;
            isShooting = true;
            if(!isTestRunning){
                shotSound.play(0.1f);
                animationManager.resetShoot();
            }

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

    /**
     * Reimposta lo stato del giocatore quando muore.
     */
    public void resetPlayer(){
        Player.INSTANCE = null;
        if(!isTestRunning)
            MusicPlayer.dispose();
    };

    /**
     * Gestisce il danneggiamento subito dal giocatore.
     *
     * @param damage La quantità di danni inflitti al giocatore.
     */
    public void takeDamage(int damage){
        this.playerLife -= damage;

        if (playerLife <= 0) {
            // Implement logic for enemy death or removal from the game
            // For example, set the enemy state to a death state and stop animations
            playerDead = true;
            currentState = PlayerState.DEAD;
            resetPlayer();
            if(!isTestRunning)
                deathSound.play(0.2f);

        } else {
            if(!isTestRunning){
                gameScreen.shakeCamera(0.3f, 4);
                damageSound.play(0.2f);
            }
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
        if(!isTestRunning){
            punchSound.play(0.1f);
            animationManager.resetAttack();
        }
    }

    public void attackDown() {
        currentState = PlayerState.ATTACK_DOWN;
        isAttacking = true;
        if(!isTestRunning){
            punchSound.play(0.1f);
            animationManager.resetAttack();
        }
    }

    public void attackRight() {
        currentState = PlayerState.ATTACK_RIGHT;
        isAttacking = true;
        if(!isTestRunning){
            punchSound.play(0.1f);
            animationManager.resetAttack();
        }
    }

    public void attackLeft() {
        currentState = PlayerState.ATTACK_LEFT;
        isAttacking = true;
        if(!isTestRunning){
            punchSound.play(0.1f);
            animationManager.resetAttack();
        }
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

    /**
     * Restituisce la regione dell'immagine corrente per il giocatore
     * in base allo stato di animazione.
     *
     * @return La regione dell'immagine corrente.
     */
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
            if(!isTestRunning)
                animationManager.updateAnimSpeed(0.07f);
            sprintStat -= 0.5f;
            if(sprintStat <= 0){
                this.isSprinting = false;
            }
        } else {
            if(!isTestRunning)
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
        this.canRegenerateSprint = !isSprinting;
    }

    public PlayerAnimationManager getAnimationManager() {
        return animationManager;
    }

    public Rectangle getArea(){
        return new Rectangle(getPlayerX(), getPlayerY(),getPLAYER_WIDTH(),getPLAYER_HEIGHT());
    }

    /**
     * Restituisce la hitbox del giocatore.
     *
     * @return La hitbox del giocatore.
     */
    public Rectangle getHitBox(){
        return new Rectangle(HitBoxX, HitBoxY, HitBoxWidht , HitBoxHeight);
    }

    /**
     * Restituisce la lista di proiettili attualmente presenti nel gioco.
     *
     * @return Lista di proiettili.
     */
    public List<Bullet> getBullets() {
        return bullets;
    }

    /**
     * Verifica se il giocatore è morto.
     *
     * @return True se il giocatore è morto, altrimenti False.
     */
    public boolean isPlayerDead() {
        return playerDead;
    }

    /**
     * Verifica se il giocatore ha vinto.
     *
     * @return True se il giocatore ha vinto, altrimenti False.
     */
    public boolean hasPlayerWon() {
        return playerWon;
    }

    /**
     * Imposta lo stato di vittoria del giocatore.
     *
     * @param playerWon True se il giocatore ha vinto, altrimenti False.
     */
    public void setPlayerWon(boolean playerWon) {
        this.playerWon = playerWon;
    }

    /**
     * Aggiorna la vita del giocatore aggiungendo il valore specificato.
     * Se il valore aggiunto porta la vita del giocatore oltre 100, la vita viene limitata a 100.
     *
     * @param life Valore da aggiungere alla vita del giocatore.
     */
    public void setPlayerLife(int life){
        this.playerLife += life;
        if (playerLife >= 100){
            playerLife = 100;
        }
        System.out.println("PLAYER LIFE "+ playerLife);
    }

    /**
     * Imposta il numero di proiettili disponibili per il giocatore.
     *
     * @param shoot Numero di proiettili disponibili.
     */
    public void setBulletCount(int shoot) {
        this.bulletCount = shoot;
    }

    /**
     * Restituisce il numero di proiettili disponibili per il giocatore.
     *
     * @return Numero di proiettili disponibili.
     */
    public int getBulletCount(){
        return this.bulletCount;
    }

    /**
     * Imposta la coordinata X della posizione del giocatore.
     *
     * @param x Coordinata X della posizione del giocatore.
     */
    public void setPlayerX(int x) {
        this.playerX = x;
    }

    /**
     * Imposta la coordinata Y della posizione del giocatore.
     *
     * @param y Coordinata Y della posizione del giocatore.
     */
    public void setPlayerY(int y) {
        this.playerY = y;
    }
}

