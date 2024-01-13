package Model.NPC;

import com.badlogic.gdx.math.Vector2;

/**
 * L'interfaccia {@code NPCFactory} è una factory astratta per la creazione di oggetti NPC.
 * Implementando questa interfaccia, è possibile definire nuovi tipi di NPC e fornire
 * una logica specifica per la loro creazione.
 * @author Alin Marian Habasescu
 */
public interface NPCFactory {

    /**
     * Crea un nuovo NPC con il nome specificato e la posizione data.
     *
     * @param name      Il nome dell'NPC da creare.
     * @param position  La posizione iniziale dell'NPC nel mondo di gioco.
     * @return Un'istanza di {@code NPC} creata secondo le specifiche dell'implementazione.
     */
    NPC createNPC(String name ,Vector2 position);
}
