package View;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

/**
 * La classe `DesktopLauncher` contiene il metodo `main` che avvia l'applicazione su piattaforma desktop.
 * @author Alin Marian Habasescu
 */
public class DesktopLauncher {
    /**
     * Il metodo principale che avvia l'applicazione su desktop.
     *
     * @param args Gli argomenti della riga di comando.
     */
    public static void main(String[] args) {
        // Configurazione della finestra di gioco
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setIdleFPS(60); // Imposta il frame rate quando l'applicazione è inattiva
        config.useVsync(true); // Utilizza la sincronizzazione verticale
        config.setTitle("CodeBreakers: The Revenge"); // Titolo della finestra di gioco
        config.setWindowedMode(1152, 864); // Modalità finestra con dimensioni specificate
        config.setResizable(false); // Impedisce la ridimensionabilità della finestra

        // Crea una nuova istanza di `Boot` (la classe principale del gioco) e avvia l'applicazione desktop
        new Lwjgl3Application(new Boot(), config);
    }
}

