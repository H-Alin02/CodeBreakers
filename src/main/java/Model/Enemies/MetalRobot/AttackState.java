package Model.Enemies.MetalRobot;

public class AttackState implements RobotState{
    private float attackTimer = 2f;
    private float attackRange = 100f;
    private float timeBetweenAttacks = 2f;
    @Override
    public void update(MetalRobot metalRobot, float delta) {
        // Logica specifica per lo stato di attacco

        // Esegui l'attacco quando il timer raggiunge l'intervallo desiderato
        attackTimer += delta;
        if (attackTimer >= timeBetweenAttacks) {
            metalRobot.attackPlayer();
            attackTimer = 0f; // Reimposta il timer dopo l'attacco
        }

        // Verifica se l'animazione di attacco è completa

    }

    @Override
    public void enter(MetalRobot metalRobot) {
        // Logica specifica quando entra nello stato di attacco
        if(metalRobot.distanceToPlayer > attackRange){
            metalRobot.changeState(new ChasingState());
        }
    }

    @Override
    public void exit(MetalRobot metalRobot) {
        // Logica specifica quando esce dallo stato di attacco
    }
}
