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
        enemies.add(new Enemy(100000, 10, 192, 1152));
        enemies.add(new Enemy(100000, 10, 512 , 1152));
        enemies.add(new Enemy(100000, 10, 320, 896));
        enemies.add(new Enemy(100000, 10, 1472, 384));
        enemies.add(new Enemy(100000, 10, 1216, 256));
        enemies.add(new Enemy(100000, 10, 1536, 128));
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
