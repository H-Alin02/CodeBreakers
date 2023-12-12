package Model.Enemies;

import Model.Enemies.Dummy.DummyAnimationManager;
import Model.Enemies.Dummy.DummyEnemyCreator;
import Model.Enemies.MetalRobot.MetalRobotCreator;

import java.util.ArrayList;
import java.util.List;

public class EnemyManager {
    private final List<Enemy> enemies;
    private final DummyAnimationManager animationManager;
    private DummyEnemyCreator dummyEnemyCreator;
    private MetalRobotCreator metalRobotCreator;

    public EnemyManager(){
        this.enemies = new ArrayList<>();
        this.animationManager = new DummyAnimationManager();
        this.dummyEnemyCreator = new DummyEnemyCreator();
        this.metalRobotCreator = new MetalRobotCreator();
    }

    public void initializeEnemies(){
        // Add enemies to the EnemyManager
        // Offset from tile x = -20 , y = -10
        enemies.add(dummyEnemyCreator.createEnemy(172,1142));
        enemies.add(dummyEnemyCreator.createEnemy(492,1142));
        enemies.add(dummyEnemyCreator.createEnemy(300, 886));
        enemies.add(dummyEnemyCreator.createEnemy(1452, 374));
        enemies.add(dummyEnemyCreator.createEnemy(1260, 246));
        enemies.add(dummyEnemyCreator.createEnemy(1580, 118));

        //Moving enemies
        enemies.add(metalRobotCreator.createEnemy(150,118));
        enemies.add(metalRobotCreator.createEnemy(350,118));
        enemies.add(metalRobotCreator.createEnemy(500,118));

        enemies.add(metalRobotCreator.createEnemy(1452,1142));
        enemies.add(metalRobotCreator.createEnemy(1260,1142));
        enemies.add(metalRobotCreator.createEnemy(1580,886));

    }

    public void update( float delta){
        enemies.removeIf(Enemy::isDead);
        for(Enemy enemy : enemies){
            enemy.update(delta);
        }
        animationManager.update(delta);
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

}
