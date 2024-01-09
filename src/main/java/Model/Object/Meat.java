package Model.Object;

import Model.SoundPlayer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;


public class Meat implements GameObject{
    private int meatX, meatY;
    private TextureRegion texture;
    private final int OBJECT_HEIGHT = 32;
    private final int OBJECT_WIDTH = 32;
    private boolean remove;
    private final float SCALE = 1.2f;
    private String name ;
    private int meatValue;
    private static final SoundPlayer pickSound = new SoundPlayer("sound_effects/pick_food.mp3");
    public static void updateSound(float delta) {pickSound.update(delta);}

    public Meat(int meatX, int meatY){
        this.meatX = meatX;
        this.meatY = meatY;
        texture = new TextureRegion(new Texture(Gdx.files.internal("object/meat/meat.png")));

        name = "meat";
        remove = false;
    }

    //public SoundPlayer getPickSound() {return  null;}

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture,meatX,meatY,OBJECT_HEIGHT * SCALE,OBJECT_WIDTH * SCALE);
    }

    @Override
    public Rectangle getArea() {
        return new Rectangle(meatX, meatY, OBJECT_WIDTH, OBJECT_HEIGHT);
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
        return meatValue;
    }

    @Override
    public void setValue(int value) {
        meatValue = value;
    }

    @Override
    public SoundPlayer getPickSound() {return pickSound;}
}
