
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
        // Update the player state based on input
        // If no movement keys are pressed, set the player to standing
        player.setSprinting(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT));

        Boolean up = Gdx.input.isKeyPressed(Input.Keys.W);
        Boolean down = Gdx.input.isKeyPressed(Input.Keys.S);
        Boolean left = Gdx.input.isKeyPressed(Input.Keys.A);
        Boolean right = Gdx.input.isKeyPressed(Input.Keys.D);

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            System.out.println("shoot function called");
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
                    player.setSPEED(4);
                else
                    player.setSPEED(5);

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
    public void update(float delta){

    }
}
