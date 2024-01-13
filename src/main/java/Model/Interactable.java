package Model;

import Model.Object.ObjectManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * L'interfaccia rappresenta un oggetto interagibile nel gioco, con cui il giocatore può interagire
 * @author Alin Marian Habasescu
 */
public interface Interactable {

    /**
     * Gestisce l'interazione con l'oggetto interagibile da parte del giocatore.
     *
     * @param player L'entità del giocatore nel gioco.
     */
    void interact(Player player);

    /**
     * Ottiene la posizione dell'oggetto interagibile.
     *
     * @return Un oggetto Vector2 rappresentante la posizione dell'oggetto interagibile.
     */
    Vector2 getPosition();

    /**
     * Verifica se c'è una collisione con l'oggetto interagibile basandosi sulle coordinate e le dimensioni fornite.
     *
     * @param x      La coordinata x dell'oggetto per verificare la collisione.
     * @param y      La coordinata y dell'oggetto per verificare la collisione.
     * @param width  La larghezza dell'oggetto per verificare la collisione.
     * @param height L'altezza dell'oggetto per verificare la collisione.
     * @return True se c'è una collisione, altrimenti false.
     */
    boolean isCollision(float x, float y, float width, float height);

    /**
     * Disegna l'oggetto interagibile, eventualmente con informazioni aggiuntive
     * in base alla posizione del giocatore o ad altre condizioni.
     *
     * @param batch  Lo SpriteBatch utilizzato per il rendering.
     * @param player L'entità del giocatore nel gioco.
     */
    void draw(SpriteBatch batch, Player player);

    /**
     * Reimposta lo stato dell'oggetto interagibile al suo stato iniziale.
     */
    void reset();

    /**
     * Imposta l'ObjectManager per l'oggetto interagibile per interagire con altre entità di gioco.
     *
     * @param objectManager L'istanza di ObjectManager.
     */
    void addObjectManager(ObjectManager objectManager);
}
