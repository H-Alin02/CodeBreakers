
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

    public void handleInput()
    {
        //System.out.println(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT));

        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT))
            {player.setSPEED(10);}
        else
            {player.setSPEED(5);}

        // Update the player state based on input
        // If no movement keys are pressed, set the player to standing
        player.setSprinting(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT));

        Boolean up = Gdx.input.isKeyPressed(Input.Keys.W);
        Boolean down = Gdx.input.isKeyPressed(Input.Keys.S);
        Boolean left = Gdx.input.isKeyPressed(Input.Keys.A);
        Boolean right = Gdx.input.isKeyPressed(Input.Keys.D);

        // Check attack key separately
        if (Gdx.input.isKeyPressed(Input.Keys.K)) {

            // Handle attack only once when the key is pressed and released
            if (Gdx.input.isKeyJustPressed(Input.Keys.K))

                player.checkMeleeAttack();
        } else {
            // If no attack key is pressed, check movement keys
            if ((up && down) || (left && right) || (!up && !down && !left && !right)) {
                // If no movement keys are pressed, set the player to standing
                player.currentState = PlayerState.STANDING;
                return;
            }

            if ((up || down) && (left || right))
                player.setSPEED(3);
            else
                player.setSPEED(5);

            if (up && !player.upColliding()) {
                player.moveUp();
            }

            if (down && !player.downColliding()) {
                player.moveDown();
            }

            if (left && !player.leftColliding()) {
                player.moveLeft();
            }

            if (right && !player.rightColliding()) {
                player.moveRight();
            }
        }
    }
    public void update(float delta){

    }
}
