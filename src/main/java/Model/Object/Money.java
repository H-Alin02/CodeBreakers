package Model.Object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;


public class Money implements GameObject{
    private int moneyX, moneyY;
    private TextureRegion texture;
    private final int OBJECT_HEIGHT = 32;
    private final int OBJECT_WIDTH = 32;
    private boolean remove;
    private final float SCALE = 1.2f;
    private String name ;
    private int money;

    public Money(int moneyX, int moneyY){
        this.moneyX = moneyX;
        this.moneyY = moneyY;
        texture = new TextureRegion(new Texture(Gdx.files.internal("object/money/money.png")));

        name = "money";
        remove = false;
        money = 500;
    }

    //public SoundPlayer getPickSound() {return  null;}

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture,moneyX,moneyY,OBJECT_HEIGHT * SCALE,OBJECT_WIDTH * SCALE);
    }

    @Override
    public Rectangle getArea() {
        return new Rectangle(moneyX, moneyY, OBJECT_WIDTH, OBJECT_HEIGHT);
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
        return money;
    }

    @Override
    public void setValue(int value) {
        money += value;
    }

}
