package Model.Object;

import Model.SoundPlayer;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

/**
 * La classe {@code Coin} rappresenta un oggetto moneta nel gioco.
 * Implementa l'interfaccia {@code GameObject} e fornisce metodi per
 * interagire con e gestire gli oggetti moneta.
 * @author Rakotomalala Manda
 */
public class Coin implements GameObject {

    private final Animation<TextureRegion> coin;
    private int coinX, coinY;
    private final int OBJECT_HEIGHT = 32;
    private final int OBJECT_WIDTH = 32;
    private float stateTime;
    private boolean remove;
    private final float SCALE = 1.2f;
    private String name;
    private int coinValue;
    private static final SoundPlayer pickSound = new SoundPlayer("sound_effects/pick_coin.wav");

    /**
     * Aggiorna il suono di raccolta dell'oggetto moneta.
     *
     * @param delta Il tempo trascorso dall'ultimo aggiornamento.
     */
    public static void updateSound(float delta) {
        pickSound.update(delta);
    }

    /**
     * Costruisce un nuovo oggetto moneta con le coordinate specificate.
     *
     * @param coinX La coordinata x dell'oggetto moneta.
     * @param coinY La coordinata y dell'oggetto moneta.
     */
    public Coin(int coinX, int coinY) {
        this.coinX = coinX;
        this.coinY = coinY;

        // Animazione della moneta
        Array<TextureRegion> coinFrames = new Array<>();
        for (int i = 0; i <= 13; i++) {
            coinFrames.add(new TextureRegion(new Texture("object/coin/frame" + i + ".png")));
        }
        coin = new Animation<>(0.05f, coinFrames, Animation.PlayMode.LOOP);

        name = "moneta";
        remove = false;
        coinValue = 50;
    }

    /**
     * Ottiene il suono di raccolta associato all'oggetto moneta.
     *
     * @return Il lettore audio del suono di raccolta.
     */
    public SoundPlayer getPickSound() {
        return pickSound;
    }

    /**
     * Aggiorna lo stato dell'animazione della moneta.
     *
     * @param delta Il tempo trascorso dall'ultimo aggiornamento.
     */
    public void update(float delta) {
        stateTime += delta;
    }

    /**
     * Ottiene l'area di delimitazione dell'oggetto moneta.
     *
     * @return Il rettangolo che rappresenta l'area di delimitazione.
     */
    @Override
    public Rectangle getArea() {
        return new Rectangle(coinX, coinY, OBJECT_WIDTH, OBJECT_HEIGHT);
    }

    /**
     * Verifica se l'oggetto moneta deve essere rimosso dal gioco.
     *
     * @return {@code true} se l'oggetto deve essere rimosso, {@code false} altrimenti.
     */
    @Override
    public boolean isRemove() {
        return remove;
    }

    /**
     * Imposta lo stato di rimozione dell'oggetto moneta.
     *
     * @param b Lo stato di rimozione, {@code true} per rimuovere, {@code false} altrimenti.
     */
    @Override
    public void setRemove(boolean b) {
        remove = b;
    }

    /**
     * Disegna l'oggetto moneta utilizzando il lotto di sprite fornito.
     *
     * @param batch Il lotto di sprite utilizzato per il rendering.
     */
    public void draw(SpriteBatch batch) {
        batch.draw(getKeyFrame(), coinX, coinY, OBJECT_WIDTH * SCALE, OBJECT_HEIGHT * SCALE);
    }

    /**
     * Ottiene il frame chiave dell'animazione della moneta.
     *
     * @return Il frame chiave corrente dell'animazione.
     */
    public TextureRegion getKeyFrame() {
        return coin.getKeyFrame(stateTime, true);
    }

    /**
     * Ottiene il nome dell'oggetto moneta.
     *
     * @return Il nome dell'oggetto.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Ottiene il valore associato all'oggetto moneta.
     *
     * @return Il valore dell'oggetto.
     */
    @Override
    public int getValue() {
        return coinValue;
    }

    /**
     * Imposta il valore associato all'oggetto moneta.
     *
     * @param value Il valore da aggiungere al valore corrente.
     */
    @Override
    public void setValue(int value) {
        coinValue += value;
    }
}
