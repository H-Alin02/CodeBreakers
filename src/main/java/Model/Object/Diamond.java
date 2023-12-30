package Model.Object;

import Model.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;


public class Diamond implements ObjectGame{
    private int diamondX, diamondY;
    private TextureRegion texture;
    private final int OBJECT_HEIGHT = 32;
    private final int OBJECT_WIDTH = 32;
    private boolean remove;
    private final float SCALE = 1.2f;
    private String name ;
    public Diamond(int diamondX, int diamondY){
        this.diamondX = diamondX;
        this.diamondY = diamondY;
        this.texture = new TextureRegion(new Texture(Gdx.files.internal("object/diamond/diamond.png")));

        this.name = "diamond";
        this.remove = false;
    }
    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(texture,diamondX,diamondY,OBJECT_HEIGHT * SCALE,OBJECT_WIDTH * SCALE);
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
        return new Rectangle(diamondX,diamondY,OBJECT_WIDTH,OBJECT_HEIGHT);
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
    public String getName(){return name;}

    @Override
    public int getValue() {
        return 0;
    }

    @Override
    public void setValue(int value) {

    }
}
