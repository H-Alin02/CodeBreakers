package Controller;

/**
 * La classe MenuMediator implementa l'interfaccia Mediator e gestisce la comunicazione
 * tra il Gamescreen, che gestisce anche la logica di gioco, e il Menu di gioco.
 * Fornisce metodi per comunicare la richiesta, da parte del menu, di cambiare lo stato del gioco
 * e la schermata attuale a Gamescreen, il quale cambia lo stato delle richieste da true a false quando viene esaudita.
 *
 * @author Francesco Gambone
 */
public class MenuMediator implements Mediator {

    private boolean changeGameState;
    private boolean changeToOptionsScreen;
    private boolean changeToMainMenuScreen;

    /**
     * Costruttore della classe MenuMediator.
     * Inizializza le variabili di stato a false.
     */
    public MenuMediator() {
        this.changeGameState = false;
        this.changeToOptionsScreen = false;
        this.changeToMainMenuScreen = false;
    }

    /**
     * Restituisce lo stato della richiesta di cambio di stato del gioco.
     *
     * @return true se il gioco deve cambiare stato, altrimenti false.
     */
    public boolean isChangeGameState() {
        return changeGameState;
    }

    /**
     * Restituisce lo stato della richiesta di cambio alla schermata principale del menu.
     *
     * @return true se deve passare alla schermata principale del menu, altrimenti false.
     */
    public boolean isChangeToMainMenuScreen() {
        return changeToMainMenuScreen;
    }

    /**
     * Restituisce lo stato della richiesta di cambio alla schermata delle opzioni del menu.
     *
     * @return true se deve passare alla schermata delle opzioni del menu, altrimenti false.
     */
    public boolean isChangeToOptionsScreen() {
        return changeToOptionsScreen;
    }

    /**
     * Cambia lo stato della richiesta di cambio stato del gioco.
     */
    @Override
    public void changeGameStatus() {
        this.changeGameState = !this.changeGameState;
    }

    /**
     * Cambia lo stato della richiesta di passagio alla schermata del menu principale.
     */
    @Override
    public void changeToMenuScreen() {
        this.changeToMainMenuScreen = !this.changeToMainMenuScreen;
    }

    /**
     * Cambia lo stato della richiesta di passaggio alla schermata delle opzioni del menu.
     */
    @Override
    public void changeToOptionsScreen() {
        this.changeToOptionsScreen = !this.changeToOptionsScreen;
    }
}
