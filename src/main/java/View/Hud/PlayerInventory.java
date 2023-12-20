package View.Hud;

import Model.Object.ObjectManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class PlayerInventory {
    /*
        Francesco: ho provato a rendere l'inventario funzionale, con il numero di monete raccolte rappresentano nell'inventario, ma
        purtropp non ci sono riuscito in quanto il numero di monete e all' interno dell'istanza item nella classe ObjectManager.
        Ciò significa che dovrei in qualche modo passare item da Objectmanager a Hud per poi finire su PlayerInventory. Urge quindi
        l'implementazione del pattern Observer.



     */


    private ObjectManager objectManager ;
    private final float SCALE = 0.2f;

    private Table table;
    private Label labelCoin;
    private Label labelKey;
    private Label labelMeat;
    private Label labelMoney;
    private Label labelDiamond;

    public PlayerInventory(ObjectManager objectManager){


        this.objectManager = objectManager;
        table = new Table();
        table.right().top();
        table.setFillParent(true);

        //key icon
        Texture image1 = new Texture(Gdx.files.internal("inventory/key/key_A_gold.png"));
        Image icon1 = new Image(image1);
        icon1.setSize(image1.getWidth()*SCALE, image1.getHeight()*SCALE);
        //coin icon
        Texture image2 = new Texture(Gdx.files.internal("inventory/coin/coin.png"));
        Image icon2 = new Image(image2);
        icon2.setSize(image2.getWidth()*SCALE, image2.getHeight()*SCALE);
        //money icon
        Texture image3 = new Texture(Gdx.files.internal("inventory/money/money.png"));
        Image icon3 = new Image(image3);
        icon1.setSize(image3.getWidth()*SCALE, image3.getHeight()*SCALE);
        //meat icon
        Texture image4 = new Texture(Gdx.files.internal("inventory/meat/meat.png"));
        Image icon4 = new Image(image4);
        icon2.setSize(image4.getWidth()*SCALE, image4.getHeight()*SCALE);
        //diamond icon
        Texture image5 = new Texture(Gdx.files.internal("inventory/diamond/diamond.png"));
        Image icon5 = new Image(image5);
        icon2.setSize(image5.getWidth()*SCALE, image5.getHeight()*SCALE);

        labelCoin = new Label(String.format("%01d",objectManager.getItem().getCoin()),new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        labelKey = new Label(String.format("%01d",objectManager.getItem().getKey()),new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        labelMeat = new Label(String.format("%01d",objectManager.getItem().getMeat()),new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        labelMoney = new Label(String.format("%01d",objectManager.getItem().getMoney()),new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        labelDiamond = new Label(String.format("%01d",objectManager.getItem().getDiamond()),new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.center();
        table.setFillParent(true);

        //keys
        table.add(icon1).padRight(5);
        table.add(labelKey).padBottom(5).row();
        //diamonds
        table.add(icon5).padRight(5);
        table.add(labelDiamond).padBottom(5).row();
        //coins
        table.add(icon2).padRight(5);
        table.add(labelCoin).padBottom(5).row();
        //moneys
        table.add(icon3).padRight(5);
        table.add(labelMoney).padBottom(5).row();
        //meat
        table.add(icon4).padRight(5);
        table.add(labelMeat).row();

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
        //update labelCoin

        labelCoin.setText(String.format("%01d",objectManager.getItem().getCoin()));

        //update labelDiamond

        labelDiamond.setText(String.format("%01d",objectManager.getItem().getDiamond()));

        //update labelKey

        labelKey.setText(String.format("%01d", objectManager.getItem().getKey()));

        //update labelMoney

        labelMoney.setText(String.format("%01d", objectManager.getItem().getMoney()));

        //update labelMeat

        labelMeat.setText(String.format("%01d", objectManager.getItem().getMeat()));
    }

}
