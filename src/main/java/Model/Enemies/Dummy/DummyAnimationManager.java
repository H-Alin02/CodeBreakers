package Model.Enemies.Dummy;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
/**
 * La classe DummyAnimationManager gestisce le animazioni per il nemico Dummy.
 * Ogni animazione è rappresentata da un oggetto Animation<TextureRegion>.
 * @author Alin Marian Habasescu
 */
public class DummyAnimationManager {
    private final Animation<TextureRegion> idleAnimation;
    //private final Animation<TextureRegion> walkAnimation;
    //private final Animation<TextureRegion> attackAnimation;
    private final Animation<TextureRegion> damageAnimation1;
    private final Animation<TextureRegion> damageAnimation2;
    private final Animation<TextureRegion> damageAnimation3;
    private float stateTime;

    /**
     * Costruttore della classe DummyAnimationManager.
     * Inizializza le animazioni del nemico Dummy.
     */
    public DummyAnimationManager(){
        //initialize enemy animations here

        //Idle animation
        stateTime = 0;
        Array<TextureRegion> idleFrames = new Array<>();
        for (int i = 1; i<=1; i++){
            idleFrames.add(new TextureRegion(new Texture("enemies/Dummy/Idle/Idle" + i + ".png")));
        }
        idleAnimation = new Animation<>(0.1f, idleFrames, Animation.PlayMode.LOOP);

        //Damage animation1
        Array<TextureRegion> damageFrames1 = new Array<>();
        for (int i = 1; i<=4; i++){
            damageFrames1.add(new TextureRegion(new Texture("enemies/Dummy/Hit1/Hit" + i + ".png")));
        }
        damageAnimation1 = new Animation<>(0.1f, damageFrames1, Animation.PlayMode.NORMAL);

        //Damage animation2
        Array<TextureRegion> damageFrames2 = new Array<>();
        for (int i = 1; i<=5; i++){
            damageFrames2.add(new TextureRegion(new Texture("enemies/Dummy/Hit2/Hit" + i + ".png")));
        }
        damageAnimation2 = new Animation<>(0.1f, damageFrames2, Animation.PlayMode.NORMAL);

        //Damage animation3
        Array<TextureRegion> damageFrames3 = new Array<>();
        for (int i = 1; i<=8; i++){
            damageFrames3.add(new TextureRegion(new Texture("enemies/Dummy/Hit3/Hit" + i + ".png")));
        }
        damageAnimation3 = new Animation<>(0.1f, damageFrames3, Animation.PlayMode.NORMAL);


    }

    /**
     * Aggiorna il tempo di stato per le animazioni.
     *
     * @param delta Il tempo trascorso dall'ultimo aggiornamento.
     */
    public void update(float delta){
        stateTime += delta;
    }

    /**
     * Restituisce il frame chiave dell'animazione in base allo stato del Dummy.
     *
     * @param state Lo stato attuale del Dummy.
     * @return Il frame attuale dell'animazione.
     */
    public TextureRegion getKeyFrame( DummyState state){
        // Implement logic to return the appropriate animation frame based on the enemy state
        return switch (state) {
            case IDLE -> idleAnimation.getKeyFrame(stateTime, true);
            case DAMAGE_1 -> damageAnimation1.getKeyFrame(stateTime, false);
            case DAMAGE_2 -> damageAnimation2.getKeyFrame(stateTime, false);
            case DAMAGE_3 -> damageAnimation3.getKeyFrame(stateTime, false);
            // Add more cases for other states
            default -> getDefaultFrame();
        };
    }

    private TextureRegion getDefaultFrame() {
        return idleAnimation.getKeyFrame(0);
    }

    /**
     * Verifica se l'animazione di danneggiamento è completata per uno stato specifico del Dummy.
     *
     * @param dummyState Lo stato del Dummy.
     * @return True se l'animazione di danneggiamento è completata, altrimenti False.
     */
    public boolean isDamageAnimationFinished(DummyState dummyState){
        return switch (dummyState) {
            case IDLE -> false;
            case WALK -> false;
            case ATTACK -> false;
            case DAMAGE_1 -> damageAnimation1.isAnimationFinished(stateTime);
            case DAMAGE_2 -> damageAnimation2.isAnimationFinished(stateTime);
            case DAMAGE_3 -> damageAnimation3.isAnimationFinished(stateTime);
            case DEAD -> false;
        };
    }

    /**
     * Resettare il tempo di stato per l'animazione.
     */
    public void resetDamage(){
        this.stateTime = 0;
    }
}
