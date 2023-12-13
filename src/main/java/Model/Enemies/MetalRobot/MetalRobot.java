package Model.Enemies.MetalRobot;

import Model.Enemies.Enemy;
import Model.MapModel;
import Model.Player;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

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
    private final int  HitBoxWidht = 42;
    private final int  HitBoxHeight = 51;
    private Rectangle hitBox;

    private final MetalRobotAnimationManager animationManager;
    private boolean damageAnimationComplete = true;
    private boolean deadAnimationComplete = true;
    private boolean attackAnimationComplete = true;
    private final Array<MetalRobotState> enemyHitStates = Array.with(MetalRobotState.HIT1, MetalRobotState.HIT2);
    private final Array<MetalRobotState> enemyDeadStates = Array.with(MetalRobotState.DEAD1, MetalRobotState.DEAD2);
    private final Array<MetalRobotState> enemyAttackStates = Array.with(MetalRobotState.ATTACK1, MetalRobotState.ATTACK2);
    private final MapModel mapModel = MapModel.getInstance();

    //variabili per il movimento del nemico
    private int movementSpeed = 3;
    private float movementTimer = 0f;
    private float movementDuration = 2f;
    private char currentDirection = 'w';
    private char flip = 'a';

    // Range di movimento del nemico (puoi regolare questo valore)
    private float chasingArea = 350f;
    private float distanceToPlayer;
    private boolean isChasing = false;
    private boolean isAttacking = false;
    private float attackRange = 90f;
    private float attackTimer = 0f;
    private float timeBetweenAttacks = 2f;

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
        if (enemyHitStates.contains(currentState,true) && !damageAnimationComplete ) {
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

        // Check for attack state and animation completion
        if (enemyAttackStates.contains(currentState,true) && !attackAnimationComplete ) {
            if (animationManager.isAnimationFinished(currentState)) {
                // Transition back to idle state
                currentState = (flip == 'a') ? MetalRobotState.IDLE1 : MetalRobotState.IDLE2;
                if(distanceToPlayer < attackRange) player.takeDamage(damage);
                damageAnimationComplete = true; // Reset the flag
                isAttacking = false;
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
            if(!isAttacking) {
                if (isChasing) {
                    moveTowardsPlayer();
                    attackTimer += delta;
                    if (distanceToPlayer < attackRange && attackTimer >= timeBetweenAttacks) {
                        System.out.println("Enemy ready to attack ");
                        currentState = (flip == 'a') ? MetalRobotState.ATTACK1 : MetalRobotState.ATTACK2;
                        animationManager.resetDamage();
                        attackAnimationComplete = false;
                        isAttacking = true;
                        attackTimer = 0f;
                    }
                } else {
                    moveEnemy();
                }
            }
        }

        distanceToPlayer = calculateDistance(player.getPlayerX(), player.getPlayerY());
        isChasing = distanceToPlayer < chasingArea && hasLineOfSight();
    }

    private boolean hasLineOfSight() {
        float playerX = player.getPlayerX();
        float playerY = player.getPlayerY();

        float startX = enemyX;
        float startY = enemyY;

        // Itera su tutti gli oggetti (muri, ecc.) e altri nemici
        for (Enemy otherEnemy : player.getEnemies()) {
            if (otherEnemy != this) {
                if (Intersector.intersectSegmentRectangle(startX, startY, playerX, playerY, otherEnemy.getHitBox())) {
                    // Collisione con un altro nemico, no Line of Sight
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

    private void moveTowardsPlayer() {
        float playerX = player.getPlayerX();
        float playerY = player.getPlayerY();

        // Calcola la direzione verso cui muoversi
        float angle = MathUtils.atan2(playerY - enemyY, playerX - enemyX);

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



    private void moveEnemy() {
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
                //currentState = (flip == 'a') ? MetalRobotState.IDLE1 : MetalRobotState.IDLE2;
                break;
        }
    }

    private float calculateDistance(float x , float y) {
        float playerX = player.getPlayerX();
        float playerY = player.getPlayerY();
        float dx = enemyX - playerX;
        float dy = enemyY - playerY;
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

    public boolean isChasing(){
        return isChasing;
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

    public float getChasingArea(){
        return chasingArea;
    }

}
