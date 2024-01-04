package Model.Object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Medikit implements GameObject{
    private int medikitX, medikitY;
    private TextureRegion texture;
    private final int OBJECT_HEIGHT = 32;
    private final int OBJECT_WIDTH = 32;
    private boolean remove;
    private boolean isAvailable;
    private final float SCALE = 1.2f;
    private String name ;
    private int medicalLife;
    public Medikit(int medikitX, int medikitY){
        this.medikitX = medikitX;
        this.medikitY = medikitY;
        this.texture = new TextureRegion(new Texture(Gdx.files.internal("object/medikit/medikit.png")));

        this.name = "medikit";
        this.remove = false;
        this.isAvailable = true;
    }

    //public SoundPlayer getPickSound() {return  null;}

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture,medikitX,medikitY,OBJECT_HEIGHT * SCALE,OBJECT_WIDTH * SCALE);
    }

    @Override
    public Rectangle getArea() {
        return new Rectangle(medikitX,medikitY,OBJECT_WIDTH,OBJECT_HEIGHT);
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
        return medicalLife;
    }

    @Override
    public void setValue(int value) {
        medicalLife = value;
    }

}
