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
    private int medicalLife;
    private int energy;

    public ObjectManager(){
        this.objects = new Array<>();
        item = new Item();
        player = Player.getInstance();
        this.objectCreator = new ObjectCreator();
        medicalLife = 100;
        energy = 25;
    }

    public void initializeObject(){
        objects.add(objectCreator.createObject("chest",1650,1200));
        objects.add(objectCreator.createObject("coin",3600,1800));
        objects.add(objectCreator.createObject("coin",3500,1800));
        objects.add(objectCreator.createObject("coin",3400,1800));
        objects.add(objectCreator.createObject("diamond",1850,1850));
        objects.add(objectCreator.createObject("diamond",1200,1850));
        objects.add(objectCreator.createObject("key",3100,2800));
        objects.add(objectCreator.createObject("meat",1600,1000));
        objects.add(objectCreator.createObject("money",1100,1300));
        objects.add(objectCreator.createObject("money",3100,1300));
        objects.add(objectCreator.createObject("money",2100,1400));
        objects.add(objectCreator.createObject("ammunition",850,1000));
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
                if (Objects.equals(obj.getName(),"medikit")){
                    if (player.getPlayerLife()>= 100){
                        player.setPlayerLife(0);
                    }else {
                        obj.setRemove(true);
                        player.setPlayerLife(medicalLife);
                        objects.removeIndex(objects.indexOf(obj,false));
                    }
                }
                else if(Objects.equals(obj.getName(),"meat")){
                        if (player.getPlayerLife()>= 100){
                            player.setPlayerLife(0);
                        }else {
                            obj.setRemove(true);
                            player.setPlayerLife(energy);
                            objects.removeIndex(objects.indexOf(obj,false));
                        }
                }
                else {
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
                    } else if (Objects.equals(obj.getName(), "money") && obj.isRemove()) {
                        item.addMoney(obj);
                        objects.removeIndex(objects.indexOf(obj,false));
                    }else if (Objects.equals(obj.getName(), "ammunition") && obj.isRemove()) {
                        item.addAmmunition(obj);
                        objects.removeIndex(objects.indexOf(obj,false));
                    }
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
