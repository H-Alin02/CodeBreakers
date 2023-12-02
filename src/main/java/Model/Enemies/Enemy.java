package Model.Enemies;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Enemy {
    private int health;
    private int damage;
    private int enemyX;
    private int enemyY;
    private final int enemyWidth = 32;
    private final int enemyHeight = 32;
    private EnemyState currentState;
    private EnemyAnimationManager animationManager;
    private boolean damageAnimationComplete = true;
    private final Array<EnemyState> enemyStates = Array.with(EnemyState.DAMAGE_1,EnemyState.DAMAGE_2, EnemyState.DAMAGE_3);


    public Enemy(int initialHealth, int damage , int startX, int startY){
        this.health = initialHealth;
        this.damage = damage;
        this.enemyX = startX;
        this.enemyY = startY;
        this.currentState = EnemyState.IDLE;
        this.animationManager = new EnemyAnimationManager();
    }

    public void update(float delta){
        animationManager.update(delta);
        // Check for damage state and animation completion
        if (enemyStates.contains(currentState,true) && !damageAnimationComplete) {
            // Print information for debugging
            System.out.println("Enemy Info: State - " + currentState + ", Position - (" + enemyX + ", " + enemyY + "), Health - " + health);

            if (animationManager.isDamageAnimationFinished(currentState)) {
                // Transition back to idle state
                currentState = EnemyState.IDLE;
                damageAnimationComplete = true; // Reset the flag

                // Print a message after the state transition
            }
        }

    }

    public void takeDamage(int damage) {
        health -= damage;

        // Check if the enemy is still alive
        if (health <= 0) {
            // Implement logic for enemy death or removal from the game
            // For example, set the enemy state to a death state and stop animations
            currentState = EnemyState.DEAD;
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
