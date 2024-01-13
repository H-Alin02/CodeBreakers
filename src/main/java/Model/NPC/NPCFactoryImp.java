package Model.NPC;

import com.badlogic.gdx.math.Vector2;

/**
 * L'implementazione dell'interfaccia {@code NPCFactory} che fornisce un'implementazione
 * concreta per la creazione di oggetti NPC specifici in base al nome fornito.
 * @author Alin Marian Habasescu
 */
public class NPCFactoryImp implements NPCFactory{

    /**
     * Crea un nuovo NPC in base al nome specificato e alla posizione data.
     *
     * @param name      Il nome dell'NPC da creare.
     * @param position  La posizione iniziale dell'NPC nel mondo di gioco.
     * @return Un'istanza di {@code NPC} creata in base al nome specificato.
     *         Se il nome non corrisponde a nessun NPC conosciuto, restituisce {@code null}.
     */
    @Override
    public NPC createNPC(String name,Vector2 position) {
        return switch (name) {
            case "DrGarfild" -> new DrGarfield(position);
            case "Dave" -> new DaveNPC(position);
            default -> null;
        };
    }
}
