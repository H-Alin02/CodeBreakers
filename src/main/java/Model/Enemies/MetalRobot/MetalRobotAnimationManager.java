package Model.Enemies.MetalRobot;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class MetalRobotAnimationManager {
    private final Animation<TextureRegion> idleAnimation1;
    private final Animation<TextureRegion> idleAnimation2;
    private float stateTime;

    public MetalRobotAnimationManager(){
        //initialize enemy animations here
        stateTime = 0;

        //Idle animation1
        Array<TextureRegion> idleFrames1 = new Array<>();
        for (int i = 1; i<=8; i++){
            idleFrames1.add(new TextureRegion(new Texture("enemies/MetalRobot/Idle1/metalMonster" + i + ".png")));
        }
        idleAnimation1 = new Animation<>(0.1f, idleFrames1, Animation.PlayMode.LOOP);

        //Idle animation2
        Array<TextureRegion> idleFrames2 = new Array<>();
        for (int i = 1; i<=8; i++){
            idleFrames2.add(new TextureRegion(new Texture("enemies/MetalRobot/Idle2/metalMonster" + i + ".png")));
        }
        idleAnimation2 = new Animation<>(0.1f, idleFrames2, Animation.PlayMode.LOOP);
    }

    public void update(float delta){
        stateTime += delta;
    }

    public TextureRegion getKeyFrame( MetalRobotState state){
        // Implement logic to return the appropriate animation frame based on the enemy state
        return switch (state) {
            case IDLE1 -> idleAnimation1.getKeyFrame(stateTime, true);
            case IDLE2 -> idleAnimation2.getKeyFrame(stateTime, true);
            default -> getDefaultFrame();
        };
    }

    private TextureRegion getDefaultFrame() {
        return idleAnimation1.getKeyFrame(0);
    }

    public void resetDamage(){
        this.stateTime = 0;
    }

}
