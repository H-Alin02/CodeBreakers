package Model.Entities.Player;

/**
 * L'enumerazione PlayerState rappresenta gli stati possibili di un giocatore in un gioco.
 * Gli stati includono la posizione del giocatore (STANDING, WALK_UP, WALK_DOWN, WALK_LEFT, WALK_RIGHT),
 * l'azione di attacco in diverse direzioni (ATTACK_UP, ATTACK_DOWN, ATTACK_RIGHT, ATTACK_LEFT),
 * l'azione di sparare in diverse direzioni (SHOOT_UP, SHOOT_DOWN, SHOOT_RIGHT, SHOOT_LEFT),
 * oltre a stati come DEAD e PAUSE.
 */
public enum PlayerState {
    STANDING,        // Il giocatore è fermo
    WALK_UP,         // Il giocatore si muove verso l'alto
    WALK_DOWN,       // Il giocatore si muove verso il basso
    WALK_LEFT,       // Il giocatore si muove a sinistra
    WALK_RIGHT,      // Il giocatore si muove a destra
    ATTACK_UP,       // Il giocatore attacca verso l'alto
    ATTACK_DOWN,     // Il giocatore attacca verso il basso
    ATTACK_RIGHT,    // Il giocatore attacca a destra
    ATTACK_LEFT,     // Il giocatore attacca a sinistra
    SHOOT_UP,        // Il giocatore spara verso l'alto
    SHOOT_DOWN,      // Il giocatore spara verso il basso
    SHOOT_RIGHT,     // Il giocatore spara a destra
    SHOOT_LEFT,      // Il giocatore spara a sinistra
    DEAD,            // Il giocatore è morto
    PAUSE            // Il gioco è in pausa
}

