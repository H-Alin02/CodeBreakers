package Model;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Door {
    private float x,y;
    private float doorWidth;
    private float doorHeight;
    private boolean isOpen;
    private boolean isClosing; // Nuovo flag per gestire l'animazione di chiusura
    private Rectangle boundingBox;
    private final Animation<TextureRegion> doorOpen;
    private final Animation<TextureRegion> doorClose;
    private float stateTime;

    public Door( float x, float y, float width, float height){
        this.x = x;
        this.y = y;
        this.doorWidth = width;
        this.doorHeight = height;
        this.isOpen = false;
        this.isClosing = false;
        this.boundingBox = new Rectangle(x,y, width, height);

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

    public void update(float delta){
        stateTime += delta;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(getKeyFrame(),x,y,doorWidth,doorHeight);
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean isOpen) {
        this.isOpen = isOpen;
        if (isOpen) {
            stateTime = 0;
            isClosing = true;
            // Se la porta è aperta, il bounding box è vuoto o non collidente
            boundingBox.set(0, 0, 0, 0);
        } else {
            stateTime = 0;
            isClosing = false;
            // Se la porta è chiusa, aggiorna il bounding box della porta
            boundingBox.set(x, y, doorWidth , doorHeight);
        }
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
}
