package View.Hud;

import Model.Object.ObjectManager;
import Model.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class PlayerInventory implements HudComponent{

    private ObjectManager objectManager ;
    private final float SCALE = 0.2f;

    private Table table;
    private Label labelCoin;
    private Label labelKey;
    private Label labelAmmunition;

    //objects counters
    private int coinValue;
    private int keyValue;
    private int ammunitionValue;
    public PlayerInventory(ObjectManager objectManager){
        //item = new Item();
        //stageViewport = new FitViewport(Boot.INSTANCE.getScreenWidth()/2,Boot.INSTANCE.getScreenHeight()/2);
        //stage = new Stage(stageViewport,batch);

        this.objectManager = objectManager;
        table = new Table();
        table.right().top();
        //table.setFillParent(true);

        //table.setDebug(true);

        coinValue = objectManager.getItem().getCoin();
        keyValue = objectManager.getItem().getKey();
        ammunitionValue = objectManager.getItem().getAmmunition();

        Texture image1 = new Texture(Gdx.files.internal("inventory/key/key_A_gold.png"));
        Image icon1 = new Image(image1);
        icon1.setSize(image1.getWidth()*SCALE, image1.getHeight()*SCALE);

        Texture image2 = new Texture(Gdx.files.internal("inventory/coin/coin.png"));
        Image icon2 = new Image(image2);
        icon2.setSize(image2.getWidth()*SCALE, image2.getHeight()*SCALE);

        Texture image6 = new Texture(Gdx.files.internal("inventory/ammunition/ammunition.png"));
        Image icon6 = new Image(image6);
        icon6.setSize(image6.getWidth()*SCALE, image6.getHeight()*SCALE);

        labelCoin = new Label(String.format("%01d",coinValue),new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        labelKey = new Label(String.format("%01d",keyValue),new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        labelAmmunition = new Label(String.format("%03d",ammunitionValue), new Label.LabelStyle(new BitmapFont(),Color.WHITE));


        table.add(icon1).padRight(5);
        table.add(labelKey).padRight(5);
        table.add(icon2).padRight(5);
        table.add(labelCoin).padRight(5);
        table.add(icon6).padRight(5);
        table.add(labelAmmunition).padRight(5);

        table.setVisible(true);

    }

    public Table getTable() {
        return table;
    }

    public void visibilitySwitch(){
        boolean visible = this.table.isVisible();
        if (visible) {
            this.table.setVisible(false);
        } else if (!(visible)) {
            this.table.setVisible(true);
        }
    }

    @Override
    public void update(Player player) {

        coinValue = objectManager.getItem().getCoin();
        labelCoin.setText(String.format("%01d", coinValue));

        keyValue = objectManager.getItem().getKey();
        labelKey.setText(String.format("%01d", keyValue));

        ammunitionValue = player.getBulletCount();
        labelAmmunition.setText(String.format("%03d", ammunitionValue));
    }
}
