package View.Hud;

import Model.Object.ObjectManager;
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
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Hud extends WidgetGroup {

    private Stage stage;
    private FitViewport stageViewport;

    private PlayerStats playerStats;

    private MapName mapName;

    private PlayerInventory inventory;

    private boolean pause = false;

    Label pauseLabel = new Label("PAUSE", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
    Container pauseContainer = new Container(pauseLabel);

    public Hud(SpriteBatch spriteBatch, ObjectManager objectManager) {
        stageViewport = new FitViewport(Boot.INSTANCE.getScreenWidth()/2,Boot.INSTANCE.getScreenHeight()/2);
        stage = new Stage(stageViewport, spriteBatch); //create stage with the stageViewport and the SpriteBatch given in Constructor

        playerStats = new PlayerStats();
        mapName = new MapName();
        inventory = new PlayerInventory(objectManager);



        pauseContainer.setVisible(false);



        Table rootTable = new Table();
        rootTable.setFillParent(true);
        stage.addActor(rootTable);

        /*/-------------------test inventario--------------------------
        Label inventoryLabel = new Label("INVENTORY", new Label.LabelStyle(new BitmapFont(), Color.WHITE));


        inventory= new Container(inventoryLabel);
        inventory.center();
        inventory.setFillParent(true);

        inventory.setVisible(false);

        stage.addActor(inventory);
        //--------------------------------------------------------------/*/
        rootTable.top().left();
        rootTable.add(playerStats.getTableStats()).expandX();

        rootTable.add(mapName.getSceneName()).expandX();

        rootTable.add(inventory.getTable()).expandX();
        rootTable.row();
        rootTable.add(pauseContainer).expandX().expandY();

    }

    public Stage getStage() {
        return stage;
    }

    public void update(Player player, float delta) {
        this.playerStats.update(player);
        this.mapName.update(player);
        this.inventory.update();
        if(Gdx.input.isKeyJustPressed(Input.Keys.I)){
            this.inventory.visibilitySwitch();
        }

        /*/----------------test inventario---------------------
        if(Gdx.input.isKeyJustPressed(Input.Keys.I)) {

            boolean visible = inventory.isVisible();
            if (visible) {
                inventory.setVisible(false);
            } else if (!(visible)) {
                inventory.setVisible(true);
            }
        }
        //----------------------------------------------------/*/
    }

    public boolean isPause() {
        return pause;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    public void setPauseVisibility(){
        boolean visible = pauseContainer.isVisible();
        if (visible) {
            pauseContainer.setVisible(false);
            pause = false;
        } else if (!(visible)) {
            pauseContainer.setVisible(true);
            pause = true;
        }
    }

    public void dispose(){
        stage.dispose();
    }
}
