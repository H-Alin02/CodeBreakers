package Model.NPC;

import View.Hud.NPCObserver;

/**
 * L'interfaccia {@code NPC} rappresenta un personaggio non giocante (NPC) nel gioco.
 * Gli oggetti che implementano questa interfaccia sono in grado di interagire
 * con il giocatore e possono essere osservati da un {@code NPCObserver}.
 * Rappresenta il Product del Pattern Factory Method , e il Publisher del Pattern Observer.
 * @author Alin Marian Habasescu
 */

public interface NPC {

    /**
     * Avvia una conversazione con l'NPC.
     */
    void talk();

    /**
     * Aggiunge un osservatore {@code NPCObserver} all'NPC.
     *
     * @param observer L'osservatore da aggiungere.
     */
    void addObserver(NPCObserver observer);

    /**
     * Aggiorna lo stato dell'NPC nel tempo.
     *
     * @param delta Il tempo trascorso dall'ultimo aggiornamento.
     */
    void update(float delta);

}
