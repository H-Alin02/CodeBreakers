package Model.Enemies.MetalRobot;

import Model.SoundPlayer;

/**
 * La classe {@code IdleState} rappresenta lo stato di inattività di un nemico di tipo MetalRobot.
 * Durante questo stato, il nemico può cambiare direzione casualmente e, se rileva il giocatore
 * nel suo raggio di inseguimento, transita allo stato di inseguimento.
 * @author Alin Marian Habasescu
 * @author Gabriele Zimmerhofer
 */
public class IdleState implements RobotState {
    private char currentDirection = 'w';
    private float movementTimer = 0f;
    private final float movementDuration = 2f;
    private SoundPlayer alertSound;

    /**
     * Esegue le azioni associate allo stato di inattività del nemico.
     * Durante questo stato, il nemico può cambiare direzione casualmente e,
     * se rileva il giocatore nel suo raggio di inseguimento, transita allo stato di inseguimento.
     *
     * @param metalRobot Il nemico di tipo MetalRobot sul quale applicare lo stato.
     * @param delta Il tempo trascorso dall'ultimo aggiornamento del gioco.
     * @return Il prossimo stato del nemico, o {@code this} se rimane nello stato corrente.
     */
    @Override
    public RobotState runCurrentState(MetalRobot metalRobot, float delta) {
        // Implementare logica per passare allo stato chasing altrimenti rimanere in questo stato
        // se il nemico vede il player ed è nel suo range di chasing allora passa allo stato di Chasing
        if (metalRobot.isChasing()) {
            //alertSound.play(0.1f);
            return new ChasingState();
        } else {
            movementTimer += delta;
            // Change movement direction every 'movementDuration' seconds
            if (movementTimer >= movementDuration) {
                movementTimer = 0f;
                currentDirection = getRandomDirection();
            }
            // Se il nemico non è stato colpito e non è morto , si muove
            if (!metalRobot.enemyHitStates.contains(metalRobot.currentState, true) &&
                    !metalRobot.enemyDeadStates.contains(metalRobot.currentState, true))
                metalRobot.moveEnemy(currentDirection);

            return this;
        }
    }

    /**
     * Genera una direzione casuale tra le possibili direzioni.
     *
     * @return Una direzione casuale.
     */
    private char getRandomDirection() {
        char[] directions = {'w', 's', 'a', 'd', 'q', 'e', 'x', 'z', 'f'};
        int randomIndex = (int) (Math.random() * directions.length);
        return directions[randomIndex];
    }
}

