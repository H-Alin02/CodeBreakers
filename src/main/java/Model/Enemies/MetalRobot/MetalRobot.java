package Model.Enemies.MetalRobot;

import Model.Enemies.Enemy;
import Model.MapModel;
import Model.Player;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.math.Rectangle;

public class MetalRobot implements Enemy {
    private int health;
    private int damage;
    private int enemyX;
    private int enemyY;
    private final int enemyWidth = 32;
    private final int enemyHeight = 32;
    private MetalRobotState currentState;
    private Player player = Player.getInstance();

    //Hit box
    private int HitBoxX;
    private int HitBoxY;
    private final int  HitBoxWidht = 42;
    private final int  HitBoxHeight = 51;
    private Rectangle hitBox;

    private final MetalRobotAnimationManager animationManager;
    private boolean damageAnimationComplete = true;
    private boolean deadAnimationComplete = true;
    private final Array<MetalRobotState> enemyHitStates = Array.with(MetalRobotState.HIT1, MetalRobotState.HIT2);
    private final Array<MetalRobotState> enemyDeadStates = Array.with(MetalRobotState.DEAD1, MetalRobotState.DEAD2);
    private final MapModel mapModel = MapModel.getInstance();

    //variabili per il movimento del nemico
    private int movementSpeed = 1;
    private float movementTimer = 0f;
    private float movementDuration = 2f;
    private char currentDirection = 'w';
    private char flip = 'a';

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
        // Check for damage state and animation completion
        if (enemyHitStates.contains(currentState,true) && !damageAnimationComplete) {
            // Print information for debugging
            System.out.println("Enemy Info: State - " + currentState + ", Position - (" + enemyX + ", " + enemyY + "), Health - " + health);

            if (animationManager.isDamageAnimationFinished(currentState)) {
                // Transition back to idle state
                currentState = (flip == 'a') ? MetalRobotState.IDLE1 : MetalRobotState.IDLE2;
                damageAnimationComplete = true; // Reset the flag
            }
        }

        if (enemyDeadStates.contains(currentState,true) && !deadAnimationComplete) {
            // Print information for debugging
            System.out.println("Enemy Info: State - " + currentState + ", Position - (" + enemyX + ", " + enemyY + "), Health - " + health);

            if (animationManager.isDamageAnimationFinished(currentState)) {
                // Transition back to idle state
                deadAnimationComplete = true; // Reset the flag
            }
        }


        movementTimer += delta;

        // Change movement direction every 'movementDuration' seconds
        if (movementTimer >= movementDuration) {
            movementTimer = 0f;
            currentDirection = getRandomDirection();
        }

        // Move the enemy based on the current direction
        if(!enemyDeadStates.contains(currentState,true) && !enemyHitStates.contains(currentState,true)){
            moveEnemy();
        }


    }


    private void moveEnemy() {
        switch (currentDirection) {
            // Move Up
            case 'w':
                if (!isCollision(enemyX, enemyY + movementSpeed)) {
                    enemyY += movementSpeed;
                }
                break;
            // Move Down
            case 's':
                if (!isCollision(enemyX, enemyY - movementSpeed)) {
                    enemyY -= movementSpeed;
                }
                break;
            // Move Left
            case 'a':
                currentState = MetalRobotState.WALK1;
                if (!isCollision(enemyX - movementSpeed, enemyY)) {
                    enemyX -= movementSpeed;
                    flip = 'a';
                }
                break;
            // Move Right
            case 'd':
                currentState = MetalRobotState.WALK2;
                if (!isCollision(enemyX + movementSpeed, enemyY)) {
                    enemyX += movementSpeed;
                    flip = 'd';
                }
                break;
            // Move Up-Left
            case 'q' :
                currentState = MetalRobotState.WALK1;
                if (!isCollision(enemyX - movementSpeed, enemyY + movementSpeed)) {
                    enemyX -= movementSpeed;
                    enemyY += movementSpeed;
                    flip = 'a';
                }
                break;
            // Move Up-Right
            case 'e' :
               currentState = MetalRobotState.WALK2;
               if (!isCollision(enemyX + movementSpeed, enemyY + movementSpeed)) {
                   enemyX += movementSpeed;
                   enemyY += movementSpeed;
                   flip = 'd';
               }
               break;
            // Move Down-Right
            case 'x' :
               currentState = MetalRobotState.WALK2;
               if (!isCollision(enemyX + movementSpeed, enemyY - movementSpeed)) {
                   enemyX += movementSpeed;
                   enemyY -= movementSpeed;
                   flip = 'd';
               }
               break;
            // Move Down-Left
            case 'z' :
               currentState = MetalRobotState.WALK1;
               if (!isCollision(enemyX - movementSpeed, enemyY - movementSpeed)) {
                   enemyX -= movementSpeed;
                   enemyY -= movementSpeed;
                   flip = 'a';
               }
               break;
            // Idle
            case 'f' :
                break;
        }
    }

    public boolean isCollision(float x, float y) {
        hitBox = new Rectangle(x+8, y+6, HitBoxWidht, HitBoxHeight);
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

        return mapModel.isCollisionWithScaledObjects(x+8, y+6, HitBoxWidht+15, HitBoxHeight+15);

    }

    private char getRandomDirection() {
        char[] directions = {'w', 's', 'a', 'd', 'q', 'e', 'x', 'z', 'f'};
        int randomIndex = (int) (Math.random() * directions.length);
        return directions[randomIndex];
    }

    @Override
    public void takeDamage(int damage) {
        if(!enemyDeadStates.contains(currentState, true)) {
            health -= damage;
            // Check if the enemy is still alive
            if (health <= 0) {
                // Implement logic for enemy death or removal from the game
                // For example, set the enemy state to a death state and stop animations
                deadAnimationComplete = false;
                currentState = (flip == 'a') ? MetalRobotState.DEAD1 : MetalRobotState.DEAD2;
                animationManager.resetDamage();

            } else {
                // If the enemy is still alive, play the damage animation
                currentState = (flip == 'a') ? MetalRobotState.HIT1 : MetalRobotState.HIT2;
                animationManager.resetDamage();
                damageAnimationComplete = false;
            }
        }
    }

    public Rectangle getHitBox(){
        return hitBox;
    }

    @Override
    public boolean isDead() {
        return currentState == MetalRobotState.DEAD1 || currentState == MetalRobotState.DEAD2;
    }

    @Override
    public boolean isDamageAnimationComplete() {
        return deadAnimationComplete;
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
