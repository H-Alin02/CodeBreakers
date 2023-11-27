
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
            if (Gdx.input.isKeyJustPressed(Input.Keys.K))


                if (player.getDirection() == 'w') {
                    player.attackUp();
                } else if (player.getDirection() == 's') {
                    player.attackDown();
                } else if (player.getDirection() == 'a') {
                    player.attackLeft();
                } else if (player.getDirection() == 'd') {
                    player.attackRight();
                }
        } else {
            // If no attack key is pressed, check movement keys
            Boolean up = Gdx.input.isKeyPressed(Input.Keys.W);
            Boolean down = Gdx.input.isKeyPressed(Input.Keys.S);
            Boolean left = Gdx.input.isKeyPressed(Input.Keys.A);
            Boolean right = Gdx.input.isKeyPressed(Input.Keys.D);

            if((up == down) || (left == right) || (!up && !down && !left && !right))
            {
                // If no movement keys are pressed, set the player to standing
                player.currentState = PlayerState.STANDING;
            }

            int k = 1;
            if(player.isSprinting())
                k = 2;

            if((up && left) || (left && down) || (down & right) || (right && up))
                player.setSPEED(2*k);
            else
                player.setSPEED(5*k);

            if (up) {
                if (!player.upColliding()) {
                    player.moveUp();
                }
            } if (down) {
                if (!player.downColliding()) {
                    player.moveDown();
                }
            } if (left) {
                if (!player.leftColliding()) {
                    player.moveLeft();
                }
            } if (right) {
                if (!player.rightColliding()) {
                    player.moveRight();
                }
            }
        }
    }
    public void update(float delta){

    }
}
