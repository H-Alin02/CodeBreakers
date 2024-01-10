package Model.NPC;

import Model.Object.ObjectManager;

public interface NPC {
    void talk();
    void addObserver(NPCObserver observer);

    void update(float delta);

    void addObjectManager(ObjectManager objectManager);
}
