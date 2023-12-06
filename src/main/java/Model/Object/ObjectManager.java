package Model.Object;

import Model.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;


public class ObjectManager {
    private Array<ObjectGame> objects = new Array<>();

    private Player player;


    public ObjectManager(){
        player = Player.getInstance();
        Texture texture = new Texture(Gdx.files.internal("object/money/money.png"));
        Money money1 = new Money(100,300,new TextureRegion(texture));
        objects.add(money1);

        Texture texture1 = new Texture(Gdx.files.internal("object/key/key_A_gold.png"));
        Key key1 = new Key(300,1000,new TextureRegion(texture1));
        Key key2 = new Key(850,200,new TextureRegion(texture1));
        objects.add(key1);
        objects.add(key2);

        Texture texture2 = new Texture(Gdx.files.internal("object/diamond/diamond.png"));
        Diamond diamond1 = new Diamond(850,850,new TextureRegion(texture2));
        Diamond diamond2 = new Diamond(200,850,new TextureRegion(texture2));
        objects.add(diamond1);
        objects.add(diamond2);

        Coin coin = new Coin(1600,300);
        objects.add(coin);

        Texture texture3 = new Texture(Gdx.files.internal("object/meat/meat.png"));
        Meat meat1 = new Meat(1600,1000,new TextureRegion(texture3));
        objects.add(meat1);

        Texture texture4 = new Texture(Gdx.files.internal("object/chest/chest.png"));
        Chest chest1 = new Chest(1650,1300,new TextureRegion(texture4));
        objects.add(chest1);

    }

    public void draw(SpriteBatch batch){
        for(ObjectGame obj : objects){
            obj.draw(batch);
        }

    }

    public void checkCollision(){
        for (int i = 0; i < objects.size; i++){
            ObjectGame obj = objects.get(i);

            if(obj.collide(player)){
                obj.setRemove(true);
            }
        }
    }

    public void update(float delta) {
        for (int i = 0; i < objects.size; i++){
            ObjectGame obj = objects.get(i);
            obj.update(delta);
            if (obj.isRemove()){
                objects.removeIndex(i);
            }
            checkCollision();
        }

    }
}
