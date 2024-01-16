package Model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PlayerTest {

    Player player ;
    @BeforeEach
    public void setUp() {
        player = Player.getInstance();
        Assertions.assertNotNull(player);
    }

    @AfterEach
    public void tearDown() {
        player = null;
        Assertions.assertNull(player);
    }

    @Test
    public void testMovement(){
        int playerInitialY = player.getPlayerY();
        int playerInitialX = player.getPlayerX();

        player.moveDown();
        Assertions.assertEquals(playerInitialY - player.getSPEED(),player.getPlayerY());
        player.setPlayerY(playerInitialY);

        player.moveUp();
        Assertions.assertEquals(playerInitialY + player.getSPEED(), player.getPlayerY());
        player.setPlayerY(playerInitialY);

        player.moveRight();
        Assertions.assertEquals(playerInitialX + player.getSPEED(), player.getPlayerX());
        player.setPlayerX(playerInitialX);

        player.moveLeft();
        Assertions.assertEquals(playerInitialX -  player.getSPEED(), player.getPlayerX());
        player.setPlayerX(playerInitialX);

    }

    @Test
    public void testDamageTaken(){
        int playerLife = player.getPlayerLife();
        player.takeDamage(10);
        Assertions.assertEquals(playerLife - 10 , player.getPlayerLife());
    }
}