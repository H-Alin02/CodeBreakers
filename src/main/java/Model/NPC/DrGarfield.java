package Model.NPC;

import Model.Entities.Interactable;
import Model.Object.ObjectCreator;
import Model.Object.ObjectManager;
import Model.Entities.Player.Player;
import Model.SoundPlayer;
import View.Hud.NPCObserver;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.Arrays;
import java.util.List;

public class DrGarfield implements NPC , Interactable {
    private Vector2 position;
    private Animation<TextureRegion> animation1;
    private Texture interactTexture;
    private NPCObserver observer;
    private float stateTime ;
    private boolean hasTalked = false;
    private List<String> dialogues;
    private int currentIndex;
    private ObjectManager objectManager;

    public DrGarfield(Vector2 position){
        this.position = position;

        stateTime = 0;
        Array<TextureRegion> idleFrames = new Array<>();
        for (int i = 1; i<=4; i++){
            idleFrames.add(new TextureRegion(new Texture("NPC/DrGarfild/DrGarfild" + i + ".png")));
        }
        animation1 = new Animation<>(0.2f, idleFrames, Animation.PlayMode.LOOP);

        interactTexture = new Texture(Gdx.files.internal("map/Interaction/Interaction.png"));

        dialogues = Arrays.asList(
               "Oh, FINALMENTE, pensavo di lasciarci le penne... \no meglio la pelle... però non mi dispiacerebbe avere un paio di ali...",
                "Scusa, mi sono lasciato trasportare dai miei pensieri... \nGrazie mille per avermi salvato!",
                "Un gruppo di fanatici ha messo a soqquadro la base con l'obiettivo \ndi prendere il controllo della nuova IA in svilluppo qui nella base: AM",
                "Devi ASSOLUTAMENTE fermarli! \nDevi IMPEDIRE che AM cada sotto il controllo di questi pazzi!",
                "Io e alcuni miei colleghi abbiamo creato un apposito malware per \nDISTRUGGERE AM, da usare proprio in casi come questo, è nascosto in \nCINQUE chiavette diverse... questa è la mia!",
                "Non mi chiedere dove l'ho nascosta, meglio non saperlo...",
                "IO comunque me ne sgattaiolo via, Buona Fortuna... ne avrai bisogno."
        );
        currentIndex = 0;
    }

    @Override
    public void interact(Player player) {
        SoundPlayer.playClickSound();
        talk();

        currentIndex++;

        if(currentIndex == 8){
            player.setPlayerWon(true);
        }

        if(currentIndex == 5){
            objectManager.addObject(new ObjectCreator().createObject("keyUSB",(int)position.x+128,(int)position.y));
        }
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
                150)) {
            float messageX = position.x + 10;
            float messageY = position.y + 32*2;

            batch.draw(interactTexture, messageX, messageY, 96, 96);
        } else if (!hasTalked) {
            observer.onNPCFinishedTalk();
            hasTalked = true; // Imposta la variabile a true dopo la chiamata
        }
    }

    @Override
    public void reset() {
        this.currentIndex = 0;
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
        if(currentIndex < dialogues.size()){
            hasTalked = false;
            notifyObservers("Dr.Garfield : " + dialogues.get(currentIndex));
        }
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

    @Override
    public void addObjectManager(ObjectManager objectManager) {
        this.objectManager = objectManager;
    }

    private void notifyObservers(String message) {
        observer.onNPCTalk(message);
    }
}
