package Model.Entities.Object;

import Model.Entities.Player.Player;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 * La classe `Bullet` rappresenta un proiettile sparato dal giocatore nel gioco.
 */
public class Bullet {

    /**
     * Posizione del proiettile lungo l'asse x.
     */
    private float x;

    /**
     * Posizione del proiettile lungo l'asse y.
     */
    private float y;

    /**
     * Velocità del proiettile.
     */
    private float speed;

    /**
     * Direzione del proiettile ('w', 's', 'a', 'd' per su, giù, sinistra, destra).
     */
    private char direction;

    /**
     * Flag che indica se il proiettile è attivo.
     */
    private boolean active;

    /**
     * Animazione del proiettile quando si spara in basso.
     */
    private final Animation<TextureRegion> shootDownAnimation;

    /**
     * Animazione del proiettile quando si spara in alto.
     */
    private final Animation<TextureRegion> shootUpAnimation;

    /**
     * Animazione del proiettile quando si spara a destra.
     */
    private final Animation<TextureRegion> shootRightAnimation;

    /**
     * Animazione del proiettile quando si spara a sinistra.
     */
    private final Animation<TextureRegion> shootLeftAnimation;

    /**
     * Animazione del proiettile quando colpisce un bersaglio.
     */
    private final Animation<TextureRegion> hitAnimation;
    /**
     * Stato corrente del proiettile.
     */
    private BulletState bulletState;

    /**
     * Tempo trascorso dall'attivazione del proiettile.
     */
    private float stateTime;

    /**
     * Oggetto del giocatore che ha sparato il proiettile.
     */
    Player player = Player.getInstance();

    /**
     * Costruttore della classe `Bullet`.
     *
     * @param startX    Posizione iniziale lungo l'asse x.
     * @param startY    Posizione iniziale lungo l'asse y.
     * @param speed     Velocità del proiettile.
     * @param direction Direzione del proiettile ('w', 's', 'a', 'd' per su, giù, sinistra, destra).
     */
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

        //Animation Hit
        Array<TextureRegion> hitFrames = new Array<>();
        for (int i = 1; i<=4; i++){
            hitFrames.add(new TextureRegion(new Texture("Bullets/BulletHit/BulletHit" + i + ".png")));
        }
        hitAnimation = new Animation<>(0.07f, hitFrames, Animation.PlayMode.NORMAL);
    }

    /**
     * Aggiorna la posizione del proiettile in base alla direzione e alla velocità.
     *
     * @param delta Tempo trascorso dall'ultimo aggiornamento.
     */
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

        for (Bullet bullet : player.getBullets()) {
            player.inflictShootDamageToEnemies(bullet);
            // Aggiungi la logica di collisione qui, se necessario
        }
    }

    /**
     * Restituisce il frame chiave dell'animazione corrispondente allo stato del proiettile.
     *
     * @param state Stato del proiettile.
     * @return Frame chiave dell'animazione.
     */
    public TextureRegion getKeyFrame(BulletState state) {
        return switch (state) {
            case SHOOT_DOWN -> shootDownAnimation.getKeyFrame(stateTime, true);
            case SHOOT_UP -> shootUpAnimation.getKeyFrame(stateTime, true);
            case SHOOT_RIGHT -> shootRightAnimation.getKeyFrame(stateTime, true);
            case SHOOT_LEFT -> shootLeftAnimation.getKeyFrame(stateTime, true);
            case HIT -> hitAnimation.getKeyFrame(stateTime, false);
            // Add more cases for other states
            default -> getDefaultFrame();
        };
    }

    private TextureRegion getDefaultFrame() {
        // Return a default frame or standing animation
        return shootDownAnimation.getKeyFrame(0);
    }

    /**
     * Restituisce il frame corrente del proiettile.
     *
     * @return Frame corrente del proiettile.
     */
    public TextureRegion getCurrentFrame() {
        return this.getKeyFrame(bulletState);
    }

    /**
     * Verifica se il proiettile è attivo.
     *
     * @return True se il proiettile è attivo, altrimenti false.
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Disattiva il proiettile.
     */
    public void deactivate() {
        active = false;
    }

    /**
     * Restituisce la posizione lungo l'asse y del proiettile.
     *
     * @return Posizione lungo l'asse y.
     */
    public float getY() {
        return y;
    }

    /**
     * Restituisce la posizione lungo l'asse x del proiettile.
     *
     * @return Posizione lungo l'asse x.
     */
    public float getX() {
        return x;
    }

    /**
     * Restituisce lo stato corrente del proiettile.
     *
     * @return Stato corrente del proiettile.
     */
    public BulletState getBulletState() {
        return bulletState;
    }

    /**
     * Imposta lo stato del proiettile.
     *
     * @param bulletState Nuovo stato del proiettile.
     */
    public void setBulletState(BulletState bulletState) {
        this.bulletState = bulletState;
    }
}
