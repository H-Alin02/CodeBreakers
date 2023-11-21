
package Controller;

import Model.Player;
import Model.PlayerState;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class PlayerInputManager {
    private final Player player;

    public PlayerInputManager(Player player) {
        this.player = player;
    }

    public void handleInput() {
        // Update the player state based on input
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            player.moveUp();
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            player.moveDown();
        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            player.moveLeft();
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.moveRight();
        } else {
            // If no movement keys are pressed, set the player to standing
            player.currentState = PlayerState.STANDING;
        }
    }
    public void update(float delta){

    }
}
