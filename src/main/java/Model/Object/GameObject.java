package Model.Object;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameObject {
    private float x, y;
    private Texture texture;

    public GameObject(float x, float y, Texture texture){
        this.x = x;
        this.y = y;
        this.texture = texture;
    }

    public void draw(SpriteBatch batch){
        batch.draw(texture,x,y);
    }
}
