
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
        // If no movement keys are pressed, set the player to standing
        player.setIsSprinting(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT));

        // Check attack key separately
        if (Gdx.input.isKeyPressed(Input.Keys.K)) {
            // Handle attack only once when the key is pressed and released
            if (Gdx.input.isKeyJustPressed(Input.Keys.K)) {
                if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                    player.attackUp();
                } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                    player.attackDown();
                } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                    player.attackLeft();
                } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                    player.attackRight();
                }
            }
        } else {
            // If no attack key is pressed, check movement keys
            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                if (!player.isCollision(player.getPlayerX() + (player.getPLAYER_WIDTH() / 4), player.getPlayerY() + player.getSPEED())) {
                    player.moveUp();
                }
            } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                if (!player.isCollision(player.getPlayerX() + (player.getPLAYER_WIDTH() / 4), player.getPlayerY() - player.getSPEED())) {
                    player.moveDown();
                }
            } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                if (!player.isCollision(player.getPlayerX() + (player.getPLAYER_WIDTH() / 4) - player.getSPEED(), player.getPlayerY())) {
                    player.moveLeft();
                }
            } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                if (!player.isCollision(player.getPlayerX() + (player.getPLAYER_WIDTH() / 4) + player.getSPEED(), player.getPlayerY())) {
                    player.moveRight();
                }
            } else {
                // If no movement keys are pressed, set the player to standing
                player.currentState = PlayerState.STANDING;
            }
        }
    }
    public void update(float delta){

    }
}
