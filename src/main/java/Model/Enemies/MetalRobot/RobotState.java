package Model.Enemies.MetalRobot;

/**
 * L'interfaccia {@code RobotState} definisce uno stato del nemico di tipo MetalRobot
 * e fornisce il metodo necessario per eseguire le azioni associate a tale stato.
 * Questa è un'applicazione del Design Pattern State ed ogni stato del nemico deve
 * implementare questa interfaccia per gestire
 * le transizioni e l'evoluzione del comportamento del nemico.
 * @author Alin Marian Habasescu
 */
public interface RobotState {

    /**
     * Esegue le azioni associate allo stato corrente del nemico.
     *
     * @param metalRobot Il nemico di tipo MetalRobot sul quale applicare lo stato.
     * @param delta Il tempo trascorso dall'ultimo aggiornamento del gioco.
     * @return Il prossimo stato del nemico, o lo stato attuale se non vi è alcuna transizione di stato.
     */
    RobotState runCurrentState(MetalRobot metalRobot, float delta);
}
