package Model.Object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;


public class Key implements GameObject{
    private int keyX, keyY;
    private final int OBJECT_HEIGHT = 32;
    private final int OBJECT_WIDTH = 32;
    private boolean remove;
    private TextureRegion texture ;
    private final float SCALE = 1.2f;
    private String name ;
    public Key(int keyX, int keyY){
        this.keyX = keyX;
        this.keyY = keyY;
        texture = new TextureRegion(new Texture(Gdx.files.internal("object/key/key_A_gold.png")));

        name = "key";
        remove = false;
    }
    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture,keyX,keyY,OBJECT_HEIGHT * SCALE,OBJECT_WIDTH * SCALE);
    }

    //public SoundPlayer getPickSound() {return  null;}

    @Override
    public Rectangle getArea() {
        return new Rectangle(keyX,keyY,OBJECT_WIDTH,OBJECT_HEIGHT);
    }

    @Override
    public boolean isRemove() {
        return remove;
    }

    @Override
    public void setRemove(boolean b) {
        remove = b;
    }

    @Override
    public String getName(){return name;}

    @Override
    public int getValue() {
        return 0;
    }

    @Override
    public void setValue(int value) {

    }
}
