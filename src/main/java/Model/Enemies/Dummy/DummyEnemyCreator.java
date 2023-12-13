package Model.Enemies.Dummy;

import Model.Enemies.EnemyCreator;

public class DummyEnemyCreator implements EnemyCreator {
    @Override
    public Dummy createEnemy(int startX, int startY) {
        return new Dummy(1000,  startX, startY);
    }
}
