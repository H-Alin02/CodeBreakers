package Model.Entities.MetalRobot;

public class ChasingState implements RobotState{
    private float attackRange = 90f;
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
