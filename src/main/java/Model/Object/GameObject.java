package Model.Object;

import Model.Player;
import Model.SoundPlayer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;


public interface GameObject {
    void draw(SpriteBatch batch);
    default void update(float delta) {
        getPickSound().update(delta);
    }
    default boolean collide(Player player) {
        return player.getArea().overlaps(getArea());
    }
    Rectangle getArea();
    boolean isRemove();
    void setRemove(boolean b);
    String getName();
    int getValue();
    void setValue(int value);
    default SoundPlayer getPickSound() {
        return new SoundPlayer("sound_effects/item_pick_sound.wav");
    }
}
