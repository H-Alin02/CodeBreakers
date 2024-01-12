package Model.Object;

import Model.Player;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

/**
 * La classe {@code ObjectManager} gestisce gli oggetti di gioco presenti nella mappa.
 * Contiene un elenco di oggetti, un creatore di oggetti e gestisce le collisioni con il giocatore.
 * @author Rakotomalala Manda
 */
public class ObjectManager {

    private Array<GameObject> objects;
    private ObjectGameCreator objectCreator;
    private Player player;
    private Item item;
    private int medicalLife;
    private int energy;

    /**
     * Costruisce un nuovo gestore di oggetti, inizializza gli oggetti e i valori iniziali.
     */
    public ObjectManager() {
        this.objects = new Array<>();
        item = new Item();
        player = Player.getInstance();
        this.objectCreator = new ObjectCreator();
        medicalLife = 100;
        energy = 25;
    }

    /**
     * Inizializza gli oggetti nella mappa di gioco.
     */
    public void initializeObject() {
        // Inizializzazione degli oggetti nella mappa tutorial
        objects.add(objectCreator.createObject("coin", 3600, 1800));
        objects.add(objectCreator.createObject("coin", 3500, 1800));
        objects.add(objectCreator.createObject("coin", 3400, 1800));

        objects.add(objectCreator.createObject("meat", 3100, 2800));
        objects.add(objectCreator.createObject("meat", 1600, 1000));
        objects.add(objectCreator.createObject("ammunition", 2944, 2816));
        objects.add(objectCreator.createObject("medikit", 850, 1300));

        // Inizializzazione degli oggetti nella mappa di gioco
        objects.add(objectCreator.createObject("ammunition", 6336, 2048));
        objects.add(objectCreator.createObject("ammunition", 6080, 2048));
        objects.add(objectCreator.createObject("medikit", 8320, 3520));
        objects.add(objectCreator.createObject("key", 8192, 3968));
        objects.add(objectCreator.createObject("key", 7616, 4288));
    }

    /**
     * Disegna gli oggetti nella mappa utilizzando il lotto di sprite fornito.
     *
     * @param batch Il lotto di sprite utilizzato per il rendering.
     */
    public void draw(SpriteBatch batch) {
        for (GameObject obj : objects) {
            obj.draw(batch);
        }
    }

    /**
     * Verifica le collisioni tra il giocatore e gli oggetti nella mappa.
     * Se si verifica una collisione, gestisce l'effetto dell'oggetto sulla salute e le risorse del giocatore.
     */
    public void checkCollision() {
        for (GameObject obj : objects) {
            if (obj.collide(player)) {
                boolean removeItem = true;

                if (obj instanceof Medikit) {
                    if (player.getPlayerLife() >= 100) {
                        player.setPlayerLife(0);
                        removeItem = false;
                    } else {
                        player.setPlayerLife(medicalLife);
                    }

                }

                if (obj instanceof Meat) {
                    if (player.getPlayerLife() >= 100) {
                        player.setPlayerLife(0);
                        removeItem = false;
                    } else {
                        player.setPlayerLife(energy);
                    }
                }

                if (obj instanceof Ammunition) {
                    player.setBulletCount(player.getBulletCount() + 50);
                }

                if (removeItem) {
                    obj.getPickSound().play(0.2f);
                    objects.removeIndex(objects.indexOf(obj, false));
                    obj.setRemove(true);
                    item.add(obj);
                }
            }
        }
    }

    /**
     * Aggiorna gli oggetti nella mappa e verifica le collisioni.
     *
     * @param delta Il tempo trascorso dall'ultimo aggiornamento.
     */
    public void update(float delta) {
        for (int i = 0; i < objects.size; i++) {
            GameObject obj = objects.get(i);
            obj.update(delta);
            checkCollision();
        }

        GameObject.pickSound.update(delta);
        Ammunition.updateSound(delta);
        Coin.updateSound(delta);
        Meat.updateSound(delta);
    }

    /**
     * Restituisce l'oggetto {@code Item} contenente gli oggetti raccolti dal giocatore.
     *
     * @return L'oggetto {@code Item} contenente gli oggetti raccolti.
     */
    public Item getItem() {
        return this.item;
    }

    /**
     * Aggiunge un oggetto alla lista degli oggetti nella mappa.
     *
     * @param object L'oggetto da aggiungere.
     */
    public void addObject(GameObject object) {
        this.objects.add(object);
    }
}
