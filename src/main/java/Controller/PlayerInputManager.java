
package Controller;

import Model.Interactable;
import Model.Player;
import Model.PlayerState;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.Array;

public class PlayerInputManager {
    private final Player player;

    public PlayerInputManager(Player player) {
        this.player = player;
    }

    public void handleInput()
    {
        //System.out.println(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT));
        // Update the player state based on input
        // If no movement keys are pressed, set the player to standing
        player.setSprinting(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT));

        Boolean up = Gdx.input.isKeyPressed(Input.Keys.W);
        Boolean down = Gdx.input.isKeyPressed(Input.Keys.S);
        Boolean left = Gdx.input.isKeyPressed(Input.Keys.A);
        Boolean right = Gdx.input.isKeyPressed(Input.Keys.D);

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            player.shoot();
        }
        // Check attack key separately
        if (Gdx.input.isKeyPressed(Input.Keys.K)) {
                player.checkMeleeAttack();
        } else {
            // If no attack key is pressed, check movement keys
            if ((up && down) || (left && right) || (!up && !down && !left && !right)) {
                // If no movement keys are pressed, set the player to standing
                if(!player.isAttacking() && !player.isShooting()) player.currentState = PlayerState.STANDING;
                return;
            }
            if(!player.isAttacking() && !player.isShooting()){
                if ((up || down) && (left || right))
                    player.setSPEED(3);
                else
                    player.setSPEED(4);

                if (up) {
                    player.moveUp();
                }

                if (down) {
                    player.moveDown();
                }

                if (left) {
                    player.moveLeft();
                }

                if (right) {
                    player.moveRight();
                }
            }
        }
    }

    public void handleInteractInput(Array<Interactable> interactables){
        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            player.interactWithNearestObject(interactables);
        }
    }
    public void update(float delta){

    }
}
