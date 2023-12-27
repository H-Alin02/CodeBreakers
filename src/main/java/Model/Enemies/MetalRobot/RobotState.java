package Model.Enemies.MetalRobot;

public interface RobotState {
    RobotState runCurrentState(MetalRobot metalRobot, float delta);
}
