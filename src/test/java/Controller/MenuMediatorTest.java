package Controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MenuMediatorTest {

    MenuMediator menuMediator;

    @BeforeEach
    void setUp() {
        menuMediator = new MenuMediator();
        assertNotNull(menuMediator);
    }

    @AfterEach
    void tearDown() {
        menuMediator = null;
        assertNull(menuMediator);
    }

    @Test
    public void testChangeGameState(){
        assertFalse(menuMediator.isChangeGameState());
        menuMediator.changeGameStatus();
        assertTrue(menuMediator.isChangeGameState());
    }

    @Test
    public void testChangeToOptionScreen(){
        assertFalse(menuMediator.isChangeToOptionsScreen());
        menuMediator.changeToOptionsScreen();
        assertTrue(menuMediator.isChangeToOptionsScreen());
    }

    @Test
    public void testChangeToMainMenu(){
        assertFalse(menuMediator.isChangeToMainMenuScreen());
        menuMediator.changeToMenuScreen();
        assertTrue(menuMediator.isChangeToMainMenuScreen());
    }
}