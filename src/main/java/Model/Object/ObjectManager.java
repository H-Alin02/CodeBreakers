package Model.Object;

import Model.Player;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;


public class ObjectManager {
    private Array<GameObject> objects ;
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
        //mappa tutorial

        objects.add(objectCreator.createObject("coin",3600,1800));
        objects.add(objectCreator.createObject("coin",3500,1800));
        objects.add(objectCreator.createObject("coin",3400,1800));


        objects.add(objectCreator.createObject("meat",3100,2800));
        objects.add(objectCreator.createObject("meat",1600,1000));
        objects.add(objectCreator.createObject("ammunition",2944,2816));
        objects.add(objectCreator.createObject("medikit",850,1300));

        //Mappa di gioco
        objects.add(objectCreator.createObject("ammunition",6336,2048));
        objects.add(objectCreator.createObject("ammunition",6080,2048));
        objects.add(objectCreator.createObject("medikit",8320,3520));
        objects.add(objectCreator.createObject("key",8192,3968));

    }

    public void draw(SpriteBatch batch){
        for(GameObject obj : objects){
            obj.draw(batch);
        }

    }

    public void checkCollision(){
        for(GameObject obj : objects) {
            if (obj.collide(player)) {

                boolean removeItem = true;

                if (obj instanceof  Medikit){
                    if (player.getPlayerLife()>= 100){
                        player.setPlayerLife(0);
                        removeItem = false;
                    }else {
                        player.setPlayerLife(medicalLife);
                    }

                }

                if (obj instanceof  Meat) {
                    if (player.getPlayerLife() >= 100){
                        player.setPlayerLife(0);
                        removeItem = false;
                    }else{
                        player.setPlayerLife(energy);
                    }
                }

                if (obj instanceof Ammunition) {
                    player.setBulletCount(player.getBulletCount()+50);
                }

                if(removeItem) {
                    obj.getPickSound().play(0.2f);
                    objects.removeIndex(objects.indexOf(obj, false));
                    obj.setRemove(true);
                    item.add(obj);
                }
            }
        }
    }

    public void update(float delta) {
        for (int i = 0; i < objects.size; i++){
            GameObject obj = objects.get(i);
            obj.update(delta);
            checkCollision();
        }

        GameObject.pickSound.update(delta);
        Ammunition.updateSound(delta);
        Coin.updateSound(delta);
        Meat.updateSound(delta);
    }

    public Item getItem(){
        return this.item;
    }

    public void addObject(GameObject object){
        this.objects.add(object);
    }
}
