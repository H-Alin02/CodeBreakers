package Model.Enemies.Dummy;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DummyTest {

    Dummy dummmy;
    @BeforeEach
    void setUp() {
        dummmy = new Dummy(100, 100, 100);
        assertNotNull(dummmy);
    }

    @AfterEach
    void tearDown() {
        dummmy = null;
        assertNull(dummmy);
    }

    @Test
    void takeDamage() {
        int health = dummmy.getHealth();
        dummmy.takeDamage(10);
        assertEquals(health - 10 ,dummmy.getHealth());
        dummmy.setHealth(health);

        dummmy.takeDamage(100);
        assertEquals(health - 100 , dummmy.getHealth());
        dummmy.setHealth(health);

        Assertions.assertThrows(IllegalArgumentException.class, () -> { dummmy.takeDamage(-1);});
        dummmy.setHealth(health);

        dummmy.takeDamage(0);
        assertEquals(health, dummmy.getHealth());
        dummmy.setHealth(health);

    }
}