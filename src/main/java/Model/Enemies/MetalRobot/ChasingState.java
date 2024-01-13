package Model.Enemies.MetalRobot;

/**
 * La classe {@code ChasingState} rappresenta lo stato di inseguimento di un nemico di tipo MetalRobot.
 * Durante questo stato, il nemico segue il giocatore e può transitare allo stato di attacco
 * se è nel raggio d'attacco del suo attacco, altrimenti se il nemico esce dal raggio di inseguimento
 * il nemico torna allo stato di inattività.
 * @author Alin Marian Habasescu
 */
public class ChasingState implements RobotState{
    private float attackRange = 90f;

    /**
     * Esegue le azioni associate allo stato di inseguimento del nemico.
     * Durante questo stato, il nemico segue il giocatore e può transitare allo stato di attacco
     * se è nel raggio d'attacco del suo attacco, oppure transitare allo stato di inattività se il Player esce
     * dal raggio di inseguimento.
     *
     * @param metalRobot Il nemico di tipo MetalRobot sul quale applicare lo stato.
     * @param delta Il tempo trascorso dall'ultimo aggiornamento del gioco.
     * @return Il prossimo stato del nemico, o {@code this} se rimane nello stato corrente.
     */
    @Override
    public RobotState runCurrentState(MetalRobot metalRobot, float delta) {
        if(metalRobot.distanceToPlayer <= attackRange &&
                !metalRobot.enemyHitStates.contains(metalRobot.currentState, true)){
            return new AttackState();
        } else if (!metalRobot.isChasing()) {
            return  new IdleState();
        }else {
            // Logica specifica per lo stato di chasing
            if(!metalRobot.enemyHitStates.contains(metalRobot.currentState,true) &&
                    !metalRobot.enemyDeadStates.contains(metalRobot.currentState,true))
                metalRobot.moveTowardsPlayer();
        }

        return this;
    }
}
