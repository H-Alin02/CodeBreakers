package View.Hud;

import Model.Player;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class Menu implements HudComponent{
    private Table menuTable;
    public Menu(){
        //definizione stile pulsante
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = new BitmapFont();
        textButtonStyle.fontColor = Color.WHITE;
        //creazione pulsanti
        TextButton continueButton = new TextButton("Continue", textButtonStyle);

        menuTable = new Table();
        //menuTable.setDebug(true);

        //aggiunta listener ai pulsanti
        continueButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("sheeeeeeeeeeeeeeeeeesh");
            }
        });

        //aggiunta pulsanti a table
        menuTable.add(continueButton).center().fill();

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

    public Table getTable() {
        return menuTable;
    }

    @Override
    public void update(Player player) {

    }
}
