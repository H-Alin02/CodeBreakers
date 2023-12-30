package Model;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public interface Interactable {
    void interact(Player player);
    Vector2 getPosition();

    boolean isCollision(float x, float y, float width, float height);

    void draw(SpriteBatch batch, Player player);
}
