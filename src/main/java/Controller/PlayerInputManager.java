
package Controller;

import Model.Interactable;
import Model.Player;
import Model.PlayerState;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.utils.Array;

/**
 * Questa classe gestisce l'input del giocatore, aggiornando lo stato del giocatore
 * in base agli input ricevuti dalla tastiera o dal mouse.
 * @author Alin Marian Habasescu
 * @author Francesco Gambone
 * @author Gabriele Zimmerhofer
 */
public class PlayerInputManager {
    /**
     * Il giocatore associato a questo gestore di input.
     */
    private final Player player;

    /**
     * Crea un nuovo gestore di input per il giocatore specificato.
     *
     * @param player Il giocatore da gestire.
     */
    public PlayerInputManager(Player player) {
        this.player = player;
    }

    /**
     * Gestisce gli input del giocatore, aggiornando lo stato del giocatore
     * in base ai tasti premuti sulla tastiera o ai bottoni del mouse premuti.
     */
    public void handleInput()
    {
        //System.out.println(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT));
        // Update the player state based on input
        // If no movement keys are pressed, set the player to standing
        player.setSprinting(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT));

        Boolean up = Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP);
        Boolean down = Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN);
        Boolean left = Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT);
        Boolean right = Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT);
        if(player.currentState!= PlayerState.DEAD) {
            if (Gdx.input.isKeyPressed(Input.Keys.SPACE) || Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
                player.shoot();
            }
            // Check attack key separately
            if (Gdx.input.isKeyPressed(Input.Keys.K) || Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
                player.checkMeleeAttack();
            } else {
                // If no attack key is pressed, check movement keys
                if ((up && down) || (left && right) || (!up && !down && !left && !right)) {
                    // If no movement keys are pressed, set the player to standing
                    if (!player.isAttacking() && !player.isShooting()) player.currentState = PlayerState.STANDING;
                    return;
                }
                if (!player.isAttacking() && !player.isShooting()) {
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
    }

    /**
     * Gestisce l'input di interazione, permettendo al giocatore di interagire
     * con gli oggetti interagibili nelle vicinanze quando il tasto "E" viene premuto.
     *
     * @param interactables Un array di oggetti interagibili.
     */
    public void handleInteractInput(Array<Interactable> interactables){
        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
            player.interactWithNearestObject(interactables);
        }
    }

    /**
     * Aggiorna il gestore di input con il passare del tempo.
     *
     * @param delta Il tempo trascorso dall'ultimo aggiornamento.
     */
    public void update(float delta){

    }
}
