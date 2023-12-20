package View.Hud;

import Model.Player;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;


public class PlayerStats implements HudComponent{

    private Label lifeLabel = new Label("Life", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
    private Label staminaLabel = new Label("Stamina", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
    private Skin skin = new Skin();
    private ProgressBar lifeBar;
    private ProgressBar staminaBar;
    private Table tableStats = new Table();
    private float playerLife = 1f;
    private float playerStamina = 1f;
    public PlayerStats(){
        //skin e pixmap sono relativi a ProgressBar
        Pixmap pixmap = new Pixmap(10, 10, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        skin.add("white", new Texture(pixmap));

        //life bar
        TextureRegionDrawable textureLifeBar = new TextureRegionDrawable(new TextureRegion(new Texture("hudAssets/barGreen_horizontalMid.png")));
        ProgressBar.ProgressBarStyle lifeBarStyle = new ProgressBar.ProgressBarStyle(skin.newDrawable("white", Color.DARK_GRAY), textureLifeBar);
        lifeBar = new ProgressBar(0, 100, 0.5f, false, lifeBarStyle);
        lifeBar.setValue(playerLife);
        lifeBarStyle.knobBefore = lifeBarStyle.knob;
        lifeBar.setSize(50, 10);

        //stamina bar
        TextureRegionDrawable textureStaminaBar = new TextureRegionDrawable(new TextureRegion(new Texture("hudAssets/barBlue_horizontalMid.png")));
        ProgressBar.ProgressBarStyle staminaBarStyle = new ProgressBar.ProgressBarStyle(skin.newDrawable("white", Color.DARK_GRAY), textureStaminaBar);
        staminaBar = new ProgressBar(0, 100, 0.5f, false, staminaBarStyle);
        staminaBar.setValue(playerStamina);
        staminaBarStyle.knobBefore = staminaBarStyle.knob;
        staminaBarStyle.disabledKnob = staminaBarStyle.knob;

        lifeBar.setSize(50, 10);

        //tables

        tableStats.left();
        tableStats.top();
        tableStats.setFillParent(true);

        tableStats.add(lifeLabel);
        tableStats.add(lifeBar).spaceRight(10);

        tableStats.row();
        tableStats.add(staminaLabel);
        tableStats.add(staminaBar).spaceRight(10);

    }

    public Label getStaminaLabel() {
        return staminaLabel;
    }

    public void setStaminaLabel(Label staminaLabel) {
        this.staminaLabel = staminaLabel;
    }

    public Label getLifeLabel() {
        return lifeLabel;
    }

    public void setLifeLabel(Label lifeLabel) {
        this.lifeLabel = lifeLabel;
    }


    public ProgressBar getLifeBar() {
        return lifeBar;
    }

    public void setLifeBar(ProgressBar lifeBar) {
        this.lifeBar = lifeBar;
    }

    public ProgressBar getStaminaBar() {
        return staminaBar;
    }

    public void setStaminaBar(ProgressBar staminaBar) {
        this.staminaBar = staminaBar;
    }

    public Table getTableStats() {
        return tableStats;
    }

    public void setTableStats(Table tableStats) {
        this.tableStats = tableStats;
    }

    public float getPlayerLife() {
        return playerLife;
    }

    public void setPlayerLife(float playerLife) {
        this.playerLife = playerLife;
    }

    public float getPlayerStamina() {
        return playerStamina;
    }

    public void setPlayerStamina(float playerStamina) {
        this.playerStamina = playerStamina;
    }

    @Override
    public void update(Player player) {
        lifeBar.setValue(player.getPlayerLife());
        staminaBar.setValue(player.getPlayerStamina());
    }
}
