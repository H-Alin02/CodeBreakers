package Model.Object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;


public class Chest implements GameObject{
    private int chestX, chestY;
    private TextureRegion texture;
    private final int OBJECT_HEIGHT = 32;
    private final int OBJECT_WIDTH = 32;
    private boolean remove;
    private final float SCALE = 2f;
    private  String name ;

    //public SoundPlayer getPickSound() {return  null;}

    public Chest(int chestX, int chestY){
        this.chestX = chestX;
        this.chestY = chestY;
        texture = new TextureRegion(new Texture(Gdx.files.internal("object/chest/chest.png"))) ;

        name = "chest";
        remove = false;
    }
    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture,chestX,chestY,OBJECT_HEIGHT * SCALE,OBJECT_WIDTH * SCALE);
    }

    @Override
    public Rectangle getArea() {
        return new Rectangle(chestX, chestY, OBJECT_WIDTH, OBJECT_HEIGHT);
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
