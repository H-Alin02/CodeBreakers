package Model.Entities.MetalRobot;

public interface RobotState {
    RobotState runCurrentState(MetalRobot metalRobot, float delta);
}
