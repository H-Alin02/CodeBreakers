package Model.Object;

import Model.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;


public class ObjectManager {
    private Array<GameObject> objects = new Array<>();

    private Player player;
    private Coin coin;

    public ObjectManager(){
        player = Player.getInstance();
        Texture texture = new Texture(Gdx.files.internal("object/frame0000.png"));
        GameObject object1 = new GameObject("money",100,100,new TextureRegion(texture));
        objects.add(object1);

        Texture texture1 = new Texture(Gdx.files.internal("object/key_A_gold.png"));
        GameObject object2 = new GameObject("key",200,200,new TextureRegion(texture1));
        GameObject object3 = new GameObject("key",850,200,new TextureRegion(texture1));
        objects.add(object3);
        objects.add(object2);

        Texture texture2 = new Texture(Gdx.files.internal("object/frame0006.png"));
        GameObject object4 = new GameObject("diamond",850,850,new TextureRegion(texture2));
        GameObject object5 = new GameObject("diamond",200,850,new TextureRegion(texture2));
        objects.add(object4);
        objects.add(object5);

        coin = new Coin(1600,200);


    }

    public void draw(SpriteBatch batch){
        for(GameObject obj : objects){
            obj.draw(batch);
        }
        coin.draw(batch);
    }

    public void checkCollision(){
        for (int i = 0; i < objects.size; i++){
            GameObject obj = objects.get(i);

            if(obj.collide(player)){
                obj.setRemove(true);
            }
        }
    }

    public void update(float delta) {
        for (int i = 0; i < objects.size; i++){
            GameObject obj = objects.get(i);
            if (obj.isRemove()){
                objects.removeIndex(i);
            }
            checkCollision();
        }
        coin.update(delta);

    }
}
