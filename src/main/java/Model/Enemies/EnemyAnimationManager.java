package Model.Enemies;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class EnemyAnimationManager {
    private final Animation<TextureRegion> idleAnimation;
    //private final Animation<TextureRegion> walkAnimation;
    //private final Animation<TextureRegion> attackAnimation;
    private final Animation<TextureRegion> damageAnimation;
    private float stateTime;

    public EnemyAnimationManager(){
        //initialize enemy animations here

        //Idle animation
        stateTime = 0;
        Array<TextureRegion> idleFrames = new Array<>();
        for (int i = 1; i<=1; i++){
            idleFrames.add(new TextureRegion(new Texture("enemies/Idle" + i + ".png")));
        }
        idleAnimation = new Animation<>(1f, idleFrames, Animation.PlayMode.LOOP);

        //Damage animation
        Array<TextureRegion> damageFrames = new Array<>();
        for (int i = 1; i<=4; i++){
            damageFrames.add(new TextureRegion(new Texture("enemies/Hit" + i + ".png")));
        }
        damageAnimation = new Animation<>(0.15f, damageFrames, Animation.PlayMode.NORMAL);
    }

    public void update(float delta){
        stateTime += delta;
    }

    public TextureRegion getKeyFrame( EnemyState state){
        // Implement logic to return the appropriate animation frame based on the enemy state
        // Similar to how you did for the player
        return switch (state) {
            case IDLE -> idleAnimation.getKeyFrame(stateTime, true);
            case DAMAGE -> damageAnimation.getKeyFrame(stateTime, false);
            // Add more cases for other states
            default -> getDefaultFrame();
        };

    }

    private TextureRegion getDefaultFrame() {
        return idleAnimation.getKeyFrame(0);
    }

    public boolean isDamageAnimationFinished(){
        return damageAnimation.isAnimationFinished(stateTime);
    }
    public void resetDamage(){
        this.stateTime = 0;
    }
}
