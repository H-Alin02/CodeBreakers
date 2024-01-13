package View.Hud;

import Model.Player;

/**
 * L'interfaccia `HudComponent` definisce un contratto per gli elementi dell'HUD
 * che necessitano di essere aggiornati con le informazioni più recenti del giocatore.
 * @author Francesco Gambone
 */
public interface HudComponent {
    /**
     * Aggiorna l'elemento dell'HUD con le informazioni più recenti del giocatore.
     *
     * @param player Il giocatore del gioco.
     */
    void update(Player player);
}

