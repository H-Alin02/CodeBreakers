package Model.Object;

import com.badlogic.gdx.utils.Array;

/**
 * La classe {@code Item} rappresenta una collezione di oggetti nel gioco.
 * Fornisce metodi per aggiungere, contare, ottenere valori e rimuovere oggetti di diversi tipi.
 * @author Rakotomalala Manda
 */
public class Item {

    private final Array<GameObject> items;

    /**
     * Aggiunge un oggetto di tipo generico alla collezione.
     *
     * @param item L'oggetto da aggiungere.
     * @param <T>  Il tipo dell'oggetto.
     */
    public <T extends GameObject> void add(T item) {
        items.add(item);
        System.out.println(item.getName() + " raccolto, numero di oggetti: " + items.size);
    }

    /**
     * Costruttore della classe Item.
     * Inizializza la collezione di oggetti.
     */
    public Item() {
        this.items = new Array<>();
    }

    /**
     * Conta il numero di oggetti di un tipo specificato nella collezione.
     *
     * @param type Il tipo dell'oggetto da contare.
     * @param <T>  Il tipo dell'oggetto.
     * @return Il numero di oggetti del tipo specificato.
     */
    public <T extends GameObject> int typeCount(Class<T> type) {
        int value = 0;

        for (GameObject item : items)
            if (type.isInstance(item))
                value++;

        return value;
    }

    /**
     * Calcola la somma dei valori degli oggetti di un tipo specificato nella collezione.
     *
     * @param type Il tipo dell'oggetto di cui sommare i valori.
     * @param <T>  Il tipo dell'oggetto.
     * @return La somma dei valori degli oggetti del tipo specificato.
     */
    public <T extends GameObject> int typeValue(Class<T> type) {
        int value = 0;

        for (GameObject item : items)
            if (type.isInstance(item))
                value += item.getValue();

        return value;
    }

    /**
     * Rimuove il primo oggetto del tipo specificato dalla collezione.
     *
     * @param type Il tipo dell'oggetto da rimuovere.
     * @param <T>  Il tipo dell'oggetto.
     */
    public <T extends GameObject> void remove(Class<T> type) {
        for (GameObject item : items) {
            if (type.isInstance(item)) {
                items.removeIndex(items.indexOf(item, false));
                return;
            }
        }
    }

    /**
     * Ottiene il numero di oggetti di tipo {@code KeyUSB} nella collezione.
     *
     * @return Il numero di oggetti di tipo {@code KeyUSB}.
     */
    public int getUSB() {
        return typeCount(KeyUSB.class);
    }

    /**
     * Ottiene la somma dei valori degli oggetti di tipo {@code Coin} nella collezione.
     *
     * @return La somma dei valori degli oggetti di tipo {@code Coin}.
     */
    public int getCoin() {
        return typeValue(Coin.class);
    }

    /**
     * Ottiene il numero di oggetti di tipo {@code Key} nella collezione.
     *
     * @return Il numero di oggetti di tipo {@code Key}.
     */
    public int getKey() {
        return typeCount(Key.class);
    }

    /**
     * Ottiene la somma dei valori degli oggetti di tipo {@code Ammunition} nella collezione.
     *
     * @return La somma dei valori degli oggetti di tipo {@code Ammunition}.
     */
    public int getAmmunition() {
        return typeValue(Ammunition.class);
    }

    /**
     * Aggiorna lo stato della collezione di oggetti.
     *
     * @param delta Il tempo trascorso dall'ultimo aggiornamento.
     */
    public void update(float delta) {
        // Metodo vuoto, poiché l'aggiornamento della collezione potrebbe non essere necessario.
    }
}
