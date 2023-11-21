package Controller;

import Model.Player;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

public class PlayerInputManager implements InputProcessor {
    private Player player;

    public PlayerInputManager(Player player) {
        this.player = player;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.W:
                player.moveUp();
                break;
            case Input.Keys.S:
                player.moveDown();
                break;
            case Input.Keys.A:
                player.moveLeft();
                break;
            case Input.Keys.D:
                player.moveRight();
                break;
            // Add more keys for shooting, melee attacks, etc.
        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        // Handle key release if needed
        return true;
    }

    @Override
    public boolean keyTyped(char c) {
        return false;
    }

    @Override
    public boolean touchDown(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchCancelled(int i, int i1, int i2, int i3) {
        return false;
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2) {
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i1) {
        return false;
    }

    @Override
    public boolean scrolled(float v, float v1) {
        return false;
    }

    public void update(float delta){

    }
}
