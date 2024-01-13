package View.Hud;

import Model.Player;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * La classe `PlayerStats` rappresenta un componente dell'HUD che mostra le statistiche
 * del giocatore, inclusa la vita e la stamina con relative barre di progresso.
 * @author Francesco Gambone
 * @author Alin Marian Habasescu
 */
public class PlayerStats implements HudComponent {

    private Label lifeLabel = new Label("Vita", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
    private Label staminaLabel = new Label("Stamina", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
    private ProgressBar lifeBar;
    private ProgressBar staminaBar;
    private Skin skin = new Skin();
    private Table tableStats = new Table(skin);
    private float playerLife = 1f;
    private float playerStamina = 1f;

    /**
     * Costruisce un nuovo oggetto `PlayerStats` con le etichette e le barre per la vita e la stamina.
     */
    public PlayerStats() {
        // Configurazione del tema per le ProgressBar
        Pixmap pixmap = new Pixmap(1, 10, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("white", new Texture(pixmap));

        // Configurazione della barra della vita
        TextureRegionDrawable textureLifeBar = new TextureRegionDrawable(new TextureRegion(new Texture("hudAssets/barGreen_horizontalMid.png")));
        ProgressBar.ProgressBarStyle lifeBarStyle = new ProgressBar.ProgressBarStyle(skin.newDrawable("white", Color.DARK_GRAY), textureLifeBar);
        lifeBar = new ProgressBar(0, 100, 0.5f, false, lifeBarStyle);
        lifeBar.setValue(playerLife);
        lifeBarStyle.knobBefore = lifeBarStyle.knob;

        // Configurazione della barra della stamina
        TextureRegionDrawable textureStaminaBar = new TextureRegionDrawable(new TextureRegion(new Texture("hudAssets/barBlue_horizontalMid.png")));
        ProgressBar.ProgressBarStyle staminaBarStyle = new ProgressBar.ProgressBarStyle(skin.newDrawable("white", Color.DARK_GRAY), textureStaminaBar);
        staminaBar = new ProgressBar(0, 100, 0.5f, false, staminaBarStyle);
        staminaBar.setValue(playerStamina);
        staminaBarStyle.knobBefore = staminaBarStyle.knob;
        staminaBarStyle.disabledKnob = staminaBarStyle.knob;

        pixmap.dispose();

        // Impostazione dello sfondo della tabella
        Pixmap bgPixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        bgPixmap.setColor(0, 0, 0, 0.5f);
        bgPixmap.fill();
        TextureRegionDrawable textureBackground = new TextureRegionDrawable(new TextureRegion(new Texture(bgPixmap)));
        tableStats.setBackground(textureBackground);
        bgPixmap.dispose();

        // Aggiunta delle etichette e delle barre di progresso alla tabella
        tableStats.add(lifeLabel).padRight(10).padLeft(10);
        tableStats.add(lifeBar).padRight(10);

        tableStats.row();
        tableStats.add(staminaLabel).padRight(10).padLeft(10);
        tableStats.add(staminaBar).padRight(10);
    }

    /**
     * Restituisce la Table contenente le statistiche del giocatore.
     *
     * @return La Table delle statistiche del giocatore.
     */
    public Table getTableStats() {
        return tableStats;
    }

    @Override
    public void update(Player player) {
        // Aggiorna i valori delle barre di vita e stamina in base allo stato attuale degli attributi vita e stamina del giocatore
        lifeBar.setValue(player.getPlayerLife());
        staminaBar.setValue(player.getPlayerStamina());
    }
}
