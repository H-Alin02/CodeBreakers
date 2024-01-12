package Model.Object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

/**
 * La classe {@code Key} rappresenta un oggetto chiave nel gioco.
 * Implementa l'interfaccia {@code GameObject} e fornisce metodi per
 * disegnare e gestire le informazioni relative alle chiavi.
 * @author Rakotomalala Manda
 */
public class Key implements GameObject {

    private int keyX, keyY;
    private final int OBJECT_HEIGHT = 32;
    private final int OBJECT_WIDTH = 32;
    private boolean remove;
    private TextureRegion texture;
    private final float SCALE = 1.2f;
    private String name;

    /**
     * Costruisce una nuova chiave con le coordinate specificate.
     *
     * @param keyX La coordinata x della chiave.
     * @param keyY La coordinata y della chiave.
     */
    public Key(int keyX, int keyY) {
        this.keyX = keyX;
        this.keyY = keyY;
        texture = new TextureRegion(new Texture(Gdx.files.internal("object/key/key_A_gold.png")));

        name = "chiave";
        remove = false;
    }

    /**
     * Disegna la chiave utilizzando il lotto di sprite fornito.
     *
     * @param batch Il lotto di sprite utilizzato per il rendering.
     */
    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, keyX, keyY, OBJECT_HEIGHT * SCALE, OBJECT_WIDTH * SCALE);
    }

    /**
     * Ottiene l'area di delimitazione della chiave.
     *
     * @return Il rettangolo che rappresenta l'area di delimitazione.
     */
    @Override
    public Rectangle getArea() {
        return new Rectangle(keyX, keyY, OBJECT_WIDTH, OBJECT_HEIGHT);
    }

    /**
     * Verifica se la chiave deve essere rimossa dal gioco.
     *
     * @return {@code true} se la chiave deve essere rimossa, {@code false} altrimenti.
     */
    @Override
    public boolean isRemove() {
        return remove;
    }

    /**
     * Imposta lo stato di rimozione della chiave.
     *
     * @param b Lo stato di rimozione, {@code true} per rimuovere, {@code false} altrimenti.
     */
    @Override
    public void setRemove(boolean b) {
        remove = b;
    }

    /**
     * Ottiene il nome della chiave.
     *
     * @return Il nome della chiave.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Ottiene il valore associato alla chiave.
     *
     * @return Il valore della chiave (sempre 0 per le chiavi).
     */
    @Override
    public int getValue() {
        return 0;
    }

    /**
     * Imposta il valore associato alla chiave (non utilizzato per le chiavi).
     *
     * @param value Il valore da impostare (non utilizzato per le chiavi).
     */
    @Override
    public void setValue(int value) {
        // Metodo vuoto, poiché le chiavi non hanno un valore.
    }
}
