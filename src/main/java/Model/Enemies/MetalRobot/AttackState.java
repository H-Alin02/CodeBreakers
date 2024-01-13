package Model.Enemies.MetalRobot;

/**
 * La classe {@code AttackState} rappresenta lo stato di attacco di un nemico di tipo MetalRobot.
 * Durante questo stato, il nemico esegue attacchi al giocatore quando è nel raggio d'attacco.
 * @author Alin Marian Habasescu
 */
public class AttackState implements RobotState{

    /**
     * Esegue le azioni associate allo stato di attacco del nemico.
     * Durante questo stato, il nemico attacca il giocatore quando è nel raggio d'attacco specificato.
     * Inoltre, transita allo stato di inseguimento se il giocatore si allontana.
     *
     * @param metalRobot Il nemico di tipo MetalRobot sul quale applicare lo stato.
     * @param delta Il tempo trascorso dall'ultimo aggiornamento del gioco.
     * @return Il prossimo stato del nemico, o {@code this} se rimane nello stato corrente.
     */
    @Override
    public RobotState runCurrentState(MetalRobot metalRobot, float delta) {
        float attackRange = 90f;

        if(metalRobot.distanceToPlayer > attackRange){
            metalRobot.setHasAttacked(false);
            return new ChasingState();
        } else {
            // Logica specifica per lo stato di attacco
            // Esegui l'attacco quando il timer raggiunge l'intervallo desiderato
            metalRobot.attackPlayer();
            return this;
        }
    }
}
