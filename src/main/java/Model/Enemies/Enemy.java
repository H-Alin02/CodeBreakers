package Model.Enemies;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public interface Enemy {
    void update(float delta);
    void takeDamage(int damage);
    TextureRegion getCurrentFrame();
    float getEnemyX();
    float getEnemyY();
    float getEnemyWidth();
    float getEnemyHeight();
    Rectangle getHitBox();

    boolean isDead();

    boolean isDamageAnimationComplete();
}
