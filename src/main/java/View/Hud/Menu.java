package View.Hud;

import Controller.MenuMediator;
import Model.Player;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * La classe `Menu` rappresenta un componente dell'HUD che gestisce i pulsanti
 * del menu di gioco, come Continua, Opzioni e Torna al menù.
 * @author Francesco Gambone
 */
public class Menu implements HudComponent {
    private Table menuTable;
    private MenuMediator menuMediator;

    /**
     * Costruisce un nuovo oggetto `Menu` con i pulsanti necessari e collegati al mediatore del menu.
     *
     * @param menuMediator Il mediatore per la gestione del menu.
     */
    public Menu(MenuMediator menuMediator) {
        this.menuMediator = menuMediator;

        // Creazione dei pulsanti
        TextButton continueButton = createTextButton("Continua");
        TextButton optionsButton = createTextButton("Opzioni");
        TextButton mainMenuButton = createTextButton("Torna al menù");

        menuTable = new Table();

        Pixmap bgPixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        bgPixmap.setColor(0, 0, 0, 0.5f);
        bgPixmap.fill();
        TextureRegionDrawable textureBackground = new TextureRegionDrawable(new TextureRegion(new Texture(bgPixmap)));

        menuTable.setBackground(textureBackground);

        // Aggiunta di listener ai pulsanti
        continueButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                menuMediator.changeGameStatus();
            }
        });

        optionsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                menuMediator.changeToOptionsScreen();
            }
        });

        mainMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                menuMediator.changeToMenuScreen();
            }
        });

        // Aggiunta dei pulsanti alla tabella
        menuTable.add(continueButton).center();
        menuTable.row();
        menuTable.add(optionsButton).center().padTop(20f);
        menuTable.row();
        menuTable.add(mainMenuButton).center().padTop(20f);

        menuTable.setVisible(false);

        bgPixmap.dispose();
    }

    /**
     * Alterna la visibilità del menu di gioco.
     */
    public void visibilitySwitch() {
        boolean visible = this.menuTable.isVisible();
        this.menuTable.setVisible(!visible);
    }

    /**
     * Restituisce la Table contenente i pulsanti del menu.
     *
     * @return La Table dei pulsanti del menu.
     */
    public Table getTable() {
        return menuTable;
    }

    // Metodo privato per la creazione di un pulsante di testo
    private TextButton createTextButton(String text) {
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();

        // Stile del testo normale
        buttonStyle.font = new BitmapFont();
        buttonStyle.font.getData().setScale(1.5f);
        buttonStyle.font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        // Colore del testo normale
        buttonStyle.fontColor = Color.WHITE;

        // Colore del testo quando il pulsante è focused
        buttonStyle.overFontColor = Color.YELLOW;

        // Colore del testo quando il pulsante è attivo (premuto)
        buttonStyle.downFontColor = Color.RED;

        // Crea un TextButton direttamente impostando il colore del testo
        TextButton button = new TextButton(text, buttonStyle);

        // Aggiungi un ChangeListener per gestire il cambio di focus
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // Cambia il colore del testo in base allo stato del pulsante
                if (button.isPressed()) {
                    button.getLabel().setColor(buttonStyle.downFontColor);
                    button.getLabel().setColor(buttonStyle.fontColor);
                } else if (button.isOver()) {
                    button.getLabel().setColor(buttonStyle.overFontColor);
                } else {
                    button.getLabel().setColor(buttonStyle.fontColor);
                }
            }
        });
        return button;
    }

    @Override
    public void update(Player player) {
        // Metodo di update vuoto, poiché la classe gestisce solo l'aspetto grafico
    }
}
