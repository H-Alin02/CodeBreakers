
package Controller;

import Model.MapModel;
import Model.Player;
import Model.PlayerState;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class PlayerInputManager {
    private final Player player;
    private MapModel model;

    public PlayerInputManager(Player player) {
        this.player = player;
    }


    public void handleInput() {
        // Update the player state based on input
        if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT)){
            player.changeSpeed();
        }
        else {
            if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                if (!player.isCollision(player.getPlayerX() + (player.getPLAYER_WIDTH()/4) , player.getPlayerY() + player.getSPEED())) {
                    player.moveUp();
                }
            } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                if (!player.isCollision(player.getPlayerX() + (player.getPLAYER_WIDTH()/4), player.getPlayerY() - player.getSPEED())){
                    player.moveDown();
                }
            } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                if (!player.isCollision(player.getPlayerX() + (player.getPLAYER_WIDTH()/4) - player.getSPEED(), player.getPlayerY())) {
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
