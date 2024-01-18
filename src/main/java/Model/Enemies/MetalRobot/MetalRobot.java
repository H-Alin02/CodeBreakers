package Model.Enemies.MetalRobot;

import Model.*;
import Model.Enemies.Enemy;
import View.Boot;
import View.GameScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

/**
 * La classe rappresenta un nemico di tipo MetalRobot nel gioco.
 * Implementa l'interfaccia Enemy e gestisce il comportamento, lo stato e le animazioni del nemico.
 * @author Alin Marian Habasescu
 * @author Gabriele Zimmerhofer
 */
public class MetalRobot implements Enemy {
    private int health; // Vita del nemico
    private int damage; // Danno inflitto dal nemico
    private int enemyX; // Posizione x del nemico sulla mappa
    private int enemyY; // Posizione Y del nemico sulla mappa
    private final int enemyWidth = 32; // Larghezza dell'immagine del nemico
    private final int enemyHeight = 32; // Altezza dell'immagine del nemico
    MetalRobotState currentState;  // Stato del nemico con enum ( per animazioni )
    private RobotState currentRobotState; // Stato del nemico con Pattern STATE
    private final Player player = Player.getInstance(); // Istanza di Player

    //Hit box
    private int hitBoxX; // Posizione x della HitBox del nemico
    private int hitBoxY; // Posizione y della HitBox del nemico
    private final int  HitBoxWidht = 42; // Larghezza della HitBox
    private final int  HitBoxHeight = 51; // Altezza della HitBox
    private Rectangle hitBox; // Oggetto rettangolare rappresentante la hitBox

    //private final MetalRobotAnimationManager animationManager; // Menager delle animazioni del nemico
    // Flag per determinare la fine della animazioni di attacco , danno ricevuto e morte del nemico
    private boolean damageAnimationComplete = true;
    private boolean deadAnimationComplete = true;

    //TODO eliminare le doppie animazioni per direzione sinistra e destra e inserire la funzione flip()
    final Array<MetalRobotState> enemyHitStates = Array.with(MetalRobotState.HIT1, MetalRobotState.HIT2);
    final Array<MetalRobotState> enemyDeadStates = Array.with(MetalRobotState.DEAD1, MetalRobotState.DEAD2);
    private final Array<MetalRobotState> enemyAttackStates = Array.with(MetalRobotState.ATTACK1, MetalRobotState.ATTACK2);
    private MapModel mapModel = null;
    private MetalRobotAnimationManager animationManager = null;

    //variabili per il movimento del nemico
    private final int movementSpeed = 3;
    char flip = 'a';

    // Range di movimento del nemico (puoi regolare questo valore)
    private final float chasingArea = 350f;
    float distanceToPlayer;
    private boolean isChasing = false;
    private boolean hasAttacked = false;

    private GameScreen gameScreen;
    private boolean isTestRunning;

    private SoundPlayer punchSound;
    private SoundPlayer alertSound;
    private SoundPlayer deathSound;
    private SoundPlayer damageSound;

    /**
     * Aggiorna lo stato dei suoni.
     *
     * @param delta Tempo trascorso dal frame precedente.
     */
    public void updateSound(float delta)
    {
        if(!isTestRunning){
            punchSound.update(delta);
            damageSound.update(delta);
            alertSound.update(delta);
            deathSound.update(delta);
        }
    }

    /**
     * Costruttore della classe MetalRobot.
     *
     * @param initialHealth Vita iniziale del nemico.
     * @param damage        Danno inflitto dal nemico.
     * @param startX        Posizione iniziale x del nemico sulla mappa.
     * @param startY        Posizione iniziale y del nemico sulla mappa.
     */
    public MetalRobot(int initialHealth, int damage , int startX, int startY){
        this.health = initialHealth;
        this.damage = damage;
        this.enemyX = startX;
        this.enemyY = startY;
        this.currentRobotState = new IdleState();
        this.isTestRunning = Gdx.files == null;

        if (!isTestRunning){
            punchSound = new SoundPlayer("sound_effects/robot_punch.wav");
            alertSound = new SoundPlayer("sound_effects/robot_alert.wav");
            deathSound = new SoundPlayer(1.3f, 5, "sound_effects/robot_death_sound.wav");
            damageSound = new SoundPlayer("sound_effects/robot_damaged.wav");
            mapModel = MapModel.getInstance();
            this.animationManager = new MetalRobotAnimationManager();
        }
    }

