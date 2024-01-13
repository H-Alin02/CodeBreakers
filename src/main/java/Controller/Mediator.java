package Controller;
/**
 * L'interfaccia Mediator definisce metodi per gestire la comunicazione
 * e la coordinazione tra componenti del sistema.
 * Questa interfaccia fornisce metodi per cambiare lo stato del gioco
 * e navigare tra diverse schermate.
 * @author Francesco Gambone
 */

public interface Mediator {

    /**
     * Metodo per cambiare lo stato del gioco.
     * Questo metodo dovrebbe essere chiamato quando si desidera
     * cambiare lo stato corrente del gioco.
     */
    void changeGameStatus();

    /**
     * Metodo per passare alla schermata del menu.
     * Questo metodo dovrebbe essere chiamato quando si desidera
     * visualizzare la schermata del menu principale.
     */
    void changeToMenuScreen();

    /**
     * Metodo per passare alla schermata delle opzioni.
     * Questo metodo dovrebbe essere chiamato quando si desidera
     * visualizzare la schermata delle opzioni del gioco.
     */
    void changeToOptionsScreen();
}
