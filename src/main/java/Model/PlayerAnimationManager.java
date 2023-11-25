package Model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class PlayerAnimationManager {
    private final Animation<TextureRegion> idleAnimation;
    private final Animation<TextureRegion> walkUpAnimation;
    private final Animation<TextureRegion> walkDownAnimation;
    private final Animation<TextureRegion> walkLeftAnimation;
    private final Animation<TextureRegion> walkRightAnimation;
    private float animSpeed = 0.1f;


// Add more animations for shooting, melee attacks, etc.

    private float stateTime;

    public PlayerAnimationManager() {
        // Initialize your animations here
        stateTime = 0;

        //Animation Idle
        Array<TextureRegion> idleFrames = new Array<>();
        for (int i = 1; i<=4; i++){
            idleFrames.add(new TextureRegion(new Texture("player/Idle/Idle" + i + ".png")));
        }
        idleAnimation = new Animation<>(0.15f, idleFrames, Animation.PlayMode.LOOP);

        //Animation Run Down
        Array<TextureRegion> walkDownFrames = new Array<>();
        for (int i = 1; i<=8; i++){
            walkDownFrames.add(new TextureRegion(new Texture("player/RunDown/RunDown" + i + ".png")));
        }
        walkDownAnimation = new Animation<>(animSpeed, walkDownFrames, Animation.PlayMode.LOOP);

        //Animation Run Right
        Array<TextureRegion> walkRightFrames = new Array<>();
        for (int i = 1; i<=8; i++){
            walkRightFrames.add(new TextureRegion(new Texture("player/RunRight/RunRight" + i + ".png")));
        }
        walkRightAnimation = new Animation<>(animSpeed, walkRightFrames, Animation.PlayMode.LOOP);

        //Animation Run Left
        Array<TextureRegion> walkLeftFrames = new Array<>();
        for (int i = 1; i<=8; i++){
            walkLeftFrames.add(new TextureRegion(new Texture("player/RunLeft/RunLeft" + i + ".png")));
        }
        walkLeftAnimation = new Animation<>(animSpeed, walkLeftFrames, Animation.PlayMode.LOOP);

        //Animation Run Up
        Array<TextureRegion> walkUpFrames = new Array<>();
        for (int i = 1; i<=8; i++){
            walkUpFrames.add(new TextureRegion(new Texture("player/RunUp/RunUp" + i + ".png")));
        }
        walkUpAnimation = new Animation<>(animSpeed, walkUpFrames, Animation.PlayMode.LOOP);
    }

    public TextureRegion getKeyFrame(PlayerState state) {
        switch (state) {
            case STANDING:
                return idleAnimation.getKeyFrame(stateTime,true);
            case WALK_UP:
                return walkUpAnimation.getKeyFrame(stateTime, true);
            case WALK_DOWN:
                return walkDownAnimation.getKeyFrame(stateTime, true);
            case WALK_LEFT:
                return walkLeftAnimation.getKeyFrame(stateTime, true);
            case WALK_RIGHT:
                return walkRightAnimation.getKeyFrame(stateTime, true);
            // Add more cases for other states
            default:
                return getDefaultFrame();
        }
    }

    private TextureRegion getDefaultFrame() {
        // Return a default frame or standing animation
        return idleAnimation.getKeyFrame(0);
    }

    public void update(float delta) {
        stateTime += delta;
    }

    public void updateAnimSpeed(float newSpeed) {
        // Update the animation speed for all animations
        walkUpAnimation.setFrameDuration(newSpeed);
        walkDownAnimation.setFrameDuration(newSpeed);
        walkLeftAnimation.setFrameDuration(newSpeed);
        walkRightAnimation.setFrameDuration(newSpeed);

        // Update the stored animSpeed value
        animSpeed = newSpeed;
    }
    public float getAnimSpeed() {
        return animSpeed;
    }
    public void setAnimSpeed(float animSpeed) {
        this.animSpeed = animSpeed;
    }
}

