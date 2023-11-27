package Model.Enemies;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Enemy {
    private int health;
    private int damage;
    private int enemyX;
    private int enemyY;
    private final int enemyWidth = 32;
    private final int enemyHeight = 32;
    private EnemyState currentState;
    private EnemyAnimationManager animationManager;

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
        //Handle enemy death or removal from game
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
