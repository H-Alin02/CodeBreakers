package Model.Enemies.MetalRobot;

import Model.Enemies.Enemy;
import Model.MapModel;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MetalRobot implements Enemy {
    private int health;
    private int damage;
    private int enemyX;
    private int enemyY;
    private final int enemyWidth = 32;
    private final int enemyHeight = 32;
    private MetalRobotState currentState;
    private final MetalRobotAnimationManager animationManager;
    //private boolean damageAnimationComplete = true;
    private final MapModel mapModel = MapModel.getInstance();

    //variabili per il movimento del nemico
    private int movementSpeed = 1;
    private float movementTimer = 0f;
    private float movementDuration = 2f;
    private char currentDirection = 'w';

    public MetalRobot(int initialHealth, int damage , int startX, int startY){
        this.health = initialHealth;
        this.damage = damage;
        this.enemyX = startX;
        this.enemyY = startY;
        this.currentState = MetalRobotState.IDLE1;
        this.animationManager = new MetalRobotAnimationManager();
    }
    @Override
    public void update(float delta) {
        animationManager.update(delta);
        /* Parte di danno subito
        // Check for damage state and animation completion
        if (enemyStates.contains(currentState,true) && !damageAnimationComplete) {
            // Print information for debugging
            System.out.println("Enemy Info: State - " + currentState + ", Position - (" + enemyX + ", " + enemyY + "), Health - " + health);

            if (animationManager.isDamageAnimationFinished(currentState)) {
                // Transition back to idle state
                currentState = DummyState.IDLE;
                damageAnimationComplete = true; // Reset the flag
            }
        }*/


        movementTimer += delta;

        // Change movement direction every 'movementDuration' seconds
        if (movementTimer >= movementDuration) {
            movementTimer = 0f;
            currentDirection = getRandomDirection();
        }
        // Move the enemy based on the current direction
        moveEnemy(delta);

    }

    private void moveEnemy(float delta) {
        switch (currentDirection) {
            case 'w':
                if (!isCollision(enemyX, enemyY + movementSpeed)) {
                    enemyY += movementSpeed;
                }
                break;
            case 's':
                if (!isCollision(enemyX, enemyY - movementSpeed)) {
                    enemyY -= movementSpeed;
                }
                break;
            case 'a':
                currentState = MetalRobotState.IDLE1;
                if (!isCollision(enemyX - movementSpeed, enemyY)) {
                    enemyX -= movementSpeed;
                }
                break;
            case 'd':
                currentState = MetalRobotState.IDLE2;
                if (!isCollision(enemyX + movementSpeed, enemyY)) {
                    enemyX += movementSpeed;
                }
                break;
            case 'q' :
                currentState = MetalRobotState.IDLE1;
                if (!isCollision(enemyX - movementSpeed, enemyY + movementSpeed)) {
                    enemyX -= movementSpeed;
                    enemyY += movementSpeed;
                }
                break;
           case 'e' :
               currentState = MetalRobotState.IDLE2;
               if (!isCollision(enemyX + movementSpeed, enemyY + movementSpeed)) {
                   enemyX += movementSpeed;
                   enemyY += movementSpeed;
               }
               break;
           case 'x' :
               currentState = MetalRobotState.IDLE2;
               if (!isCollision(enemyX + movementSpeed, enemyY - movementSpeed)) {
                   enemyX += movementSpeed;
                   enemyY -= movementSpeed;
               }
               break;
           case 'z' :
               currentState = MetalRobotState.IDLE1;
               if (!isCollision(enemyX - movementSpeed, enemyY - movementSpeed)) {
                   enemyX -= movementSpeed;
                   enemyY -= movementSpeed;
               }
               break;
        }
    }

    public boolean isCollision(float x, float y) {
        //check for collision with map object
        return mapModel.isCollisionWithScaledObjects(x, y, 32 + 15, 32 + 15);

    }

    private char getRandomDirection() {
        char[] directions = {'w', 's', 'a', 'd', 'q', 'e', 'x', 'z'};
        int randomIndex = (int) (Math.random() * directions.length);
        return directions[randomIndex];
    }

    @Override
    public void takeDamage(int damage) {

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
