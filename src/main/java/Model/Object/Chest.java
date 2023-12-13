package Model.Object;

import Model.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;


public class Chest implements ObjectGame{
    private int chestX, chestY;
    private TextureRegion texture;
    private final int OBJECT_HEIGHT = 32;
    private final int OBJECT_WIDTH = 32;
    private boolean remove;
    private final float SCALE = 2f;
    private  String name ;

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
    public void update(float delta) {

    }

    @Override
    public boolean collide(Player player) {
        Rectangle rect = getArea();
        Rectangle rect2 = player.getArea();

        return rect2.overlaps(rect);
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
}
