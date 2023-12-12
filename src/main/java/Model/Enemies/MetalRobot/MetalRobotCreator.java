package Model.Enemies.MetalRobot;

import Model.Enemies.Enemy;
import Model.Enemies.EnemyCreator;

public class MetalRobotCreator implements EnemyCreator {
    @Override
    public Enemy createEnemy(int startX, int startY) {
        return new MetalRobot(100, 10,startX, startY);
    }
}
