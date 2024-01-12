package Model.Object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

/**
 * La classe {@code KeyUSB} rappresenta un oggetto chiavetta USB nel gioco.
 * Implementa l'interfaccia {@code GameObject} e fornisce metodi per
 * disegnare e gestire le informazioni relative alle chiavette USB.
 * @author Rakotomalala Manda
 */
public class KeyUSB implements GameObject {

    private int keyX, keyY;
    private final int OBJECT_HEIGHT = 32;
    private final int OBJECT_WIDTH = 32;
    private boolean remove;
    private TextureRegion texture;
    private final float SCALE = 1.2f;
    private String name;

    /**
     * Costruisce una nuova chiavetta USB con le coordinate specificate.
     *
     * @param keyX La coordinata x della chiavetta USB.
     * @param keyY La coordinata y della chiavetta USB.
     */
    public KeyUSB(int keyX, int keyY) {
        this.keyX = keyX;
        this.keyY = keyY;
        texture = new TextureRegion(new Texture(Gdx.files.internal("object/keyUSB/ChiavettaUSB.png")));

        name = "chiavettaUSB";
        remove = false;
    }

    /**
     * Disegna la chiavetta USB utilizzando il lotto di sprite fornito.
     *
     * @param batch Il lotto di sprite utilizzato per il rendering.
     */
    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, keyX, keyY, OBJECT_HEIGHT * SCALE, OBJECT_WIDTH * SCALE);
    }

    /**
     * Ottiene l'area di delimitazione della chiavetta USB.
     *
     * @return Il rettangolo che rappresenta l'area di delimitazione.
     */
    @Override
    public Rectangle getArea() {
        return new Rectangle(keyX, keyY, OBJECT_WIDTH, OBJECT_HEIGHT);
    }

    /**
     * Verifica se la chiavetta USB deve essere rimossa dal gioco.
     *
     * @return {@code true} se la chiavetta USB deve essere rimossa, {@code false} altrimenti.
     */
    @Override
    public boolean isRemove() {
        return remove;
    }

    /**
     * Imposta lo stato di rimozione della chiavetta USB.
     *
     * @param b Lo stato di rimozione, {@code true} per rimuovere, {@code false} altrimenti.
     */
    @Override
    public void setRemove(boolean b) {
        remove = b;
    }

    /**
     * Ottiene il nome della chiavetta USB.
     *
     * @return Il nome della chiavetta USB.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Ottiene il valore associato alla chiavetta USB.
     *
     * @return Il valore della chiavetta USB (sempre 0 per le chiavette USB).
     */
    @Override
    public int getValue() {
        return 0;
    }

    /**
     * Imposta il valore associato alla chiavetta USB (non utilizzato per le chiavette USB).
     *
     * @param value Il valore da impostare (non utilizzato per le chiavette USB).
     */
    @Override
    public void setValue(int value) {
        // Metodo vuoto, poiché le chiavette USB non hanno un valore.
    }
}
