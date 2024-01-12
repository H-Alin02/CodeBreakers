package Model.Entities.MetalRobot;

public class AttackState implements RobotState{

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
