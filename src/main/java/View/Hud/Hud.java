package View.Hud;

import Controller.MenuMediator;
import Model.NPC.NPCObserver;
import Model.Object.ObjectManager;
import Model.Player;
import View.Boot;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Hud extends WidgetGroup implements NPCObserver {

    private Stage stage;
    private FitViewport stageViewport;

    private PlayerStats playerStats;

    private MapName mapName;

    private PlayerInventory inventory;
    private DialogueBox dialogueBox;
    private boolean npcReadyToTalk = false;

    private boolean pause = false;

    Label pauseLabel = new Label("PAUSE", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
    Container pauseContainer = new Container(pauseLabel);

    Menu menu;

    public Hud(SpriteBatch spriteBatch, ObjectManager objectManager, MenuMediator menuMediator) {
        stageViewport = new FitViewport(Boot.INSTANCE.getScreenWidth()/2,Boot.INSTANCE.getScreenHeight()/2);
        stage = new Stage(stageViewport, spriteBatch); //create stage with the stageViewport and the SpriteBatch given in Constructor

        Table filler = new Table();

        playerStats = new PlayerStats();
        mapName = new MapName();
        inventory = new PlayerInventory(objectManager);
        dialogueBox = new DialogueBox("");

        menu = new Menu(menuMediator);


        pauseContainer.setVisible(false);



        Table rootTable = new Table();
        rootTable.setFillParent(true);
        rootTable.setRound(false);

        //rootTable.setDebug(true);

        stage.addActor(rootTable);
        //popolazione stage e posizionamento degli elementi
        rootTable.top().left();

        rootTable.add(playerStats.getTableStats()).uniform();
        rootTable.add(filler).fill().uniform();

        //rootTable.add(mapName.getSceneName()).expandX();

        rootTable.add(inventory.getTable()).uniform();

        rootTable.row().height(300);

        rootTable.add(filler).fill();
        //rootTable.add(pauseContainer).expandX().expandY();
        rootTable.add(menu.getTable());
        rootTable.add(filler).fill();

        rootTable.row();

        rootTable.add(dialogueBox.getTable()).colspan(3).fill().expand();

        Gdx.input.setInputProcessor(stage);



    }

    public Stage getStage() {
        return stage;
    }

    public void update(Player player, float delta) {

        this.playerStats.update(player);
        this.mapName.update(player);
        this.inventory.update(player);
        this.menu.update(player);

        System.out.println(npcReadyToTalk);
        if(npcReadyToTalk){
            dialogueBox.show(dialogueBox.getText());
        }else dialogueBox.hide();
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
      
        if(npcReadyToTalk){
            dialogueBox.show(dialogueBox.getText());
        }

        }

    @Override
    public void onNPCTalk(String message) {
        this.npcReadyToTalk = true;
        System.out.println(message + "dialogue box " + dialogueBox);
        System.out.println("OnNPCTalk Called");
        dialogueBox.setMessage(message);
    }

    @Override
    public void onNPCFinishedTalk() {
        System.out.println("NPC FInisched to talk");
        this.npcReadyToTalk = false;
    }

    public void dispose(){
        stage.dispose();
    }

    public void setMenuVisibility() {
        menu.visibilitySwitch();

    }
}
