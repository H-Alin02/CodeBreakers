package Model.Object;

import Model.Player;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;


public interface ObjectGame {
    void draw(SpriteBatch batch);
    void update(float delta);
    boolean collide(Player player);
    Rectangle getArea();
    boolean isRemove();
    void setRemove(boolean b);
    String getName();
}