    /**
     * Aggiorna il nemico in base al tempo trascorso dal frame precedente.
     *
     * @param delta Tempo trascorso dal frame precedente.
     */
    @Override
    public void update(float delta) {
        if (Boot.INSTANCE.getScreen() instanceof GameScreen) gameScreen = (GameScreen) Boot.INSTANCE.getScreen();

        if (!isTestRunning) {
            animationManager.update(delta);
            updateSound(delta);
        }

        hitBoxX = enemyX + (8 * 3);
        hitBoxY = enemyY + (6 * 3);

        runStateMachine(delta);

        distanceToPlayer = calculateDistance(player.getHitBox().x + player.getHitBox().width / 2, player.getHitBox().y + player.getHitBox().height / 2);
        isChasing = distanceToPlayer < chasingArea && hasLineOfSight();

        // Check for damage state and animation completion

        if (enemyHitStates.contains(currentState, true) && !damageAnimationComplete) {
            if (animationManager.isAnimationFinished(currentState)) {
                // Transition back to idle state
                currentState = (flip == 'a') ? MetalRobotState.IDLE1 : MetalRobotState.IDLE2;
                damageAnimationComplete = true; // Reset the flag
            }
        }


        if (enemyDeadStates.contains(currentState,true) && !deadAnimationComplete) {
            if (animationManager.isAnimationFinished(currentState)) {
                // Transition back to idle state
                deadAnimationComplete = true; // Reset the flag
            }
        }

    }

    /**
     * Gestisce lo stato corrente del nemico attraverso la macchina a stati creata con Pattern State
     *
     * @param delta Tempo trascorso dall'ultimo aggiornamento.
     */
    public void runStateMachine(float delta){
        RobotState nextState = currentRobotState.runCurrentState(this, delta);
        if(nextState != null){
            switchToNextState(nextState);
        }
    }

    /**
     * Cambia lo stato corrente del nemico , utilizzando uno stato tra quelli creati con Pattern State
     *
     * @param nextState Il prossimo stato del nemico.
     */
    private void switchToNextState( RobotState nextState){
        currentRobotState = nextState;
    }

    /**
     * Verifica se il nemico ha una linea visiva con il giocatore.
     *
     * @return True se c'è una linea visiva, altrimenti False.
     */
    private boolean hasLineOfSight() {
        float playerX = player.getHitBox().x + player.getHitBox().width/2;
        float playerY = player.getHitBox().y + player.getHitBox().height/2;

        float startX = hitBox.x + hitBox.width / 2;
        float startY = hitBox.y + hitBox.height / 2;

        // Itera su tutti gli oggetti (muri, ecc.) e altri nemici
        for (Enemy otherEnemy : player.getEnemies()) {
            if (otherEnemy != this) {
                if (Intersector.intersectSegmentRectangle(startX, startY, playerX, playerY, otherEnemy.getHitBox())) {
                    // Collisione con un altro nemico, no Line of Sight
                    return false;
                }
            }
        }

        for(Interactable interactable : mapModel.getInteractables()){
            if(interactable instanceof Door){
                if(Intersector.intersectSegmentRectangle(startX, startY, playerX, playerY, ((Door) interactable).getBoundingBox())){
                    return false;
                }
            }
        }

        // Verifica anche eventuali collisioni con gli oggetti della mappa
        for (MapObject object : mapModel.getScaledCollisionObjects()) {
            if (object instanceof RectangleMapObject) {
                Rectangle rectangle = ((RectangleMapObject) object).getRectangle();
                if (Intersector.intersectSegmentRectangle(startX, startY, playerX, playerY, rectangle)) {
                    // Collisione con un oggetto, no Line of Sight
                    return false;
                }
            }
        }

        // Se il loop è completato senza restituire, c'è Line of Sight
        return true;
    }

