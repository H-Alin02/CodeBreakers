package Model.NPC;

import View.Hud.Hud;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public class NPCManager {
    private final List<NPC> NPC;
    private NPCFactoryImp NPCFactory;

    public NPCManager(){
       this.NPC = new ArrayList<>();
       this.NPCFactory = new NPCFactoryImp();
    }

    public void initializeNPCs(){
        NPC.add(NPCFactory.createNPC("Dave",new Vector2(2176,2816)));
        NPC.add(NPCFactory.createNPC("DrGarfild", new Vector2(6528, 4352)));
    }

    public void update( float delta){
        for(NPC npc : NPC){
            npc.update(delta);
        }
    }

    public List<NPC> getNPC() {
        return NPC;
    }

    public void addObserversToNPC(Hud hud){
        for(NPC npc : NPC){
            npc.addObserver(hud);
        }
    }
}

