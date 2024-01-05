package Model.NPC;

import Model.Interactable;
import Model.Player;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class DrGarfield implements NPC , Interactable {
    private Vector2 position;
    private Animation<TextureRegion> animation1;
    private Texture interactTexture;
    private NPCObserver observer;
    private float stateTime ;
    private boolean hasTalked = false;

    public DrGarfield(Vector2 position){
        this.position = position;

        stateTime = 0;
        Array<TextureRegion> idleFrames = new Array<>();
        for (int i = 1; i<=4; i++){
            idleFrames.add(new TextureRegion(new Texture("NPC/DrGarfild/DrGarfild" + i + ".png")));
        }
        animation1 = new Animation<>(0.2f, idleFrames, Animation.PlayMode.LOOP);

        interactTexture = new Texture(Gdx.files.internal("map/Interaction/Interaction.png"));
    }

    @Override
    public void interact(Player player) {
        talk();
    }

    @Override
    public Vector2 getPosition() {
        return new Vector2(position.x+ 32/2,position.y+32/2);
    }

    @Override
    public boolean isCollision(float x, float y, float width, float height) {
        return false;
    }

    @Override
    public void draw(SpriteBatch batch, Player player) {
        batch.draw(getKeyFrame(),position.x,position.y,32*3,32*3);

        if (isPlayerInRange(player.getHitBox().x+ player.getHitBox().width/2,
                player.getHitBox().y +player.getHitBox().height/2,
                120)) {
            float messageX = position.x + 10;
            float messageY = position.y + 32*2;

            batch.draw(interactTexture, messageX, messageY, 96, 96);
        } else if (!hasTalked) {
            observer.onNPCFinishedTalk();
            hasTalked = true; // Imposta la variabile a true dopo la chiamata
        }
    }

    public boolean isPlayerInRange(float playerX, float playerY, float range) {
        float distance = new Vector2(playerX, playerY).dst(position.x + 32/2, position.y + 32/2);
        return distance <= range;
    }

    public TextureRegion getKeyFrame() {
        return animation1.getKeyFrame(stateTime, true);
    }

    @Override
    public void talk() {
        hasTalked = false;
        String message = "SONO FOTTUTAMENTE PAZZO ";
        notifyObservers("SONO PAZZO " + message);
    }

    @Override
    public void addObserver(NPCObserver observer) {
        this.observer = observer;
        System.out.println("Added observer for DrGarfield : " + observer);
    }

    @Override
    public void update(float delta) {
        stateTime += delta;
    }

    private void notifyObservers(String message) {
        observer.onNPCTalk(message);
    }
}
