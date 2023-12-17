package View.Hud;

import Model.Player;
import View.Boot;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Hud extends WidgetGroup {

    private Stage stage;
    private FitViewport stageViewport;

    private PlayerStats playerStats;

    private MapName mapName;

    private Container inventory;
    public Hud(SpriteBatch spriteBatch) {
        stageViewport = new FitViewport(Boot.INSTANCE.getScreenWidth()/2,Boot.INSTANCE.getScreenHeight()/2);
        stage = new Stage(stageViewport, spriteBatch); //create stage with the stageViewport and the SpriteBatch given in Constructor

        playerStats = new PlayerStats();
        mapName = new MapName();


        Label inventoryLabel = new Label("INVENTORY", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        //-------------------test inventario--------------------------
        inventory= new Container(inventoryLabel);
        inventory.center();
        inventory.setFillParent(true);

        inventory.setVisible(false);

        stage.addActor(inventory);
        //--------------------------------------------------------------
        stage.addActor(playerStats.getTableStats());
        stage.addActor(mapName.getSceneName());

    }

    public Stage getStage() {
        return stage;
    }

    public void update(Player player) {
        this.playerStats.update(player);
        this.mapName.update(player);
        //----------------test inventario---------------------
        if(Gdx.input.isKeyJustPressed(Input.Keys.I)) {

            boolean visible = inventory.isVisible();
            if (visible) {
                inventory.setVisible(false);
            } else if (!(visible)) {
                inventory.setVisible(true);
            }
        }
        //----------------------------------------------------
        }


    public void dispose(){
        stage.dispose();
    }
}
