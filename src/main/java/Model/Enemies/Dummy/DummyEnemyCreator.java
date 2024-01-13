package Model.Enemies.Dummy;

import Model.Enemies.EnemyCreator;
/**
 * La classe DummyEnemyCreator implementa l'interfaccia EnemyCreator e fornisce
 * un metodo per creare un nemico Dummy con una salute iniziale specificata.
 * @author Alin Marian Habasescu
 */
public class DummyEnemyCreator implements EnemyCreator {

    /**
     * Crea un nuovo nemico Dummy con la salute iniziale specificata.
     *
     * @param startX La coordinata x iniziale del Dummy.
     * @param startY La coordinata y iniziale del Dummy.
     * @return Un nuovo oggetto Dummy con la salute iniziale specificata.
     * @see Dummy
     */
    @Override
    public Dummy createEnemy(int startX, int startY) {
        return new Dummy(1000,  startX, startY);
    }
}
