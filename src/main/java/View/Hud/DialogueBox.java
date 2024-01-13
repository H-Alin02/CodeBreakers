package View.Hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * La classe `DialogueBox` rappresenta un elemento dell'interfaccia grafica utente
 * per visualizzare dialoghi in un gioco. Utilizza il framework Scene2D di LibGDX per
 * i componenti dell'interfaccia utente.
 * @author Alin Marian Habasescu
 * @author Francesco Gambone
 */
public class DialogueBox {
    private Table table;
    private BitmapFont font;
    private Label dialogueLabel;
    private boolean isVisible;
    private String text;

    /**
     * Costruisce una nuova istanza di `DialogueBox` con il messaggio iniziale specificato.
     *
     * @param message Il messaggio iniziale da visualizzare nella casella di dialogo.
     */
    public DialogueBox(String message) {
        font = new BitmapFont();
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        dialogueLabel = new Label("", new Label.LabelStyle(font, Color.BLACK));
        this.text = message;
        table = new Table();

        // Aggiungi lo sfondo o l'immagine per la casella di dialogo
        TextureRegionDrawable backgroundTexture = new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("NPC/DialogueBox.png"))));
        table.setBackground(backgroundTexture);

        table.add(dialogueLabel).left();

        table.setVisible(false);
        isVisible = false;
    }

    /**
     * Restituisce l'elemento di tipo Table sottostante di `DialogueBox`.
     *
     * @return La Table che rappresenta la casella di dialogo.
     */
    public Table getTable() {
        return table;
    }

    /**
     * Visualizza il testo specificato nella casella di dialogo.
     *
     * @param text Il testo da visualizzare.
     */
    public void show(String text) {
        dialogueLabel.setText(text);
        table.setVisible(true);
        isVisible = true;
    }

    /**
     * Restituisce il testo attualmente visualizzato nella casella di dialogo.
     *
     * @return Il testo attualmente visualizzato nella casella di dialogo.
     */
    public String getText() {
        return text;
    }

    /**
     * Nasconde la casella di dialogo.
     */
    public void hide() {
        table.setVisible(false);
        isVisible = false;
    }

    /**
     * Verifica se la casella di dialogo è attualmente visibile.
     *
     * @return `true` se la casella di dialogo è visibile, altrimenti `false`.
     */
    public boolean isVisible() {
        return isVisible;
    }

    /**
     * Imposta il messaggio da visualizzare nella casella di dialogo.
     *
     * @param message Il nuovo messaggio da visualizzare.
     */
    public void setMessage(String message) {
        this.text = message;
        this.dialogueLabel.setText(message);
    }
}
