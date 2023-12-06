package Model.Object;

import Model.Player;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;


public class Money implements ObjectGame{
    private int moneyX, moneyY;
    private TextureRegion texture;
    private final int OBJECT_HEIGHT = 32;
    private final int OBJECT_WIDTH = 32;
    private boolean remove;
    private final float SCALE = 1.2f;

    public Money(int moneyX, int moneyY, TextureRegion texture){
        this.moneyX = moneyX;
        this.moneyY = moneyY;
        this.texture = texture;

        remove = false;
    }
    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture,moneyX,moneyY,OBJECT_HEIGHT * SCALE,OBJECT_WIDTH * SCALE);
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
}
