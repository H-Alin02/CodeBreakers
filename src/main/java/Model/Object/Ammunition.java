package Model.Object;

import Model.SoundPlayer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

/**
 * La classe {@code Ammunition} rappresenta un oggetto munizioni nel gioco.
 * Implementa l'interfaccia {@code GameObject} e fornisce metodi per
 * interagire con e gestire gli oggetti munizioni.
 * @author Rakotomalala Manda
 */
public class Ammunition implements GameObject {

    private int ammunitionX, ammunitionY;
    private TextureRegion texture;
    private final int OBJECT_HEIGHT = 32;
    private final int OBJECT_WIDTH = 32;
    private boolean remove;
    private final float SCALE = 1.2f;
    private String name;
    private int ammunitionValue;
    private static final SoundPlayer pickSound = new SoundPlayer("sound_effects/pick_ammo.mp3");

    /**
     * Aggiorna il suono di raccolta dell'oggetto munizioni.
     *
     * @param delta Il tempo trascorso dall'ultimo aggiornamento.
     */
    public static void updateSound(float delta) {
        pickSound.update(delta);
    }

    /**
     * Costruisce un nuovo oggetto munizioni con le coordinate specificate.
     *
     * @param ammunitionX La coordinata x dell'oggetto munizioni.
     * @param ammunitionY La coordinata y dell'oggetto munizioni.
     */
    public Ammunition(int ammunitionX, int ammunitionY) {
        this.ammunitionX = ammunitionX;
        this.ammunitionY = ammunitionY;
        this.texture = new TextureRegion(new Texture(Gdx.files.internal("object/ammunition/ammunition.png")));

        this.name = "munizioni";
        this.remove = false;
        this.ammunitionValue = 50;
    }

    /**
     * Ottiene il suono di raccolta associato all'oggetto munizioni.
     *
     * @return Il lettore audio del suono di raccolta.
     */
    public SoundPlayer getPickSound() {
        return pickSound;
    }

    /**
     * Disegna l'oggetto munizioni utilizzando il lotto di sprite fornito.
     *
     * @param batch Il lotto di sprite utilizzato per il rendering.
     */
    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, ammunitionX, ammunitionY, OBJECT_HEIGHT * SCALE, OBJECT_WIDTH * SCALE);
    }

    /**
     * Ottiene l'area di delimitazione dell'oggetto munizioni.
     *
     * @return Il rettangolo che rappresenta l'area di delimitazione.
     */
    @Override
    public Rectangle getArea() {
        return new Rectangle(ammunitionX, ammunitionY, OBJECT_WIDTH, OBJECT_HEIGHT);
    }

    /**
     * Verifica se l'oggetto munizioni deve essere rimosso dal gioco.
     *
     * @return {@code true} se l'oggetto deve essere rimosso, {@code false} altrimenti.
     */
    @Override
    public boolean isRemove() {
        return this.remove;
    }

    /**
     * Imposta lo stato di rimozione dell'oggetto munizioni.
     *
     * @param b Lo stato di rimozione, {@code true} per rimuovere, {@code false} altrimenti.
     */
    @Override
    public void setRemove(boolean b) {
        this.remove = b;
    }

    /**
     * Ottiene il nome dell'oggetto munizioni.
     *
     * @return Il nome dell'oggetto.
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Ottiene il valore associato all'oggetto munizioni.
     *
     * @return Il valore dell'oggetto.
     */
    @Override
    public int getValue() {
        return ammunitionValue;
    }

    /**
     * Imposta il valore associato all'oggetto munizioni.
     *
     * @param value Il valore da aggiungere al valore corrente.
     */
    @Override
    public void setValue(int value) {
        ammunitionValue += value;
    }
}
