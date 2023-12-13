package Model.Enemies.MetalRobot;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class MetalRobotAnimationManager {
    private final Animation<TextureRegion> idleAnimation1;
    private final Animation<TextureRegion> idleAnimation2;
    private final Animation<TextureRegion> walkAnimation1;
    private final Animation<TextureRegion> walkAnimation2;
    private final Animation<TextureRegion> hitAnimation1;
    private final Animation<TextureRegion> hitAnimation2;
    private final Animation<TextureRegion> deadAnimation1;
    private final Animation<TextureRegion> deadAnimation2;
    private final Animation<TextureRegion> attackAnimation1;
    private final Animation<TextureRegion> attackAnimation2;
    private float stateTime;

    public MetalRobotAnimationManager(){
        //initialize enemy animations here
        stateTime = 0;

        //Idle animation1
        Array<TextureRegion> idleFrames1 = new Array<>();
        for (int i = 1; i<=7; i++){
            idleFrames1.add(new TextureRegion(new Texture("enemies/MetalRobot/Idle1/Idle" + i + ".png")));
        }
        idleAnimation1 = new Animation<>(0.1f, idleFrames1, Animation.PlayMode.LOOP);

        //Idle animation1
        Array<TextureRegion> idleFrames2 = new Array<>();
        for (int i = 1; i<=7; i++){
            idleFrames2.add(new TextureRegion(new Texture("enemies/MetalRobot/Idle1/Idle" + i + ".png")));
        }
        idleAnimation2 = new Animation<>(0.1f, idleFrames2, Animation.PlayMode.LOOP);


        //Walk animation1
        Array<TextureRegion> walkFrames1 = new Array<>();
        for (int i = 1; i<=8; i++){
            walkFrames1.add(new TextureRegion(new Texture("enemies/MetalRobot/Walk1/metalMonster" + i + ".png")));
        }
        walkAnimation1 = new Animation<>(0.1f, walkFrames1, Animation.PlayMode.LOOP);

        //Walk animation2
        Array<TextureRegion> walkFrames2 = new Array<>();
        for (int i = 1; i<=8; i++){
            walkFrames2.add(new TextureRegion(new Texture("enemies/MetalRobot/Walk2/metalMonster" + i + ".png")));
        }
        walkAnimation2 = new Animation<>(0.1f, walkFrames2, Animation.PlayMode.LOOP);

        //Hit animation1
        Array<TextureRegion> hitFrames1 = new Array<>();
        for (int i = 1; i<=8; i++){
            hitFrames1.add(new TextureRegion(new Texture("enemies/MetalRobot/Hit1/Hit" + i + ".png")));
        }
        hitAnimation1 = new Animation<>(0.07f, hitFrames1, Animation.PlayMode.NORMAL);

        //Hit animation2
        Array<TextureRegion> hitFrames2 = new Array<>();
        for (int i = 1; i<=8; i++){
            hitFrames2.add(new TextureRegion(new Texture("enemies/MetalRobot/Hit2/Hit" + i + ".png")));
        }
        hitAnimation2 = new Animation<>(0.07f, hitFrames2, Animation.PlayMode.NORMAL);

        //Dead animation1
        Array<TextureRegion> deadFrames1 = new Array<>();
        for (int i = 1; i<=12; i++){
            deadFrames1.add(new TextureRegion(new Texture("enemies/MetalRobot/Dead1/Dead" + i + ".png")));
        }
        deadAnimation1 = new Animation<>(0.07f, deadFrames1, Animation.PlayMode.NORMAL);

        //Dead animation2
        Array<TextureRegion> deadFrames2 = new Array<>();
        for (int i = 1; i<=12; i++){
            deadFrames2.add(new TextureRegion(new Texture("enemies/MetalRobot/Dead2/Dead" + i + ".png")));
        }
        deadAnimation2 = new Animation<>(0.07f, deadFrames2, Animation.PlayMode.NORMAL);

        //Attack animation1
        Array<TextureRegion> attackFrames1 = new Array<>();
        for (int i = 1; i<=10; i++){
            attackFrames1.add(new TextureRegion(new Texture("enemies/MetalRobot/Attack1/Attack" + i + ".png")));
        }
        attackAnimation1 = new Animation<>(0.07f, attackFrames1, Animation.PlayMode.NORMAL);

        //Attack animation2
        Array<TextureRegion> attackFrames2 = new Array<>();
        for (int i = 1; i<=10; i++){
            attackFrames2.add(new TextureRegion(new Texture("enemies/MetalRobot/Attack2/Attack" + i + ".png")));
        }
        attackAnimation2 = new Animation<>(0.07f, attackFrames2, Animation.PlayMode.NORMAL);
    }

    public void update(float delta){
        stateTime += delta;
    }

    public TextureRegion getKeyFrame( MetalRobotState state){
        // Implement logic to return the appropriate animation frame based on the enemy state
        return switch (state) {
            case IDLE1 -> idleAnimation1.getKeyFrame(stateTime, true);
            case IDLE2 -> idleAnimation2.getKeyFrame(stateTime, true);
            case WALK1 -> walkAnimation1.getKeyFrame(stateTime, true);
            case WALK2 -> walkAnimation2.getKeyFrame(stateTime, true);
            case HIT1 -> hitAnimation1.getKeyFrame(stateTime, false);
            case HIT2 -> hitAnimation2.getKeyFrame(stateTime, false);
            case DEAD1 -> deadAnimation1.getKeyFrame(stateTime, false);
            case DEAD2 -> deadAnimation2.getKeyFrame(stateTime, false);
            case ATTACK1 -> attackAnimation1.getKeyFrame(stateTime, false);
            case ATTACK2 -> attackAnimation2.getKeyFrame(stateTime, false);
        };
    }

    public boolean isAnimationFinished(MetalRobotState state){
        return switch (state) {
            case IDLE1, IDLE2, WALK1, WALK2 -> false;
            case HIT1 -> hitAnimation1.isAnimationFinished(stateTime);
            case HIT2 -> hitAnimation2.isAnimationFinished(stateTime);
            case DEAD1 -> deadAnimation1.isAnimationFinished(stateTime);
            case DEAD2 -> deadAnimation2.isAnimationFinished(stateTime);
            case ATTACK1 -> attackAnimation1.isAnimationFinished(stateTime);
            case ATTACK2 -> attackAnimation2.isAnimationFinished(stateTime);
        };
    }


    public void resetDamage(){
        this.stateTime = 0;
    }

}
