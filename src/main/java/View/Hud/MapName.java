package View.Hud;

import Model.Player;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class MapName implements HudComponent{
    private String worldValue;
    private Label worldLabel = new Label("Tutorial", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
    private Container sceneName;

    public MapName(){
        sceneName = new Container(worldLabel);//forse è meglio cambiare sceneName con levelName
        sceneName.top();
        sceneName.setFillParent(true);
    }

    public Container getSceneName() {
        return sceneName;
    }

    @Override
    public void update(Player player) {

    }
}
