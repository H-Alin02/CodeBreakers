package Model.NPC;

public interface NPCObserver {
    void onNPCTalk(String message);

    void onNPCFinishedTalk();
}
