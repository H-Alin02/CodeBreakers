package Model.Object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class ObjectManager {
    private ArrayList<GameObject> objects = new ArrayList<>();

    public ObjectManager(){
        Texture texture = new Texture(Gdx.files.internal("object/frame0000.png"));
        GameObject object1 = new GameObject(100,100,texture);
        objects.add(object1);

        Texture texture1 = new Texture(Gdx.files.internal("object/key_A_gold.png"));
        GameObject object2 = new GameObject(200,200,texture1);
        objects.add(object2);
    }

    public void draw(SpriteBatch batch){
        for(GameObject obj : objects){
            obj.draw(batch);
        }
    }
}
