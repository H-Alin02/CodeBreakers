package Model.Enemies;

import java.util.ArrayList;
import java.util.List;

public class EnemyManager {
    private List<Enemy> enemies;
    private final EnemyAnimationManager animationManager;

    public EnemyManager(){
        enemies = new ArrayList<>();
        animationManager = new EnemyAnimationManager();
    }

    public void initializeEnemies(){
        // Add enemies to the EnemyManager
        enemies.add(new Enemy(100, 10, 192, 832));
    }

    public void update( float delta){
        for(Enemy enemy : enemies){
            enemy.update(delta);
        }
        animationManager.update(delta);
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

}
