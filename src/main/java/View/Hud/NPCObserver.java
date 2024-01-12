package View.Hud;

public interface NPCObserver {
    void onNPCTalk(String message);

    void onNPCFinishedTalk();
}
