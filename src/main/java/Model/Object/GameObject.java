package Model.Object;

import Model.Entities.Player.Player;
import Model.SoundPlayer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;


public interface GameObject {
    SoundPlayer pickSound = new SoundPlayer("sound_effects/Item/item_pick_sound.wav");
    void draw(SpriteBatch batch);
    default void update(float delta) {}
    default boolean collide(Player player) {
        return player.getArea().overlaps(getArea());
    }
    Rectangle getArea();
    boolean isRemove();
    void setRemove(boolean b);
    String getName();
    int getValue();
    public void setValue(int value);
    default SoundPlayer getPickSound() {
        return pickSound;
    }
}
