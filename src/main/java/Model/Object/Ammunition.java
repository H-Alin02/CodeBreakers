package Model.Object;

import Model.SoundPlayer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Ammunition implements GameObject {
    private int ammunitionX, ammunitionY;
    private TextureRegion texture;
    private final int OBJECT_HEIGHT = 32;
    private final int OBJECT_WIDTH = 32;
    private boolean remove;
    private final float SCALE = 1.2f;
    private String name ;
    private int ammunitionValue;
    private static final SoundPlayer pickSound = new SoundPlayer("sound_effects/pick_ammo.mp3");
    public static void updateSound(float delta) {pickSound.update(delta);}

    public Ammunition(int ammunitionX, int ammunitionY){
        this.ammunitionX = ammunitionX;
        this.ammunitionY = ammunitionY;
        this.texture = new TextureRegion(new Texture(Gdx.files.internal("object/ammunition/ammunition.png")));

        this.name = "ammunition";
        this.remove = false;
        this.ammunitionValue = 50;
    }

    public SoundPlayer getPickSound()
    {
        return pickSound;
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture,ammunitionX,ammunitionY,OBJECT_HEIGHT * SCALE,OBJECT_WIDTH * SCALE);
    }

    @Override
    public Rectangle getArea() {
        return new Rectangle(ammunitionX,ammunitionY,OBJECT_WIDTH,OBJECT_HEIGHT);
    }

    @Override
    public boolean isRemove() {
        return this.remove;
    }

    @Override
    public void setRemove(boolean b) {
        this.remove = b;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getValue() {
        return ammunitionValue;
    }

    @Override
    public void setValue(int value) {
        ammunitionValue += value;
    }
}
