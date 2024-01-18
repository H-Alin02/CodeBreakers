package Model.Enemies.Dummy;

import Model.Enemies.Enemy;
import Model.SoundPlayer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

/**
 * La classe Dummy rappresenta un nemico del tipo "Dummy" nel gioco.
 * Implementa l'interfaccia Enemy e gestisce la logica del nemico.
 * @author Alin Marian Habasescu
 * @author Gabriele Zimmerhofer
 */
public class Dummy implements Enemy {
    private int health;
    private int enemyX;
    private int enemyY;
    private final int enemyWidth = 32;
    private final int enemyHeight = 32;

    //Hit box
    private int HitBoxX;
    private int HitBoxY;
    private final int  HitBoxWidht = 42;
    private final int  HitBoxHeight = 51;
    private final Rectangle hitBox;

    private DummyState currentState;
    private DummyAnimationManager animationManager = null;
    private boolean damageAnimationComplete = true;
    private final Array<DummyState> enemyStates = Array.with(DummyState.DAMAGE_1, DummyState.DAMAGE_2, DummyState.DAMAGE_3);
    private SoundPlayer damageSound ;

    boolean isTestRunning;
    /**
     * Aggiorna il suono del danneggiamento del Dummy.
     *
     * @param delta Il tempo trascorso dall'ultimo aggiornamento.
     */
    public void updateSound(float delta){
        if(!isTestRunning)
            damageSound.update(delta);
    }

    /**
     * Costruttore della classe Dummy.
     *
     * @param initialHealth La salute iniziale del Dummy.
     * @param startX        La coordinata x iniziale del Dummy.
     * @param startY        La coordinata y iniziale del Dummy.
     */
    public Dummy(int initialHealth , int startX, int startY){
        this.isTestRunning = Gdx.files == null;
        this.health = initialHealth;
        this.enemyX = startX;
        this.enemyY = startY;
        this.HitBoxX = enemyX + (9 * 3);
        this.HitBoxY = enemyY + (6 * 3);
        this.currentState = DummyState.IDLE;
        this.hitBox = new Rectangle(HitBoxX, HitBoxY, HitBoxWidht, HitBoxHeight);
        if(!isTestRunning){
            this.animationManager = new DummyAnimationManager();
            this.damageSound = new SoundPlayer("sound_effects/player_punch.mp3");
        }
    }

    /**
     * Aggiorna lo stato e le animazioni del Dummy.
     *
     * @param delta Il tempo trascorso dall'ultimo aggiornamento.
     */
    @Override
    public void update(float delta){
        updateSound(delta);

        if(!isTestRunning) {
            animationManager.update(delta);
            // Check for damage state and animation completion
            if (enemyStates.contains(currentState, true) && !damageAnimationComplete) {
                // Print information for debugging
                System.out.println("Enemy Info: State - " + currentState + ", Position - (" + enemyX + ", " + enemyY + "), Health - " + health);

                if (animationManager.isDamageAnimationFinished(currentState)) {
                    // Transition back to idle state
                    currentState = DummyState.IDLE;
                    damageAnimationComplete = true; // Reset the flag
                }
            }
        }
    }

    /**
     * Infligge danni al Dummy.
     *
     * @param damage I danni inflitti al Dummy.
     *
     */
    @Override
    public void takeDamage(int damage) {
        if(damage < 0){
            throw new IllegalArgumentException("Can't do nagative damage!");
        }else {
            health -= damage;
            if (!isTestRunning)
                damageSound.play(0.2f);

            // Check if the enemy is still alive
            if (health <= 0) {
                // Implement logic for enemy death or removal from the game
                // For example, set the enemy state to a death state and stop animations
                currentState = DummyState.DEAD;
                if (!isTestRunning)
                    animationManager.resetDamage();
            } else {
                // If the enemy is still alive, play the damage animation
                currentState = enemyStates.random();
                if (!isTestRunning)
                    animationManager.resetDamage();
                damageAnimationComplete = false;
            }
        }
    }

    /**
     * Restituisce il frame corrente dell'animazione del Dummy.
     *
     * @return Il frame corrente dell'animazione del Dummy.
     */
    public TextureRegion getCurrentFrame(){
        return animationManager.getKeyFrame(currentState);
    }

    /**
     * Restituisce la coordinata x del Dummy.
     *
     * @return La coordinata x del Dummy.
     */
    public float getEnemyX() {
        return this.enemyX;
    }

    /**
     * Restituisce la coordinata y del Dummy.
     *
     * @return La coordinata y del Dummy.
     */
    public float getEnemyY() {
        return this.enemyY;
    }

    /**
     * Restituisce la larghezza del Dummy.
     *
     * @return La larghezza del Dummy.
     */
    public float getEnemyWidth() {
        return enemyWidth * 3;
    }

    /**
     * Restituisce l'altezza del Dummy.
     *
     * @return L'altezza del Dummy.
     */
    public float getEnemyHeight() {
        return enemyHeight * 3;
    }

    /**
     * Restituisce la hitbox del Dummy.
     *
     * @return La hitbox del Dummy.
     */
    @Override
    public Rectangle getHitBox() {
        return hitBox;
    }

    /**
     * Verifica se il Dummy è morto.
     *
     * @return True se il Dummy è morto, altrimenti False.
     */
    @Override
    public boolean isDead() {
        return currentState == DummyState.DEAD;
    }

    /**
     * Verifica se l'animazione di danneggiamento del Dummy è completa.
     *
     * @return True se l'animazione di danneggiamento è completa, altrimenti False.
     */
    @Override
    public boolean isDamageAnimationComplete() {
        return damageAnimationComplete;
    }

    /**
     * Restituisce la vita del nemico Dummy
     * @return la vita del dummy
     */
    public int getHealth() {
        return health;
    }

    /**
     * Imposta la vita del Dummy a quella messa al parametro
     * @param health la vita del Dummy
     */
    public void setHealth(int health){
        this.health = health;
    }
}
