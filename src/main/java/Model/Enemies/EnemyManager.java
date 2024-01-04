package Model.Enemies;

import Model.Enemies.Dummy.DummyEnemyCreator;
import Model.Enemies.MetalRobot.MetalRobotCreator;

import java.util.ArrayList;
import java.util.List;

public class EnemyManager {
    private final List<Enemy> enemies;
    private DummyEnemyCreator dummyEnemyCreator;
    private MetalRobotCreator metalRobotCreator;

    public EnemyManager(){
        this.enemies = new ArrayList<>();
        this.dummyEnemyCreator = new DummyEnemyCreator();
        this.metalRobotCreator = new MetalRobotCreator();
    }

    public void initializeEnemies(){
        // Add enemies to the EnemyManager
        // Offset from tile x = -20 , y = -10
        //Poligono
        enemies.add(dummyEnemyCreator.createEnemy(3436,3126));
        enemies.add(dummyEnemyCreator.createEnemy(3628,3062));
        enemies.add(dummyEnemyCreator.createEnemy(3820, 3136));

        //Gym
        enemies.add(dummyEnemyCreator.createEnemy(492, 2166));
        enemies.add(dummyEnemyCreator.createEnemy(876, 2166));
        enemies.add(dummyEnemyCreator.createEnemy(492, 1846));
        enemies.add(dummyEnemyCreator.createEnemy(876, 1846));

        //Moving enemies
        enemies.add(metalRobotCreator.createEnemy(1664,1014));
        //enemies.add(metalRobotCreator.createEnemy(350,118));
        //enemies.add(metalRobotCreator.createEnemy(500,118));

        /*enemies.add(metalRobotCreator.createEnemy(1452,1142));
        enemies.add(metalRobotCreator.createEnemy(1260,1142));
        enemies.add(metalRobotCreator.createEnemy(1580,886));*/

    }

    public void update( float delta){
        enemies.removeIf(enemy -> enemy.isDead() && enemy.isDamageAnimationComplete());
        for(Enemy enemy : enemies){
            enemy.update(delta);
        }
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

}