    /**
     * Muove il nemico verso il giocatore.
     */
    public void moveTowardsPlayer() {
        float playerX = player.getHitBox().x + player.getHitBox().width/2;;
        float playerY = player.getHitBox().y + player.getHitBox().height/2;

        // Calcola la direzione verso cui muoversi
        float angle = MathUtils.atan2(playerY - hitBox.y + hitBox.height / 2, playerX - hitBox.x + hitBox.width / 2);

        // Imposta la direzione del nemico in base all'angolo di movimento
        setMovementDirection(angle);
    }

    private void setMovementDirection(float angle) {
        // Calcola la direzione in base all'angolo
        float degrees = MathUtils.radiansToDegrees * angle;

        // Scegli la direzione in base all'angolo
        if (degrees >= -22.5f && degrees < 22.5f) {
            moveRight(); // Muovi a destra
        } else if (degrees >= 22.5f && degrees < 67.5f) {
            moveUpRight(); // Muovi in alto a destra
        } else if (degrees >= 67.5f && degrees < 112.5f) {
           moveUp(); // Muovi in alto
        } else if (degrees >= 112.5f && degrees < 157.5f) {
            moveUpLeft(); // Muovi in alto a sinistra
        } else if (degrees >= 157.5f || degrees < -157.5f) {
            moveLeft(); // Muovi a sinistra
        } else if (degrees >= -157.5f && degrees < -112.5f) {
            moveDownLeft(); // Muovi in basso a sinistra
        } else if (degrees >= -112.5f && degrees < -67.5f) {
            moveDown(); // Muovi in basso
        } else if (degrees >= -67.5f && degrees < -22.5f) {
            moveDownRight(); // Muovi in basso a destra
        }
    }

    /**
     * Attacca il giocatore.
     */
    public void attackPlayer(){
        if (!enemyAttackStates.contains(currentState, true))
            if(!hasAttacked){
                punchSound.play(0.05f);

                if(flip == 'a') {
                    currentState = MetalRobotState.ATTACK1;
                    animationManager.resetDamage();
                }else{
                    currentState = MetalRobotState.ATTACK2;
                    animationManager.resetDamage();
                    flip = 'd';
                }
                hasAttacked = true;
            }

        // Controlla lo stato di attacco e il completamento dell'animazione
        if (enemyAttackStates.contains(currentState, true)) {
            if (hasAttacked && animationManager.isAnimationFinished(currentState)) {
                player.takeDamage(damage);
                hasAttacked = false;  // Reimposta la flag dopo che l'animazione è terminata
                currentState = (flip == 'a') ? MetalRobotState.IDLE1 : MetalRobotState.IDLE2;
            }
        }
    }

    /**
     * Muove il nemico in base alla direzione specificata.
     * La direzione è indicata da un carattere che rappresenta la direzione
     * del movimento, come specificato nel parametro 'currentDirection'.
     * Le direzioni possibili sono: 'w' per su, 's' per giù, 'a' per sinistra,
     * 'd' per destra, 'q' per su e sinistra, 'e' per su e destra, 'x' per giù e destra,
     * 'z' per giù e sinistra. 'f' rappresenta uno stato di inattività (idle).
     * Il carattere che indica la direzione viene cambiato randomicamente nella logica dello Stato Idle
     * @param currentDirection La direzione del movimento del nemico.
     * @see IdleState
     */
    public void moveEnemy(char currentDirection) {
        switch (currentDirection) {
            // Move Up
            case 'w':
                moveUp();
                break;
            // Move Down
            case 's':
                moveDown();
                break;
            // Move Left
            case 'a':
                moveLeft();
                break;
            // Move Right
            case 'd':
                moveRight();
                break;
            // Move Up-Left
            case 'q' :
                moveUpLeft();
                break;
            // Move Up-Right
            case 'e' :
                moveUpRight();
                break;
            // Move Down-Right
            case 'x' :
                moveDownRight();
                break;
            // Move Down-Left
            case 'z' :
                moveDownLeft();
                break;
            // Idle
            case 'f' :
                currentState = (flip == 'a') ? MetalRobotState.IDLE1 : MetalRobotState.IDLE2;
                break;
        }
    }

