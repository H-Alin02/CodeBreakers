package Model.Enemies;

import Model.Enemies.Dummy.Dummy;
import Model.Enemies.MetalRobot.MetalRobot;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnemyManagerTest {

    EnemyManager enemyManager;
    @BeforeEach
    void setUp() {
        enemyManager = new EnemyManager();
        assertNotNull(enemyManager);
    }

    @AfterEach
    void tearDown() {
        enemyManager = null;
        assertNull(enemyManager);
    }

    @Test
    void initializeEnemies() {
        enemyManager.initializeEnemies();
        assertFalse(enemyManager.getEnemies().isEmpty());

        var numDummy = enemyManager.getEnemies().stream()
                .filter(enemy -> enemy instanceof Dummy)
                .count();

        // I Dummy creati dovrebbero essere 7
        assertEquals(7, numDummy);

        var numMetalRobot = enemyManager.getEnemies().stream()
                .filter(enemy -> enemy instanceof MetalRobot)
                .count();

        // I MetalRobot dovrebbero essere 7
        assertEquals(7, numMetalRobot);
    }
}