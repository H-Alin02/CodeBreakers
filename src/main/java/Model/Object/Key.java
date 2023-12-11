package Model.Object;

import Model.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;


public class Key implements ObjectGame{
    private int keyX, keyY;
    private final int OBJECT_HEIGHT = 32;
    private final int OBJECT_WIDTH = 32;
    private boolean remove;
    private TextureRegion texture ;
    private final float SCALE = 1.2f;
    public Key(int keyX, int keyY){
        this.keyX = keyX;
        this.keyY = keyY;
        texture = new TextureRegion(new Texture(Gdx.files.internal("object/key/key_A_gold.png")));

        remove = false;
    }
    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture,keyX,keyY,OBJECT_HEIGHT * SCALE,OBJECT_WIDTH * SCALE);
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
}
