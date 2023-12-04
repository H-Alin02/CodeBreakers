package View;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
public class Hud {
    private Stage stage;
    private FitViewport stageViewport;

    public Hud(SpriteBatch spriteBatch) {
        stageViewport = new FitViewport(Boot.INSTANCE.getScreenWidth()/2,Boot.INSTANCE.getScreenHeight()/2);
        stage = new Stage(stageViewport, spriteBatch); //create stage with the stageViewport and the SpriteBatch given in Constructor

        Table table = new Table();
        table.top();
        table.setFillParent(true);
        Label worldLabel = new Label("Tutorial", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        /*...
        ... //add the Buttons etc.*/
        table.add(worldLabel);
        stage.addActor(table);
    }

    public Stage getStage() {
        return stage;
    }

    public void dispose(){
        stage.dispose();
    }
}
