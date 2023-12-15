package View;

import Model.Player;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Hud {

    private Stage stage;
    private FitViewport stageViewport;
    private ProgressBar lifeBar;
    private int playerLife= 1;

    public Hud(SpriteBatch spriteBatch) {
        stageViewport = new FitViewport(Boot.INSTANCE.getScreenWidth()/2,Boot.INSTANCE.getScreenHeight()/2);
        stage = new Stage(stageViewport, spriteBatch); //create stage with the stageViewport and the SpriteBatch given in Constructor

        //labels
        Label worldLabel = new Label("Tutorial", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        Label lifeLabel = new Label("Life", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        Label staminaLabel = new Label("Stamina", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        Skin skin = new Skin();
        Pixmap pixmap = new Pixmap(10, 10, Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("white", new Texture(pixmap));

        //life bar
        TextureRegionDrawable textureLifeBar = new TextureRegionDrawable(new TextureRegion(new Texture("hudAssets/barGreen_horizontalMid.png")));
        ProgressBar.ProgressBarStyle lifeBarStyle = new ProgressBar.ProgressBarStyle(skin.newDrawable("white", Color.DARK_GRAY), textureLifeBar);
        lifeBar = new ProgressBar(0, 100, 0.5f, false, lifeBarStyle);
        lifeBar.setValue(playerLife);//<-- cambiare 10 con la vita del player
        lifeBarStyle.knobBefore = lifeBarStyle.knob;
        lifeBar.setSize(50, 10);

        //stamina bar
        TextureRegionDrawable textureStaminaBar = new TextureRegionDrawable(new TextureRegion(new Texture("hudAssets/barBlue_horizontalMid.png")));
        ProgressBar.ProgressBarStyle staminaBarStyle = new ProgressBar.ProgressBarStyle(skin.newDrawable("white", Color.DARK_GRAY), textureStaminaBar);
        ProgressBar staminaBar = new ProgressBar(0, 10, 0.1f, false, staminaBarStyle);
        staminaBar.setValue(0);//<-- cambiare 10 con la stamina del player
        staminaBarStyle.knobBefore = staminaBarStyle.knob;
        //staminaBarStyle.disabledKnob = staminaBarStyle.knob;

        lifeBar.setSize(50, 10);

        //tables
        Table tableStats = new Table();
        tableStats.left().top();
        tableStats.setFillParent(true);

        //containers
        Container sceneName = new Container(worldLabel);
        sceneName.top();
        sceneName.setFillParent(true);

        //populate tables
        tableStats.add(lifeLabel);
        tableStats.add(lifeBar).spaceRight(10);

        tableStats.row();
        tableStats.add(staminaLabel);
        tableStats.add(staminaBar).spaceRight(10);

        //add elements of the hud to the stage
        stage.addActor(tableStats);
        stage.addActor(sceneName);
    }

    public Stage getStage() {
        return stage;
    }

    public int getPlayerLife() {
        return playerLife;
    }

    public void setPlayerLife(int playerLife) {
        this.playerLife = playerLife;
    }

    public void update(Player player) {
        lifeBar.setValue(player.getPlayerLife());
    }

    public void dispose(){
        stage.dispose();
    }
}
