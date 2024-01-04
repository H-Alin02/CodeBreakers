package Model.NPC;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public class NPCManager {
    private final List<NPC> NPC;
    private DaveNPCFactory daveNPCFactory;

    public NPCManager(){
       this.NPC = new ArrayList<>();
       this.daveNPCFactory = new DaveNPCFactory();
    }

    public void initializeNPCs(){
        NPC.add(daveNPCFactory.createNPC(new Vector2(2176,2816)));
    }

    public void update( float delta){

    }

    public List<NPC> getNPC() {
        return NPC;
    }
}

