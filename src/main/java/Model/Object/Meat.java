package Model.Object;

import Model.SoundPlayer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

/**
 * La classe {@code Meat} rappresenta un oggetto carne nel gioco.
 * Implementa l'interfaccia {@code GameObject} e fornisce metodi per
 * disegnare e gestire le informazioni relative alla carne.
 * @author Rakotomalala Manda
 */
public class Meat implements GameObject {

    private int meatX, meatY;
    private TextureRegion texture;
    private final int OBJECT_HEIGHT = 32;
    private final int OBJECT_WIDTH = 32;
    private boolean remove;
    private final float SCALE = 1.2f;
    private String name;
    private int meatValue;
    private static final SoundPlayer pickSound = new SoundPlayer("sound_effects/pick_food.mp3");

    /**
     * Aggiorna il suono di raccolta dell'oggetto carne.
     *
     * @param delta Il tempo trascorso dall'ultimo aggiornamento.
     */
    public static void updateSound(float delta) {
        pickSound.update(delta);
    }

    /**
     * Costruisce un nuovo oggetto carne con le coordinate specificate.
     *
     * @param meatX La coordinata x dell'oggetto carne.
     * @param meatY La coordinata y dell'oggetto carne.
     */
    public Meat(int meatX, int meatY) {
        this.meatX = meatX;
        this.meatY = meatY;
        texture = new TextureRegion(new Texture(Gdx.files.internal("object/meat/meat.png")));

        name = "carne";
        remove = false;
    }

    /**
     * Disegna l'oggetto carne utilizzando il lotto di sprite fornito.
     *
     * @param batch Il lotto di sprite utilizzato per il rendering.
     */
    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture, meatX, meatY, OBJECT_HEIGHT * SCALE, OBJECT_WIDTH * SCALE);
    }

    /**
     * Ottiene l'area di delimitazione dell'oggetto carne.
     *
     * @return Il rettangolo che rappresenta l'area di delimitazione.
     */
    @Override
    public Rectangle getArea() {
        return new Rectangle(meatX, meatY, OBJECT_WIDTH, OBJECT_HEIGHT);
    }

    /**
     * Verifica se l'oggetto carne deve essere rimosso dal gioco.
     *
     * @return {@code true} se l'oggetto carne deve essere rimosso, {@code false} altrimenti.
     */
    @Override
    public boolean isRemove() {
        return remove;
    }

    /**
     * Imposta lo stato di rimozione dell'oggetto carne.
     *
     * @param b Lo stato di rimozione, {@code true} per rimuovere, {@code false} altrimenti.
     */
    @Override
    public void setRemove(boolean b) {
        remove = b;
    }

    /**
     * Ottiene il nome dell'oggetto carne.
     *
     * @return Il nome dell'oggetto carne.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Ottiene il valore associato all'oggetto carne.
     *
     * @return Il valore dell'oggetto carne.
     */
    @Override
    public int getValue() {
        return meatValue;
    }

    /**
     * Imposta il valore associato all'oggetto carne.
     *
     * @param value Il valore da impostare.
     */
    @Override
    public void setValue(int value) {
        meatValue = value;
    }

    /**
     * Ottiene il suono di raccolta associato all'oggetto carne.
     *
     * @return Il lettore audio del suono di raccolta.
     */
    @Override
    public SoundPlayer getPickSound() {
        return pickSound;
    }
}
