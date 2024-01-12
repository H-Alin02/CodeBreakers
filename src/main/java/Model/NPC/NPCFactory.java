package Model.NPC;

import com.badlogic.gdx.math.Vector2;

public interface NPCFactory {
    NPC createNPC(String name ,Vector2 position);
}
