package Model.Enemies.MetalRobot;

public class ChasingState implements RobotState{
    private float attackRange = 100f;

    @Override
    public void update(MetalRobot metalRobot, float delta) {
        // Logica specifica per lo stato di chasing
        if(!metalRobot.enemyHitStates.contains(metalRobot.currentState,true) &&
                !metalRobot.enemyDeadStates.contains(metalRobot.currentState,true))
            metalRobot.moveTowardsPlayer();

        if (metalRobot.distanceToPlayer < attackRange &&
                !metalRobot.enemyHitStates.contains(metalRobot.currentState, true)) {
            metalRobot.changeState(new AttackState());
        }
    }

    @Override
    public void enter(MetalRobot metalRobot) {
        // Logica specifica quando entra nello stato di chasing
        if (!metalRobot.isChasing()) {
            // Non possiamo entrare nello stato di chasing se non stiamo inseguendo il giocatore
            metalRobot.changeState(new IdleState());
        }
    }

    @Override
    public void exit(MetalRobot metalRobot) {
        // Logica specifica quando esce dallo stato di chasing
    }
}
