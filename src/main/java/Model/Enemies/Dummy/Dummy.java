package Model.Enemies.Dummy;

import Model.Enemies.Enemy;
import Model.MapModel;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Dummy implements Enemy {
    private int health;
    private int enemyX;
    private int enemyY;
    private final int enemyWidth = 32;
    private final int enemyHeight = 32;
    private DummyState currentState;
    private final DummyAnimationManager animationManager;
    private boolean damageAnimationComplete = true;
    private final Array<DummyState> enemyStates = Array.with(DummyState.DAMAGE_1, DummyState.DAMAGE_2, DummyState.DAMAGE_3);
    private final MapModel mapModel = MapModel.getInstance();


    public Dummy(int initialHealth , int startX, int startY){
        this.health = initialHealth;
        this.enemyX = startX;
        this.enemyY = startY;
        this.currentState = DummyState.IDLE;
        this.animationManager = new DummyAnimationManager();
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
}