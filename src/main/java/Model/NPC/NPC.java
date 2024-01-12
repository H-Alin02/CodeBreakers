package Model.NPC;

import View.Hud.NPCObserver;

public interface NPC {
    void talk();
    void addObserver(NPCObserver observer);

    void update(float delta);


}
