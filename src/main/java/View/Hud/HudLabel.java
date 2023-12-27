package View.Hud;

import Model.Player;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class HudLabel implements HudComponent{
    private Label label;



    public HudLabel(String stringLabel) {
        this.label = new Label(stringLabel, new Label.LabelStyle(new BitmapFont(), Color.WHITE));
    }

    @Override
    public void update(Player player) {

    }
}