    private float calculateDistance(float x , float y) {
        float dx = (hitBoxX + HitBoxWidht/2) - x;
        float dy = (hitBoxY + HitBoxHeight/2) - y;
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    private void moveDownLeft() {
        if (!isCollision(enemyX - movementSpeed, enemyY - movementSpeed)) {
            currentState = MetalRobotState.WALK1;
            enemyX -= movementSpeed-1;
            enemyY -= movementSpeed-1;
            flip = 'a';
        }
    }

    private void moveDownRight() {
        if (!isCollision(enemyX + movementSpeed, enemyY - movementSpeed)) {
            currentState = MetalRobotState.WALK2;
            enemyX += movementSpeed-1;
            enemyY -= movementSpeed-1;
            flip = 'd';
        }
    }

    private void moveUpRight() {
        if (!isCollision(enemyX + movementSpeed, enemyY + movementSpeed)) {
            currentState = MetalRobotState.WALK2;
            enemyX += movementSpeed-1;
            enemyY += movementSpeed-1;
            flip = 'd';
        }
    }

    private void moveUpLeft() {
        if (!isCollision(enemyX - movementSpeed, enemyY + movementSpeed)) {
            currentState = MetalRobotState.WALK1;
            enemyX -= movementSpeed-1;
            enemyY += movementSpeed-1;
            flip = 'a';
        }
    }

    private void moveRight() {
        if (!isCollision(enemyX + movementSpeed, enemyY)) {
            currentState = MetalRobotState.WALK2;
            enemyX += movementSpeed;
            flip = 'd';
        }
    }

    private void moveLeft() {
        if (!isCollision(enemyX - movementSpeed, enemyY)) {
            currentState = MetalRobotState.WALK1;
            enemyX -= movementSpeed;
            flip = 'a';
        }
    }

    private void moveDown() {
        if (!isCollision(enemyX, enemyY - movementSpeed)) {
            currentState = (flip == 'a') ? MetalRobotState.WALK1 : MetalRobotState.WALK2;
            enemyY -= movementSpeed;
        }
    }

    private void moveUp() {
        if (!isCollision(enemyX, enemyY + movementSpeed)) {
            currentState = (flip == 'a') ? MetalRobotState.WALK1 : MetalRobotState.WALK2;
            enemyY += movementSpeed;
        }
    }

    /**
     * Gestisce la collisione del MetalRobot con i muri della mappa, altri nemici e il Player
     *
     * @param x posizione x del nemico MetalRobot
     * @param y posizione y del nemico MetalRobot
     */
    public boolean isCollision(float x, float y) {
        hitBox = new Rectangle(x+(8*3), y+(6*3), HitBoxWidht, HitBoxHeight);
        //check for collision with map object, other enemies or player

        for(Enemy enemy : player.getEnemies()){
            Rectangle enemyHitBox = enemy.getHitBox();
            if(enemyHitBox != null && hitBox.overlaps(enemyHitBox) && !enemy.equals(this)){
                return true;
            }
        }

        if(hitBox.overlaps(new Rectangle(player.getPlayerX(), player.getPlayerY(), player.getPLAYER_WIDTH(), player.getPLAYER_HEIGHT()))) {
            return true;
        }

        if(!isTestRunning)
            return mapModel.isCollisionWithScaledObjects(x+8, y+6, HitBoxWidht+15, HitBoxHeight+15);

        return false;
    }

    /**
     * Gestisce il danneggiamento subito dal nemico.
     *
     * @param damage Quantità di danni inflitti al nemico.
     */
    @Override
    public void takeDamage(int damage) {
        if(damage < 0){
            throw new IllegalArgumentException("Can't do nagative damage!");
        }else {
            if (!enemyDeadStates.contains(currentState, true)) {
                health -= damage;
                // Check if the enemy is still alive
                if (health <= 0) {
                    // Implement logic for enemy death or removal from the game
                    // For example, set the enemy state to a death state and stop animations
                    currentState = (flip == 'a') ? MetalRobotState.DEAD1 : MetalRobotState.DEAD2;
                    deadAnimationComplete = false;
                    if (!isTestRunning) {
                        deathSound.play(0.1f);
                        animationManager.resetDamage();
                        gameScreen.shakeCamera(0.3f, 4);
                    }

                } else {
                    // If the enemy is still alive, play the damage animation
                    currentState = (flip == 'a') ? MetalRobotState.HIT1 : MetalRobotState.HIT2;
                    damageAnimationComplete = false;
                    if (!isTestRunning) {
                        animationManager.resetDamage();
                        gameScreen.shakeCamera(0.3f, 4);
                        damageSound.play(0.1f);
                    }
                    //TODO aggiungere knowback

                }
            }
        }
    }

    /**
     * Restituisce la hitBox del nemico, rappresentata come un oggetto Rectangle.
     *
     * @return La hitBox del nemico.
     */
    public Rectangle getHitBox(){
        return hitBox;
    }

    /**
     * Verifica se il nemico è morto.
     *
     * @return True se il nemico è morto, altrimenti False.
     */
    @Override
    public boolean isDead() {
        return currentState == MetalRobotState.DEAD1 || currentState == MetalRobotState.DEAD2;
    }

    /**
     * Restituisce il flag isChasing che verifica se il nemico insegue il Player
     *
     * @return True se il nemico sta inseguendo, altrimenti False.
     */
    public boolean isChasing(){
        return isChasing;
    }

    /**
     * Verifica se l'animazione di danneggiamento del nemico è completa.
     *
     * @return True se l'animazione è completa, altrimenti False.
     */
    @Override
    public boolean isDamageAnimationComplete() {
        return deadAnimationComplete;
    }

    /**
     * Restituisce il frame corrente dell'animazione del nemico.
     *
     * @return Il frame corrente dell'animazione.
     */
    public TextureRegion getCurrentFrame(){
        return animationManager.getKeyFrame(currentState);
    }

    public float getEnemyX() {
        return this.enemyX;
    }

    public float getEnemyY() {
        return this.enemyY;
    }

    public float getEnemyWidth() {
        return enemyWidth * 3;
    }

    public float getEnemyHeight() {
        return enemyHeight * 3;
    }

    /**
     * Restituisce l'area di inseguimento del nemico.
     *
     * @return L'area di inseguimento del nemico.
     */
    public float getChasingArea(){
        return chasingArea;
    }

    /**
     * Imposta lo stato di attacco del nemico.
     *
     * @param hasAttacked True se il nemico ha attaccato, altrimenti False.
     */
    public void setHasAttacked(boolean hasAttacked) {
        this.hasAttacked = hasAttacked;
    }

    /**
     * Restituisce lo stato corrente del nemico, cioè uno degli stati creati con Pattern State.
     *
     * @return Lo stato corrente del nemico.
     */
    public MetalRobotState getCurrentState() {
        return this.currentState;
    }

    /***
     * Restituisce la velocità di movimento del nemico MetalMonster
     * @return La velocità di movimento
     */
    public int getMovementSpeed(){
        return this.movementSpeed;
    }

    public void setEnemyX(int enemyX){
        this.enemyX = enemyX;
    }
    public void setEnemyY(int enemyY) {
        this.enemyY = enemyY;
    }

    public int getEnemyHealth(){
        return this.health;
    }

    public void setEnemyHealth(int health){
        this.health = health;
    }
}
