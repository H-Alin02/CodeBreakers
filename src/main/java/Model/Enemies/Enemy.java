package Model.Enemies;

import Model.MapModel;
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
    private final EnemyAnimationManager animationManager;
    private boolean damageAnimationComplete = true;
    private final Array<EnemyState> enemyStates = Array.with(EnemyState.DAMAGE_1,EnemyState.DAMAGE_2, EnemyState.DAMAGE_3);
    private final MapModel mapModel = MapModel.getInstance();

    //variabili per il movimento del nemico
    private final boolean isMoving;
    private int movementSpeed = 1;
    private float movementTimer = 0f;
    private float movementDuration = 3f;
    private char currentDirection = 'w';

    public Enemy(int initialHealth, int damage , int startX, int startY, boolean isMoving){
        this.health = initialHealth;
        this.damage = damage;
        this.enemyX = startX;
        this.enemyY = startY;
        this.currentState = EnemyState.IDLE;
        this.animationManager = new EnemyAnimationManager();
        this.isMoving = isMoving;
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
            }
        }


        if(isMoving) {
            movementTimer += delta;

            // Change movement direction every 'movementDuration' seconds
            if (movementTimer >= movementDuration) {
                movementTimer = 0f;
                currentDirection = getRandomDirection();
            }
            // Move the enemy based on the current direction
            moveEnemy(delta);
        }
    }

    private void moveEnemy(float delta) {
        if(!enemyStates.contains(currentState,true)) {
            switch (currentDirection) {
                case 'w':
                    if (isCollision(enemyX, enemyY + movementSpeed)) {
                        enemyY += movementSpeed;
                    }
                    break;
                case 's':
                    if (isCollision(enemyX, enemyY - movementSpeed)) {
                        enemyY -= movementSpeed;
                    }
                    break;
                case 'a':
                    if (isCollision(enemyX - movementSpeed, enemyY)) {
                        enemyX -= movementSpeed;
                    }
                    break;
                case 'd':
                    if (isCollision(enemyX + movementSpeed, enemyY)) {
                        enemyX += movementSpeed;
                    }
                    break;
            }
        }
    }

    public boolean isCollision(float x, float y) {
        //check for collision with map object
        return !mapModel.isCollisionWithScaledObjects(x, y, 32 + 15, 32 + 15);

    }

    private char getRandomDirection() {
        char[] directions = {'w', 's', 'a', 'd'};
        int randomIndex = (int) (Math.random() * directions.length);
        return directions[randomIndex];
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

    public boolean getIsMoving(){
        return this.isMoving;
    }
}
