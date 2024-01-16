package Model.Enemies.MetalRobot;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MetalRobotTest {

    MetalRobot metalRobot;
    @BeforeEach
    void setUp() {
        metalRobot = new MetalRobot(100, 10, 6912,1280);
        Assertions.assertNotNull(metalRobot);
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    public void testMovement(){
        int initialX = (int)metalRobot.getEnemyX();
        int initialY = (int)metalRobot.getEnemyY();
        int movementSpeed = metalRobot.getMovementSpeed();

        metalRobot.moveEnemy('w');
        Assertions.assertEquals(initialY + movementSpeed, metalRobot.getEnemyY());
        metalRobot.setEnemyY(initialY);

        metalRobot.moveEnemy('s');
        Assertions.assertEquals(initialY - movementSpeed, metalRobot.getEnemyY());
        metalRobot.setEnemyY(initialY);

        metalRobot.moveEnemy('d');
        Assertions.assertEquals(initialX + movementSpeed, metalRobot.getEnemyX());
        metalRobot.setEnemyX(initialX);

        metalRobot.moveEnemy('a');
        Assertions.assertEquals(initialX - movementSpeed, metalRobot.getEnemyX());
        metalRobot.setEnemyX(initialX);

        metalRobot.moveEnemy('q');
        Assertions.assertEquals(initialX - (movementSpeed - 1), metalRobot.getEnemyX());
        Assertions.assertEquals(initialY + (movementSpeed - 1), metalRobot.getEnemyY());
        metalRobot.setEnemyX(initialX);
        metalRobot.setEnemyY(initialY);

        metalRobot.moveEnemy('e');
        Assertions.assertEquals(initialX + (movementSpeed-1), metalRobot.getEnemyX());
        Assertions.assertEquals(initialY + (movementSpeed-1), metalRobot.getEnemyY());
        metalRobot.setEnemyX(initialX);
        metalRobot.setEnemyY(initialY);

        metalRobot.moveEnemy('x');
        Assertions.assertEquals(initialX + (movementSpeed - 1), metalRobot.getEnemyX());
        Assertions.assertEquals(initialY - (movementSpeed - 1), metalRobot.getEnemyY());
        metalRobot.setEnemyX(initialX);
        metalRobot.setEnemyY(initialY);

        metalRobot.moveEnemy('z');
        Assertions.assertEquals(initialX - (movementSpeed-1), metalRobot.getEnemyX());
        Assertions.assertEquals(initialY - (movementSpeed-1), metalRobot.getEnemyY());
        metalRobot.setEnemyX(initialX);
        metalRobot.setEnemyY(initialY);

        metalRobot.moveEnemy('f');
        Assertions.assertEquals(initialX , metalRobot.getEnemyX());
        Assertions.assertEquals(initialY, metalRobot.getEnemyY());
        metalRobot.setEnemyX(initialX);
    }

    @Test
    public void testDamageReceived(){
        int health = metalRobot.getEnemyHealth();
        metalRobot.takeDamage(10);
        Assertions.assertEquals(health - 10 , metalRobot.getEnemyHealth());
        metalRobot.setEnemyHealth(health);

        metalRobot.takeDamage(100);
        Assertions.assertEquals(health - 100 , metalRobot.getEnemyHealth());
        metalRobot.setEnemyHealth(health);

        Assertions.assertThrows(IllegalArgumentException.class, () -> { metalRobot.takeDamage(-1);});
        metalRobot.setEnemyHealth(health);

        metalRobot.takeDamage(0);
        Assertions.assertEquals(health - 0 , metalRobot.getEnemyHealth());
        metalRobot.setEnemyHealth(health);

    }
}