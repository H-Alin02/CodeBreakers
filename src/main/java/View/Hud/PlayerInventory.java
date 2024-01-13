package View.Hud;

import Model.Object.ObjectManager;
import Model.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * La classe `PlayerInventory` rappresenta un componente dell'HUD che mostra
 * l'inventario del giocatore, inclusi i conteggi di monete, chiavi, munizioni e USB.
 * @author Manda Hery Ny Aina
 * @author Francesco Gambone
 */
public class PlayerInventory implements HudComponent {

    private ObjectManager objectManager;
    private final float SCALE = 0.2f;

    private Table table;
    private Label labelCoin;
    private Label labelKey;
    private Label labelAmmunition;
    private Label labelUSB;

    // Contatori degli oggetti
    private int coinValue;
    private int keyValue;
    private int ammunitionValue;
    private int USB;

    /**
     * Costruisce un nuovo oggetto `PlayerInventory` con i conteggi iniziali degli oggetti.
     *
     * @param objectManager Il gestore degli oggetti del gioco.
     */
    public PlayerInventory(ObjectManager objectManager) {
        this.objectManager = objectManager;
        table = new Table();

        coinValue = objectManager.getItem().getCoin();
        keyValue = objectManager.getItem().getKey();
        ammunitionValue = objectManager.getItem().getAmmunition();
        USB = objectManager.getItem().getUSB();

        // Creazione delle immagini degli oggetti
        Texture image1 = new Texture(Gdx.files.internal("inventory/key/key_A_gold.png"));
        Image icon1 = new Image(image1);
        icon1.setSize(image1.getWidth() * SCALE, image1.getHeight() * SCALE);

        Texture image2 = new Texture(Gdx.files.internal("inventory/coin/coin.png"));
        Image icon2 = new Image(image2);
        icon2.setSize(image2.getWidth() * SCALE, image2.getHeight() * SCALE);

        Texture image6 = new Texture(Gdx.files.internal("inventory/ammunition/ammunition.png"));
        Image icon6 = new Image(image6);
        icon6.setSize(image6.getWidth() * SCALE, image6.getHeight() * SCALE);

        Texture image5 = new Texture(Gdx.files.internal("inventory/USB/usb.png"));
        Image icon5 = new Image(image5);
        icon5.setSize(image1.getWidth() * SCALE, image5.getHeight() * SCALE);

        // Creazione delle etichette per i conteggi
        labelCoin = new Label(String.format("%01d", coinValue), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        labelKey = new Label(String.format("%01d", keyValue), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        labelAmmunition = new Label(String.format("%03d", ammunitionValue), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        labelUSB = new Label(String.format("%01d", USB), new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        // Impostazione dello sfondo della tabella
        Pixmap bgPixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        bgPixmap.setColor(0, 0, 0, 0.5f);
        bgPixmap.fill();
        TextureRegionDrawable textureBackground = new TextureRegionDrawable(new TextureRegion(new Texture(bgPixmap)));
        table.setBackground(textureBackground);
        bgPixmap.dispose();

        // Aggiunta di oggetti e conteggi alla tabella
        table.add(icon1).padRight(5);
        table.add(labelKey).padRight(5);
        table.add(icon2).padRight(5);
        table.add(labelCoin).padRight(5);
        table.add(icon6).padRight(5);
        table.add(labelAmmunition).padRight(5);
        table.add(icon5).padRight(5);
        table.add(labelUSB);

        table.setVisible(true);
    }

    /**
     * Restituisce la tabella contenente l'inventario del giocatore.
     *
     * @return La tabella dell'inventario del giocatore.
     */
    public Table getTable() {
        return table;
    }

    /**
     * Alterna la visibilità della tabella dell'inventario.
     */
    public void visibilitySwitch() {
        boolean visible = this.table.isVisible();
        if (visible) {
            this.table.setVisible(false);
        } else if (!visible) {
            this.table.setVisible(true);
        }
    }

    @Override
    public void update(Player player) {
        // Aggiorna i conteggi degli oggetti in base allo stato attuale nel gioco
        coinValue = objectManager.getItem().getCoin();
        labelCoin.setText(String.format("%4d", coinValue));

        keyValue = objectManager.getItem().getKey();
        labelKey.setText(String.format("%01d", keyValue));

        ammunitionValue = player.getBulletCount();
        labelAmmunition.setText(String.format("%3d", ammunitionValue));

        USB = objectManager.getItem().getUSB();
        labelUSB.setText(String.format("%01d", USB));
    }
}
