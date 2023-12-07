package Model.Object;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface ObjectManagerFactory {
    void draw(SpriteBatch batch);
    void update(float delta);
    void checkCollision();
}
