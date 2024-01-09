package Model.Enemies;

import Model.Enemies.Dummy.Dummy;
import Model.Enemies.Dummy.DummyEnemyCreator;
import Model.Enemies.MetalRobot.MetalRobot;
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
        enemies.add(metalRobotCreator.createEnemy(6912,1280));
        enemies.add(metalRobotCreator.createEnemy(6848,1856));
        enemies.add(metalRobotCreator.createEnemy(6848,2688));
        enemies.add(metalRobotCreator.createEnemy(7616,2520));
        enemies.add(metalRobotCreator.createEnemy(7872,2496));
        enemies.add(metalRobotCreator.createEnemy(7808,3224));
        enemies.add(metalRobotCreator.createEnemy(8960,3820));


    }

    public void update( float delta){
        enemies.removeIf(enemy -> enemy.isDead() && enemy.isDamageAnimationComplete());
        for(Enemy enemy : enemies){
            enemy.update(delta);
        }

        MetalRobot.updateSound(delta);
        Dummy.updateSound(delta);
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

}
