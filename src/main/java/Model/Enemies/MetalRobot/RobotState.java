package Model.Enemies.MetalRobot;

public interface RobotState {
    void update( MetalRobot metalRobot , float delta);
    void enter (MetalRobot metalRobot);
    void exit(MetalRobot metalRobot);
}
