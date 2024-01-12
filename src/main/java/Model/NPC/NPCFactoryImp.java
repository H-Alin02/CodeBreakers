package Model.NPC;

import com.badlogic.gdx.math.Vector2;

public class NPCFactoryImp implements NPCFactory{
    @Override
    public NPC createNPC(String name,Vector2 position) {
        return switch (name) {
            case "DrGarfild" -> new DrGarfield(position);
            case "Dave" -> new DaveNPC(position);
            default -> null;
        };
    }
}
