package Model.Enemies.MetalRobot;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MetalRobotCreatorTest {

    MetalRobotCreator metalRobotCreator;
    @BeforeEach
    void setUp() {
        metalRobotCreator = new MetalRobotCreator();
        Assertions.assertNotNull(metalRobotCreator);
    }

    @AfterEach
    void tearDown() {
        metalRobotCreator = null;
        Assertions.assertNull(metalRobotCreator);
    }

    @Test
    void createEnemy() {
        var enemy = metalRobotCreator.createEnemy(100, 100);
        Assertions.assertNotNull(enemy);
        Assertions.assertEquals( MetalRobot.class, enemy.getClass());
        Assertions.assertEquals(100, enemy.getEnemyX());
        Assertions.assertEquals(100, enemy.getEnemyY());
    }
}