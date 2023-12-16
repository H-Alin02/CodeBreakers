package Model.Enemies.MetalRobot;

public class IdleState implements RobotState{
    private char currentDirection = 'w';
    private float movementTimer = 0f;
    private final float movementDuration = 2f;

    @Override
    public void update(MetalRobot metalRobot, float delta) {
        // Logica specifica per lo stato di idle
        movementTimer += delta;
        // Change movement direction every 'movementDuration' seconds
        if (movementTimer >= movementDuration) {
            movementTimer = 0f;
            currentDirection = getRandomDirection();
        }
        // Se il nemico non è stato colpito e non è morto , si muove
        if(!metalRobot.enemyHitStates.contains(metalRobot.currentState,true) &&
                !metalRobot.enemyDeadStates.contains(metalRobot.currentState,true))
            metalRobot.moveEnemy(currentDirection);
    }

    @Override
    public void enter(MetalRobot metalRobot) {
        // Logica specifica quando entra nello stato di idle
        if(metalRobot.isChasing()){
            // Non possiamo entrare nello stato di idle se stiamo inseguendo il giocatore
            metalRobot.changeState(new ChasingState());
        }
        //Altrimenti, possiamo entrare nello stato di idle
    }

    @Override
    public void exit(MetalRobot metalRobot) {
        // Logica specifica quando esce dallo stato di idle
    }

    private char getRandomDirection() {
        char[] directions = {'w', 's', 'a', 'd', 'q', 'e', 'x', 'z', 'f'};
        int randomIndex = (int) (Math.random() * directions.length);
        return directions[randomIndex];
    }


}
