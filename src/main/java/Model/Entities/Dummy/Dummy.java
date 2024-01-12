package Model.Entities.Dummy;

import Model.Entities.Enemy;
import Model.SoundPlayer;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

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
    private final DummyAnimationManager animationManager;
    private boolean damageAnimationComplete = true;
    private final Array<DummyState> enemyStates = Array.with(DummyState.DAMAGE_1, DummyState.DAMAGE_2, DummyState.DAMAGE_3);


    private static final SoundPlayer damageSound = new SoundPlayer("sound_effects/Enemy/dummy_damaged.mp3");
    public static void updateSound(float delta){
        damageSound.update(delta);
    }

    public Dummy(int initialHealth , int startX, int startY){
        this.health = initialHealth;
        this.enemyX = startX;
        this.enemyY = startY;
        this.HitBoxX = enemyX + (9 * 3);
        this.HitBoxY = enemyY + (6 * 3);
        this.currentState = DummyState.IDLE;
        this.animationManager = new DummyAnimationManager();
        this.hitBox = new Rectangle(HitBoxX, HitBoxY, HitBoxWidht, HitBoxHeight);
    }

    @Override
    public void update(float delta){
        animationManager.update(delta);
        // Check for damage state and animation completion
        if (enemyStates.contains(currentState,true) && !damageAnimationComplete) {
            // Print information for debugging
            System.out.println("Enemy Info: State - " + currentState + ", Position - (" + enemyX + ", " + enemyY + "), Health - " + health);

            if (animationManager.isDamageAnimationFinished(currentState)) {
                // Transition back to idle state
                currentState = DummyState.IDLE;
                damageAnimationComplete = true; // Reset the flag
            }
        }
    }

    @Override
    public void takeDamage(int damage) {
        health -= damage;
        damageSound.play(0.2f);

        // Check if the enemy is still alive
        if (health <= 0) {
            // Implement logic for enemy death or removal from the game
            // For example, set the enemy state to a death state and stop animations
            currentState = DummyState.DEAD;
            animationManager.resetDamage();
        } else {
            // If the enemy is still alive, play the damage animation
            currentState = enemyStates.random();
            animationManager.resetDamage();
            damageAnimationComplete = false;
        }
    }

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

    @Override
    public Rectangle getHitBox() {
        return hitBox;
    }

    @Override
    public boolean isDead() {
        return currentState == DummyState.DEAD ;
    }

    @Override
    public boolean isDamageAnimationComplete() {
        return damageAnimationComplete;
    }
}
