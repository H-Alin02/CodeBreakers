package View.Hud;

/**
 * L'interfaccia `NPCObserver` definisce un contratto per gli oggetti che desiderano
 * osservare gli eventi di dialogo con un personaggio non giocante (NPC) nell'HUD.
 * @author Alin Marian Habasescu
 *
 */
public interface NPCObserver {
    /**
     * Metodo chiamato quando un NPC inizia a parlare.
     *
     * @param message Il messaggio del dialogo avviato dall'NPC.
     */
    void onNPCTalk(String message);

    /**
     * Metodo chiamato quando un NPC ha finito di parlare.
     */
    void onNPCFinishedTalk();
}
