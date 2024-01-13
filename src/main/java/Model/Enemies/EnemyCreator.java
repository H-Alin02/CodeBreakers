package Model.Enemies;

/**
 * L'interfaccia {@code EnemyCreator} definisce il contratto per un EnemyCreator di nemici nel gioco.
 * Le classi che implementano questa interfaccia devono fornire un metodo per creare nuovi nemici
 * con le coordinate iniziali specificate.
 * Rappresenta l'interfaccia Creator del Design Pattern Factory Method.
 * @author Alin Marian Habasescu
 */
public interface EnemyCreator {

    /**
     * Crea un nuovo nemico con le coordinate iniziali specificate.
     *
     * @param startX La coordinata X iniziale del nemico.
     * @param startY La coordinata Y iniziale del nemico.
     * @return Un'istanza di {@code Enemy} creata con le coordinate fornite.
     */
    Enemy createEnemy(int startX, int startY);
}
