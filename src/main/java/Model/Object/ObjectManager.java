package Model.Object;

import Model.Player;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

import java.util.Objects;


public class ObjectManager {
    private Array<ObjectGame> objects ;
    private ObjectGameCreator objectCreator;

    private Player player;
    private Item item;


    public ObjectManager(){
        this.objects = new Array<>();
        item = new Item();
        player = Player.getInstance();
        this.objectCreator = new ObjectCreator();

    }

    public void initializeObject(){
        objects.add(objectCreator.createObject("chest",1650,1300));
        objects.add(objectCreator.createObject("coin",1600,300));
        objects.add(objectCreator.createObject("diamond",850,850));
        objects.add(objectCreator.createObject("diamond",200,850));
        objects.add(objectCreator.createObject("key",300,1000));
        objects.add(objectCreator.createObject("meat",1600,1000));
        objects.add(objectCreator.createObject("money",100,300));
        objects.add(objectCreator.createObject("ammunition",850,200));
        objects.add(objectCreator.createObject("medikit",850,1300));
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
                }else if (Objects.equals(obj.getName(), "ammunition") && obj.isRemove()) {
                    item.addAmmunition(obj);
                    objects.removeIndex(objects.indexOf(obj,false));
                }else if (Objects.equals(obj.getName(), "medikit") && obj.isRemove()) {
                    item.addMedikit(obj);
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

    public Item getItem(){
        return this.item;
    }
}
