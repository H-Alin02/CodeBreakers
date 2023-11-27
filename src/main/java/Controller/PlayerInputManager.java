
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
            if((up == down) || (left == right) || (!up && !down && !left && !right))
            {
                // If no movement keys are pressed, set the player to standing
                player.currentState = PlayerState.STANDING;
            }

            if((up && left) || (left && down) || (down & right) || (right && up))
                player.setSPEED(3);
            else
                player.setSPEED(5);

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
