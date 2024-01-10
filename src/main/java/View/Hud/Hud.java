package View.Hud;

import Controller.MenuMediator;
import Model.NPC.NPCObserver;
import Model.Object.ObjectManager;
import Model.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class Hud extends WidgetGroup implements NPCObserver {

    private Stage stage;
    private ScreenViewport stageViewport;

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
        stageViewport = new ScreenViewport();
        stageViewport.setUnitsPerPixel(0.6f);


        stage = new Stage(stageViewport, spriteBatch); //create stage with the stageViewport and the SpriteBatch given in Constructor

        Pixmap bgPixmap = new Pixmap(1,1, Pixmap.Format.RGBA8888);
        bgPixmap.setColor(0,0,0,0.5f);
        bgPixmap.fill();
        TextureRegionDrawable textureBackground = new TextureRegionDrawable(new TextureRegion(new Texture(bgPixmap)));





        Table filler = new Table();
        Table topFiller = new Table();
        topFiller.setBackground(textureBackground);
        bgPixmap.dispose();

        playerStats = new PlayerStats();
        mapName = new MapName();
        inventory = new PlayerInventory(objectManager);
        dialogueBox = new DialogueBox("");

        menu = new Menu(menuMediator);

        pauseContainer.setVisible(false);

        Table rootTable = new Table();
        rootTable.setFillParent(true);


        //rootTable.setDebug(true);

        stage.addActor(rootTable);
        //popolazione stage e posizionamento degli elementi

        //prima riga
        rootTable.add(playerStats.getTableStats()).left().uniform().padLeft(10).fill().padTop(15);
        rootTable.add(filler).fill().uniform().padTop(15);
        rootTable.add(inventory.getTable()).center().uniform().padRight(10).fill().padTop(15);

        rootTable.row();
        //seconda riga
        rootTable.add(filler).fill();
        rootTable.add(menu.getTable()).fill().expand().center().padLeft(30).padRight(30).padTop(50).padBottom(50);
        rootTable.add(filler).fill();

        rootTable.row();
        //terza riga
        rootTable.add(dialogueBox.getTable()).colspan(3).height(100).padBottom(25);

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

        //System.out.println(npcReadyToTalk);
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

        pauseContainer.setVisible(pause = !visible);
      
        if(npcReadyToTalk){
            dialogueBox.show(dialogueBox.getText());
        }

    }

    @Override
    public void onNPCTalk(String message) {
        this.npcReadyToTalk = true;
        dialogueBox.setMessage(message);
    }

    @Override
    public void onNPCFinishedTalk() {
        this.npcReadyToTalk = false;
    }

    public void dispose(){
        stage.dispose();
    }

    public void setMenuVisibility() {
        menu.visibilitySwitch();
        Gdx.input.setInputProcessor(stage);

    }
}
