package Model;

import Model.Object.Key;
import Model.Object.ObjectManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Door implements Interactable{
    private float x,y;
    private float doorWidth;
    private float doorHeight;
    private boolean isOpen;
    private boolean isClosing; // Nuovo flag per gestire l'animazione di chiusura
    private boolean startIsLocked;
    private boolean isLocked;
    private Rectangle boundingBox;
    private final Animation<TextureRegion> doorOpen;
    private final Animation<TextureRegion> doorClose;
    private float stateTime;
    private Texture interactTexture;
    private static final SoundPlayer openingSound = new SoundPlayer("sound_effects/door_opening.mp3");
    private static final SoundPlayer closingSound = new SoundPlayer("sound_effects/door_opening.mp3");
    private static final SoundPlayer errorSound = new SoundPlayer("sound_effects/door_error.mp3");
    private ObjectManager objectManager;

    static {
        errorSound.setParallel(false);
    }

    public static void updateSound(float delta){
        openingSound.update(delta);
        closingSound.update(delta);
        errorSound.update(delta);
    }

    public Door( float x, float y, float width, float height, boolean isLocked){
        this.x = x;
        this.y = y;
        this.doorWidth = width;
        this.doorHeight = height;
        this.isOpen = false;
        this.isClosing = false;
        this.isLocked = isLocked;
        this.startIsLocked = isLocked;
        this.boundingBox = new Rectangle(x,y, width, height);
        interactTexture = new Texture(Gdx.files.internal("map/Interaction/Interaction.png"));

        String path = (width == 64) ? "Door2" : "Door1";

        //Animation doorOpen
        Array<TextureRegion> door1Frames = new Array<>();
        for (int i = 1; i<=4; i++){
            door1Frames.add(new TextureRegion(new Texture("map/"+path+"/Door" + i + ".png")));
        }
        doorOpen = new Animation<>(0.2f, door1Frames, Animation.PlayMode.NORMAL);

        //Animation doorClose
        Array<TextureRegion> door2Frames = new Array<>();
        for (int i = 4; i>=1; i--){
            door2Frames.add(new TextureRegion(new Texture("map/"+path+"/Door" + i + ".png")));
        }
        doorClose = new Animation<>(0.2f, door2Frames, Animation.PlayMode.NORMAL);
    }

    public TextureRegion getKeyFrame() {
        if (isClosing) {
            return doorClose.getKeyFrame(stateTime, true);
        } else {
            return doorOpen.getKeyFrame(stateTime, true);
        }
    }

    public void update(float delta)
    {
        stateTime += delta;
        openingSound.update(delta);
        closingSound.update(delta);
    }

    public void draw(SpriteBatch batch, Player player) {
        batch.draw(getKeyFrame(),x,y,doorWidth,doorHeight);
        // Disegna il messaggio sopra la porta se è aperta e il giocatore è nel raggio
        if (isPlayerInRange(player.getHitBox().x+ player.getHitBox().width/2,
                player.getHitBox().y +player.getHitBox().height/2,
                120)) {
            float messageX = x + 20;
            float messageY = y + doorHeight + 20;

            batch.draw(interactTexture, messageX, messageY, 96, 96);
        }
    }

    @Override
    public void reset() {
        stateTime = 0;
        isOpen = false;
        isClosing = false;
        isLocked = startIsLocked;
        boundingBox.set(x, y, doorWidth, doorHeight);
    }

    @Override
    public void addObjectManager(ObjectManager objectManager) {
        this.objectManager = objectManager;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public boolean isPlayerInRange(float playerX, float playerY, float range) {
        float distance = new Vector2(playerX, playerY).dst(x + doorWidth/2, y + doorHeight/2);
        return distance <= range;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getDoorWidth(){
        return doorWidth;
    }

    public float getDoorHeight(){
        return doorHeight;
    }
    public Rectangle getBoundingBox() {
        return boundingBox;
    }

    @Override
    public void interact(Player player) {
        if(isLocked && objectManager.getItem().getKey() > 0){
            objectManager.getItem().remove(Key.class);
            this.isLocked = false;
        }

        if(!isLocked){
            if(!player.getHitBox().overlaps(new Rectangle(x, y, doorWidth, doorHeight))) {
                if (!isOpen) {
                    openDoor();
                } else {
                    closeDoor();
                }
            }
        }
        else
            errorSound.play(0.2f);
    }

    private void openDoor() {
        stateTime = 0;
        isOpen = true;
        isClosing = true;
        boundingBox.set(0, 0, 0, 0);
        openingSound.play(0.1f);
    }

    private void closeDoor() {
        stateTime = 0;
        isOpen = false;
        isClosing = false;
        boundingBox.set(x, y, doorWidth, doorHeight);
        closingSound.play(0.1f);
    }

    @Override
    public Vector2 getPosition() {
        return new Vector2(this.x + doorWidth/2, this.y+doorHeight/2);
    }

    @Override
    public boolean isCollision(float x, float y, float width, float height) {
        return !isOpen && boundingBox.overlaps(new Rectangle(x, y, width, height));
    }


}
