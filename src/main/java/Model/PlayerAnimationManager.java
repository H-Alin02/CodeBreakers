package Model;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class PlayerAnimationManager {
    // Idle Animation
    private final Animation<TextureRegion> idleAnimation;

    // Walk Animations
    private final Animation<TextureRegion> walkUpAnimation;
    private final Animation<TextureRegion> walkDownAnimation;
    private final Animation<TextureRegion> walkLeftAnimation;
    private final Animation<TextureRegion> walkRightAnimation;

    // Melee Animations
    private final Animation<TextureRegion> attackDownAnimation;
    private final Animation<TextureRegion> attackUpAnimation;
    private final Animation<TextureRegion> attackRightAnimation;
    private final Animation<TextureRegion> attackLeftAnimation;

    // Shoot Animations
    private final Animation<TextureRegion> shootDownAnimation;
    private final Animation<TextureRegion> shootUpAnimation;
    private final Animation<TextureRegion> shootRightAnimation;
    private final Animation<TextureRegion> shootLeftAnimation;


    private float animSpeed = 0.1f;
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

        //Animation Attack Up
        Array<TextureRegion> attackUpFrames = new Array<>();
        for (int i = 1; i<=4; i++){
            attackUpFrames.add(new TextureRegion(new Texture("player/AttackUp/AttackUp" + i + ".png")));
        }
        attackUpAnimation = new Animation<>(0.1f, attackUpFrames, Animation.PlayMode.NORMAL);

        //Animation AttackDown
        Array<TextureRegion> attackDownFrames = new Array<>();
        for (int i = 1; i<=4; i++){
            attackDownFrames.add(new TextureRegion(new Texture("player/AttackDown/AttackDown" + i + ".png")));
        }
        attackDownAnimation = new Animation<>(0.1f, attackDownFrames, Animation.PlayMode.NORMAL);

        //Animation AttackRight
        Array<TextureRegion> attackRightFrames = new Array<>();
        for (int i = 1; i<=4; i++){
            attackRightFrames.add(new TextureRegion(new Texture("player/AttackRight/AttackRight" + i + ".png")));
        }
        attackRightAnimation = new Animation<>(0.1f, attackRightFrames, Animation.PlayMode.NORMAL);

        //Animation AttackLeft
        Array<TextureRegion> attackLeftFrames = new Array<>();
        for (int i = 1; i<=4; i++){
            attackLeftFrames.add(new TextureRegion(new Texture("player/AttackLeft/AttackLeft" + i + ".png")));
        }
        attackLeftAnimation = new Animation<>(0.1f, attackLeftFrames, Animation.PlayMode.NORMAL);

        //Animation ShootDown
        Array<TextureRegion> shootDownFrames = new Array<>();
        for (int i = 1; i<=3; i++){
            shootDownFrames.add(new TextureRegion(new Texture("player/ShootDown/ShootDown" + i + ".png")));
        }
        shootDownAnimation = new Animation<>(0.2f, shootDownFrames, Animation.PlayMode.NORMAL);

        //Animation ShootUp
        Array<TextureRegion> shootUpFrames = new Array<>();
        for (int i = 1; i<=3; i++){
            shootUpFrames.add(new TextureRegion(new Texture("player/ShootUp/ShootUp" + i + ".png")));
        }
        shootUpAnimation = new Animation<>(0.2f, shootUpFrames, Animation.PlayMode.NORMAL);

        //Animation ShootDown
        Array<TextureRegion> shootRightFrames = new Array<>();
        for (int i = 1; i<=3; i++){
            shootRightFrames.add(new TextureRegion(new Texture("player/ShootRight/ShootRight" + i + ".png")));
        }
        shootRightAnimation = new Animation<>(0.2f,shootRightFrames, Animation.PlayMode.NORMAL);

        //Animation ShootDown
        Array<TextureRegion> shootLeftFrames = new Array<>();
        for (int i = 1; i<=3; i++){
            shootLeftFrames.add(new TextureRegion(new Texture("player/ShootLeft/ShootLeft" + i + ".png")));
        }
        shootLeftAnimation = new Animation<>(0.2f, shootLeftFrames, Animation.PlayMode.NORMAL);
    }

    public TextureRegion getKeyFrame(PlayerState state) {
        return switch (state) {
            case STANDING -> idleAnimation.getKeyFrame(stateTime, true);
            case WALK_UP -> walkUpAnimation.getKeyFrame(stateTime, true);
            case WALK_DOWN -> walkDownAnimation.getKeyFrame(stateTime, true);
            case WALK_LEFT -> walkLeftAnimation.getKeyFrame(stateTime, true);
            case WALK_RIGHT -> walkRightAnimation.getKeyFrame(stateTime, true);
            case ATTACK_DOWN -> attackDownAnimation.getKeyFrame(stateTime, false);
            case ATTACK_UP -> attackUpAnimation.getKeyFrame(stateTime, false);
            case ATTACK_LEFT -> attackLeftAnimation.getKeyFrame(stateTime, false);
            case ATTACK_RIGHT -> attackRightAnimation.getKeyFrame(stateTime, false);
            case SHOOT_DOWN -> shootDownAnimation.getKeyFrame(stateTime, false);
            case SHOOT_UP -> shootUpAnimation.getKeyFrame(stateTime, false);
            case SHOOT_RIGHT -> shootRightAnimation.getKeyFrame(stateTime, false);
            case SHOOT_LEFT -> shootLeftAnimation.getKeyFrame(stateTime, false);
            // Add more cases for other states
            default -> getDefaultFrame();
        };
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

    public float getAnimTime() {
        return stateTime;
    }


    public void resetAttack(){
        this.stateTime = 0;
    }

    public void resetShoot(){ this.stateTime = 0;}
}

