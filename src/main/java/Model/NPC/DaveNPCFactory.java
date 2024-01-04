package Model.NPC;

import com.badlogic.gdx.math.Vector2;

public class DaveNPCFactory implements NPCFactory{
    @Override
    public NPC createNPC(Vector2 position) {
        return new DaveNPC(position);
    }
}
