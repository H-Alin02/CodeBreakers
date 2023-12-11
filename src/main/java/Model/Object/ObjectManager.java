package Model.Object;

import Model.Player;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

import java.util.Objects;


public class ObjectManager implements ObjectManagerFactory{
    private Array<ObjectGame> objects = new Array<>();

    private Player player;
    private Item item;


    public ObjectManager(){
        item = new Item();
        player = Player.getInstance();
        Money money1 = new Money(100,300);
        objects.add(money1);


        Key key1 = new Key(300,1000);
        Key key2 = new Key(850,200);
        objects.add(key1);
        objects.add(key2);

        Diamond diamond1 = new Diamond(850,850);
        Diamond diamond2 = new Diamond(200,850);
        objects.add(diamond1);
        objects.add(diamond2);

        Coin coin = new Coin(1600,300);
        objects.add(coin);

        Meat meat1 = new Meat(1600,1000);
        objects.add(meat1);

        Chest chest1 = new Chest(1650,1300);
        objects.add(chest1);

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
