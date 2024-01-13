package Model.Enemies;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

/**
 * L'interfaccia {@code Enemy} definisce il contratto per la rappresentazione di un nemico nel gioco.
 * Gli oggetti che implementano questa interfaccia devono fornire metodi per l'aggiornamento,
 * il subire danni, ottenere il frame corrente, ottenere le coordinate e le dimensioni del nemico,
 * ottenere la hitbox, verificare se il nemico è morto e se l'animazione di danni è completa.
 * E' l'interfaccia Product del Patter Creazionale Factory Method usato per la creazione dei Nemici
 * @author Alin Marian Habasescu
 */
public interface Enemy {
    void update(float delta);
    void takeDamage(int damage);
    TextureRegion getCurrentFrame();
    float getEnemyX();
    float getEnemyY();
    float getEnemyWidth();
    float getEnemyHeight();
    Rectangle getHitBox();

    boolean isDead();

    boolean isDamageAnimationComplete();
}
