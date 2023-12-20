package View.Hud;

import Model.Object.ObjectManager;
import Model.Player;
import View.Boot;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Hud extends WidgetGroup {

    private Stage stage;
    private FitViewport stageViewport;

    private PlayerStats playerStats;

    private MapName mapName;

    private PlayerInventory inventory;

    public Hud(SpriteBatch spriteBatch, ObjectManager objectManager) {
        stageViewport = new FitViewport(Boot.INSTANCE.getScreenWidth()/2,Boot.INSTANCE.getScreenHeight()/2);
        stage = new Stage(stageViewport, spriteBatch); //create stage with the stageViewport and the SpriteBatch given in Constructor

        playerStats = new PlayerStats();
        mapName = new MapName();
        inventory = new PlayerInventory(objectManager);


        /*/-------------------test inventario--------------------------
        Label inventoryLabel = new Label("INVENTORY", new Label.LabelStyle(new BitmapFont(), Color.WHITE));


        inventory= new Container(inventoryLabel);
        inventory.center();
        inventory.setFillParent(true);

        inventory.setVisible(false);

        stage.addActor(inventory);
        //--------------------------------------------------------------/*/
        stage.addActor(playerStats.getTableStats());
        stage.addActor(mapName.getSceneName());
        stage.addActor(inventory.getTable());

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


    public void dispose(){
        stage.dispose();
    }
}
