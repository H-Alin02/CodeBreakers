package Model.Object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

/**
 * La classe {@code Medikit} rappresenta un oggetto kit medico nel gioco.
 * Implementa l'interfaccia {@code GameObject} e fornisce metodi per
 * disegnare e gestire le informazioni relative ai kit medici.
 * @author Rakotomalala Manda
 */
public class Medikit implements GameObject {

    private int medikitX, medikitY;
    private TextureRegion texture;
    private final int OBJECT_HEIGHT = 32;
    private final int OBJECT_WIDTH = 32;
    private boolean remove;
    private boolean isAvailable;
    private final float SCALE = 1.2f;
    private String name;
    private int medicalLife;

    /**
     * Costruisce un nuovo oggetto kit medico con le coordinate specificate.
     *
     * @param medikitX La coordinata x dell'oggetto kit medico.
     * @param medikitY La coordinata y dell'oggetto kit medico.
     */
    public Medikit(int medikitX, int medikitY) {
        this.medikitX = medikitX;
        this.medikitY = medikitY;
        this.texture = new TextureRegion(new Texture(Gdx.files.internal("object/medikit/medikit.png")));

        this.name = "kitMedico";
        this.remove = false;
        this.isAvailable = true;
    }

    /**
     * Disegna l'oggetto kit medico utilizzando il lotto di sprite fornito.
     *
     * @param batch Il lotto di sprite utilizzato per il rendering.
     */
    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, medikitX, medikitY, OBJECT_HEIGHT * SCALE, OBJECT_WIDTH * SCALE);
    }

    /**
     * Ottiene l'area di delimitazione dell'oggetto kit medico.
     *
     * @return Il rettangolo che rappresenta l'area di delimitazione.
     */
    @Override
    public Rectangle getArea() {
        return new Rectangle(medikitX, medikitY, OBJECT_WIDTH, OBJECT_HEIGHT);
    }

    /**
     * Verifica se l'oggetto kit medico deve essere rimosso dal gioco.
     *
     * @return {@code true} se l'oggetto kit medico deve essere rimosso, {@code false} altrimenti.
     */
    @Override
    public boolean isRemove() {
        return this.remove;
    }

    /**
     * Imposta lo stato di rimozione dell'oggetto kit medico.
     *
     * @param b Lo stato di rimozione, {@code true} per rimuovere, {@code false} altrimenti.
     */
    @Override
    public void setRemove(boolean b) {
        this.remove = b;
    }

    /**
     * Ottiene il nome dell'oggetto kit medico.
     *
     * @return Il nome dell'oggetto kit medico.
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Ottiene il valore associato all'oggetto kit medico.
     *
     * @return Il valore dell'oggetto kit medico (quantità di vita curata).
     */
    @Override
    public int getValue() {
        return medicalLife;
    }

    /**
     * Imposta il valore associato all'oggetto kit medico.
     *
     * @param value Il valore da impostare (quantità di vita curata).
     */
    @Override
    public void setValue(int value) {
        medicalLife = value;
    }
}
