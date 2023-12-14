package View;

import Model.Object.Item;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class PlayerInventory {

    private Stage stage;
    private Item item;
    private FitViewport stageViewport;
    private final float SCALE = 0.2f;


    public PlayerInventory(SpriteBatch batch){
        item = new Item();
        stageViewport = new FitViewport(Boot.INSTANCE.getScreenWidth()/2,Boot.INSTANCE.getScreenHeight()/2);
        stage = new Stage(stageViewport,batch);

        Table table = new Table();
        table.left().top();
        table.setFillParent(true);

        Texture image1 = new Texture(Gdx.files.internal("inventory/key/key_A_gold.png"));
        Image icon1 = new Image(image1);
        icon1.setSize(image1.getWidth()*SCALE, image1.getHeight()*SCALE);

        Texture image2 = new Texture(Gdx.files.internal("inventory/coin/coin.png"));
        Image icon2 = new Image(image2);
        icon2.setSize(image2.getWidth()*SCALE, image2.getHeight()*SCALE);

        Texture image3 = new Texture(Gdx.files.internal("inventory/money/money.png"));
        Image icon3 = new Image(image3);
        icon1.setSize(image3.getWidth()*SCALE, image3.getHeight()*SCALE);

        Texture image4 = new Texture(Gdx.files.internal("inventory/meat/meat.png"));
        Image icon4 = new Image(image4);
        icon2.setSize(image4.getWidth()*SCALE, image4.getHeight()*SCALE);

        Label label1 = new Label(item.getCoin(),new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(icon1).padBottom(5).row();
        table.add(icon2).padRight(5);
        table.add(label1).padBottom(5).row();
        table.add(icon3).padBottom(5).row();
        table.add(icon4).padBottom(5).row();
        stage.addActor(table);
    }
    public Stage getStage() {
        return stage;
    }

    public void dispose(){
        stage.dispose();
    }
    public void update(float delta){
        item.update(delta);
    }

}
