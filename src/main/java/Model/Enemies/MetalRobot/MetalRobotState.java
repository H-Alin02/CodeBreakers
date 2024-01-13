package Model.Enemies.MetalRobot;

/**
 * L'enumerazione {@code MetalRobotState} rappresenta gli stati possibili di un nemico di tipo MetalRobot.
 * Questa enum viene utilizzata per gestire le animazioni del nemico di tipo MetalRobot.
 * Ogni costante in questa enumerazione rappresenta uno stato specifico del nemico durante il gioco.
 * Gli stati includono animazioni come l'immobilità (IDLE), il movimento (WALK), il colpo ricevuto (HIT),
 * l'attacco (ATTACK) e la morte (DEAD).
 * @author Alin Marian Habasescu
 */
public enum MetalRobotState {
    IDLE1, IDLE2, WALK1, WALK2, HIT1, HIT2, ATTACK1, ATTACK2,DEAD1, DEAD2
}
