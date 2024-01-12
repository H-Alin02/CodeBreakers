package Model.Object;

import Model.Player;
import Model.SoundPlayer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * L'interfaccia {@code GameObject} definisce il contratto per gli oggetti del gioco.
 * Gli oggetti che implementano questa interfaccia devono fornire metodi per il rendering,
 * l'aggiornamento, la gestione delle collisioni e altre informazioni essenziali.
 * @author Rakotomalala Manda
 */
public interface GameObject {

    /**
     * Il suono di raccolta predefinito per gli oggetti del gioco.
     */
    SoundPlayer pickSound = new SoundPlayer("sound_effects/item_pick_sound.wav");

    /**
     * Disegna l'oggetto utilizzando il lotto di sprite fornito.
     *
     * @param batch Il lotto di sprite utilizzato per il rendering.
     */
    void draw(SpriteBatch batch);

    /**
     * Aggiorna lo stato dell'oggetto in base al tempo trascorso dal suo ultimo aggiornamento.
     *
     * @param delta Il tempo trascorso dall'ultimo aggiornamento.
     */
    default void update(float delta) {}

    /**
     * Verifica se l'oggetto collide con il giocatore.
     *
     * @param player Il giocatore con cui verificare la collisione.
     * @return {@code true} se c'è una collisione, {@code false} altrimenti.
     */
    default boolean collide(Player player) {
        return player.getArea().overlaps(getArea());
    }

    /**
     * Ottiene l'area di delimitazione dell'oggetto.
     *
     * @return Il rettangolo che rappresenta l'area di delimitazione.
     */
    Rectangle getArea();

    /**
     * Verifica se l'oggetto deve essere rimosso dal gioco.
     *
     * @return {@code true} se l'oggetto deve essere rimosso, {@code false} altrimenti.
     */
    boolean isRemove();

    /**
     * Imposta lo stato di rimozione dell'oggetto.
     *
     * @param b Lo stato di rimozione, {@code true} per rimuovere, {@code false} altrimenti.
     */
    void setRemove(boolean b);

    /**
     * Ottiene il nome dell'oggetto.
     *
     * @return Il nome dell'oggetto.
     */
    String getName();

    /**
     * Ottiene il valore associato all'oggetto.
     *
     * @return Il valore dell'oggetto.
     */
    int getValue();

    /**
     * Imposta il valore associato all'oggetto.
     *
     * @param value Il valore da aggiungere al valore corrente.
     */
    void setValue(int value);

    /**
     * Ottiene il suono di raccolta associato all'oggetto.
     *
     * @return Il lettore audio del suono di raccolta.
     */
    default SoundPlayer getPickSound() {
        return pickSound;
    }
}
