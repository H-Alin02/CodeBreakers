package Model.Entities.Object;

/**
 * L'enumerazione {@code BulletState} rappresenta gli stati possibili di un proiettile.
 * Un proiettile può trovarsi in uno dei seguenti stati: SHOOT_DOWN, SHOOT_UP, SHOOT_RIGHT, SHOOT_LEFT, HIT.
 * <p>
 * Gli stati indicano la direzione del movimento del proiettile o il fatto che il proiettile è stato colpito.
 * </p>
 *
 * @author [Il tuo nome]
 * @version 1.0
 */
public enum BulletState {
    /**
     * Stato che rappresenta il movimento del proiettile verso il basso.
     */
    SHOOT_DOWN,

    /**
     * Stato che rappresenta il movimento del proiettile verso l'alto.
     */
    SHOOT_UP,

    /**
     * Stato che rappresenta il movimento del proiettile verso destra.
     */
    SHOOT_RIGHT,

    /**
     * Stato che rappresenta il movimento del proiettile verso sinistra.
     */
    SHOOT_LEFT,

    /**
     * Stato che indica che il proiettile ha colpito qualcosa.
     */
    HIT
}

