package View.Hud;

import Controller.MenuMediator;
import Model.Object.ObjectManager;
import Model.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 * La classe `Hud` rappresenta l'interfaccia utente (HUD) del gioco, contenente informazioni
 * sul giocatore, l'inventario, la casella di dialogo e il menu.
 * @author Francesco Gambone
 * @author Alin Marian Habasescu
 */

//TODO Aggiungere Javadoc specificando l'implementazione del pattern Obserber.
public class Hud extends WidgetGroup implements NPCObserver {

    private Stage stage;
    private ScreenViewport stageViewport;
    private PlayerStats playerStats;
    private PlayerInventory inventory;
    private DialogueBox dialogueBox;
    private Menu menu;

    private boolean npcReadyToTalk = false;

    /**
     * Costruisce un nuovo oggetto `Hud` con i componenti necessari per visualizzare
     * informazioni sul giocatore, l'inventario, la casella di dialogo e il menu.
     *
     * @param spriteBatch      Il batch per il disegno degli elementi di scena.
     * @param objectManager    Il gestore degli oggetti del gioco.
     * @param menuMediator     Il mediatore per la gestione del menu.
     */
    public Hud(SpriteBatch spriteBatch, ObjectManager objectManager, MenuMediator menuMediator) {
        stageViewport = new ScreenViewport();
        stageViewport.setUnitsPerPixel(0.6f);

        stage = new Stage(stageViewport, spriteBatch);

        Pixmap bgPixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        bgPixmap.setColor(0, 0, 0, 0.5f);
        bgPixmap.fill();
        TextureRegionDrawable textureBackground = new TextureRegionDrawable(new TextureRegion(new Texture(bgPixmap)));

        Table filler = new Table();
        Table topFiller = new Table();
        topFiller.setBackground(textureBackground);
        bgPixmap.dispose();

        playerStats = new PlayerStats();
        inventory = new PlayerInventory(objectManager);
        dialogueBox = new DialogueBox("");

        menu = new Menu(menuMediator);

        Table rootTable = new Table();
        rootTable.setFillParent(true);

        stage.addActor(rootTable);

        // Popolazione dello stage e posizionamento degli elementi

        // Prima riga
        rootTable.add(playerStats.getTableStats()).left().uniform().padLeft(10).fill().padTop(15);
        rootTable.add(filler).fill().uniform().padTop(15);
        rootTable.add(inventory.getTable()).center().uniform().padRight(10).fill().padTop(15);

        rootTable.row();
        // Seconda riga
        rootTable.add(filler).fill();
        rootTable.add(menu.getTable()).fill().expand().center().padLeft(30).padRight(30).padTop(50).padBottom(50);
        rootTable.add(filler).fill();

        rootTable.row();
        // Terza riga
        rootTable.add(dialogueBox.getTable()).colspan(3).height(100).padBottom(25);

        Gdx.input.setInputProcessor(stage);
    }

    /**
     * Restituisce lo stage associato all'HUD.
     *
     * @return Lo stage dell'HUD.
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Aggiorna l'HUD con le informazioni più recenti del giocatore.
     *
     * @param player Il giocatore.
     * @param delta  L'intervallo di tempo trascorso dall'ultimo aggiornamento.
     */
    public void update(Player player, float delta) {
        this.playerStats.update(player);
        this.inventory.update(player);
        this.menu.update(player);

        if (npcReadyToTalk) {
            dialogueBox.show(dialogueBox.getText());
        } else {
            dialogueBox.hide();
        }
    }

    //TODO Aggiungere Javadcc
    @Override
    public void onNPCTalk(String message) {
        this.npcReadyToTalk = true;
        dialogueBox.setMessage(message);
    }

    //TODO Aggiungere javadoc
    @Override
    public void onNPCFinishedTalk() {
        this.npcReadyToTalk = false;
    }

    /**
     * Rilascia le risorse associate all'HUD.
     */
    public void dispose() {
        stage.dispose();
    }

    /**
     * Imposta la visibilità del menu.
     */
    public void setMenuVisibility() {
        menu.visibilitySwitch();
    }

    /**
     * Abilita l'input processor per lo stage.
     */
    public void setInputProcessorOn() {
        Gdx.input.setInputProcessor(stage);
    }
}
