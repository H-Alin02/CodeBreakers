package Model.Entities.Dummy;

import Model.Entities.EnemyCreator;

public class DummyEnemyCreator implements EnemyCreator {
    @Override
    public Dummy createEnemy(int startX, int startY) {
        return new Dummy(1000,  startX, startY);
    }
}
