package View.Hud;

import Model.Player;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar.ProgressBarStyle;

public class HudProgressBar implements HudComponent {
    Pixmap pixmap;
    Skin skin;

    ProgressBar progressBar;
    private float value;


    public HudProgressBar(String source) {
        this.pixmap = new Pixmap(10, 10, Pixmap.Format.RGBA8888);
        this.pixmap.setColor(Color.WHITE);
        this.pixmap.fill();
        this.skin= new Skin();
        this.skin.add("white", new Texture(pixmap));

        this.value = 1f;

        TextureRegionDrawable textureProgressBar = new TextureRegionDrawable(new TextureRegion(new Texture(source)));
        ProgressBarStyle progressBarStyle = new ProgressBarStyle(skin.newDrawable("white", Color.DARK_GRAY), textureProgressBar);
        progressBarStyle.knobBefore = progressBarStyle.knob;

        progressBar = new ProgressBar(0, 10, 0.1f, false, progressBarStyle);

        progressBar.setValue(value);


        progressBar.setSize(50, 10);




    }

    @Override
    public void update(Player player) {
        this.progressBar.setValue(player.getPlayerLife());
    }
}
