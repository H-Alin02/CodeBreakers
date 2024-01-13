package Model.Enemies.MetalRobot;

import Model.Enemies.EnemyCreator;

/**
 * La classe {@code MetalRobotCreator} è un'implementazione di {@code EnemyCreator}
 * specificamente progettata per creare istanze di {@code MetalRobot} nel gioco.
 * Fornisce un metodo per creare un nuovo nemico di tipo MetalRobot con le coordinate iniziali specificate.
 * @author Alin Marian Habasescu
 */
public class MetalRobotCreator implements EnemyCreator {

    /**
     * Crea e restituisce un nuovo nemico di tipo MetalRobot con le coordinate iniziali specificate.
     *
     * @param startX La coordinata X iniziale del MetalRobot.
     * @param startY La coordinata Y iniziale del MetalRobot.
     * @return Un'istanza di {@code MetalRobot} con le specifiche coordinate iniziali.
     */
    @Override
    public MetalRobot createEnemy(int startX, int startY) {
        return new MetalRobot(100, 10,startX, startY);
    }
}
