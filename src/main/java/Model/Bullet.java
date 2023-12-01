package Model;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Bullet {
    private float x,y; //Posizione del Proiettile
    private float speed; //velocità del proiettile
    private char direction; // Direzione del proiettile
    private boolean active; //Flag per indicare se il proiettile è attivo
    private final Animation<TextureRegion> shootDownAnimation;
    private final Animation<TextureRegion> shootUpAnimation;
    private final Animation<TextureRegion> shootRightAnimation;
    private final Animation<TextureRegion> shootLeftAnimation;

    //private final Animation<TextureRegion> HitAnimation;
    private BulletState bulletState;
    private float stateTime;

    public Bullet(float startX, float startY, float speed, char direction){
        this.x = startX;
        this.y = startY;
        this.speed = speed;
        this.direction = direction;
        this.active = true;

        stateTime = 0;

        //Animation Shoot
        Array<TextureRegion> shootDownFrames = new Array<>();
        for (int i = 1; i<=4; i++){
            shootDownFrames.add(new TextureRegion(new Texture("Bullets/BulletDown/BulletDown" + i + ".png")));
        }
        shootDownAnimation = new Animation<>(0.15f, shootDownFrames, Animation.PlayMode.LOOP);

        //Animation Shoot
        Array<TextureRegion> shootUpFrames = new Array<>();
        for (int i = 1; i<=4; i++){
            shootUpFrames.add(new TextureRegion(new Texture("Bullets/BulletUp/BulletUp" + i + ".png")));
        }
        shootUpAnimation = new Animation<>(0.15f, shootUpFrames, Animation.PlayMode.LOOP);

        //Animation Shoot
        Array<TextureRegion> shootRightFrames = new Array<>();
        for (int i = 1; i<=4; i++){
            shootRightFrames.add(new TextureRegion(new Texture("Bullets/BulletRight/BulletRight" + i + ".png")));
        }
        shootRightAnimation = new Animation<>(0.15f, shootRightFrames, Animation.PlayMode.LOOP);

        //Animation Shoot
        Array<TextureRegion> shootLeftFrames = new Array<>();
        for (int i = 1; i<=4; i++){
            shootLeftFrames.add(new TextureRegion(new Texture("Bullets/BulletLeft/BulletLeft" + i + ".png")));
        }
        shootLeftAnimation = new Animation<>(0.15f, shootLeftFrames, Animation.PlayMode.LOOP);

        /*//Animation Hit
        Array<TextureRegion> hitFrames = new Array<>();
        for (int i = 1; i<=8; i++){
            hitFrames.add(new TextureRegion(new Texture("player/RunDown/RunDown" + i + ".png")));
        }
        HitAnimation = new Animation<>(0.15f, hitFrames, Animation.PlayMode.LOOP);*/
    }

    public void update(float delta) {
        stateTime += delta;
        // Aggiorna la posizione del proiettile in base alla direzione e alla velocità
        switch (direction) {
            case 'w':
                y += speed; //Modificare per delta ?
                break;
            case 's':
                y -= speed;
                break;
            case 'a':
                x -= speed;
                break;
            case 'd':
                x += speed;
                break;
        }
    }

    public TextureRegion getKeyFrame(BulletState state) {
        return switch (state) {
            case SHOOT_DOWN -> shootDownAnimation.getKeyFrame(stateTime, true);
            case SHOOT_UP -> shootUpAnimation.getKeyFrame(stateTime, true);
            case SHOOT_RIGHT -> shootRightAnimation.getKeyFrame(stateTime, true);
            case SHOOT_LEFT -> shootLeftAnimation.getKeyFrame(stateTime, true);
            //case HIT -> hitAnimation.getKeyFrame(stateTime, true);

            // Add more cases for other states
            default -> getDefaultFrame();
        };
    }

    private TextureRegion getDefaultFrame() {
        // Return a default frame or standing animation
        return shootDownAnimation.getKeyFrame(0);
    }

    public TextureRegion getCurrentFrame() {
        return this.getKeyFrame(bulletState);
    }

    public boolean isActive() {
        return active;
    }

    public void deactivate() {
        active = false;
    }

    public float getY() {
        return y;
    }

    public float getX() {
        return x;
    }

    public BulletState getBulletState() {
        return bulletState;
    }

    public void setBulletState(BulletState bulletState) {
        this.bulletState = bulletState;
    }
}
