package Model.NPC;

public interface NPC {
    void talk();
    void addObserver(NPCObserver observer);

    void update(float delta);


}
