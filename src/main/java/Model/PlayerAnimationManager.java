package Model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class PlayerAnimationManager {
    private final Animation<TextureRegion> walkUpAnimation;
    private final Animation<TextureRegion> walkDownAnimation;
    private final Animation<TextureRegion> walkLeftAnimation;
    private final Animation<TextureRegion> walkRightAnimation;

    // Add more animations for shooting, melee attacks, etc.

    private float stateTime;

    public PlayerAnimationManager() {
        // Initialize your animations here
        stateTime = 0;
        //Animation walk Down
        Array<TextureRegion> walkDownFrames = new Array<>();
        for (int i = 1; i<=4; i++){
            walkDownFrames.add(new TextureRegion(new Texture("player/walkDown" + i + ".png")));
        }
        walkDownAnimation = new Animation<>(0.2f, walkDownFrames, Animation.PlayMode.LOOP);
        //Animation walk Right
        Array<TextureRegion> walkRightFrames = new Array<>();
        for (int i = 1; i<=4; i++){
            walkRightFrames.add(new TextureRegion(new Texture("player/walkRight" + i + ".png")));
        }
        walkRightAnimation = new Animation<>(0.2f, walkRightFrames, Animation.PlayMode.LOOP);
        //Animation walk Left
        Array<TextureRegion> walkLeftFrames = new Array<>();
        for (int i = 1; i<=4; i++){
            walkLeftFrames.add(new TextureRegion(new Texture("player/walkLeft" + i + ".png")));
        }
        walkLeftAnimation = new Animation<>(0.2f, walkLeftFrames, Animation.PlayMode.LOOP);
        //Animation walk Up
        Array<TextureRegion> walkUpFrames = new Array<>();
        for (int i = 1; i<=4; i++){
            walkUpFrames.add(new TextureRegion(new Texture("player/walkUp" + i + ".png")));
        }
        walkUpAnimation = new Animation<>(0.2f, walkUpFrames, Animation.PlayMode.LOOP);
    }

    public TextureRegion getKeyFrame(PlayerState state) {
        switch (state) {
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
        return walkDownAnimation.getKeyFrame(0);
    }

    public void update(float delta) {
        stateTime += delta;
    }
}

