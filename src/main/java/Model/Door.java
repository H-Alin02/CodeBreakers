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

public class Door implements Interactable {
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
    private static final SoundPlayer errorSound = new SoundPlayer(1, 1, "sound_effects/door_error.mp3");
    private ObjectManager objectManager;

    /**
     * Updates the sound effects for the door.
     *
     * @param delta The time passed since the last update.
     */
    public static void updateSound(float delta){
        openingSound.update(delta);
        closingSound.update(delta);
        errorSound.update(delta);
    }

    /**
     * Constructs a Door object with the specified parameters.
     *
     * @param x        The x-coordinate of the door.
     * @param y        The y-coordinate of the door.
     * @param width    The width of the door.
     * @param height   The height of the door.
     * @param isLocked The initial locked state of the door.
     */
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

    /**
     * Gets the current key frame for rendering the door.
     *
     * @return The TextureRegion representing the current key frame.
     */
    public TextureRegion getKeyFrame() {
        if (isClosing) {
            return doorClose.getKeyFrame(stateTime, true);
        } else {
            return doorOpen.getKeyFrame(stateTime, true);
        }
    }

    /**
     * Updates the state of the door based on the elapsed time.
     *
     * @param delta The time passed since the last update.
     */
    public void update(float delta)
    {
        stateTime += delta;
        openingSound.update(delta);
        closingSound.update(delta);
    }

    /**
     * Draws the door and interaction message above it if it's open and the player is in range.
     *
     * @param batch  The SpriteBatch used for rendering.
     * @param player The player entity in the game.
     */
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

    /**
     * Resets the door to its initial state.
     */
    @Override
    public void reset() {
        stateTime = 0;
        isOpen = false;
        isClosing = false;
        isLocked = startIsLocked;
        boundingBox.set(x, y, doorWidth, doorHeight);
    }

    /**
     * Sets the ObjectManager for the door to interact with other game entities.
     *
     * @param objectManager The ObjectManager instance.
     */
    @Override
    public void addObjectManager(ObjectManager objectManager) {
        this.objectManager = objectManager;
    }

    /**
     * Checks if the door is open.
     *
     * @return True if the door is open, false otherwise.
     */
    public boolean isOpen() {
        return isOpen;
    }

    /**
     * Checks if the player is in range of the door.
     *
     * @param playerX The x-coordinate of the player.
     * @param playerY The y-coordinate of the player.
     * @param range   The range within which the player is considered in range.
     * @return True if the player is in range, false otherwise.
     */
    public boolean isPlayerInRange(float playerX, float playerY, float range) {
        float distance = new Vector2(playerX, playerY).dst(x + doorWidth/2, y + doorHeight/2);
        return distance <= range;
    }

    /**
     * Gets the x-coordinate of the door.
     *
     * @return The x-coordinate of the door.
     */
    public float getX() {
        return x;
    }

    /**
     * Gets the y-coordinate of the door.
     *
     * @return The y-coordinate of the door.
     */
    public float getY() {
        return y;
    }

    /**
     * Gets the width of the door.
     *
     * @return The width of the door.
     */
    public float getDoorWidth(){
        return doorWidth;
    }

    /**
     * Gets the height of the door.
     *
     * @return The height of the door.
     */
    public float getDoorHeight(){
        return doorHeight;
    }

    /**
     * Gets the bounding box of the door.
     *
     * @return The bounding box of the door as a Rectangle.
     */
    public Rectangle getBoundingBox() {
        return boundingBox;
    }

    /**
     * Handles the interaction with the door, allowing the player to open or close it.
     *
     * @param player The player entity in the game.
     */
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

    /**
     * Opens the door, initiating the opening animation and playing the corresponding sound.
     */
    private void openDoor() {
        stateTime = 0;
        isOpen = true;
        isClosing = true;
        boundingBox.set(0, 0, 0, 0);
        openingSound.play(0.1f);
    }

    /**
     * Closes the door, initiating the closing animation and playing the corresponding sound.
     */
    private void closeDoor() {
        stateTime = 0;
        isOpen = false;
        isClosing = false;
        boundingBox.set(x, y, doorWidth, doorHeight);
        closingSound.play(0.1f);
    }

    /**
     * Gets the position of the door's center.
     *
     * @return A Vector2 representing the position of the door's center.
     */
    @Override
    public Vector2 getPosition() {
        return new Vector2(this.x + doorWidth/2, this.y+doorHeight/2);
    }

    /**
     * Checks if there is a collision with the door based on the provided coordinates and dimensions.
     *
     * @param x      The x-coordinate of the object to check for collision.
     * @param y      The y-coordinate of the object to check for collision.
     * @param width  The width of the object to check for collision.
     * @param height The height of the object to check for collision.
     * @return True if there is a collision, false otherwise.
     */
    @Override
    public boolean isCollision(float x, float y, float width, float height) {
        return !isOpen && boundingBox.overlaps(new Rectangle(x, y, width, height));
    }


}
