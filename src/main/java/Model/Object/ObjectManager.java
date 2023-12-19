package Model.Object;

import Model.Player;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

import java.util.Objects;


public class ObjectManager {
    private Array<ObjectGame> objects ;
    private ChestCreator chest;
    private CoinCreator coin;
    private DiamondCreator diamond;
    private KeyCreator key;
    private MeatCreator meat;
    private MoneyCreator money;

    private Player player;
    private Item item;


    public ObjectManager(){
        this.objects = new Array<>();
        item = new Item();
        player = Player.getInstance();
        this.chest = new ChestCreator();
        this.coin = new CoinCreator();
        this.diamond = new DiamondCreator();
        this.key = new KeyCreator();
        this.meat = new MeatCreator();
        this.money = new MoneyCreator();

    }

    public void initializeObject(){
        objects.add(chest.createObject(1650,1300));
        objects.add(coin.createObject(1600,300));
        objects.add(diamond.createObject(850,850));
        objects.add(diamond.createObject(200,850));
        objects.add(key.createObject(300,1000));
        objects.add(key.createObject(850,200));
        objects.add(meat.createObject(1600,1000));
        objects.add(money.createObject(100,300));
    }

    public void draw(SpriteBatch batch){
        for(ObjectGame obj : objects){
            obj.draw(batch);
        }

    }

    public void checkCollision(){
        for(ObjectGame obj : objects) {
            if (obj.collide(player)) {
                obj.setRemove(true);

                if (Objects.equals(obj.getName(), "coin") && obj.isRemove()) {
                    item.addCoin(obj);
                    objects.removeIndex(objects.indexOf(obj,false));
                } else if (Objects.equals(obj.getName(), "key") && obj.isRemove()) {
                    item.addKey(obj);
                    objects.removeIndex(objects.indexOf(obj,false));
                } else if (Objects.equals(obj.getName(), "diamond") && obj.isRemove()) {
                    item.addDiamond(obj);
                    objects.removeIndex(objects.indexOf(obj,false));
                } else if (Objects.equals(obj.getName(), "meat") && obj.isRemove()) {
                    item.addMeat(obj);
                    objects.removeIndex(objects.indexOf(obj,false));
                } else if (Objects.equals(obj.getName(), "money") && obj.isRemove()) {
                    item.addMoney(obj);
                    objects.removeIndex(objects.indexOf(obj,false));
                }

            }
        }
    }

    public void update(float delta) {
        for (int i = 0; i < objects.size; i++){
            ObjectGame obj = objects.get(i);
            obj.update(delta);
            checkCollision();
        }

    }
}
