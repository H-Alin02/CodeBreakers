package View.Hud;

import Controller.MenuMediator;
import Model.Player;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class Menu implements HudComponent{
    private Table menuTable;
    private MenuMediator menuMediator;
    public Menu(MenuMediator menuMediator){
        this.menuMediator = menuMediator;

        //creazione pulsanti
        TextButton continueButton = createTextButton("Continue");
        TextButton mainMenuButton = createTextButton("Exit Game");

        menuTable = new Table();
        //menuTable.setDebug(true);

        //aggiunta listener ai pulsanti
        continueButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                menuMediator.changeGameStatus();


            }
        });

        mainMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                menuMediator.changeScreen();


            }
        });



        //aggiunta pulsanti a table
        menuTable.add(continueButton).center();
        menuTable.row();
        menuTable.add(mainMenuButton).center().padTop(20f);

        menuTable.setVisible(false);




    }
    public void visibilitySwitch(){
        boolean visible = this.menuTable.isVisible();
        if (visible) {
            this.menuTable.setVisible(false);
        } else if (!(visible)) {
            this.menuTable.setVisible(true);
        }
    }

    public void changeGameStatus(){

    };
    public Table getTable() {
        return menuTable;
    }

    private TextButton createTextButton(String text) {
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();

        // Stile del testo normale
        buttonStyle.font = new BitmapFont();
        buttonStyle.font.getData().setScale(1f);
        // Migliora la risoluzione della scritta dopo lo Scale
        buttonStyle.font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        // Colore del testo normale
        buttonStyle.fontColor = Color.WHITE;

        // Colore del testo quando il pulsante è focused
        buttonStyle.overFontColor = Color.YELLOW; // Puoi cambiare il colore del testo focused secondo le tue preferenze

        // Colore del testo quando il pulsante è attivo (premuto)
        buttonStyle.downFontColor = Color.RED; // Puoi cambiare il colore del testo quando il pulsante è attivo secondo le tue preferenze

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


    }
}
