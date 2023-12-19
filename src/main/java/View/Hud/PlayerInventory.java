package View.Hud;

import Model.Object.Item;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class PlayerInventory {
    /*
        Francesco: ho provato a rendere l'inventario funzionale, con il numero di monete raccolte rappresentano nell'inventario, ma
        purtropp non ci sono riuscito in quanto il numero di monete e all' interno dell'istanza item nella classe ObjectManager.
        Ciò significa che dovrei in qualche modo passare item da Objectmanager a Hud per poi finire su PlayerInventory. Urge quindi
        l'implementazione del pattern Observer.



     */

    private Stage stage;
    private Model.Object.Item item;
    private FitViewport stageViewport;
    private final float SCALE = 0.2f;

    private Table table;
    private Label labelCoin;

    //objects counters
    private int coinValue = 0;
    //private int keyValue;
    //private int moneyValue;
    //private int meatValue;
    public PlayerInventory(){
        item = new Item();
        //stageViewport = new FitViewport(Boot.INSTANCE.getScreenWidth()/2,Boot.INSTANCE.getScreenHeight()/2);
        //stage = new Stage(stageViewport,batch);

        table = new Table();
        table.right().top();
        table.setFillParent(true);

        coinValue = item.getCoin();

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

        labelCoin = new Label(String.format("%01d",coinValue),new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.center();
        table.setFillParent(true);
        table.add(icon1).padBottom(5).row();
        table.add(icon2).padRight(5);
        table.add(labelCoin).padBottom(5).row();
        table.add(icon3).padBottom(5).row();
        table.add(icon4).padBottom(5).row();

        table.setVisible(false);

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



    public void update(){
        coinValue = item.getCoin();
        labelCoin.setText(String.format("%01d", coinValue));


    }

}
